package com.unis.db.common.utils;

import com.unis.db.config.JdbcPool;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @author xuli
 * @date 2019/3/12
 */
public class JdbcUtils {
    /**
     * 数据库连接池
     */
    private static JdbcPool pool = new JdbcPool();

    /**
     * @return Connection数据库连接对象
     */
    public static Connection getConnection()  {
        return pool.getConnection();
    }

    /**
     * 释放资源
     *
     * @param conn Connection数据库连接对象
     */
    public static void release(Connection conn) {
        if (conn != null) {
            try {
                //关闭Connection数据库连接对象
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 释放资源
     *
     * @param conn Connection数据库连接对象
     * @param st   负责执行SQL命令的Statement对象
     */
    public static void release(Connection conn, Statement st) {
        if (st != null) {
            try {
                //关闭负责执行SQL命令的Statement对象
                st.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        release(conn);
    }

    /**
     * 释放资源
     *
     * @param conn Connection数据库连接对象
     * @param st   负责执行SQL命令的Statement对象
     * @param rs   存储查询结果的ResultSet对象
     */
    public static void release(Connection conn, Statement st, ResultSet rs) {
        if (rs != null) {
            try {
                //关闭存储查询结果的ResultSet对象
                rs.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        release(conn, st);
    }
}
