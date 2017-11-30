
/* Scribe
 * 
 * Vincent Rodriguez
 * Michael Takla
 * Felipe Mansilla
 * Noah Feldman
 * 
 */

package databaseObjects;

public class User {

	private int mUserID;

	private String mUsername;
	private String mPassword;

	public User(int userid, String username, String password) {
		this.mUserID = userid;
		this.mUsername = username;
		this.mPassword = password;
	}

	public int getUserID() {
		return this.mUserID;
	}

	public String getUsername() {
		return this.mUsername;
	}

	public String getPassword() {
		return this.mPassword;
	}
}
