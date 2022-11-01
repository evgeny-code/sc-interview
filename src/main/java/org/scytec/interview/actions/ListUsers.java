package org.scytec.interview.actions;

import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;
import io.undertow.util.StatusCodes;
import lombok.RequiredArgsConstructor;
import org.scytec.interview.db.api.UserDao;
import org.scytec.interview.db.impl.UserDaoImpl;
import org.scytec.interview.pojo.User;
import org.scytec.interview.services.JsonMapper;

import java.util.List;
import java.util.concurrent.Callable;

@RequiredArgsConstructor
public class ListUsers implements Callable<Void> {
    private final HttpServerExchange exchange;

    private UserDao userDao = UserDaoImpl.INSTANCE;

    @Override
    public Void call() throws Exception {
        List<User> users = userDao.findAll();

        exchange.setStatusCode(StatusCodes.OK);
        exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "application/json");
        exchange.getResponseSender().send(JsonMapper.INSTANCE.toJson(users));

        return null;
    }
}
