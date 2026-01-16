package com.ims.servlet;

import com.google.gson.Gson;
import com.ims.dao.PurchaseOrderDao;
import com.ims.model.Employee;
import com.ims.model.PurchaseOrder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.text.SimpleDateFormat;

@WebServlet("/PlacePurchaseOrderServlet")
@MultipartConfig
public class PlacePurchaseOrderServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final PurchaseOrderDao poDao = new PurchaseOrderDao();
    private final Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("loggedInUser") == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        try {
            Employee employee = (Employee) session.getAttribute("loggedInUser");
            
            PurchaseOrder po = new PurchaseOrder();
            po.setProductId(Integer.parseInt(request.getParameter("productId")));
            po.setQuantityOrdered(Integer.parseInt(request.getParameter("quantity")));
            po.setSupplierName(request.getParameter("supplierName"));
            
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            po.setExpectedDelivery(sdf.parse(request.getParameter("expectedDelivery")));
            
            po.setEmployeeId(employee.getEmployeeId());
            po.setOrderDate(new java.util.Date()); // Set current date as order date
            po.setStatus("Pending");

            if (poDao.placeOrder(po)) {
                response.getWriter().write("{\"status\": \"success\", \"message\": \"Purchase order placed successfully.\"}");
            } else {
                throw new Exception("Failed to place order.");
            }

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"status\": \"error\", \"message\": \"Error placing order.\"}");
            e.printStackTrace();
        }
    }
}