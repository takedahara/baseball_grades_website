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
public class ListServlet extends HttpServlet {

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

		out.println("<html>");
		out.println("<body>");

		out.println("<h3 style=\"background:#808000;color:#ffffff;\">成績検索</h3>");
		out.println("<form action=\"search\" method=\"GET\">");
		out.println("選手名： ");
		out.println("<input type=\"text\" name=\"search_player\"/>");
		out.println("<br/>");
		out.println("<input type=\"submit\" value=\"検索\"/>");
		out.println("</form>");
		
		
		out.println("<h3 style=\"background:#008000;color:#ffffff;\">選手情報検索</h3>");
		out.println("<form action=\"item\" method=\"GET\">");
		out.println("選手名： ");
		out.println("<input type=\"text\" name=\"pid\"/>");
		out.println("<br/>");
		out.println("<input type=\"submit\" value=\"検索\"/>");
		out.println("</form>");
		
		
		
		
		

		out.println("<h3 style=\"background:#000080;color:#ffffff;\">選手一覧</h3>");
		Connection conn = null;
		Statement stmt = null;
		try {
			Class.forName("org.postgresql.Driver");
			conn = DriverManager.getConnection("jdbc:postgresql://" + _hostname
					+ ":5432/" + _dbname, _username, _password);
			stmt = conn.createStatement();

			out.println("<table border=\"1\">");
			out.println("<tr><th>選手名</th><th>チーム名</th><th>年齢</th><th>守備位置</th><th>背番号</th></tr>");

			ResultSet rs = stmt.executeQuery("SELECT * FROM information_of_player");
			while (rs.next()) {
				
				String player = rs.getString("player");
				String team = rs.getString("team");
				String age = rs.getString("age");
				String position = rs.getString("position");
				String uniform_number=rs.getString("uniform_number");

				out.println("<tr>");
				out.println("<td><a href=\"item?pid=" + player + "\">" + player
						+ "</a></td>");
		
				out.println("<td><a href=\"team?team=" + team + "\">" + team
						+ "</a></td>");
				out.println("<td>" + age + "</td>");
				out.println("<td>" + position + "</td>");
				out.println("<td>" + uniform_number + "</td>");
				
				
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

		out.println("<h3 style=\"background:#008080;color:#ffffff;\">追加</h3>");
		out.println("<form action=\"add\" method=\"GET\">");
		out.println("選手名： ");
		out.println("<input type=\"text\" name=\"add_player\"/>");
		out.println("<br/>");
		out.println("チーム名： ");
		out.println("<input type=\"text\" name=\"add_team\"/>");
		out.println("<br/>");
		out.println("年齢： ");
		out.println("<input type=\"text\" name=\"add_age\"/>");
		out.println("<br/>");
		out.println("守備位置： ");
		out.println("<input type=\"text\" name=\"add_position\"/>");
		out.println("<br/>");
		out.println("背番号： ");
		out.println("<input type=\"text\" name=\"add_uniform_number\"/>");
		out.println("<br/>");
		out.println("<input type=\"submit\" value=\"追加\"/>");
		out.println("</form>");

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
