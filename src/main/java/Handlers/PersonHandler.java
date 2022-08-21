package Handlers;

import Result.PersonResult;
import Services.PersonService;
import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;

/**
 * A handler for the person api calls
 */
public class PersonHandler implements HttpHandler {
    /**
     * Handles the Person API calls
     * @param exchange the exchange containing the request from the
     *      client and used to send the response
     * @throws IOException if there is an ioexception
     */
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        boolean success = false;
        Gson gson = new Gson();
        String errorMessage = "Bad request";

        try {
            if (exchange.getRequestMethod().equalsIgnoreCase("get")) {
                Headers reqHeaders = exchange.getRequestHeaders();
                if (reqHeaders.containsKey("Authorization")) {
                    String authToken = reqHeaders.getFirst("Authorization");
                    String uri = exchange.getRequestURI().toString();
                    PersonResult result;

                    if (!uri.contains("/person/")) {
                        result = PersonService.person(authToken);
                    } else {
                        String personID = uri.substring("/person/".length());
                        result = PersonService.person(personID, authToken);
                    }

                    System.out.println("In person handler again!");

                    if (result.isSuccess()) {
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                        success = true;
                        OutputStream respBody = exchange.getResponseBody();
                        HandlerHelper.writeString(gson.toJson(result, PersonResult.class), respBody);
                        respBody.close();
                    } else {
                        errorMessage = result.getMessage();
                    }
                } else {
                    errorMessage = "No authorization header";
                }
            } else {
                errorMessage = "Request was not a GET";
            }

            if (!success) {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                PersonResult result = new PersonResult(false, "Error: [" + errorMessage + "]");
                OutputStream respBody = exchange.getResponseBody();
                HandlerHelper.writeString(gson.toJson(result, PersonResult.class), respBody);
                respBody.close();
            }
        }
        catch (IOException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, 0);
            exchange.getResponseBody().close();
            e.printStackTrace();
        }
    }
}
