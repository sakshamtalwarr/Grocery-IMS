package com.ims.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/TestServlet")
public class TestServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String testData = request.getParameter("test_data");
        
        System.out.println("--- TEST SERVLET ---");
        System.out.println("Data received: [" + testData + "]");
        System.out.println("--------------------");
        
        response.setContentType("text/plain");
        response.getWriter().write("Check the Eclipse console for output.");
    }
}