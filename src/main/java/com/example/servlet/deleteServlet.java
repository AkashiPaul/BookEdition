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
@WebServlet("/deleteurl")
public class deleteServlet extends HttpServlet {
	private static final String query="Delete from BOOKDATA where id=?";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PrintWriter pw=resp.getWriter();
		resp.setContentType("text/html");
		int id=Integer.parseInt(req.getParameter("id"));
		
		try {
		Class.forName("com.mysql.cj.jdbc.Driver");
		}catch(ClassNotFoundException cnf){
			cnf.printStackTrace();
		}
		try(Connection con=DriverManager.getConnection("jdbc:mysql:///book","root","password");
				PreparedStatement ps=con.prepareStatement(query);){
				ps.setInt(1, id);
				int count=ps.executeUpdate();
				if(count==1) {
					pw.write("<h2>Record is deleted successfully</h2>");
				}
				else {
					pw.write("<h2>Record not found</h2>");
				}
				
		}catch(SQLException se) {
			se.printStackTrace();
			pw.print("<h1>"+se.getMessage()+"</h1>");
		}
		pw.write("<a href='home.html'>Home</a>");
		pw.write("<br>");
		pw.write("<a href='booklist'>Book list</a>");	
	}
	@Override
		protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			doGet(req,resp);
		}
	}




