
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

@WebServlet("/LoginGuestServlet")
public class LoginGuestServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	public LoginGuestServlet() {
        super();
    }

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getSession().setAttribute("currUser", null);
		request.getSession().setAttribute("signedIn", false);
		response.sendRedirect("jsp/Profile.jsp");
	}
}
