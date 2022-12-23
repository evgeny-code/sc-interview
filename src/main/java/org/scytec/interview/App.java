package org.scytec.interview;

import io.undertow.Undertow;
import org.scytec.interview.services.DbFacade;
import org.scytec.interview.web.AppHttpHandler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class App {
    public static final ExecutorService ES = Executors.newFixedThreadPool(2 * Runtime.getRuntime().availableProcessors() - 1);

    public static void main(String[] args) {
        ES.submit(() -> {
            while (!Thread.interrupted()) {
                DbFacade.INSTANCE.flushHistory();
                try {
                    Thread.sleep(333);
                } catch (InterruptedException e) {
                    Logger.getGlobal().log(Level.WARNING, "Interrupted", e);
                    Thread.currentThread().interrupt();
                }
            }
        });

        ES.submit(() -> {
            while (!Thread.interrupted()) {
                DbFacade.INSTANCE.flushClans();
                DbFacade.INSTANCE.flushUsers();
                try {
                    Thread.sleep(123);
                } catch (InterruptedException e) {
                    Logger.getGlobal().log(Level.WARNING, "Interrupted", e);
                    Thread.currentThread().interrupt();
                }
            }
        });

        Undertow server = Undertow.builder()
                .addHttpListener(8080, "localhost")
                .setHandler(new AppHttpHandler())
                .build();

        server.start();
    }
}
