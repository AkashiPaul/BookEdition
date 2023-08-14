package com.example.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
@WebServlet("/login")
public class LoginUser extends HttpServlet {
	private static final String query="select * from users where uname=? and upad=?";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PrintWriter pw=resp.getWriter();
		resp.setContentType("text/html");
		String name=req.getParameter("name");
		String password=req.getParameter("password");
		
		
		RequestDispatcher dispatcher=null;
		try {
		Class.forName("com.mysql.cj.jdbc.Driver");
		}catch(ClassNotFoundException cnf){
			cnf.printStackTrace();
		}
		try(Connection con=DriverManager.getConnection("jdbc:mysql:///book","root","password");
				PreparedStatement ps=con.prepareStatement(query);){
				ps.setString(1, name);
				ps.setString(2, password);
				
				HttpSession session=req.getSession();
				ResultSet rs=ps.executeQuery();
				if(rs.next()) {
					//session.setAttribute("name", rs.getString("name"));
					//session.setAttribute("password", rs.getString("password"));
					dispatcher=req.getRequestDispatcher("home.html");
				}
				else {
					req.setAttribute("status", "failed");
					pw.write("<h1>Pasword or username does not match</h1>");
					dispatcher=req.getRequestDispatcher("login.html");
				}
				dispatcher.forward(req, resp);
				
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


