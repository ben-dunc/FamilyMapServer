package Handlers;

import Containers.Result.EventResult;
import Services.EventService;
import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;

/**
 *  A handler of the event api calls
 */
public class EventHandler implements HttpHandler {
    /**
     * Handles the Event API calls
     * @param exchange the exchange containing the request from the
     *      client and used to send the response
     * @throws IOException if there is an ioexception
     */
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        boolean success = false;
        String errorMessage = "Bad request";
        Gson gson = new Gson();

        try {
            if (exchange.getRequestMethod().equalsIgnoreCase("get")) {
                Headers reqHeaders = exchange.getRequestHeaders();
                if (reqHeaders.containsKey("Authorization")) {
                    String authToken = reqHeaders.getFirst("Authorization");

                    String uri = exchange.getRequestURI().toString();
                    EventResult result;
                    if (!uri.contains("/event/")) {
                        result = EventService.event(authToken);
                    } else {
                        String eventID = uri.substring("/event/".length());
                        result = EventService.event(eventID, authToken);
                    }

                    if (result.isSuccess()) {
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                        success = true;
                        OutputStream respBody = exchange.getResponseBody();
                        HandlerHelper.writeString(gson.toJson(result, EventResult.class), respBody);
                        respBody.close();
                    } else {
                        errorMessage = result.getMessage();
                    }
                } else {
                    errorMessage = "No Authorization header";
                }
            } else {
                errorMessage = "Request was not a GET";
            }

            if (!success) {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                EventResult result = new EventResult(false, "Error:[" + errorMessage + "]");
                OutputStream respBody = exchange.getResponseBody();
                HandlerHelper.writeString(gson.toJson(result, EventResult.class), respBody);
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
