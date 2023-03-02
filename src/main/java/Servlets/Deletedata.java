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

import org.json.JSONObject;

import Servlets.filmpojo;

/**
 * Servlet implementation class delrow_contents
 */
@WebServlet("/Deletedata")
public class Deletedata extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Deletedata() {
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

			// int filmid= Integer.parseInt(request.getParameter("film_id"));
			// System.out.println(filmid);
			List<filmpojo> data = new ArrayList<>();
			// Initialize the database
			String JDBC_DRIVER = "com.mysql.jdbc.Driver";
			String DB_URL = "jdbc:mysql://localhost:3306/sakila";
			String USER = "root";
			String PASS = "Yourconnectionpassword";
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = conn.createStatement();
			int flag = 0;
			int count = 0;

			StringBuilder sb = new StringBuilder();

			BufferedReader reader = request.getReader();
			try {
				String line;
				while ((line = reader.readLine()) != null) {
					sb.append(line);
				}
			} finally {
				reader.close();
			}

			System.out.println(sb);

			JSONObject json = new JSONObject(sb.toString());
			int arry = json.optJSONArray("film_id").length();
			System.out.println(arry);
			// ArrayList<Integer> id_arr= new ArrayList<Integer>(arry);

			PrintWriter out = response.getWriter();
			PreparedStatement ps = conn.prepareStatement("UPDATE film SET isDeleted = 1  WHERE film_id =?");
			for (int i = 0; i < arry; i++) {
				ps.setInt(1, json.optJSONArray("film_id").optInt(i));
				ps.executeUpdate();

				count++;
			}
			if (count >= 1) {
				out.println("DATA DELETION SUCESSFULL");
			} else {
				out.println("NO SUCH  film ID EXISTS");
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