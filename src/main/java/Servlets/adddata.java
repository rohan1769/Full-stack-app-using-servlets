package Servlets;

import java.io.BufferedReader;
import java.io.IOException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import java.time.ZoneId;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class addrow_contents
 */
@WebServlet("/adddata")
public class adddata extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static ZoneId defaultZoneId;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public adddata() {
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

		try {

			// String JDBC_DRIVER = "com.mysql.jdbc.Driver";

			String title = request.getParameter("title");
			String des = request.getParameter("description");

			/*
			 * SimpleDateFormat sdfmt7= new SimpleDateFormat("yyyy"); java.util.Date
			 * year1=sdfmt7.parse(request.getParameter("release_year")); java.sql.Date
			 * year2=new java.sql.Date( year1.getTime());
			 */
			int year2 = Integer.parseInt(request.getParameter("release_year"));

			String lang = request.getParameter("language");
			String special = request.getParameter("special_features");
			String rating = request.getParameter("rating");
			String director = request.getParameter("director");

			String DB_URL = "jdbc:mysql://localhost:3306/sakila";
			String USER = "root";
			String PASS = "Yourconnectionpassword";
			Class.forName("com.mysql.cj.jdbc.Driver");
			defaultZoneId = ZoneId.systemDefault();
			Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
			// Create a SQL query to insert data into demo table
			// demo table consists of two columns, so two '?' is used
			String sql = "INSERT INTO film (title, description, release_year,language_id,special_features,rating,director)  VALUES (?,?,?, ?, ?, ?,?)";
			PreparedStatement statement = con.prepareStatement(sql);

			StringBuilder accept_data = new StringBuilder();

			BufferedReader reader = request.getReader();
			try {
				String line;
				while ((line = reader.readLine()) != null) {
					accept_data.append(line);
				}
			} finally {
				reader.close();
			}

			System.out.println(year2);

			// ( JSONObject json = new JSONObject(accept_data.toString());

			statement.setString(1, title);
			System.out.println(title);

			statement.setString(2, des);
			statement.setInt(3, year2);
			statement.setString(4, lang);
			statement.setString(5, special);
			statement.setString(6, rating);
			statement.setString(7, director);

			statement.executeUpdate();

			// Close all the connections
			statement.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}