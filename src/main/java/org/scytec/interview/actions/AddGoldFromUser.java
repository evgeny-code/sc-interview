package org.scytec.interview.actions;

import io.undertow.server.HttpServerExchange;
import io.undertow.util.StatusCodes;
import lombok.RequiredArgsConstructor;
import org.scytec.interview.pojo.HttpQuery;
import org.scytec.interview.services.UserAddGoldService;

import java.util.concurrent.Callable;

@RequiredArgsConstructor
public class AddGoldFromUser implements Callable<Void> {
    private final HttpServerExchange exchange;

    @Override
    public Void call() throws Exception {
        HttpQuery query = HttpQuery.of(exchange.getQueryString());
        UserAddGoldService.INSTANCE.addGoldToClan(query.getUserId(), query.getClanId(), query.getGold());

        exchange.setStatusCode(StatusCodes.OK);
        exchange.getResponseSender().send("Ok");

        return null;
    }
}
