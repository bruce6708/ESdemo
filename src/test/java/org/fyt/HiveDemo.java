package org.fyt;
import java.sql.*;

public class HiveDemo {

    //Hive2 Driver class name
    private static String driverName = "org.apache.hive.jdbc.HiveDriver";

    public static void main(String[] args) throws SQLException {
        try {
            Class.forName(driverName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }

        //Hive2 JDBC URL with LDAP
        String jdbcURL = "jdbc:inceptor2://192.168.31.101:10000/default";

        String user = "hive";
        String password = "123456";
        Connection conn = DriverManager.getConnection(jdbcURL, user, password);
        Statement  stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("select * from t_test");
        ResultSetMetaData rsmd = rs.getMetaData();
        int size = rsmd.getColumnCount();
        while(rs.next()) {
            StringBuffer value = new StringBuffer();
            for(int i = 0; i < size; i++) {
                value.append(rs.getString(i+1)).append("\t");
            }
            System.out.println(value.toString());
        }
        rs.close();
        stmt.close();
        conn.close();
    }
}