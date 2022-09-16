package com.p1;

import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;

import com.p1.controllers.ReimbursementController;
import com.p1.controllers.SessionController;
import com.p1.controllers.UserController;

public final class App {
    private App() {
    }

    public static void main(String[] args) {
        UserController userController = new UserController();
        ReimbursementController reimbursementController = new ReimbursementController();
        SessionController sessionController = new SessionController();

        Javalin app = Javalin.create(config -> {
            // Location.CLASSPATH is referencing the resources folder for maven
            config.addStaticFiles("/frontend", Location.CLASSPATH);
        }).start(9000);

        // login endpoints
        app.get("/api/session", sessionController::checkSession);
        app.post("/api/session", sessionController::login);
        app.delete("/api/session", sessionController::logout);

        // reimbursement endpoints
        app.get("/api/reimbursement/retrieveType={retrieveType}&retrieveParam={retrieveParam}",
                reimbursementController::retrieveReimbursementsBy);
        app.post("/api/reimbursement", reimbursementController::createNewReimbursementReport);
        app.patch("/api/reimbursement/reimbId={reimbId}&newStatus={newStatus}",
                reimbursementController::resolveReimbursement);

        // other userendpoints
        app.get("/api/reimbursementTypes", userController::getAllReimbTypes);

    }
}
