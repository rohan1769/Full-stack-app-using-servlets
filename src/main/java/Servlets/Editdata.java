package Servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Servlets.filmpojo;

/**
 * Servlet implementation class editrow_contents
 */
@WebServlet("/Editdata")
public class Editdata extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Editdata() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		Connection conn = null;
		Statement stmt = null;

		try {

			// String JDBC_DRIVER = "com.mysql.jdbc.Driver";

			int filmid = Integer.parseInt(request.getParameter("film_id"));

			System.out.println(filmid);
			String des = request.getParameter("description");
			System.out.println(request.getParameter("description"));
			/*
			 * SimpleDateFormat sdfmt7= new SimpleDateFormat("yyyy"); java.util.Date
			 * year1=sdfmt7.parse(request.getParameter("release_year")); java.sql.Date
			 * year2=new java.sql.Date( year1.getTime());
			 */
			// int year2= Integer.parseInt(request.getParameter("release_year"));

			// String lang= request.getParameter("language");
			// String special= request.getParameter("special_features");
			// String rating=request.getParameter("rating");
			String director = request.getParameter("director");
			System.out.println(request.getParameter("director"));

			List<filmpojo> data = new ArrayList<>();
			// Initialize the database
			// String JDBC_DRIVER = "com.mysql.jdbc.Driver";
			String DB_URL = "jdbc:mysql://localhost:3306/sakila";
			String USER = "root";
			String PASS = "Yourconnectionpassword";
			Class.forName("com.mysql.cj.jdbc.Driver");

			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = conn.createStatement();

			/*
			 * StringBuilder sb = new StringBuilder();
			 *
			 * BufferedReader reader = request.getReader(); try { String line; while ((line
			 * = reader.readLine()) != null) { sb.append(line); } } finally {
			 * reader.close(); }
			 *
			 * System.out.println(sb);
			 *
			 * JSONObject json = new JSONObject(sb.toString());
			 *
			 * int num2=json.getInt("docID"); String invoice_id=Integer.toString(num2);
			 *
			 * long x= Long.parseLong(invoice_id);
			 */

			ResultSet rs = stmt.executeQuery("SELECT * FROM film");

			int count = 0;

			while (rs.next()) {
				filmpojo OP = new filmpojo();
				OP.setFilm_id(rs.getInt("film_id"));
				data.add(OP);
				// System.out.println(OP);
			}

			PrintWriter out = response.getWriter();

			for (filmpojo obj : data) {
				// int x= obj.getFilm_id();
				if (filmid == obj.getFilm_id()) {

					// System.out.println("yes");
					count++;
				}
			}
			System.out.println(count);
			if (count == 1) {
				PreparedStatement ps = conn.prepareStatement("update film set director = ? where film_id = ?");
				ps.setString(1, director);
				ps.setInt(2, filmid);
				ps.executeUpdate();

				PreparedStatement ps1 = conn.prepareStatement("update film set description=? where film_id =?");
				ps1.setString(1, des);
				ps1.setInt(2, filmid);
				ps1.executeUpdate();

				out.println("UPDATE EXECUTED SUCESSFULLY");
			} else {
				out.println("NO SUCH film ID EXISTS");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (InputMismatchException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		} finally {
			try {
				if (conn != null)
					conn.close();

			} catch (SQLException se) {
				se.printStackTrace();
			}
		}

	}
}