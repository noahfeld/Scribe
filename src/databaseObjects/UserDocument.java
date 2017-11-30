
/* Scribe
 * 
 * Vincent Rodriguez
 * Michael Takla
 * Felipe Mansilla
 * Noah Feldman
 * 
 */

package databaseObjects;

import java.sql.Blob;

public class UserDocument {

	private int docID;
	private String mName;
	private Blob blob;

	public UserDocument(int docID, String name, Blob blob) {
		this.mName = name;
		this.blob = blob;
		this.docID = docID;
	}

	public int getDocID() {
		return this.docID;
	}

	public String getName() {
		return this.mName;
	}

	public Blob getBlob() {
		return this.blob;
	}
	
	public String getExtension() {
		String[] parts = mName.split("\\.");
		return parts[parts.length - 1];
	}
	
}
