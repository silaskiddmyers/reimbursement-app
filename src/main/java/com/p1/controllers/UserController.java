package com.p1.controllers;

import com.p1.service.UserService;

import io.javalin.http.Context;

import java.util.List;

import com.p1.models.JsonResponse;
import com.p1.models.ReimbType;

public class UserController {
    UserService service = new UserService();

    public void getAllReimbTypes(Context ctx) {
        List<ReimbType> items = service.getReimbTypes();

        ctx.json(new JsonResponse(true, "retrieving all reimbursement types ", items));
    }

}
