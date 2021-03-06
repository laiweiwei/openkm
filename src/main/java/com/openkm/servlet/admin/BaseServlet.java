package com.openkm.servlet.admin;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.openkm.core.Config;
import com.openkm.core.HttpSessionManager;

public class BaseServlet extends HttpServlet  {
	private static final long serialVersionUID = 1L;
	protected static final String METHOD_GET = "GET";
	protected static final String METHOD_POST = "POST";
    
	/**
	 * Dispatch errors 
	 */
	protected void sendErrorRedirect(HttpServletRequest request, HttpServletResponse response,
			Throwable e) throws ServletException, IOException {
		request.setAttribute ("javax.servlet.jsp.jspException", e);
		ServletContext sc = getServletConfig().getServletContext();
		sc.getRequestDispatcher("/error.jsp").forward(request, response);
	}
	
	/**
	 * Dispatch errors 
	 */
	protected void sendError(PrintWriter out, String msg) throws ServletException, IOException {
		out.println("<div class=\"error\">" + msg + "</div>");
		out.flush();
	}
	
	/**
	 * Update HTTP session manager
	 */
	public void updateSessionManager(HttpServletRequest request) {
		HttpSessionManager.getInstance().update(request.getSession().getId());
	}
	
	/**
	 * Test if an user can access to administration
	 */
	public static boolean isAdmin(HttpServletRequest request) {
		return request.isUserInRole(Config.DEFAULT_ADMIN_ROLE);
	}
	
	/**
	 * Print HTML page header
	 */
	public void header(PrintWriter out, String title, String[][] breadcrumb) {
		out.println("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");
		out.println("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">");
		out.println("<html xmlns=\"http://www.w3.org/1999/xhtml\">");
		out.println("<head>");
		out.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />");
		out.println("<link rel=\"Shortcut icon\" href=\"favicon.ico\" />");
		out.println("<link rel=\"stylesheet\" href=\"css/style.css\" type=\"text/css\" />");
		out.println("<script src=\"js/biblioteca.js\" type=\"text/javascript\"></script>");
		out.println("<script type=\"text/javascript\">scrollToBottom();</script>");
		out.println("<script type=\"text/javascript\" src=\"../js/jquery-1.7.1.min.js\"></script>");
		out.println("<script type=\"text/javascript\" src=\"js/jquery.DOMWindow.js\"></script>");
		out.println("<script type=\"text/javascript\">");
		out.println("$(document).ready(function() { $dm = $('.ds').openDOMWindow({");
		out.println("height:200, width:300, eventType:'click', overlayOpacity:'57', windowSource:'iframe', windowPadding:0");
		out.println("})});");
		out.println("function dialogClose() { $dm.closeDOMWindow(); }");
		out.println("function keepSessionAlive() { $.ajax({ type:'GET', url:'ping.html', cache:false, async:false }); }");
		out.println("window.setInterval('keepSessionAlive()', 60000);");
		out.println("</script>");
		out.println("<title>" + title + "</title>");
		out.println("</head>");
		out.println("<body>");
		out.println("<ul id=\"breadcrumb\">");
		
		for (String[] elto : breadcrumb) {
			out.println("<li class=\"path\">");
			out.print("<a href=\"" + elto[0] + "\">" + elto[1] + "</a>");
			out.print("</li>");
		}
		
		out.println("<li class=\"path\">" + title + "</li>");
		out.println("</ul>");
		out.println("<br/>");
	}
	
	/**
	 * Print HTML page footer
	 */
	public void footer(PrintWriter out) {
		out.println("</body>");
		out.println("</html>");
	}
	
	/**
	 * Print ok messages
	 */
	public void ok(PrintWriter out, String msg) {
		out.print("<div class=\"ok\">" + msg + "</div>");
	}
	
	/**
	 * Print warn messages
	 */
	public void warn(PrintWriter out, String msg) {
		out.print("<div class=\"warn\">" + msg + "</div>");
	}
}
