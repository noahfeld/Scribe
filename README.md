# Scribe

## Team members:

- Vincent Rodriguez
- Michael Takla
- Felipe Mansilla
- Noah Feldman

### What works:

- Users can register for a new account
- Users can log in to their existing accounts
- Users can create new classes
- Users can request to join a class
- Users can accept new requests
	- Requests appear in discussion board, so anyone can accept
- Users can upload files (.pdf, .txt, .mp3, .mpeg, etc.)

- Guests can view classes, but cannot view discussion board or files
- Guests only have functionality to search for classes

- Search function works: returns classes with that name
- Discussion board allows for multrithreading: every user receives messages

### What does not work:

- Automatic refresh does not work, users must refresh after accepting request
- Discussion board does not allow for comments, only posts

### Starting program:
- Run Scribe.sql in database
- Alter USER and PASS in JDBCQuery to match your username and password
- Run Welcome.jsp on a Tomcat v9.0 Server