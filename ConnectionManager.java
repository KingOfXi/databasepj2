

import org.omg.CORBA.portable.ApplicationException;

import java.sql.*;

public class ConnectionManager {

    private static   String host = "localhost";
    private   static String dbname = "fun";
    private  static String user = "postgres";
    private  static  String pwd = "admin";
    private  static String port = "5432";
    //声明一个Connection类型的ThreadLocal
    private static ThreadLocal<Connection> connectionHolder = new ThreadLocal<Connection>();

    public static Connection getConnection() {
        //通过TheadLocal的get方法来获取当前线程连接对象的实例
        Connection conn = connectionHolder.get();
        if (conn == null) {
            try {
                //获取数据库驱动

                Class.forName("org.postgresql.Driver");
                String url = "jdbc:postgresql://" + host + ":" + port + "/" + dbname;
                conn= DriverManager.getConnection(url, user, pwd);
                //设置数据库连接对象
                connectionHolder.set(conn);
            } catch (Exception e) {
                System.out.println("connection has some problem");
            }
        }

        return conn;
    }

    //关闭数据库连接
    public static void closeConnection() {
        Connection conn = connectionHolder.get();

        if (conn != null) {
            try {
                conn.close();
                //从集合中清除
                connectionHolder.remove();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 手动提交事务
     *
     * @param conn
     */
    public static void beginTransaction(Connection conn) {
        try {
            if (conn != null) {
                if (conn.getAutoCommit()) {
                    conn.setAutoCommit(false);
                }
            }
        } catch (SQLException e) {
        }
    }

    /**
     * 提交事务
     *
     * @param conn
     */
    public static void commitTransaction(Connection conn) {
        try {
            if (conn != null) {
                if (!conn.getAutoCommit()) {
                    conn.commit();
                }
            }
        } catch (SQLException e) {
        }
    }

    /**
     * 回滚事务
     *
     * @param conn
     */
    public static void rollbackTransaction(Connection conn) {
        try {
            if (conn != null) {
                if (!conn.getAutoCommit()) {
                    conn.rollback();
                }
            }
        } catch (SQLException e) {
        }
    }

    /**
     * 重置connection状态
     *
     * @param conn
     */
    public static void resetConnection(Connection conn) {
        try {
            if (conn != null) {
                if (conn.getAutoCommit()) {
                    conn.setAutoCommit(false);
                } else {
                    conn.setAutoCommit(true);
                }
            }
        } catch (SQLException e) {
        }
    }


    //关闭preparedStatement
    public static void close(Statement pstmt) {
        if (pstmt != null) {
            try {
                pstmt.close();
            } catch (SQLException e) {

                e.printStackTrace();
            }
        }
    }

    //关闭ResultSet
    public static void close(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}