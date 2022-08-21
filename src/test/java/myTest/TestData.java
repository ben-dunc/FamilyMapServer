package myTest;

import Models.Authtoken;
import Models.Event;
import Models.Person;
import Models.User;

public class TestData {
    public static final Authtoken[] authtokens = {
        new Authtoken("12341234123412341234123412341234", "sheila"),
        new Authtoken("43214321432143214321432143214321", "patrick")
    };

    public static final User[] users = {
        new User ("sheila", "parker", "sheila@parker.com", "Sheila", "Parker", "f", "Sheila_Parker"),
        new User ("patrick", "spencer", "patrick@spencer.com", "Patrick", "Spencer", "m", "Patrick_Spencer")
    };

    public static final Person[] persons = {
        new Person( "Sheila_Parker", "sheila", "Sheila", "Parker", "f", "Davis_Hyer", "Blaine_McGary", "Betty_White"),
        new Person( "Davis_Hyer", "sheila", "Davis", "Hyer", "m", "Sheila_Parker", null, null),
        new Person( "Blaine_McGary", "sheila", "Blaine", "McGary", "m", "Ken_Rodham", "Mrs_Rodham", "Betty_White"),
        new Person( "Betty_White", "sheila", "Betty", "White", "f", "Frank_Jones", "Mrs_Jones", "Blaine_McGary"),
        new Person( "Ken_Rodham", "sheila", "Ken", "Rodham", "m", "Mrs_Rodham", null, null),
        new Person( "Mrs_Rodham", "sheila", "Mrs", "Rodham", "f", "Ken_Rodham", null, null),
        new Person( "Frank_Jones", "sheila", "Frank", "Jones", "m", "Mrs_Jones", null, null),
        new Person( "Mrs_Jones", "sheila", "Mrs", "Jones", "f", "Frank_Jones", null, null),
        new Person( "Patrick_Spencer", "patrick", "Patrick", "Spencer", "m", "Happy_Birthday", "Golden_Boy", null),
        new Person( "Happy_Birthday", "patrick", "Patrick", "Wilson", "m", "Golden_Boy", null, null),
        new Person( "Golden_Boy", "patrick", "Spencer", "Seeger", "f", "Happy_Birthday", null, null)
    };

    public static final Event[] events = {
        new Event("Sheila_Birth", "sheila", "Sheila_Parker", -36.1833f, 144.9667f, "Melbourne", "Australia", "birth", 1970),
        new Event("Sheila_Marriage", "sheila", "Sheila_Parker", 34.0500f, -117.7500f, "Los Angeles", "United States", "marriage", 2012),
        new Event("Sheila_Asteroids", "sheila", "Sheila_Parker", 77.4667f, -68.7667f, "Qaanaaq", "Denmark", "completed asteroids", 2014),
        new Event("Other_Asteroids", "sheila", "Sheila_Parker", 74.4667f, -60.7667f, "Qaanaaq", "Denmark", "COMPLETED ASTEROIDS", 2014),
        new Event("Sheila_Death", "sheila", "Sheila_Parker", 40.2444f, 111.6608f, "Provo", "United States", "death", 2015),
        new Event("Davis_Birth", "sheila", "Davis_Hyer", 41.7667f, 140.7333f, "Hakodate", "Japan", "birth", 1970),
        new Event("Blaine_Birth", "sheila", "Blaine_McGary", 56.1167f, 101.6000f, "Bratsk", "Russia", "birth", 1948),
        new Event("Betty_Death", "sheila", "Betty_White", 52.4833f, -0.1000f, "Birmingham", "United Kingdom", "death", 2017),
        new Event("BYU_graduation", "sheila", "Ken_Rodham", 40.2338f, -111.6585f, "United States", "Provo", "Graduated from BYU", 1879),
        new Event("Rodham_Marriage", "sheila", "Ken_Rodham", 39.15f, 127.45f, "North Korea", "Wonsan", "marriage", 1895),
        new Event("Mrs_Rodham_Backflip", "sheila", "Mrs_Rodham", 32.6667f, -114.5333f, "Mexico", "Mexicali", "Did a backflip", 1890),
        new Event("Mrs_Rodham_Java", "sheila", "Mrs_Rodham", 36.7667f, 3.2167f, "Algeria", "Algiers", "learned Java", 1890),
        new Event("Jones_Frog", "sheila", "Frank_Jones", 25.0667f, -76.6667f, "Bahamas", "Nassau", "Caught a frog", 1993),
        new Event("Jones_Marriage", "sheila", "Frank_Jones", 9.4f, 0.85f, "Ghana", "Tamale", "marriage", 1997),
        new Event("Mrs_Jones_Barbecue", "sheila", "Mrs_Jones", -24.5833f, -48.75f, "Brazil", "Curitiba", "Ate Brazilian Barbecue", 2012),
        new Event("Mrs_Jones_Surf", "sheila", "Mrs_Jones", -27.9833f, 153.4f, "Australia", "Gold Coast", "Learned to Surf", 2000),
        new Event("Thanks_Woodfield", "patrick", "Patrick_Spencer", 76.4167f, -81.1f, "Grise Fiord", "Canada", "birth", 2016),
        new Event("True_Love", "patrick", "Happy_Birthday", 43.6167f, -115.8f, "Boise", "United States", "marriage", 2016),
        new Event("Together_Forever", "patrick", "Golden_Boy", 43.6167f, -115.8f, "Boise", "United States", "marriage", 2016)
    };
}
