package Handlers;

import Request.RegisterRequest;
import Result.RegisterResult;
import Services.RegisterService;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;

/**
 * A handler for the register api calls
 */
public class RegisterHandler implements HttpHandler {

    /**
     * Handle register
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
            if (exchange.getRequestMethod().equalsIgnoreCase("post")) {
                InputStream reqBody = exchange.getRequestBody();
                String reqData = HandlerHelper.readString(reqBody);
                RegisterRequest request = gson.fromJson(reqData, RegisterRequest.class);
                RegisterResult result = RegisterService.register(request);

                if (result.isSuccess()) {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    OutputStream respBody = exchange.getResponseBody();
                    HandlerHelper.writeString(gson.toJson(result, RegisterResult.class), respBody);
                    respBody.close();
                    success = true;
                } else {
                    errorMessage = result.getMessage();
                }
            } else {
                errorMessage = "Request method is not POST";
            }

            if (!success) {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                RegisterResult result = new RegisterResult(null, null, null, false, errorMessage);
                OutputStream respBody = exchange.getResponseBody();
                HandlerHelper.writeString(gson.toJson(result, RegisterResult.class), respBody);
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
