package com.example.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@WebServlet("/register")
public class RegistrationServlet extends HttpServlet {
	private static final String query="insert into BOOKDATA(BOOKNAME,BOOKEDITION,BOOKPRICe)values (?,?,?)";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PrintWriter pw=resp.getWriter();
		resp.setContentType("text/html");
		String bookName=req.getParameter("bookName");
		String bookedition=req.getParameter("bookedition");
		Float bookPrice=Float.parseFloat(req.getParameter("bookPrice"));
		
		try {
		Class.forName("com.mysql.cj.jdbc.Driver");
		}catch(ClassNotFoundException cnf){
			cnf.printStackTrace();
		}
		try(Connection con=DriverManager.getConnection("jdbc:mysql:///book","root","password");
				PreparedStatement ps=con.prepareStatement(query);){
				ps.setString(1, bookName);
				ps.setString(2, bookedition);
				ps.setFloat(3, bookPrice);
				int count=ps.executeUpdate();
				if(count==1) {
					pw.write("<h1>Record entered successfully");
				}
				else {
					pw.write("<h1>something went wrong</h1>");
				}
				
		}catch(SQLException se) {
			se.printStackTrace();
			pw.print("<h1>"+se.getMessage()+"</h1>");
		}
		pw.write("<a href='home.html'>Home</a>");
		}
	@Override
		protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			doGet(req,resp);
		}
	}

