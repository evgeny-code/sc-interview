package org.scytec.interview.web;

import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;
import io.undertow.util.StatusCodes;
import org.scytec.interview.App;
import org.scytec.interview.actions.*;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

public class AppHttpHandler implements HttpHandler {

    @Override
    public void handleRequest(HttpServerExchange exchange) throws Exception {
        Callable callable = resolveAction(exchange.getRequestPath(), exchange);
        if (null != callable) {
            Future<Void> voidFuture = App.ES.submit(callable);
            while (!voidFuture.isDone()) {
            }
        } else {
            exchange.setStatusCode(StatusCodes.BAD_REQUEST);
            exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/plain");
            exchange.getResponseSender().send("Wrong request");
        }
    }

    private Callable resolveAction(String requestPath, HttpServerExchange exchange) {
        switch (requestPath) {
            case "/actions/addGoldToClan":
                return new AddGoldFromUser(exchange);
            case "/actions/completeTask":
                return new AddGoldByTask(exchange);
            case "/actions/generate":
                return new GenerateRandomData();
            case "/clans":
                return new ListClans(exchange);
            case "/users":
                return new ListUsers(exchange);
            case "/tasks":
                return new ListTasks(exchange);
            case "/clan-history":
                return new ListClanHistory(exchange);
        }

        return null;
    }
}
