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
@WebServlet ("/editScreen")
public class EditScreen extends HttpServlet {

	private static final String query="SELECT id,BOOKNAME,BOOKEDITION,BOOKPRICE FROM BOOKDATA WHERE id=?";

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
				ResultSet rs=ps.executeQuery();
				rs.next();
				pw.println("<form action='editurl?id="+id+"' method='post'>");
			pw.println("<table>");
			pw.println("<tr>");
			pw.println("<td>Book Name</td>");
			pw.println("<td><input type='text' name='bookName' value='"+rs.getString(2)+"'>");
			pw.println("</tr>");
			pw.println("<tr>");
			pw.println("<td>Book Edition</td>");
			pw.println("<td><input type='text' name='bookedition' value='"+rs.getString(3)+"'>");
			pw.println("</tr>");
			pw.println("<tr>");
			pw.println("<td>Book Price</td>");
			pw.println("<td><input type='text' name='bookPrice' value='"+rs.getFloat(4)+"'>");
			pw.println("</tr>");
			pw.println("<tr>");
			pw.println("<td><input type='submit' value='edit'</td>");
			pw.println("</tr>");
			pw.println("<tr>");
			pw.println("<td><input type='reset' value='cancel'</td>");
			pw.println("</tr>");
			pw.println("</table>");
			pw.println("</form>");
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


