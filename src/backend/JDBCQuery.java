
/* Scribe
 * 
 * Vincent Rodriguez
 * Michael Takla
 * Felipe Mansilla
 * Noah Feldman 
 * 
 */

package backend;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.servlet.http.Part;

import org.eclipse.jdt.internal.compiler.ast.Statement;

import databaseObjects.ClassMessage;
import databaseObjects.Classroom;
import databaseObjects.User;
import databaseObjects.UserDocument;

public class JDBCQuery {

	private static Connection conn = null;
	private static ResultSet rs = null;
	private static PreparedStatement ps = null;
	private Statement stmt = null;

	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost/Scribe";

	// SELECT statements

	// Users
	private final static String selectUserByUsername = "SELECT * FROM Users WHERE username=?";
	private final static String selectUserByUserID = "SELECT * FROM Users WHERE userID=?";
	private final static String selectPassword = "SELECT password FROM Users WHERE username=?";

	// Classes
	private final static String selectClassByClassID = "SELECT * FROM Classes WHERE classID=?";
	private final static String selectClassByClassname = "SELECT * FROM Classes WHERE classname=?";

	// Documents
	private final static String getUserDocuments = "SELECT * FROM Documents WHERE userID=?";
	private final static String getDocumentByDocumentID = "SELECT * FROM Documents WHERE docID=?";
	private final static String getDocumentByDocumentname = "SELECT * FROM Documents WHERE documentname=?";

	// Enrollments
	private final static String getUsersEnrolledInClass = "SELECT * FROM Enrollment WHERE classID=?";
	private final static String getUserEnrollments = "SELECT * FROM Enrollment WHERE userID=?";
	private final static String getUserInClass = "SELECT * FROM Enrollment WHERE classID=? AND userID=?";

	// Uploads
	private final static String getClassUploads = "SELECT * FROM Uploads WHERE classID=?";

	// Messages
	private final static String getMessagesFromClass = "SELECT * FROM Messages WHERE classID=?";
	private final static String getMessageFromID = "SELECT * FROM Messages WHERE messageID=?";

	// Requests
	private final static String getUsersWithRequests = "SELECT * FROM Requests WHERE classID=? AND active=true";

	// INSERT statements

	// Users
	private final static String addUser = "INSERT INTO Users(username, password) VALUES(?, ?)";

	// Classes
	private final static String addClass = "INSERT INTO Classes(classname, private) VALUES(?,?)";

	// Documents
	private final static String addDocument = "INSERT INTO Documents(userID, documentname, file) VALUES(?, ?, ?)";

	// Enrollments
	private final static String addEnrollment = "INSERT INTO Enrollment(classID, userID) VALUES(?, ?)";

	// Uploads
	private final static String addUpload = "INSERT INTO Uploads(classID, docID) VALUES(?, ?)";

	// Messages
	private final static String addMessage = "INSERT INTO Messages(classID, userID, level, content) VALUES(?, ?, ?, ?)";

	// Requests
	private final static String addRequest = "INSERT INTO Requests(userID, classID, active) VALUES(?, ?, true)";

	// UPDATE statements

	// Users
	private final static String updateUserUsername = "UPDATE Users SET username=? WHERE userID=?";

	private final static String updateUserPassword = "UPDATE Users SET password=? WHERE userID=?";

	// Classes
	private final static String updateClassname = "UPDATE Classes SET classname=? WHERE classID=?";

	private final static String updateClassPrivacy = "UPDATE Classes SET private=? WHERE classID=?";

	// Messages
	private final static String updateMessage = "UPDATE Messages SET content=? WHERE messageID=?";

	// Requests
	private final static String updateRequest = "UPDATE Requests SET active=false WHERE classID=? AND userID=?";

	// Database credentials
	static final String USER = "root";
	static final String PASS = "root";

	public static void connect() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void close() {
		try {
			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (conn != null) {
				conn.close();
				conn = null;
			}
			if (ps != null) {
				ps = null;
			}
		} catch (SQLException sqle) {
			System.out.println("connection close error");
			sqle.printStackTrace();
		}
	}

	// USER METHODS

	/**
	 * Add a new user to database
	 * 
	 * @param username
	 * @param password
	 */
	public static void addUser(String username, String password) {
		connect();
		try {
			PreparedStatement ps = conn.prepareStatement(addUser);
			ps.setString(1, username);
			ps.setString(2, password);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
	}

	/**
	 * gets User object using username
	 * 
	 * @param username
	 * @return
	 */
	public static User getUserByUsername(String username) {
		connect();
		try {
			PreparedStatement ps = conn.prepareStatement(selectUserByUsername);
			ps.setString(1, username);
			ResultSet result = ps.executeQuery();
			while (result.next()) {
				return new User(result.getInt("userID"), result.getString("username"), result.getString("password"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return null;
	}

	/**
	 * Returns User object using userID
	 * 
	 * @param userID
	 * @return
	 */

	public static User getUserByUserID(int userID) {
		connect();
		try {
			PreparedStatement ps = conn.prepareStatement(selectUserByUserID);
			ps.setInt(1, userID);
			ResultSet result = ps.executeQuery();
			while (result.next()) {
				return new User(userID, result.getString("username"), result.getString("password"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return null;
	}

	/**
	 * does username currently exist
	 * 
	 * @param username
	 * @return true if username exists, else false
	 */
	public static boolean doesUserExist(String username) {
		connect();
		try {
			PreparedStatement ps = conn.prepareStatement(selectUserByUsername);
			ps.setString(1, username);
			ResultSet result = ps.executeQuery();
			while (result.next()) {
				return true;
			}
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return false;
	}

	/**
	 * validates password entry to username
	 * 
	 * @param username
	 * @param password
	 * @return true if passwords match, else false
	 */
	public static boolean validate(String username, String password) {
		connect();
		try {
			ps = conn.prepareStatement(selectPassword);
			ps.setString(1, username);
			rs = ps.executeQuery();
			System.out.println(rs);
			if (rs.next()) {
				if (password.equals(rs.getString("password"))) {
					return true;
				}
			}
		} catch (SQLException e) {
			System.out.println("SQLException in function \"validate\"");
			e.printStackTrace();
		} finally {
			close();
		}
		return false;
	}

	// user UPDATE methods

	public static void updateUserUsername(String newUsername) {
		connect();
		try {
			PreparedStatement ps = conn.prepareStatement(updateUserUsername);
			ps.setString(1, newUsername);
			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
	}

	public static void updateUserPassword(String newPassword) {
		connect();
		try {
			PreparedStatement ps = conn.prepareStatement(updateUserPassword);
			ps.setString(1, newPassword);
			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
	}

	// CLASS METHODS

	/**
	 * Add new class to Classes
	 * 
	 * @param classname
	 * @param isPrivate
	 */
	public static void addClass(String classname, boolean isPrivate) {
		connect();
		try {
			PreparedStatement ps = conn.prepareStatement(addClass);
			ps.setString(1, classname);
			ps.setBoolean(2, isPrivate);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
	}

	/**
	 * Does class exist?
	 * 
	 * @param classname
	 * @return true if yes, else no
	 */
	public static boolean doesClassExist(String classname) {
		connect();
		try {
			PreparedStatement ps = conn.prepareStatement(selectClassByClassname);
			ps.setString(1, classname);
			ResultSet result = ps.executeQuery();
			while (result.next()) {
				return true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return false;
	}

	/**
	 * Get classname using classID
	 * 
	 * @param classID
	 * @return
	 */
	public static Classroom getClassFromID(int classID) {
		connect();
		try {
			PreparedStatement ps = conn.prepareStatement(selectClassByClassID);
			ps.setInt(1, classID);
			ResultSet result = ps.executeQuery();
			while (result.next()) {
				return new Classroom(classID, result.getString("classname"), result.getBoolean("private"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return null;
	}

	/**
	 * returns Classroom object from clasname
	 * 
	 * @param classname
	 * @return
	 */
	public static Classroom getClassFromClassname(String classname) {
		connect();
		try {
			PreparedStatement ps = conn.prepareStatement(selectClassByClassname);
			ps.setString(1, classname);
			ResultSet result = ps.executeQuery();
			while (result.next()) {
				return new Classroom(result.getInt("classID"), result.getString("classname"),
						result.getBoolean("private"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return null;
	}

	/**
	 * Return whether class is private or not
	 * 
	 * @param classID
	 * @return true if private else false
	 */
	public static boolean isClassPrivate(int classID) {
		connect();
		try {
			PreparedStatement ps = conn.prepareStatement(selectClassByClassID);
			ps.setInt(1, classID);
			ResultSet result = ps.executeQuery();
			while (result.next()) {
				return result.getBoolean("private");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return false;
	}

	// class UPDATE methods

	/**
	 * Assume classID exists at this point Reverses class setting from private
	 * to public static or vice versa
	 * 
	 * @param classID
	 * @return
	 */
	public static void updateClassPrivacy(int classID) {
		connect();
		boolean newPrivacySetting = !(isClassPrivate(classID));
		try {
			PreparedStatement ps = conn.prepareStatement(updateClassPrivacy);
			ps.setBoolean(1, newPrivacySetting);
			ps.setInt(2, classID);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
	}

	public static void updateClassname(int classID, String newContent) {
		connect();
		try {
			PreparedStatement ps = conn.prepareStatement(updateClassname);
			ps.setString(1, newContent);
			ps.setInt(2, classID);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
	}

	// DOCUMENT METHODS

	public static void addDocument(int userID, String documentname, Part filePart) {
		connect();
		try {
			PreparedStatement ps = conn.prepareStatement(addDocument);
			ps.setInt(1, userID);
			ps.setString(2, documentname);
			InputStream inputstream = filePart.getInputStream();
			ps.setBinaryStream(3, inputstream, (int) filePart.getSize());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			close();
		}
	}

	public static UserDocument getDocumentByDocumentname(String documentname) {
		connect();
		try {
			PreparedStatement ps = conn.prepareStatement(getDocumentByDocumentname);
			ps.setString(1, documentname);
			ResultSet result = ps.executeQuery();
			while (result.next()) {
				Blob blob = result.getBlob("file");
				return new UserDocument(result.getInt("docID"), documentname, blob);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return null;
	}

	// TODO

	/**
	 * Returns the LONGBLOB file associated with a docID
	 * 
	 * @param docID
	 * @return
	 */
	public static UserDocument getDocumentFromID(int docID) {
		connect();
		try {
			PreparedStatement ps = conn.prepareStatement(getDocumentByDocumentID);
			ps.setInt(1, docID);
			ResultSet result = ps.executeQuery();
			while (result.next()) {
				Blob blob = result.getBlob("file");
				return new UserDocument(docID, result.getString("documentname"), blob);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return null;
	}

	// TODO

	/**
	 * return vector off all docIDs associated with a given userID
	 */
	private static Vector<Integer> getUserDocuments2(int userID) {
		connect();
		Vector<Integer> userDocuments = new Vector<Integer>();
		try {
			PreparedStatement ps = conn.prepareStatement(getUserDocuments);
			ps.setInt(1, userID);
			ResultSet result = ps.executeQuery();
			while (result.next()) {
				userDocuments.add(result.getInt("docID"));
			}
			return userDocuments;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return userDocuments;
	}

	// TODO

	/**
	 * return vector of UserDocuments using userID
	 * 
	 * @param userID
	 */
	public static Vector<UserDocument> getUserDocuments(int userID) {
		connect();
		Vector<Integer> docIDs = getUserDocuments2(userID);
		Vector<UserDocument> userDocuments = new Vector<>();
		for (Integer id : docIDs) {
			userDocuments.add(getDocumentFromID(id));
		}
		close();
		return userDocuments;
	}

	// ENROLLMENT METHODS

	/**
	 * add enrollment
	 * 
	 * @param classID
	 * @param userID
	 */
	public static void addEnrollment(int classID, int userID) {
		connect();
		try {
			PreparedStatement ps = conn.prepareStatement(addEnrollment);
			ps.setInt(1, classID);
			ps.setInt(2, userID);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
	}

	public static boolean isUserEnrolledInClass(int classID, int userID) {
		connect();
		try {
			PreparedStatement ps = conn.prepareStatement(getUserInClass);
			ps.setInt(1, classID);
			ps.setInt(2, userID);
			ResultSet result = ps.executeQuery();
			while (result.next()) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return false;
	}

	/**
	 * return vector of userIDs associated with a classID
	 */
	private static Vector<Integer> getUsersEnrolledInClass2(int classID) {
		connect();
		Vector<Integer> usersInClass = new Vector<Integer>();
		try {
			PreparedStatement ps = conn.prepareStatement(getUsersEnrolledInClass);
			ps.setInt(1, classID);
			ResultSet result = ps.executeQuery();
			while (result.next()) {
				usersInClass.add(result.getInt("userID"));
			}
			return usersInClass;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return usersInClass;
	}

	/**
	 * return Vector of Users from classID
	 * 
	 * @param classID
	 * @return
	 */

	public static Vector<User> getUsersEnrolledInClass(int classID) {
		connect();
		Vector<User> enrolledUsers = new Vector<>();
		Vector<Integer> userIDs = getUsersEnrolledInClass2(classID);
		for (Integer id : userIDs) {
			enrolledUsers.add(getUserByUserID(id));
		}
		close();
		return enrolledUsers;
	}

	/**
	 * return vector of classIDs associated with userID
	 */
	private static Vector<Integer> getUserEnrollments2(int userID) {
		connect();
		Vector<Integer> enrollment = new Vector<Integer>();
		try {
			PreparedStatement ps = conn.prepareStatement(getUserEnrollments);
			ps.setInt(1, userID);
			ResultSet result = ps.executeQuery();
			while (result.next()) {
				enrollment.add(result.getInt("classID"));
			}
			return enrollment;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return enrollment;
	}

	/**
	 * return vector of classes associated with a userID
	 * 
	 * @param userID
	 * @return
	 */
	public static Vector<Classroom> getUserEnrollments(int userID) {
		connect();
		Vector<Classroom> userClasses = new Vector<>();
		Vector<Integer> classIDs = getUserEnrollments2(userID);
		for (Integer id : classIDs) {
			userClasses.add(getClassFromID(id));
		}
		close();
		return userClasses;
	}

	// UPLOAD METHODS

	/**
	 * add an upload to table
	 * 
	 * @param classID
	 * @param docID
	 */
	public static void addUpload(int classID, int docID) {
		connect();
		try {
			PreparedStatement ps = conn.prepareStatement(addUpload);
			ps.setInt(1, classID);
			ps.setInt(2, docID);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
	}

	/**
	 * return vector of all docIDs associated with a given classID
	 * 
	 * @param classID
	 * @return
	 */
	public static Vector<Integer> getClassUploads2(int classID) {
		connect();
		Vector<Integer> classDocuments = new Vector<Integer>();
		try {
			PreparedStatement ps = conn.prepareStatement(getClassUploads);
			ps.setInt(1, classID);
			ResultSet result = ps.executeQuery();
			while (result.next()) {
				classDocuments.add(result.getInt("docID"));
			}
			return classDocuments;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return classDocuments;
	}

	/**
	 * return vector of userDocuments given classID
	 * 
	 * @param classID
	 * @return
	 */
	public static Vector<UserDocument> getClassUploads(int classID) {
		connect();
		Vector<UserDocument> classDocuments = new Vector<>();
		Vector<Integer> docIDs = getClassUploads2(classID);
		for (Integer id : docIDs) {
			classDocuments.add(getDocumentFromID(id));
		}
		close();
		return classDocuments;
	}

	// MESSAGE METHODS

	public static void addMessage(int classID, int userID, int level, String content) {
		connect();
		try {
			PreparedStatement ps = conn.prepareStatement(addMessage);
			ps.setInt(1, classID);
			ps.setInt(2, userID);
			ps.setInt(3, level);
			ps.setString(4, content);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
	}

	/**
	 * return vector of all messages posted in a class's discussion board
	 * 
	 * @param classID
	 * @return
	 */
	private static Vector<Integer> getMessagesFromClass2(int classID) {
		connect();
		Vector<Integer> classMessages = new Vector<Integer>();
		try {
			PreparedStatement ps = conn.prepareStatement(getMessagesFromClass);
			ps.setInt(1, classID);
			ResultSet result = ps.executeQuery();
			while (result.next()) {
				classMessages.add(result.getInt("messageID"));
			}
			return classMessages;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return classMessages;
	}

	public static ClassMessage getMessageFromID(int messageID) {
		connect();
		try {
			PreparedStatement ps = conn.prepareStatement(getMessageFromID);
			ps.setInt(1, messageID);
			ResultSet result = ps.executeQuery();
			while (result.next()) {
				// int classid, int userid, int level, String content
				return new ClassMessage(messageID, result.getInt("classID"), result.getInt("userID"),
						result.getInt("level"), result.getString("content"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return null;
	}

	/**
	 * returns vector of message classes
	 * 
	 * @param classID
	 * @return
	 */
	public static Vector<ClassMessage> getMessagesFromClass(int classID) {
		connect();
		Vector<ClassMessage> classMessages = new Vector<>();
		Vector<Integer> messageIDs = getMessagesFromClass2(classID);
		for (Integer id : messageIDs) {
			classMessages.add(getMessageFromID(id));
		}
		close();
		return classMessages;
	}

	/**
	 * update message content
	 * 
	 * @param messageID
	 * @param newContent
	 */
	public static void updateMessage(int messageID, String newContent) {
		connect();
		try {
			PreparedStatement ps = conn.prepareStatement(updateMessage);
			ps.setString(1, newContent);
			ps.setInt(2, messageID);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
	}

	// REQUESTS METHODS

	/**
	 * Adds a request
	 * 
	 * @param userID
	 * @param classID
	 */
	public static void addRequest(int userID, int classID) {
		connect();
		try {
			PreparedStatement ps = conn.prepareStatement(addRequest);
			ps.setInt(1, userID);
			ps.setInt(2, classID);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
	}

	/**
	 * return vector of userIDs requesting access to classID s
	 * 
	 * @param classID
	 * @return
	 */
	private static Vector<Integer> getUsersWithRequests2(int classID) {
		connect();
		Vector<Integer> requests = new Vector<Integer>();
		try {
			PreparedStatement ps = conn.prepareStatement(getUsersWithRequests);
			ps.setInt(1, classID);
			ResultSet result = ps.executeQuery();
			while (result.next()) {
				requests.add(result.getInt("userID"));
			}
			return requests;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return requests;
	}

	public static Vector<User> getUsersWithRequests(int classID) {
		connect();
		Vector<User> requestingUsers = new Vector<>();
		Vector<Integer> userIDs = getUsersWithRequests2(classID);
		for (Integer id : userIDs) {
			requestingUsers.add(getUserByUserID(id));
		}
		close();
		return requestingUsers;
	}

	// request UPDATE methods

	/**
	 * switches request active status to false
	 * 
	 * @param classID
	 * @param userID
	 */
	public static void updateRequest(int classID, int userID) {
		connect();
		try {
			PreparedStatement ps = conn.prepareStatement(updateRequest);
			ps.setInt(1, classID);
			ps.setInt(2, userID);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
	}
}
