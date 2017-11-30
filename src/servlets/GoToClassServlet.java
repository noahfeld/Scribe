
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

@WebServlet("/GoToClassServlet")
public class GoToClassServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public GoToClassServlet() {
        super();
    }

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String classroom = request.getParameter("classroom");
		request.getSession().setAttribute("currClass", JDBCQuery.getClassFromClassname(classroom));
		response.sendRedirect("jsp/ClassPage.jsp");
	}
}
