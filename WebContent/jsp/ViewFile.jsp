
<!-- Scribe
  -
  - Vincent Rodriguez
  - Micahel Takla
  - Felipe Mansilla
  - Noah Feldman
  -
  -->

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="backend.JDBCQuery"%>
<%@ page import="databaseObjects.UserDocument"%>
<%@ page import="java.sql.Blob"%>
<%@ page import="java.sql.Statement"%>
<%@ page import="java.sql.ResultSet"%>
<%@ page import="java.sql.SQLException"%>
<%@ page import="java.sql.Connection"%>
<%@ page import="java.io.OutputStream"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>File</title>
	</head>
	<body>
		<%  
            String id = request.getParameter("id");
            Blob file = null;
            byte[ ] fileData = null;
            try
            {    
            	UserDocument doc = JDBCQuery.getDocumentFromID(Integer.parseInt(id));
            	switch(doc.getExtension())
            	{
            		case "mp4": response.setContentType("video/mp4");
            			break;
            		case "mp3": response.setContentType("audio/mpeg");
            			break;
            		case "pdf": response.setContentType("application/pdf");
            			break;
            		case "jpg": response.setContentType("image/gif");
            			break;
            	}
                response.setHeader("Content-Disposition", "inline");
                file = doc.getBlob();
                fileData = file.getBytes(1,(int)file.length());
                response.setContentLength(fileData.length);
                OutputStream output = response.getOutputStream();
                output.write(fileData);
                output.flush();
                output.close();
            } catch (SQLException sqle) {System.out.println("sqle: " + sqle.getMessage());} 
        %>
	</body>
</html>