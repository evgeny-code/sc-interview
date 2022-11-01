package org.scytec.interview.test;

import org.junit.Assert;
import org.junit.Test;
import org.scytec.interview.pojo.Clan;
import org.scytec.interview.pojo.User;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class AddGoldToClanTest {

    @Test
    public void run() throws InterruptedException {
        Clan[] clans = Util.getClans();
        User[] users = Util.getUsers();

        Clan clan = Util.getRandom(clans);
        int goldStart = clan.getGold();
        List<User> usersWhoAdd = new ArrayList<>();

        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Thread thread = new Thread(() -> {
                User user = Util.getRandom(users);

                final HttpClient client = HttpClient.newBuilder().build();
                HttpRequest request = HttpRequest.newBuilder()
                        .GET()
                        .uri(URI.create(String.format("http://localhost:8080/actions/addGoldToClan?clanId=%s&userId=%s&gold=%s", clan.getId(), user.getId(), 1)))
                        .build();
                try {
                    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                    System.out.println(Thread.currentThread().getId() + ": " + response.statusCode() + " " + response.body());
                    usersWhoAdd.add(user);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            threads.add(thread);
            thread.start();
        }

        threads.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread.sleep(333);

        int goldEnd = 0;
        for (Clan clanNew : Util.getClans()) {
            if (clanNew.getId() == clan.getId()) {
                System.out.println(clan);
                System.out.println(clanNew);
                goldEnd = clanNew.getGold();
            }
        }

        Assert.assertEquals(goldEnd, goldStart + 100);

    }
}
