package org.scytec.interview.actions;

import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;
import io.undertow.util.StatusCodes;
import lombok.RequiredArgsConstructor;
import org.scytec.interview.db.api.ClanDao;
import org.scytec.interview.db.impl.ClanDaoImpl;
import org.scytec.interview.pojo.Clan;
import org.scytec.interview.services.JsonMapper;

import java.util.List;
import java.util.concurrent.Callable;

@RequiredArgsConstructor
public class ListClans implements Callable<Void> {
    private final HttpServerExchange exchange;

    private ClanDao clanDao = ClanDaoImpl.INSTANCE;

    @Override
    public Void call() throws Exception {
        List<Clan> clans = clanDao.findAll();

        exchange.setStatusCode(StatusCodes.OK);
        exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "application/json");
        exchange.getResponseSender().send(JsonMapper.INSTANCE.toJson(clans));

        return null;
    }
}
