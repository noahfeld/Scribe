
/* Scribe
 * 
 * Vincent Rodriguez
 * Michael Takla
 * Felipe Mansilla
 * Noah Feldman
 * 
 */

package databaseObjects;

public class ClassMessage {

	private int mMessageID;
	private int mClassID;
	private int mUserID;
	private int mLevel;
	private String mContent;

	public ClassMessage(int messageid, int classid, int userid, int level, String content) {
		this.mMessageID = messageid;
		this.mClassID = classid;
		this.mUserID = userid;
		this.mLevel = level;
		this.mContent = content;
	}

	// GETTERS

	public int getMessageID() {
		return this.mMessageID;
	}

	public int getClassID() {
		return this.mClassID;
	}

	public int getUserID() {
		return this.mUserID;
	}

	public int getLevel() {
		return this.mLevel;
	}

	public String getContent() {
		return this.mContent;
	}

}
