DROP DATABASE if exists Scribe;
CREATE DATABASE Scribe;
USE Scribe;

CREATE TABLE Users (

	userID INTEGER PRIMARY KEY not null auto_increment,
	username VARCHAR(255) not null,
    password VARCHAR(255) not null
    
    );

CREATE TABLE Classes (

	classID INTEGER PRIMARY KEY not null auto_increment,
    classname VARCHAR(255) not null,
    private BOOLEAN not null
    
	);
	
CREATE TABLE Documents (

	docID INTEGER PRIMARY KEY not null auto_increment,
    userID INTEGER not null,
    documentname VARCHAR(255) not null,
    file LONGBLOB not null
	
);
	
CREATE TABLE Enrollment (

	enrollID INTEGER PRIMARY KEY not null auto_increment,
    classID INTEGER not null,
    userID INTEGER not null

);

CREATE TABLE Uploads (

	uploadID INTEGER PRIMARY KEY not null auto_increment,
    classID INTEGER not null,
    docID INTEGER not null
    
);

CREATE TABLE Messages (
	
	messageID INTEGER PRIMARY KEY not null auto_increment,
	classID INTEGER not null,
	userID INTEGER not null,
	level INTEGER not null,
	content VARCHAR(255) not null

);

CREATE TABLE Requests (

	requestID INTEGER PRIMARY KEY not null auto_increment,
	userID INTEGER not null,
	classID INTEGER not null,
	active BOOLEAN not null

);
