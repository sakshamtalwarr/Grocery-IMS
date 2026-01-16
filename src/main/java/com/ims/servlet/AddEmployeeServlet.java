	package com.ims.servlet;
	
	import com.google.gson.Gson;
	import com.ims.dao.EmployeeDao;
	import com.ims.model.Employee;
	import jakarta.servlet.ServletException;
	import jakarta.servlet.annotation.MultipartConfig;
	import jakarta.servlet.annotation.WebServlet;
	import jakarta.servlet.http.HttpServlet;
	import jakarta.servlet.http.HttpServletRequest;
	import jakarta.servlet.http.HttpServletResponse;
	import java.io.IOException;
	import java.sql.SQLException;
	
	@WebServlet("/AddEmployeeServlet")
	@MultipartConfig 
	public class AddEmployeeServlet extends HttpServlet {
	    private static final long serialVersionUID = 1L;
	    private final EmployeeDao employeeDao = new EmployeeDao();
	    private final Gson gson = new Gson();
	    @Override
	    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        response.setContentType("application/json");
	        response.setCharacterEncoding("UTF-8");
	
	        // --- DEBUGGING LINES ---
	        System.out.println("--- ADD EMPLOYEE ATTEMPT ---");
	        System.out.println("Received name: [" + request.getParameter("name") + "]");
	        System.out.println("Received username: [" + request.getParameter("username") + "]");
	        System.out.println("Received email: [" + request.getParameter("email") + "]");
	        System.out.println("Received password: [" + request.getParameter("password") + "]");
	        System.out.println("Received role: [" + request.getParameter("role") + "]");
	        System.out.println("Received phone_number: [" + request.getParameter("phone_number") + "]");
	        System.out.println("Received salary: [" + request.getParameter("salary") + "]");
	        System.out.println("-----------------------------");
	        // --- END DEBUGGING ---
	
	        try {
	            Employee employee = new Employee();
	            employee.setUsername(request.getParameter("username"));
	            employee.setName(request.getParameter("name"));
	            employee.setEmail(request.getParameter("email"));
	            employee.setPassword(request.getParameter("password"));
	            employee.setRole(request.getParameter("role"));
	            employee.setPhoneNumber(request.getParameter("phone_number"));
	            employee.setStatus("Active");
	
	            // Safely parse the salary
	            String salaryParam = request.getParameter("salary");
	            if (salaryParam != null && !salaryParam.trim().isEmpty()) {
	                employee.setSalary(Double.parseDouble(salaryParam));
	            } else {
	                employee.setSalary(0.0); // Default to 0 if empty
	            }
	
	            employeeDao.addEmployee(employee);
	            response.getWriter().write(gson.toJson(new ActionResponse("success", "Employee added successfully.")));
	
	        } catch (SQLException e) {
	            if (e.getErrorCode() == 1062) {
	                response.setStatus(HttpServletResponse.SC_CONFLICT);
	                response.getWriter().write(gson.toJson(new ActionResponse("error", "Username or Email already exists.")));
	            } else {
	                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	                response.getWriter().write(gson.toJson(new ActionResponse("error", "Database error occurred.")));
	                e.printStackTrace();
	            }
	        } catch (Exception e) {
	            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	            response.getWriter().write(gson.toJson(new ActionResponse("error", "Invalid data provided.")));
	            e.printStackTrace();
	        }
	    }
	
	    private static class ActionResponse {
	        String status;
	        String message;
	        public ActionResponse(String s, String m) { status = s; message = m; }
	    }
	}