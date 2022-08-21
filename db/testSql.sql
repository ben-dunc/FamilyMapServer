-- Created by Benjamin Duncan
-- July 20, 2022

-- Clear the whole db
delete from user;
delete from person;
delete from event;
delete from authtoken;


-- ====================== USER ======================

-- Insert a new user into the person base.
insert into user (username,  password, email, firstName, lastName, gender, personID) values (?,  ?, ?, ?,  ?, ?, ?);
insert into user (username,  password, email, firstName, lastName, gender, personID) values ("1___",  "password", "myemail@place.com", "ben", "duncan", "m",  "1_______________________________");
insert into user (username,  password, email, firstName, lastName, gender, personID) values ("2___",  "password", "myemail@place.com", "ben", "duncan", "m",  "2_______________________________");
insert into user (username,  password, email, firstName, lastName, gender, personID) values ("3___",  "password", "myemail@place.com", "ben", "duncan", "m",  "3_______________________________");
insert into user (username,  password, email, firstName, lastName, gender, personID) values ("4___",  "password", "myemail@place.com", "ben", "duncan", "m",  "4_______________________________");
insert into user (username,  password, email, firstName, lastName, gender, personID) values ("5___",  "password", "myemail@place.com", "ben", "duncan", "m",  "5_______________________________");
insert into user (username,  password, email, firstName, lastName, gender, personID) values ("6___",  "password", "myemail@place.com", "ben", "duncan", "m",  "6_______________________________");
insert into user (username,  password, email, firstName, lastName, gender, personID) values ("7___",  "password", "myemail@place.com", "ben", "duncan", "m",  "7_______________________________");
-- clear user table
delete from user;

-- get one user
select user.* from user join authtoken on authtoken.username = user.username where authtoken.authtoken = "12341234123412341234123412341234"
select user.* from user join authtoken on authtoken.username = user.username where authtoken.authtoken = ?;

-- ====================== PERSON ======================

-- Insert a new person into the person table.
insert into person (personID, associatedUsername, firstName, lastName, gender,  motherID, fatherID, spouseID) values (?, ?, ?, ?, ?, ?, ?, ?);
insert into person (personID, associatedUsername, firstName, lastName, gender,  motherID, fatherID, spouseID) values ("64dfasdfasdfasdfasdfasdfasdfasdf", "asdf", "ben", "duncan", "m", null, null, null);

-- clear person table 
delete from person;

-- get all associated persons by authtoken
select person.* from person join authtoken on person.associatedUsername = authtoken.username where authtoken.authtoken = ?; 
select person.* from person join authtoken on person.associatedUsername = authtoken.username where authtoken.authtoken = "12341234123412341234123412341234"; 

-- delete all associated persons
delete from person where person.associatedUsername = (select authtoken.username from authtoken where authtoken.authtoken = ?); 
delete from person where person.associatedUsername = (select authtoken.username from authtoken where authtoken.authtoken = "12341234123412341234123412341234"); 

-- get a person by their personId and authtoken
select person.* from person join authtoken on person.associatedUsername = authtoken.username where authtoken.authtoken = ? and person.personID = ?; 
select person.* from person join authtoken on person.associatedUsername = authtoken.username where authtoken.authtoken = "22341234123412341234123412341234" and person.personID = "64dfasdfasdfasdfasdfasdfasdfasdf"; 

-- ====================== EVENT ======================

-- Insert a new event into the event table.
insert into event (eventID, associatedUsername, personID, latitude, longitude, country, city, eventType, year) values (?, ?, ?, ?, ?, ?, ?, ?, ?);
insert into event (eventID, associatedUsername, personID, latitude, longitude, country, city, eventType, year) 
					values ("test16", "asdf", "8cf27717", 0.0, 0.0, "Null island", "Null city", "Test16", 2008);
insert into event (eventID, associatedUsername, personID, latitude, longitude, country, city, eventType, year) 
					values ("got an ice cream", "asdf", "8cf27717", 1.0, 1.0, "Null island", "Null city", "Got an ice cream", 2008);

-- clear event table
delete from event;

-- get associated events by authtoken
select event.* from event join authtoken on event.associatedUsername = authtoken.username where authtoken.authtoken = ? order by personID, year, eventType;

-- delete associated events by authtoken
delete from event where event.associatedUsername = (select authtoken.username from authtoken where authtoken.authtoken = ?); 

-- get an event by its eventID and authtoken
select event.* from event  join authtoken on event.associatedUsername = authtoken.username where authtoken.authtoken = ? and event.eventID = ?; 

-- ====================== AUTHTOKEN ======================

-- Insert a new authtoken into the authtoken table.
insert into authtoken (authtoken, username) values (?, ?);
insert into authtoken (authtoken, username) values ("12341234123412341234123412341234", "1___");

-- clear auttoken table
delete from authtoken;

-- get username by authtoken
select * from authtoken where authtoken.authtoken = ?;

-- remove tokens by username
select * from authtoken where authtoken.username = ?;

