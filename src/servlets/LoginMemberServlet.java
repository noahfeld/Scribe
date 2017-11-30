
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

@WebServlet("/LoginMemberServlet")
public class LoginMemberServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public LoginMemberServlet() {
        super();
    }

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		//Error Checking
		if (username == null){
			request.setAttribute("error", "Error in RegisterServ: username para null");
			request.getRequestDispatcher("jsp/Welcome.jsp").forward(request, response);
			return;
		}
		else if (password == null){
			request.setAttribute("error", "Error in RegisterServ: password para null");
			request.getRequestDispatcher("jsp/Welcome.jsp").forward(request, response);
			return;
		} else if (username.equals("") || password.equals("")){
			request.setAttribute("error", "Fields cannot be empty");
			request.getRequestDispatcher("jsp/Welcome.jsp").forward(request, response);
			return;
		}
		
		if (JDBCQuery.doesUserExist(username)){
			//correct password
			if (JDBCQuery.validate(username, password)){
				request.getSession().setAttribute("currUser", JDBCQuery.getUserByUsername(username));
				request.getSession().setAttribute("signedIn", true);
				response.sendRedirect("jsp/Profile.jsp");
			}
			//incorrect password
			else{
				request.setAttribute("error", "Incorrect password");
				request.getRequestDispatcher("jsp/Welcome.jsp").forward(request, response);
			}
		}
		//invalid username
		else{
			request.setAttribute("error", "Incorrect username");
			request.getRequestDispatcher("jsp/Welcome.jsp").forward(request, response);
		}
	}
}