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
public class TeamServlet extends HttpServlet {

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
		
		String pid = request.getParameter("team");

		out.println("<html>");
		out.println("<body>");

		
		
		
		
		
		
		out.println("<h2 style=\"background:#000080;color:#ffffff;\">チーム情報</h2>");
		Connection conns = null;
		Statement stmts = null;
		try {
			Class.forName("org.postgresql.Driver");
			conns = DriverManager.getConnection("jdbc:postgresql://" + _hostname
					+ ":5432/" + _dbname, _username, _password);
			stmts = conns.createStatement();

			out.println("<table border=\"1\">");
			out.println("<tr><th>チーム名</th><th>勝</th><th>負</th><th>分</th><th>監督</th></tr>");

			ResultSet rs = stmts.executeQuery("SELECT * FROM information_of_team WHERE team LIKE '%"+pid+"%'");
			while (rs.next()) {
				
				String team = rs.getString("team");
				String win = rs.getString("win");
				String lose = rs.getString("lose");
				String draw = rs.getString("draw");
				String manager = rs.getString("manager");
				

				out.println("<tr>");
				
				out.println("<td>" + team + "</td>");
				out.println("<td>" + win + "</td>");
				out.println("<td>" + lose + "</td>");
				out.println("<td>" + draw + "</td>");
				out.println("<td><a href=\"manager?pid=" + manager + "\">" + manager
						+ "</a></td>");
		
				
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
		
		
		
		
		
		
		
		
		
		out.println("<h2 style=\"background:#ff00ff;color:#ffffff;\">成績検索</h2>");
		Connection conn = null;
		Statement stmt = null;
		try {
			Class.forName("org.postgresql.Driver");
			conn = DriverManager.getConnection("jdbc:postgresql://" + _hostname
					+ ":5432/" + _dbname, _username, _password);
			stmt = conn.createStatement();

			out.println("<form action=\"teamupdate\" method=\"GET\">");
			out.println("チーム名： " + pid);
			out.println("<input type=\"hidden\" name=\"update_pid\" + value=\"" + pid + "\"/>");
			out.println("<br/>");
			
			ResultSet rs = stmt.executeQuery("SELECT * FROM information_of_team WHERE team LIKE '%" + pid + "%'");
			while (rs.next()) {
				String name = rs.getString("team");
				int win= rs.getInt("win");
				int lose= rs.getInt("lose");
				int draw= rs.getInt("draw");
				String manager=rs.getString("manager");
				
				
				out.println("勝： ");
				out.println("<input type=\"text\" name=\"update_win\" value=\"" + win + "\"/>");
				out.println("<br/>");
				out.println("敗： ");
				out.println("<input type=\"text\" name=\"update_lose\" value=\"" + lose + "\"/>");
				out.println("<br/>");
				out.println("分： ");
				out.println("<input type=\"text\" name=\"update_draw\" value=\"" + draw + "\"/>");
				out.println("<br/>");
				out.println("監督名： ");
				out.println("<input type=\"text\" name=\"update_manager\" value=\"" + manager + "\"/>");
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
