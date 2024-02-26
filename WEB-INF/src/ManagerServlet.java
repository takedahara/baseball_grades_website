import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class ManagerServlet extends HttpServlet {

	private String _hostname = null;
	private String _dbname = null;
	private String _username = null;
	private String _password = null;

	public void init() throws ServletException {
		// iniファイルから自分のデータベース情報を読み込む
		String iniFilePath = getServletConfig().getServletContext()
				.getRealPath("WEB-INF/le4db.ini");
		try {
			FileInputStream fis = new FileInputStream(iniFilePath);
			Properties prop = new Properties();
			prop.load(fis);
			_hostname = prop.getProperty("hostname");
			_dbname = prop.getProperty("dbname");
			_username = prop.getProperty("username");
			_password = prop.getProperty("password");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		String pid = request.getParameter("pid");

		out.println("<html>");
		out.println("<body>");

		
		
		
		
		
		
		out.println("<h2 style=\"background:#008000;color:#ffffff;\">監督情報</h2>");
		Connection conns = null;
		Statement stmts = null;
		try {
			Class.forName("org.postgresql.Driver");
			conns = DriverManager.getConnection("jdbc:postgresql://" + _hostname
					+ ":5432/" + _dbname, _username, _password);
			stmts = conns.createStatement();

			out.println("<table border=\"1\">");
			out.println("<tr><th>監督名</th><th>年齢</th><th>就任年</th></tr>");

			ResultSet rs = stmts.executeQuery("SELECT * FROM information_of_manager WHERE manager LIKE '%" + pid + "%'");
			while (rs.next()) {
				
				String manager = rs.getString("manager");
				String age = rs.getString("age");
				String year_of_appointment = rs.getString("year_of_appointment");
				
				

				out.println("<tr>");
				
				out.println("<td>" + manager + "</td>");
				out.println("<td>" + age + "</td>");
				out.println("<td>" + year_of_appointment + "</td>");
				
		
				
				out.println("</tr>");
			}
			rs.close();

			out.println("</table>");

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (conns != null) {
					conns.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		
		
		
		
		
		
		
		
		out.println("<h2 style=\"background:#800080;color:#ffffff;\">監督情報更新</h2>");
		Connection conn = null;
		Statement stmt = null;
		try {
			Class.forName("org.postgresql.Driver");
			conn = DriverManager.getConnection("jdbc:postgresql://" + _hostname
					+ ":5432/" + _dbname, _username, _password);
			stmt = conn.createStatement();

			out.println("<form action=\"managerupdate\" method=\"GET\">");
			out.println("監督名： " + pid);
			
			out.println("<br/>");
			
			ResultSet rs = stmt.executeQuery("SELECT * FROM information_of_manager WHERE manager LIKE '%" + pid + "%'");
			while (rs.next()) {
				String manager = rs.getString("manager");
				int age= rs.getInt("age");
				int year_of_appointment= rs.getInt("year_of_appointment");
				
			
				out.println("<input type=\"hidden\" name=\"update_manager\" value=\"" + manager + "\"/>");
				
				
			
				out.println("年齢： ");
				out.println("<input type=\"text\" name=\"update_age\" value=\"" + age + "\"/>");
				out.println("<br/>");
				out.println("就任年： ");
				out.println("<input type=\"text\" name=\"update_year_of_appointment\" value=\"" + year_of_appointment + "\"/>");
				out.println("<br/>");
				
				
				
				
			}
			rs.close();
			
			out.println("<input type=\"submit\" value=\"更新\"/>");
			out.println("</form>");

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	public void destroy() {
	}

}