package ua.unlimit;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/UserServlet")
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Map<Integer, String> users = new ConcurrentHashMap <> ();
	private AtomicInteger counter;

	@Override
	public void init() throws ServletException {
		super.init();
		users.put(1, "Katia");
		users.put(2, "Katka");
		users.put(3, "Klai");
		counter = new AtomicInteger(3);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Integer id = Integer.parseInt(request.getParameter("id"));
		String user = users.get(id);
		if (user == null) {
			user = "";
		}
		
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println("<h3>user: " + user + "</h3><br>");
		out.print("<a href = 'javascript:history.back();'>Back</a>");
		out.close();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		String name = request.getParameter("name");
		Integer id = null;
		if (!users.containsValue(name)) {
			id = counter.incrementAndGet();
			users.put(id, name);
		}
		
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		if (id == null) {
			out.println("<h3>user " + name + " already exists</h3>");
		} else {
			out.println("<h3>user " + name + " was created with id = " + counter.get() + "</h3>");
		}
		out.print("<br>");
		out.println("<a href = 'http://localhost:8080/WebApp/user.html'>Main page</a>");
		out.close();
	}

}
