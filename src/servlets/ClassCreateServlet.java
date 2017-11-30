
/* Scribe
 * 
 * Vincent Rodriguez
 * Michael Takla
 * Felipe Mansilla
 * Noah Feldman
 * 
 */

package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import backend.JDBCQuery;
import databaseObjects.User;

@WebServlet("/ClassCreateServlet")
public class ClassCreateServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

	public ClassCreateServlet() {
        super();
    }
	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String classname = request.getParameter("classname");
		boolean isPrivate = Boolean.parseBoolean(request.getParameter("privacy"));
		
		if (classname == null){
			request.setAttribute("error", "Error in ClassCreateServ: classname para null");
			request.getRequestDispatcher("jsp/Profile.jsp").forward(request, response);
			return;
		} else if (classname.equals("")){
			request.setAttribute("error", "Field cannot be empty");
			request.getRequestDispatcher("jsp/Profile.jsp").forward(request, response);
			return;
		}
		
		if(JDBCQuery.doesClassExist(classname)){
			request.setAttribute("error", "Class already exists");
			request.getRequestDispatcher("jsp/Profile.jsp").forward(request, response);
		}
		else{
			JDBCQuery.addClass(classname, isPrivate);
			int classID = JDBCQuery.getClassFromClassname(classname).getClassID();
			int userID = ((User)request.getSession().getAttribute("currUser")).getUserID();
			JDBCQuery.addEnrollment(classID, userID);
			response.sendRedirect("jsp/Profile.jsp");
		}
	}
}
