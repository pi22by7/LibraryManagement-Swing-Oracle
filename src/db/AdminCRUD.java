package db;

import models.Admin;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AdminCRUD {
    public boolean isValidAdmin(Admin admin)
    {
        String sql = "select admin_id, password from ADMIN where admin_id = ? and password = ?";

        try(Connection conn = DBConnectionManager.getConnection())
        {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, admin.getId());
            ps.setString(2, admin.getPassword());
            ResultSet rs = ps.executeQuery();
            while(rs != null && rs.next())
            {
                if(rs.getString(1).equals(admin.getId()) && rs.getString(2).equals(admin.getPassword()))
                    return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(AdminCRUD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}
