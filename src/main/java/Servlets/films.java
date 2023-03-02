package Servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.InputMismatchException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import Servlets.filmpojo;

/**
 * Servlet implementation class serv1
 * 
 * @param <RequestDispatcher>
 * @param <HttpServletRequest>
 * @param <HttpServletResponse>
 */
@WebServlet("/films")
public class films extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public films() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	// int num=0;
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// doGet(request, response);

		int num = Integer.parseInt(request.getParameter("start"));
		int lim = Integer.parseInt(request.getParameter("limit"));
		Connection con = null;
		@SuppressWarnings("unused")
		Statement stmt = null;
		ArrayList<filmpojo> custList = new ArrayList<>();
		@SuppressWarnings("unused")
		String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";

		try {
			Class.forName(JDBC_DRIVER);
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());

			String mysqlUrl = "jdbc:mysql://localhost:3306/sakila";
			con = DriverManager.getConnection(mysqlUrl, "root","singhrohan");
			stmt = con.createStatement();
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

			System.out.println(accept_data);

			// JSONObject json = new JSONObject(accept_data.toString());
			// int num1=json.getInt("start");
			// String num=Integer.toString(num1);
			// int num2=json.getInt("limit");
			// String lim=Integer.toString(num2);

			// String query="SELECT * from film limit " + num + ",20;";
			// System.out.println(query);

			ResultSet rs = con.createStatement()
					.executeQuery("SELECT * from film WHERE isDeleted = 0 limit " + num + "," + lim + "; ");
			// num+=20;
			System.out.println(num);
			while (rs.next()) {
				filmpojo custObj = new filmpojo();
				custObj.setFilm_id(rs.getInt("film_id"));
				custObj.setTitle(rs.getString("title"));
				custObj.setDescription(rs.getString("description"));
				custObj.setRelease(rs.getInt("release_year"));
				custObj.setLanguage(rs.getString("language_id"));
				custObj.setDirector(rs.getString("director"));
				custObj.setRating(rs.getString("rating"));
				custObj.setFeatures(rs.getString("special_features"));

				custList.add(custObj);

			}

			System.out.println(custList);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (InputMismatchException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}

		finally {
			try {
				if (con != null)
					con.close();

			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
		GsonBuilder gsonBuilder = new GsonBuilder();
		int j = 1001;
		Gson gson = gsonBuilder.create();
		String json = new Gson().toJson(custList);
		String a = "\"films\" :" + json;
		String b = "{\"total\":" + j + "," + a + "}";
		// return b;
		// String JSONObject = gson.toJson(custList);

		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		out.print(b);
		out.flush();
	}

}