import java.io.*;
import java.net.*;
import java.sql.SQLException;

import Models.User;
import Handlers.*;
import com.sun.net.httpserver.*;

/**
 * The base of the server - contains the main function to run the server
 */
public class Server {

    /**
     * Something that we haven't learned about yet.
     */
    private static final int MAX_WAITING_CONNECTIONS = 12;

    /**
     * The server itself
     */
    private HttpServer server;

    /**
     * Runs the server on this port number
     * @param portNumber the port on which the server will run. I suggest 8080, if it's available
     */
    private void run(String portNumber) {
        System.out.println("Initializing HTTP Server on port " + portNumber);

        try {
            server = HttpServer.create(
                    new InetSocketAddress(Integer.parseInt(portNumber)),
                    MAX_WAITING_CONNECTIONS);
        }
        catch (IOException e) {
            e.printStackTrace();
            return;
        }

        // Indicate that we are using the default "executor".
        // This line is necessary, but its function is unimportant for our purposes.
        server.setExecutor(null);

        System.out.println("Creating contexts");

        server.createContext("/user/register", new RegisterHandler());
        server.createContext("/user/login", new LoginHandler());
        server.createContext("/clear", new ClearHandler());
        server.createContext("/fill", new FillHandler());
        server.createContext("/load", new LoadHandler());
        server.createContext("/person/[personID]", new PersonHandler());
        server.createContext("/person", new PersonHandler());
        server.createContext("/event/[eventID]", new EventHandler());
        server.createContext("/event", new EventHandler());
        server.createContext("/", new FileHandler());

        System.out.println("Starting server");

        server.start();

        System.out.println("Server started");
    }

    /**
     * The main function for this server
     * param args Should contain one argument: the port number to run the server on
     */
    public static void main(String[] args) throws SQLException {
        String portNumber = args[0];
        new Server().run(portNumber);
    }
}

