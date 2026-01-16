package com.ims.servlet;

import com.google.gson.Gson;
import com.ims.dao.PurchaseOrderDao;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/GetPurchaseOrdersServlet")
public class GetPurchaseOrdersServlet extends HttpServlet {
    private final PurchaseOrderDao poDao = new PurchaseOrderDao();
    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        try {
            response.getWriter().write(gson.toJson(poDao.getAllPurchaseOrders()));
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
    }
}