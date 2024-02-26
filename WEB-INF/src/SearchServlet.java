import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
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
public class SearchServlet extends HttpServlet {

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

		String searchName = request.getParameter("search_player");

		out.println("<html>");
		out.println("<body>");

		out.println("<h3 style=\"background:#ff0000;color:#ffffff;\">成績検索結果</h3>");
		out.println("検索選手：" + searchName);
		
		
		
		
		
		
		
		Connection conns = null;
		Statement stmts = null;
		try {
			Class.forName("org.postgresql.Driver");
			conns = DriverManager.getConnection("jdbc:postgresql://" + _hostname
					+ ":5432/" + _dbname, _username, _password);
			stmts = conns.createStatement();

			out.println("<table border=\"1\">");
			out.println("<tr><th>選手名</th><th>打率</th></tr>");

			ResultSet rs = stmts
					.executeQuery("SELECT  player AS player,\r\n"
							+ "  (1.0*SUM(hits)/SUM(times_at_bat) ) AS Daritsu"
							+ "   FROM grades_of_player  WHERE player LIKE '%"+searchName+"%'"
							+ "GROUP BY player");
			while (rs.next()) {
				String player = rs.getString("player");
				Float date = rs.getFloat("daritsu");
				

				out.println("<tr>");
				out.println("<td>" + player + "</td>");
				out.println("<td>" + date + "</td>");
				
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

		
		
		
		Connection connss = null;
		Statement stmtss = null;
		try {
			Class.forName("org.postgresql.Driver");
			connss = DriverManager.getConnection("jdbc:postgresql://" + _hostname
					+ ":5432/" + _dbname, _username, _password);
			stmtss = connss.createStatement();

			out.println("<table border=\"1\">");
			out.println("<tr><th>選手名</th><th>ホームラン</th></tr>");

			ResultSet rs = stmtss
					.executeQuery("SELECT  player AS player,\r\n"
							+ "  SUM(homerun)  AS Homerun"
							+ "   FROM grades_of_player  WHERE player LIKE '%"+searchName+"%'"
							+ "GROUP BY player");
			while (rs.next()) {
				String player = rs.getString("player");
				String date = rs.getString("homerun");
				

				out.println("<tr>");
				out.println("<td>" + player + "</td>");
				out.println("<td>" + date + "</td>");
				
				out.println("</tr>");
			}
			rs.close();

			out.println("</table>");

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (connss != null) {
					connss.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		
		
		
		
		
		
		
		Connection connsst = null;
		Statement stmtsst = null;
		try {
			Class.forName("org.postgresql.Driver");
			connsst = DriverManager.getConnection("jdbc:postgresql://" + _hostname
					+ ":5432/" + _dbname, _username, _password);
			stmtsst = connsst.createStatement();

			out.println("<table border=\"1\">");
			out.println("<tr><th>選手名</th><th>打点</th></tr>");

			ResultSet rs = stmtsst
					.executeQuery("SELECT  player AS player,\r\n"
							+ "  SUM(rbi) AS Rbi"
							+ "   FROM grades_of_player  WHERE player LIKE '%"+searchName+"%'"
							+ "GROUP BY player");
			while (rs.next()) {
				String player = rs.getString("player");
				String date = rs.getString("rbi");
				

				out.println("<tr>");
				out.println("<td>" + player + "</td>");
				out.println("<td>" + date + "</td>");
				
				out.println("</tr>");
			}
			rs.close();

			out.println("</table>");

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (connsst != null) {
					connsst.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		
		
		
		
		
		
		
		Connection conn = null;
		Statement stmt = null;
		try {
			Class.forName("org.postgresql.Driver");
			conn = DriverManager.getConnection("jdbc:postgresql://" + _hostname
					+ ":5432/" + _dbname, _username, _password);
			stmt = conn.createStatement();

			out.println("<table border=\"1\">");
			out.println("<tr><th>選手名</th><th>日付</th><th>打数</th><th>ヒット数</th><th>ホームラン数</th><th>打点</th></tr>");

			ResultSet rs = stmt
					.executeQuery("SELECT * FROM grades_of_player WHERE player LIKE '%"
							+ searchName + "%'");
			while (rs.next()) {
				String player = rs.getString("player");
				Date date = rs.getDate("date");
				int times_at_bat = rs.getInt("times_at_bat");
				int hits = rs.getInt("hits");
				int homerun= rs.getInt("homerun");
				int rbi = rs.getInt("rbi");

				out.println("<tr>");
				out.println("<td>" + player + "</td>");
				out.println("<td>" + date + "</td>");
				out.println("<td>" + times_at_bat + "</td>");
				out.println("<td>" + hits + "</td>");
				out.println("<td>" + homerun + "</td>");
				out.println("<td>" + rbi + "</td>");
				out.println("</tr>");
			}
			rs.close();

			out.println("</table>");

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
		
		
		
		
		
		
		
		out.println("<h3 style=\"background:#808080;color:#ffffff;\">成績追加</h3>");
		out.println("<form action=\"addseiseki\" method=\"GET\">");
		out.println("選手名： ");
		out.println("<input type=\"text\" name=\"add_player\"/>");
		out.println("<br/>");
		out.println("日付： ");
		out.println("<input type=\"text\" name=\"add_date\"/>");
		out.println("<br/>");
		out.println("打数： ");
		out.println("<input type=\"text\" name=\"add_times_at_bat\"/>");
		out.println("<br/>");
		out.println("ヒット数： ");
		out.println("<input type=\"text\" name=\"add_hits\"/>");
		out.println("<br/>");
		out.println("ホームラン： ");
		out.println("<input type=\"text\" name=\"add_homerun\"/>");
		out.println("<br/>");
		out.println("打点： ");
		out.println("<input type=\"text\" name=\"add_rbi\"/>");
		out.println("<br/>");
		out.println("<input type=\"submit\" value=\"追加\"/>");
		out.println("</form>");

		out.println("</body>");
		out.println("</html>");
	
		
		
		
		
		
		
		
		
		
		
		
		
		

		out.println("<br/>");
		out.println("<a href=\"list\">トップページに戻る</a>");

		out.println("</body>");
		out.println("</html>");
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	public void destroy() {
	}

}
