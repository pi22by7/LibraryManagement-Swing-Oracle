package db;
import java.sql.Connection;

class TestDBConnection {

    public static void main(String[] args) {
        Connection c = DBConnectionManager.getConnection();
        if(c != null) {
            System.out.println("Connection Successful");
        }
    }
}
