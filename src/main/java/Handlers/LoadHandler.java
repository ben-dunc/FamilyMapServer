package Handlers;

import Containers.Request.LoadRequest;
import Containers.Result.LoadResult;
import Services.LoadService;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.net.HttpURLConnection;

/**
 *  A handler to handle the 'load' api call.
 */
public class LoadHandler implements HttpHandler {

    /**
     * Implements the handling for the Load API calls
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
                InputStream reqBody = exchange.getRequestBody();

                String reqData = HandlerHelper.readString(reqBody);

                LoadRequest request = gson.fromJson(reqData, LoadRequest.class);

                LoadResult result = LoadService.load(request);

                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                OutputStream respBody = exchange.getResponseBody();
                HandlerHelper.writeString(gson.toJson(result, LoadResult.class), respBody);
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
