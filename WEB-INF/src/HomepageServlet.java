import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class HomepageServlet extends HttpServlet {

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
		out.println("<div style=\"background:#0000ff;color:#ffffff;\"></div>");
	
		out.println("<div style=\"background:#0000ff;color:#ffffff;\"></div>");

		out.println("<h1 style=\"background:#0000ff;color:#ffffff;\">野球成績検索サイト</h1>");
		out.println("<p>このサイトでは、野球選手の成績の検索やチームの検索を行うことができます。</p>");
		
		out.println("<h2 style=\"background:#800000;color:#ffffff;\\\">選手情報</h2>");
		out.println("<td><a href=\"list" +  "\">" +"選手情報"
				+ "</a></td>");
		
		
		out.println("<h2 style=\"background:#808000;color:#ffffff;\\\">チーム情報</h2>");
		out.println("<td><a href=\"teaminformation" +  "\">" +"チーム情報"
				+ "</a></td>");
		
		
		
		
		

		out.println("<h2 style=\"background:#800080;color:#ffffff;\\\">成績ランキング</h2>");
		out.println("<td><a href=\"ranking" +  "\">" +"成績ランキング"
				+ "</a></td>");

		
		
		
		
		
		
		
		
		
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	public void destroy() {
	}

}
