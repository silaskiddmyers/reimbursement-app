package com.p1.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.p1.models.ReimbType;
import com.p1.models.User;
import com.p1.util.ConnectionUtil;

public class UserDaoJdbc implements UserDao{

    static Logger logger = LogManager.getLogger(UserDaoJdbc.class);
    
    @Override
    public User getUserByUsername(String username) {
        
        User user = null;
        
        try(Connection conn = ConnectionUtil.getConnection()) {

            String sql = "select ers_users_id ,ers_username , ers_password, user_first_name, user_last_name, user_email, eur.user_role from (select * from ers_users eu1 left outer join ers_user_role eur2 on eur2.ers_user_role_id = eu1.user_role_id) eur where ers_username =?;";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, username);

            ResultSet rs = ps.executeQuery();
            //int userId, String username, String password, String firstName, String lastName, String email, String userRole
            while(rs.next()){
                user = new User(
                    rs.getInt(1), 
                    rs.getString(2), 
                    rs.getString(3), 
                    rs.getString(4), 
                    rs.getString(5), 
                    rs.getString(6),
                    rs.getString(7));
            }
            
        } catch (SQLException e) {
            logger.error("Sql Exception Occured", e);
        }

        return user;
    }

    @Override
    public List<ReimbType> getReimbTypes() {

        List<ReimbType> types = new ArrayList<>();
        
        try(Connection conn = ConnectionUtil.getConnection()) {

            String sql = "select * from ers_reimbursement_type;";
            PreparedStatement ps = conn.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                types.add(new ReimbType(rs.getInt(1), rs.getString(2)));
            }
            
        } catch (SQLException e) {
            logger.error("Sql Exception Occured", e);
        }

        return types;
    }

    
}
