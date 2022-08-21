package Handlers;

import Result.FillResult;
import Services.FillService;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;

/**
 *  A handler for getting the 'fill' api call
 */
public class FillHandler implements HttpHandler {

    /**
     * Get fill handler
     * @param exchange the exchange containing the request from the
     *      client and used to send the response
     * @throws IOException if there is an ioexception
     */
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        boolean success = false;
        try {
            if (exchange.getRequestMethod().equalsIgnoreCase("post")) {
                Gson gson = new Gson();

                String uri = exchange.getRequestURI().toString();
                String username = uri.substring("/fill/".length(), uri.indexOf('/', "/fill/".length() + 1));
                String generations = uri.substring("/fill/".length() + username.length() + 1);

                int numGenerations;
                try {
                    numGenerations = Integer.parseInt(generations);
                } catch (NumberFormatException ex) {
                    numGenerations = 0;
                }

                FillResult result;
                if (numGenerations > 0) {
                    result = FillService.fill(username, numGenerations);
                } else {
                    result = new FillResult("The number of generations must be higher than 0", false);
                }


                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                OutputStream respBody = exchange.getResponseBody();
                HandlerHelper.writeString(gson.toJson(result, FillResult.class), respBody);
                respBody.close();

                success = true;
            }

            if (!success) {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                exchange.getResponseBody().close();
            }
        }
        catch (IOException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, 0);
            exchange.getResponseBody().close();
            e.printStackTrace();
        }
    }
}
