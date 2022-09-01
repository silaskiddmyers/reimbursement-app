package com.p1.dao;

import java.util.List;

import com.p1.models.Reimbursement;

public interface ReimbursementDao {
    /**
     * The reason this takes in so many different parameters and has many different cases is because it consolidates three different functions.
     * To avoid copying and pasting, especially since the SQL query being executed is very long, this should be easier to test
     * 
     * @param getByType is either 0, 1 or 2: 1 means it searches by status, 2 means by name. 0 returns all reciepts
     * @param getByParam is either a status to search for or is a name to search for
     * @return List of reimbursements based on the parameters
     */
    List<Reimbursement> getReimbursementsBy(int getByType, String getByParam); 

    /**
     * Way less complicated thankfully
     * @param newReimbursement this is the reimbursement that will be created
     */
    void createNewReimbursementReport(Reimbursement newReimbursement,int authorId, int reimbType);

    /**
     * 
     * @param id- id of Reimbursement to be resolved
     * @param newStatus- gives new status of reimbursement
     */
    void resolveReimbursement(int id, int resolver,  int newStatus);

    int getTypeId(String reimbType);


}
