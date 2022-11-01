package org.scytec.interview;

import io.undertow.Undertow;
import org.scytec.interview.services.DbFacade;
import org.scytec.interview.web.AppHttpHandler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class App {
    public static final ExecutorService ES = Executors.newFixedThreadPool(2 * Runtime.getRuntime().availableProcessors() - 1);

    public static void main(String[] args) {
        ES.submit(() -> {
            while (true) {
                DbFacade.INSTANCE.flushHistory();
                Thread.sleep(333);
            }
        });

        ES.submit(() -> {
            while (true) {
                DbFacade.INSTANCE.flushClans();
                DbFacade.INSTANCE.flushUsers();
                Thread.sleep(123);
            }
        });

        Undertow server = Undertow.builder()
                .addHttpListener(8080, "localhost")
                .setHandler(new AppHttpHandler())
                .build();

        server.start();
    }
}
