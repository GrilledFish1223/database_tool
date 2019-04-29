package com.unis.db.common.utils;

import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author  xuli
 * @date 2019/4/17
 */
@Configuration
public class HikariUtils {
    private static final Logger logger = LoggerFactory.getLogger(HikariUtils.class);

    private static HikariDataSource hikariDataSource;

    /**
     * get connection from hikari
     */
    public static Connection getConnection() {
        while (true) {
            try {
                return hikariDataSource.getConnection();
            } catch (SQLException e) {
                logger.error("HikariUtils getConnection() error : {}", e.getMessage());
            }
        }
    }

    /**
     * return connection to hikari
     */
    public static void releaseConnection(Connection connection) {
        if (null != connection) {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.error("HikariUtils releaseConnection(Connection) error : {}", e.getMessage());
            }
        }
    }

    /**
     * evict hikari underlying connection
     */
    public static void evictConnection(Connection connection) {
        hikariDataSource.evictConnection(connection);
    }

    @Autowired
    public void setHikariDataSource(HikariDataSource hikariDataSource) {
        HikariUtils.hikariDataSource = hikariDataSource;
    }
}
