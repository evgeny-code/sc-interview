package org.scytec.interview.actions;

import io.undertow.server.HttpServerExchange;
import io.undertow.util.StatusCodes;
import lombok.RequiredArgsConstructor;
import org.scytec.interview.pojo.HttpQuery;
import org.scytec.interview.services.TaskService;

import java.util.concurrent.Callable;

@RequiredArgsConstructor
public class AddGoldByTask implements Callable<Void> {
    private final HttpServerExchange exchange;

    @Override
    public Void call() throws Exception {
        HttpQuery query = HttpQuery.of(exchange.getQueryString());
        TaskService.INSTANCE.completeTask(query.getClanId(), query.getTaskId());

        exchange.setStatusCode(StatusCodes.OK);
        exchange.getResponseSender().send("Ok");

        return null;
    }
}
