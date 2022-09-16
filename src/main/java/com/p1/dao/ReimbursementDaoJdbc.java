package com.p1.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.p1.util.ConnectionUtil;
import com.p1.models.Reimbursement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ReimbursementDaoJdbc implements ReimbursementDao {
    static Logger logger = LogManager.getLogger(ReimbursementDao.class);

    @Override
    public List<Reimbursement> getReimbursementsBy(int getByType, String getByParam) {
        List<Reimbursement> reimbursements = new ArrayList<>();
        String statusOrName;
        if (getByType == 1) {
            statusOrName = "where reimb_status = " + "? ";
        } else if (getByType == 2) {
            statusOrName = "where eu.ers_username = " + "? ";
        } else {
            statusOrName = "";
        }

        try {
            Connection conn = ConnectionUtil.getConnection();

            String sql = "select reimb_id ,reimb_amount ,reimb_submitted ,reimb_resolved ,reimb_description ,reimb_receipt, "
                    +
                    "reimb_status, reimb_type, eu.ers_username, eu2.ers_username " +
                    "from " +
                    "ers_reimbursement er " +
                    "left outer join ers_reimbursement_status ers on " +
                    "ers.reimb_status_id = er.reimb_status_id " +
                    "left outer join ers_reimbursement_type ert on " +
                    "er.reimb_type_id = ert.reimb_type_id " +
                    "left outer join (select ers_users_id, ers_username from ers_users) eu on " +
                    "er.reimb_author = eu.ers_users_id " +
                    "left outer join  (select ers_users_id, ers_username from ers_users) eu2 on er.reimb_resolver = eu.ers_users_id "
                    + statusOrName + "order by reimb_id desc;";

            PreparedStatement ps = conn.prepareStatement(sql);
            if (statusOrName != "") {
                ps.setString(1, getByParam);
            }
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                reimbursements.add(new Reimbursement(
                        rs.getInt(1),
                        rs.getDouble(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getBoolean(6),
                        rs.getString(9),
                        rs.getString(10),
                        rs.getString(7),
                        rs.getString(8)));
            }

            conn.close();

        } catch (SQLException e) {
            logger.error("Sql Exception Occured", e);
        }

        return reimbursements;
    }

    @Override
    public void createNewReimbursementReport(Reimbursement newReimbursement, int authorId, int reimbType) {
        try {
            Connection conn = ConnectionUtil.getConnection();

            String sql = "insert into ers_reimbursement (reimb_amount, reimb_description, reimb_author, reimb_status_id, reimb_type_id) values (?, ?, ?,?,?);";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setDouble(1, newReimbursement.getAmount());
            ps.setString(2, newReimbursement.getDescription());
            ps.setInt(3, authorId);
            ps.setInt(4, 1);
            ps.setInt(5, reimbType);

            ps.executeUpdate();

            conn.close();

        } catch (SQLException e) {
            logger.error("Sql Exception Occured", e);
        }

    }

    @Override
    public void resolveReimbursement(int id, int resolver, int newStatus) {

        try {
            Connection conn = ConnectionUtil.getConnection();

            String sql = "update ers_reimbursement set reimb_status_id = ?, reimb_resolved = current_timestamp, reimb_resolver = ? where reimb_id = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, newStatus);
            ps.setInt(2, resolver);
            ps.setInt(3, id);

            ps.executeUpdate();

            conn.close();

        } catch (SQLException e) {
            logger.error("Sql Exception Occured", e);
        }
    }

    @Override
    public int getTypeId(String reimbType) {
        Integer id = 0;
        try {
            Connection conn = ConnectionUtil.getConnection();

            String sql = "select reimb_type_id from ers_reimbursement_type where reimb_type = ?";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, reimbType);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                id = rs.getInt(1);
            }

            conn.close();

        } catch (SQLException e) {
            logger.error("Sql Exception Occured", e);
        }
        return id;
    }

}
