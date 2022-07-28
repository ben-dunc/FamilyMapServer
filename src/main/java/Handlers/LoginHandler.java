package Handlers;

import Containers.Request.LoginRequest;
import Containers.Result.LoginResult;
import Services.LoginService;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;

/**
 *  A handler for the login api calls
 */
public class LoginHandler implements HttpHandler {
    /**
     * Handle login endpoint
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
                LoginRequest request = gson.fromJson(reqData, LoginRequest.class);

                LoginResult result = LoginService.login(request);

                if (result.getSuccess()) {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    success = true;
                    OutputStream respBody = exchange.getResponseBody();
                    HandlerHelper.writeString(gson.toJson(result, LoginResult.class), respBody);
                    respBody.close();
                } else {
                    errorMessage = result.getMessage();
                }
            } else {
                errorMessage = "Bad request: request was not a post.";
            }

            if (!success) {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                LoginResult result = new LoginResult(null, null, null, "Error:[" + errorMessage + "]", false);
                OutputStream respBody = exchange.getResponseBody();
                HandlerHelper.writeString(gson.toJson(result, LoginResult.class), respBody);
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
