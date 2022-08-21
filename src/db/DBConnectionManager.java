package db;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;
import java.sql.*;

public class DBConnectionManager {

    static
    {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {

        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:oracle:thin:@//localhost:1521/LIBRARY", "SYSTEM", "root");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
}

//import java.sql.*;
//class DBConnectionManager{
//    public static void main(String[] args){
//        try{
//            //step1 load the driver class
//            Class.forName("oracle.jdbc.driver.OracleDriver");
//
//            //step2 create  the connection object
//            Connection con=DriverManager.getConnection(
//                    "jdbc:oracle:thin:@localhost:1521:xe","system","oracle");
//
//            //step3 create the statement object
//            Statement stmt=con.createStatement();
//
//            //step4 execute query
//            ResultSet rs=stmt.executeQuery("select * from ADMIN");
//            while(rs.next())
//                System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3));
//
//            //step5 close the connection object
//            con.close();
//
//        }catch(Exception e){ System.out.println(e);}
//
//    }
//}