package com.servlet.register;

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
public class RegisterServlet extends HttpServlet {
	private static final String SELECT_ALL_QUERY = "SELECT * FROM USER";
	private static final String UPDATE_ALL_QUERY = "UPDATE USER SET NAME=?, CITY=?, MOBILE=?, DOB=?";
	private static final String DELETE_ALL_QUERY = "DELETE FROM USER";

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		PrintWriter pw = res.getWriter();
		res.setContentType("text/html");

		String action = req.getParameter("action");

		switch (action) {
		case "create":
			create(req, pw);
			break;
		case "read":
			readAll(pw);
			break;
		case "update":
			updateAll(req, pw);
			break;
		case "delete":
			deleteAll(pw);
			break;
		default:
			pw.println("Invalid action");
		}

		pw.close();
	}

	private void create(HttpServletRequest req, PrintWriter pw) {
		// Similar to the previous create method, you can create a new user
		// No need for a user ID parameter in this case
	}

	private void readAll(PrintWriter pw) {
		try (Connection con = DriverManager.getConnection("jdbc:mysql:///firstdb", "root", "root");
				PreparedStatement ps = con.prepareStatement(SELECT_ALL_QUERY);
				ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				int userId = rs.getInt("ID");
				String name = rs.getString("NAME");
				String city = rs.getString("CITY");
				String mobile = rs.getString("MOBILE");
				String dob = rs.getString("DOB");

				pw.println("User ID: " + userId);
				pw.println("Name: " + name);
				pw.println("City: " + city);
				pw.println("Mobile: " + mobile);
				pw.println("DOB: " + dob);
				pw.println("<br>");
			}
		} catch (SQLException se) {
			pw.println(se.getMessage());
			se.printStackTrace();
		} catch (Exception e) {
			pw.println(e.getMessage());
			e.printStackTrace();
		}
	}

	private void updateAll(HttpServletRequest req, PrintWriter pw) {
		String name = req.getParameter("name");
		String city = req.getParameter("city");
		String mobile = req.getParameter("mobile");
		String dob = req.getParameter("dob");

		try (Connection con = DriverManager.getConnection("jdbc:mysql:///firstdb", "root", "root");
				PreparedStatement ps = con.prepareStatement(UPDATE_ALL_QUERY)) {

			ps.setString(1, name);
			ps.setString(2, city);
			ps.setString(3, mobile);
			ps.setString(4, dob);

			int count = ps.executeUpdate();

			pw.println(count + " users updated successfully");
		} catch (SQLException se) {
			pw.println(se.getMessage());
			se.printStackTrace();
		} catch (Exception e) {
			pw.println(e.getMessage());
			e.printStackTrace();
		}
	}

	private void deleteAll(PrintWriter pw) {
		try (Connection con = DriverManager.getConnection("jdbc:mysql:///firstdb", "root", "root");
				PreparedStatement ps = con.prepareStatement(DELETE_ALL_QUERY)) {

			int count = ps.executeUpdate();

			pw.println(count + " users deleted successfully");
		} catch (SQLException se) {
			pw.println(se.getMessage());
			se.printStackTrace();
		} catch (Exception e) {
			pw.println(e.getMessage());
			e.printStackTrace();
		}
	}
}
