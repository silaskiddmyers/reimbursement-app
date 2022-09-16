package com.p1.controllers;

import java.util.List;

import com.p1.models.JsonResponse;
import com.p1.models.Reimbursement;
import com.p1.models.User;
import com.p1.service.ReimbursementService;

import io.javalin.http.Context;

public class ReimbursementController {
    ReimbursementService rs = new ReimbursementService();

    public void retrieveReimbursementsBy(Context ctx) {
        User user = ctx.sessionAttribute("user");
        if (user == null) {
            ctx.json(new JsonResponse(false, "cant retrieve items because no session found", null));
            return;
        }
        Integer retrieveByType = Integer.parseInt(ctx.pathParam("retrieveType"));
        String retrieveParam = ctx.pathParam("retrieveParam");
        List<Reimbursement> items = rs.retrieveReimbursementsBy(retrieveByType, retrieveParam);

        ctx.json(new JsonResponse(true, "retrieving all reimbursements for " + user.getFirstName(), items));

    }

    public void createNewReimbursementReport(Context ctx) {
        Reimbursement reimb = ctx.bodyAsClass(Reimbursement.class);
        User user = ctx.sessionAttribute("user");
        reimb.setReimbAuthor(user.getUsername());
        rs.createNewReimbursementReport(reimb, user.getUserId(), rs.getTypeId(reimb.getReimbType()));
    }

    public void resolveReimbursement(Context ctx) {
        User user = ctx.sessionAttribute("user");
        if (user == null) {
            ctx.json(new JsonResponse(false, "cant resolve because no session found", null));
            return;
        }
        Integer reimbId = Integer.parseInt(ctx.pathParam("reimbId"));
        Integer newStatus = Integer.parseInt(ctx.pathParam("newStatus"));
        rs.resolveReimbursement(reimbId, user.getUserId(), newStatus);

        ctx.json(new JsonResponse(true, "reimbursement resolved if exists", null));
    }
}
