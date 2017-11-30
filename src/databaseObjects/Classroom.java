
/* Scribe
 * 
 * Vincent Rodriguez
 * Michael Takla
 * Felipe Mansilla
 * Noah Feldman
 * 
 */

package databaseObjects;

public class Classroom {

	private int mClassID;
	private String mClassname;
	private boolean mIsPrivate;

	public Classroom(int classid, String classname, boolean isPrivate) {
		this.mClassID = classid;
		this.mClassname = classname;
		this.mIsPrivate = isPrivate;
	}

	public int getClassID() {
		return this.mClassID;
	}

	public String getClassname() {
		return this.mClassname;
	}

	public boolean isPrivate() {
		return this.mIsPrivate;
	}
	
}
