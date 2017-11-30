
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
import java.nio.file.Paths;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import backend.JDBCQuery;
import databaseObjects.Classroom;
import databaseObjects.User;

@WebServlet("/FileUploadServlet")
@MultipartConfig
public class FileUploadServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

	public FileUploadServlet() {
        super();
    }
	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Part filePart = request.getPart("file");
	    String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
	    JDBCQuery.addDocument(((User)request.getSession().getAttribute("currUser")).getUserID(), fileName, filePart);
	    JDBCQuery.addUpload(((Classroom)request.getSession().getAttribute("currClass")).getClassID(), JDBCQuery.getDocumentByDocumentname(fileName).getDocID());
	    response.sendRedirect("jsp/ClassPage.jsp");
	}
}
