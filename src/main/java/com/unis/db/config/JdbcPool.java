package com.unis.db.config;


import com.unis.db.common.enums.DatabaseTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.io.InputStream;
import java.io.PrintWriter;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Properties;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author xuli
 * @date 2019/2/12
 */
public class JdbcPool implements DataSource {
    /**
     * list Connections
     * 使用LinkedList集合来存放数据库链接，
     * 由于要频繁读写List集合，所以这里使用LinkedList存储数据库连接比较合适
     */
    private static LinkedList<Connection> listConnections = new LinkedList<Connection>();
    private final ReentrantLock lock = new ReentrantLock();
    private static final Logger logger = LoggerFactory.getLogger(JdbcPool.class);
    //private Condition condition = lock.newCondition();

    static {
        Properties prop = new Properties();
        try {
            InputStream in = JdbcPool.class.getClassLoader().getResourceAsStream("application.properties");
            prop.load(in);
            String databaseType = prop.getProperty("generate.database-type");
            if (!DatabaseTypeEnum.typeExists(databaseType)) {
                logger.error("* * * * [ INVALID DATABASE TYPE ]: {} * * * * ", databaseType.toUpperCase());
            } else {
                logger.info("* * * * [ DATABASE TYPE ]: {} * * * * ", databaseType.toUpperCase());
            }
            in = JdbcPool.class.getClassLoader().getResourceAsStream("db.properties");
            prop.load(in);
            String driver = prop.getProperty("pg_driver");
            String url = prop.getProperty("pg_url");
            String username = prop.getProperty("pg_username");
            String password = prop.getProperty("pg_password");
            if (DatabaseTypeEnum.GP.getType().equals(databaseType)) {
                driver = prop.getProperty("gp_driver");
                url = prop.getProperty("gp_url");
                username = prop.getProperty("gp_username");
                password = prop.getProperty("gp_password");
            }
            //数据库连接池的初始化连接数大小
            int jdbcPoolInitSize = Integer.parseInt(prop.getProperty("jdbcPoolInitSize"));
            //加载数据库驱动
            Class.forName(driver);
            for (int i = 0; i < jdbcPoolInitSize; i++) {
                Connection conn = DriverManager.getConnection(url, username, password);
                //将获取到的数据库连接加入到listConnections集合中，listConnections集合此时就是一个存放了数据库连接的连接池
                listConnections.add(conn);
            }
        } catch (Exception e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    @Override
    public Connection getConnection() {
        //如果数据库连接池中的连接对象的个数大于0
        if (listConnections.size() > 0) {
            lock.lock();
            //从listConnections集合中获取一个数据库连接
            final Connection conn = listConnections.removeFirst();
            lock.unlock();
            //listConnections数据库连接池空闲大小是listConnections.size()
            return (Connection) Proxy.newProxyInstance(JdbcPool.class.getClassLoader(), new Class[]{Connection.class}, new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args)
                        throws Throwable {
                    String inner = "close";
                    if (!method.getName().equals(inner)) {
                        return method.invoke(conn, args);
                    } else {
                        listConnections.add(conn);
                        //被还给listConnections数据库连接池大小为listConnections.size())
                        return null;
                    }
                }
            });
        } else {
            return null;
        }
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return null;
    }

    @Override
    public <T> T unwrap(Class<T> iface) {
        return null;
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) {
        return false;
    }

    @Override
    public PrintWriter getLogWriter() {
        return null;
    }

    @Override
    public void setLogWriter(PrintWriter out) {

    }

    @Override
    public void setLoginTimeout(int seconds) {

    }

    @Override
    public int getLoginTimeout() {
        return 0;
    }

    @Override
    public java.util.logging.Logger getParentLogger() {
        return null;
    }
}
