package org.scytec.interview.actions;

import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;
import io.undertow.util.StatusCodes;
import lombok.RequiredArgsConstructor;
import org.scytec.interview.db.api.ClanHistoryDao;
import org.scytec.interview.db.impl.ClanHistoryDaoImpl;
import org.scytec.interview.pojo.HttpQuery;
import org.scytec.interview.services.JsonMapper;

import java.util.concurrent.Callable;

@RequiredArgsConstructor
public class ListClanHistory implements Callable<Void> {
    private final HttpServerExchange exchange;

    private ClanHistoryDao clanHistoryDao = ClanHistoryDaoImpl.INSTANCE;

    @Override
    public Void call() throws Exception {
        HttpQuery query = HttpQuery.of(exchange.getQueryString());

        exchange.setStatusCode(StatusCodes.OK);
        exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "application/json");
        exchange.getResponseSender().send(JsonMapper.INSTANCE.toJson(clanHistoryDao.findByClanId(query.getClanId())));

        return null;
    }
}
