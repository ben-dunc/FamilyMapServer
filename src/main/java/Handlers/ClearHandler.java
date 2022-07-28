package Handlers;

import Containers.Result.ClearResult;
import Services.ClearService;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.net.HttpURLConnection;

/**
 *  A handler for the 'clear' api calls
 */
public class ClearHandler implements HttpHandler {

    /**
     * Handles clear function
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
            if (exchange.getRequestMethod().equalsIgnoreCase("post")) {

                ClearResult result = ClearService.clear();

                if (result.getSuccess()) {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    OutputStream respBody = exchange.getResponseBody();
                    HandlerHelper.writeString(gson.toJson(result, ClearResult.class), respBody);
                    respBody.close();
                    success = true;
                } else {
                    errorMessage = result.getMessage();
                }

            } else {
                errorMessage = "Request method was not POST";
            }

            if (!success) {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                ClearResult result = new ClearResult("Error:[" + errorMessage + "]", false);
                OutputStream respBody = exchange.getResponseBody();
                HandlerHelper.writeString(gson.toJson(result, ClearResult.class), respBody);
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
