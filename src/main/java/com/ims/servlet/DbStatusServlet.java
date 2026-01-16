package com.ims.servlet;

import com.google.gson.Gson;
import com.ims.util.DatabaseConnectionUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;

@WebServlet("/DbStatusServlet")
public class DbStatusServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final Gson gson = new Gson();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        StatusResponse statusResponse;

        try (Connection conn = DatabaseConnectionUtil.getConnection()) {
            if (conn != null) {
                // If connection is successful, create a success response
                statusResponse = new StatusResponse("success", "Database Connected Successfully");
                // The try-with-resources statement will automatically close the connection
            } else {
                // This case might happen if getConnection returns null without an exception
                statusResponse = new StatusResponse("error", "Failed to establish database connection.");
            }
        } catch (Exception e) {
            // If any exception occurs during connection, create an error response
            statusResponse = new StatusResponse("error", "Database Connection Failed: " + e.getMessage());
            e.printStackTrace();
        }

        // Send the JSON response back to the client
        response.getWriter().write(gson.toJson(statusResponse));
    }

    // Helper class for a structured JSON response
    private static class StatusResponse {
        String status;
        String message;

        public StatusResponse(String status, String message) {
            this.status = status;
            this.message = message;
        }
    }
}