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

@WebServlet("/booklist")
public class BookListServlet extends HttpServlet {
	private static final String query="SELECT id,BOOKNAME,BOOKEDITION,BOOKPRICE FROM BOOKDATA";

@Override
protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	PrintWriter pw=resp.getWriter();
	resp.setContentType("text/html");
	
	
	try {
	Class.forName("com.mysql.cj.jdbc.Driver");
	}catch(ClassNotFoundException cnf){
		cnf.printStackTrace();
	}
	try(Connection con=DriverManager.getConnection("jdbc:mysql:///book","root","password");
			PreparedStatement ps=con.prepareStatement(query);){
			ResultSet rs=ps.executeQuery();
			pw.println("<table border='1' align='center'>");
			pw.println("<tr>");
			pw.println("<th>Book Id</th>");
			pw.println("<th>Book Name</th>");
			pw.println("<th>Book Edition</th>");
			pw.println("<th>Book Price</th>");
			pw.println("<th>Edit</th>");
			pw.println("<th>Delete</th>");
			pw.println("</tr>");
			
			while(rs.next()) {
				pw.println("<tr>");
				pw.println("<td>"+rs.getInt(1)+"</td>");
				pw.println("<td>"+rs.getString(2)+"</td>");
				pw.println("<td>"+rs.getString(3)+"</td>");
				pw.println("<td>"+rs.getFloat(4)+"</td>");
				pw.println("<td><a href='editScreen?id="+rs.getInt(1)+"'>EDIT</a></td>");
				pw.println("<td><a href='deleteurl?id="+rs.getInt(1)+"'>DELETE</a></td>");
				pw.println("</tr>");
				
			}
			pw.println("</table>");
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

