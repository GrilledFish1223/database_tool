package com.unis.db.common.utils;


import org.postgresql.copy.CopyManager;
import org.postgresql.core.BaseConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author xuli
 * @date 2019/3/16
 */
public class CopyInUtils {

    private static final Logger logger = LoggerFactory.getLogger(CopyInUtils.class);

    public static boolean copyIn(String data, String tableName) {
        //默认按照逗号分隔
        String copyInSql = String.format("COPY %s FROM STDIN DELIMITER AS ','", tableName);
        //try-with-resource模式自动关流
        try (InputStream is = new ByteArrayInputStream(data.getBytes())) {
            return copyIn(is, copyInSql);
        } catch (IOException e) {
            logger.error("CopyInUtils copyIn() copyIn process IO error : {}", e);
        }
        return false;
    }

    private static boolean copyIn(InputStream is, String sql) {
        //从数据库连接池获取连接
        Connection connection = HikariUtils.getConnection();
        if (null == connection) {
            logger.error("copyIn contain db connection is null");
            return false;
        }
        try {
            //copy in
            CopyManager copyManager = new CopyManager((BaseConnection) connection.getMetaData().getConnection());
            long result = copyManager.copyIn(sql, is);
            if (result <= 0) {
                logger.error("copy in error return:" + result);
            } else {
                return true;
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            HikariUtils.releaseConnection(connection);
        }
        return false;
    }

    /**
     * 使用反射来拼接sql
     */
    public static String getSql(Object obj, String[] orderedFields) {
        Class clazz = obj.getClass();
        //获取所有方法，包括私有方法
        Method[] methods = clazz.getDeclaredMethods();
        List<String> orderedList = Arrays.asList(orderedFields);
        List<Method> fieldList = Arrays.asList(methods);
        //map相当于对每一个如下进行操作
        //filter进行过滤
        List<Object> data = orderedList.stream().map(name -> {
            Optional<Method> optional = fieldList.stream().filter(f -> f.getName().equalsIgnoreCase("get" + name)).findFirst();
            Object ret = "";
            if (optional.isPresent()) {
                try {
                    //调用invoke执行method(get+name) 返回结果
                    ret = optional.get().invoke(obj);
                    if (ret == null) {
                        ret = "";
                    }
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
            //得到的是数值
            return ret;
        }).collect(Collectors.toList());
        return data.stream().map(Object::toString).collect(Collectors.joining(","));
    }


}
