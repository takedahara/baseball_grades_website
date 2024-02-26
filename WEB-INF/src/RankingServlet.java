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
public class RankingServlet extends HttpServlet {

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

		out.println("<header class=\"header\">\n"
				+ "      <!-- ヘッダーロゴ -->\n"
				+ "      <div class=\"logo\">ハンバーガーメニュー</div>\n"
				+ "    \n"
				+ "      <!-- ハンバーガーメニュー部分 -->\n"
				+ "      <div class=\"nav\">\n"
				+ "    \n"
				+ "        <!-- ハンバーガーメニューの表示・非表示を切り替えるチェックボックス -->\n"
				+ "        <input id=\"drawer_input\" class=\"drawer_hidden\" type=\"checkbox\">\n"
				+ "    \n"
				+ "        <!-- ハンバーガーアイコン -->\n"
				+ "        <label for=\"drawer_input\" class=\"drawer_open\"><span></span></label>\n"
				+ "    \n"
				+ "        <!-- メニュー -->\n"
				+ "        <nav class=\"nav_content\">\n"
				+ "          <ul class=\"nav_list\">\n"
				+ "            <li class=\"nav_item\"><a href=\"\">メニュー1</a></li>\n"
				+ "            <li class=\"nav_item\"><a href=\"\">メニュー2</a></li>\n"
				+ "            <li class=\"nav_item\"><a href=\"\">メニュー3</a></li>\n"
				+ "          </ul>\n"
				+ "        </nav>\n"
				+ "   \n"
				+ "      </div>\n"
				+ "    </header>");


		
	
		
		
		
		

		out.println("<h2 style=\"background:#ff0000;color:#ffffff;\">打率ランキング</h2>");
		Connection conn = null;
		Statement stmt = null;
		try {
			Class.forName("org.postgresql.Driver");
			conn = DriverManager.getConnection("jdbc:postgresql://" + _hostname
					+ ":5432/" + _dbname, _username, _password);
			stmt = conn.createStatement();

			out.println("<table border=\"1\">");
			out.println("<tr><th>選手名</th><th>打率</th></tr>");

			ResultSet rs = stmt.executeQuery("SELECT  player AS player,\n"
					+ "  (1.0*SUM(hits)/SUM(times_at_bat) ) AS Daritsu\n"
					+ "   FROM grades_of_player  \n"
					+ "GROUP BY player ORDER BY daritsu DESC ;");
			while (rs.next()) {
				
				String player = rs.getString("player");
				Float daritsu = rs.getFloat("daritsu");
				

				out.println("<tr>");
				out.println("<td><a href=\"item?pid=" + player + "\">" + player
						+ "</a></td>");
		
				
				out.println("<td>" + daritsu + "</td>");
				
				
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
		
		
		out.println("<h2 style=\"background:#0000ff;color:#ffffff;\">本塁打ランキング</h2>");
		Connection conns = null;
		Statement stmts = null;
		try {
			Class.forName("org.postgresql.Driver");
			conns = DriverManager.getConnection("jdbc:postgresql://" + _hostname
					+ ":5432/" + _dbname, _username, _password);
			stmt = conns.createStatement();

			out.println("<table border=\"1\">");
			out.println("<tr><th>選手名</th><th>ホームラン</th></tr>");

			ResultSet rs = stmt.executeQuery("SELECT  player AS player,\n"
					+ "  SUM(homerun) AS Homerun"
					+ "   FROM grades_of_player  "
					+ "GROUP BY player ORDER BY homerun DESC ;");
			while (rs.next()) {
				
				String player = rs.getString("player");
				String homerun = rs.getString("homerun");
				

				out.println("<tr>");
				out.println("<td><a href=\"item?pid=" + player + "\">" + player
						+ "</a></td>");
		
				
				out.println("<td>" + homerun + "</td>");
				
				
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
		
		
		out.println("<h2 style=\"background:#008000;color:#ffffff;\">打点ランキング</h2>");
		Connection connt = null;
		Statement stmtt = null;
		try {
			Class.forName("org.postgresql.Driver");
			conn = DriverManager.getConnection("jdbc:postgresql://" + _hostname
					+ ":5432/" + _dbname, _username, _password);
			stmtt = conn.createStatement();

			out.println("<table border=\"1\">");
			out.println("<tr><th>選手名</th><th>打点</th></tr>");

			ResultSet rs = stmtt.executeQuery("SELECT  player AS player,\n"
					+ "  SUM(rbi) AS Daten\n"
					+ "   FROM grades_of_player  \n"
					+ "GROUP BY player ORDER BY daten DESC ;");
			while (rs.next()) {
				
				String player = rs.getString("player");
				String rbi = rs.getString("daten");
				

				out.println("<tr>");
				out.println("<td><a href=\"item?pid=" + player + "\">" + player
						+ "</a></td>");
		
				
				out.println("<td>" + rbi + "</td>");
				
				
				out.println("</tr>");
			}
			rs.close();

			out.println("</table>");

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (connt != null) {
					connt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		
		out.println("</body>");
		
		
		out.println(".header {\n"
				+ "  display: flex;\n"
				+ "  justify-content: space-between;\n"
				+ "  align-items: center;\n"
				+ "  padding: 0 20px;\n"
				+ "  background: #fff;\n"
				+ "}\n"
				+ "\n"
				+ ".logo {\n"
				+ "  font-size: 24px;\n"
				+ "}\n"
				+ "\n"
				+ "/* ここから下がハンバーガーメニューに関するCSS */\n"
				+ "  \n"
				+ "/* チェックボックスを非表示にする */\n"
				+ ".drawer_hidden {\n"
				+ "  display: none;\n"
				+ "}\n"
				+ "\n"
				+ "/* ハンバーガーアイコンの設置スペース */\n"
				+ ".drawer_open {\n"
				+ "  display: flex;\n"
				+ "  height: 60px;\n"
				+ "  width: 60px;\n"
				+ "  justify-content: center;\n"
				+ "  align-items: center;\n"
				+ "  position: relative;\n"
				+ "  z-index: 100;/* 重なり順を一番上にする */\n"
				+ "  cursor: pointer;\n"
				+ "}\n"
				+ "\n"
				+ "/* ハンバーガーメニューのアイコン */\n"
				+ ".drawer_open span,\n"
				+ ".drawer_open span:before,\n"
				+ ".drawer_open span:after {\n"
				+ "  content: '';\n"
				+ "  display: block;\n"
				+ "  height: 3px;\n"
				+ "  width: 25px;\n"
				+ "  border-radius: 3px;\n"
				+ "  background: #333;\n"
				+ "  transition: 0.5s;\n"
				+ "  position: absolute;\n"
				+ "}\n"
				+ "\n"
				+ "/* 三本線の一番上の棒の位置調整 */\n"
				+ ".drawer_open span:before {\n"
				+ "  bottom: 8px;\n"
				+ "}\n"
				+ "\n"
				+ "/* 三本線の一番下の棒の位置調整 */\n"
				+ ".drawer_open span:after {\n"
				+ "  top: 8px;\n"
				+ "}\n"
				+ "\n"
				+ "/* アイコンがクリックされたら真ん中の線を透明にする */\n"
				+ "#drawer_input:checked ~ .drawer_open span {\n"
				+ "  background: rgba(255, 255, 255, 0);\n"
				+ "}\n"
				+ "\n"
				+ "/* アイコンがクリックされたらアイコンが×印になように上下の線を回転 */\n"
				+ "#drawer_input:checked ~ .drawer_open span::before {\n"
				+ "  bottom: 0;\n"
				+ "  transform: rotate(45deg);\n"
				+ "}\n"
				+ "\n"
				+ "#drawer_input:checked ~ .drawer_open span::after {\n"
				+ "  top: 0;\n"
				+ "  transform: rotate(-45deg);\n"
				+ "}\n"
				+ "  \n"
				+ "/* メニューのデザイン*/\n"
				+ ".nav_content {\n"
				+ "  width: 100%;\n"
				+ "  height: 100%;\n"
				+ "  position: fixed;\n"
				+ "  top: 0;\n"
				+ "  left: 100%; /* メニューを画面の外に飛ばす */\n"
				+ "  z-index: 99;\n"
				+ "  background: #fff;\n"
				+ "  transition: .5s;\n"
				+ "}\n"
				+ "\n"
				+ "/* メニュー黒ポチを消す */\n"
				+ ".nav_list {\n"
				+ "  list-style: none;\n"
				+ "}\n"
				+ "\n"
				+ "/* アイコンがクリックされたらメニューを表示 */\n"
				+ "#drawer_input:checked ~ .nav_content {\n"
				+ "  left: 0;/* メニューを画面に入れる */\n"
				+ "}");
		
		
		
		out.println("</html>");
		
		

		
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	public void destroy() {
	}

}