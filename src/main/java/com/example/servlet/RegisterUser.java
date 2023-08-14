package com.example.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@WebServlet("/registration")
public class RegisterUser extends HttpServlet {

	private static final String query="insert into users(uname,upad,uemail,umobile)values (?,?,?,?)";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PrintWriter pw=resp.getWriter();
		resp.setContentType("text/html");
		String name=req.getParameter("name");
		String password=req.getParameter("password");
		String email=req.getParameter("email");
		String number=req.getParameter("number");
		
		
		try {
		Class.forName("com.mysql.cj.jdbc.Driver");
		}catch(ClassNotFoundException cnf){
			cnf.printStackTrace();
		}
		try(Connection con=DriverManager.getConnection("jdbc:mysql:///book","root","password");
				PreparedStatement ps=con.prepareStatement(query);){
				ps.setString(1, name);
				ps.setString(2, password);
				ps.setString(3, email);
				ps.setString(4, number);
				
				int count=ps.executeUpdate();
				if(count==1) {
					pw.write("<h1>You have  registered successfully");
					pw.write("<a href='login.html'>Login</a>");
					
				}
				else {
					pw.write("<h1>something went wrong</h1>");
				}
				
		}catch(SQLException se) {
			se.printStackTrace();
			pw.print("<h1>"+se.getMessage()+"</h1>");
		}
		
		}
	
	@Override
		protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			doGet(req,resp);
		}
	}
