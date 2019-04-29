package com.unis.db.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.unis.db.common.enums.TableTypeEnum;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

/**
 * @author xuli
 * @date 2019/2/12
 */
public class TableUtils {
    private static final Logger logger = LoggerFactory.getLogger(TableUtils.class);

    /**
     * 传入sql 得到sql查询时间
     *
     * @param sql 查询sql
     * @return 查询时间
     */
    public static double getQueryTime(String sql) {
        long begin = System.currentTimeMillis();
        try {
            Connection connection = JdbcUtils.getConnection();
            Statement stmt = connection.createStatement();
            stmt.execute(sql);
            JdbcUtils.release(connection, stmt);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return (System.currentTimeMillis() - begin) / 1000.0;
    }

    /**
     * @param sql       sql
     * @param randomNum 随机数
     * @return 返回随机内容
     */
    public static String getRandom(String sql, int randomNum) {
        String random = "0";
        try {
            Connection connection = JdbcUtils.getConnection();
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs != null) {
                for (int i = 0; i <= randomNum; i++) {
                    if (rs.next()) {
                        random = rs.getString(1);
                    }
                }
                JdbcUtils.release(connection, stmt, rs);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return random;
    }


    /**
     * 单个查询条件
     *
     * @param tableName 表名
     * @param field     字段名
     * @param value     值
     */
    public static void query(String tableName, String field, String value) {
        String querySql = String.format("select * from %s where %s = '%s'", tableName, field, value);
        double cost = getQueryTime(querySql);
        logger.info("[ QUERY {} ]:{} finished with {}s", field.toUpperCase(), querySql, cost);
    }

    /**
     * @param tableName 表名
     * @param field     字段名
     * @param value     值
     * @param flag      标识
     */
    public static void query(String tableName, String field, String value, boolean flag) {
        if (flag) {
            //查询表的数据总量
            int count = searchTotal(tableName);
            String querySql = String.format("select * from %s where %s = '%s'", tableName, field, value);
            double cost = getQueryTime(querySql);
            logger.info("[ QUERY {} WHILE INSERTING DATA]:{} finished with {}s ,and the amount of data in the {} is {}"
                    , field.toUpperCase(), querySql, cost, tableName, count);
        } else {
            query(tableName, field, value);
        }
    }


    /**
     * select count(*) from tableName
     *
     * @param tableName 表名
     */
    public static int searchTotal(String tableName) {
        int count = 0;
        try {
            Connection connection = JdbcUtils.getConnection();
            Statement stmt = connection.createStatement();
            String querySql = String.format("select count(*) from  %s", tableName);
            String sql = "set enable_seqscan = on";
            stmt.execute(sql);
            ResultSet rs = stmt.executeQuery(querySql);
            if (rs != null) {
                if (rs.next()) {
                    count = rs.getInt(1);
                }
            }
            JdbcUtils.release(connection, stmt, rs);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return count;
    }


}
