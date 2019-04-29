package com.unis.db.common.thread;

import com.unis.db.common.utils.JdbcUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.Callable;
/**
 * @author xuli
 * @date 2019/3/12
 */
public class ThreadSql implements Callable {

    private String sql;

    public ThreadSql(String sql){
        this.sql=sql;
    }

    @Override
    public Boolean call() {
        try {
            Connection conn= JdbcUtils.getConnection();
            Statement stmt=conn.createStatement();
            Boolean bl = stmt.execute(sql);
            JdbcUtils.release(conn,stmt);
            return bl;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
