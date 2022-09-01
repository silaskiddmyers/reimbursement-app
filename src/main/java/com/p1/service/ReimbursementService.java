package com.p1.service;

import java.util.List;

import com.p1.dao.ReimbursementDao;
import com.p1.dao.ReimbursementDaoJdbc;
import com.p1.models.*;


public class ReimbursementService {
    ReimbursementDao jdbc = new ReimbursementDaoJdbc();
    
    public List<Reimbursement> retrieveReimbursementsBy(int getType, String getParam) {
        return jdbc.getReimbursementsBy(getType, getParam);
    }

    public void createNewReimbursementReport(Reimbursement newReimbursement, int authorId, int reimbType) {
        jdbc.createNewReimbursementReport(newReimbursement, authorId, reimbType);
    }

    public void resolveReimbursement(int id, int resolver, int newStatus) {
        jdbc.resolveReimbursement(id, resolver, newStatus);
    }

    public int getTypeId(String reimbType) {
        return jdbc.getTypeId(reimbType);
    }

}
