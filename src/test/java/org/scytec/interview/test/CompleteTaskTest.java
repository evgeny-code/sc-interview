package org.scytec.interview.test;

import org.junit.Assert;
import org.junit.Test;
import org.scytec.interview.pojo.Clan;
import org.scytec.interview.pojo.Task;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CompleteTaskTest {

    final HttpClient client = HttpClient.newBuilder().build();

    @Test
    public void run() throws IOException, InterruptedException {
        Clan[] clans = Util.getClans();
        Task[] tasks = Util.getTasks();

        Clan clan = Util.getRandom(clans);
        int goldStart = clan.getGold().get();
        List<Task> completedTasks = Collections.synchronizedList(new ArrayList<>());

        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Thread thread = new Thread(() -> {
                Task task = Util.getRandom(tasks);

                HttpRequest request = HttpRequest.newBuilder()
                        .GET()
                        .uri(URI.create(String.format("http://localhost:8080/actions/completeTask?clanId=%s&taskId=%s", clan.getId(), task.getId())))
                        .build();
                try {
                    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                    System.out.println(Thread.currentThread().getId() + ": " + response.statusCode() + " " + response.body());
                    completedTasks.add(task);
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
                goldEnd = clanNew.getGold().get();
            }
        }

        long sum = completedTasks.stream().collect(Collectors.summarizingInt(Task::getReward)).getSum();

        Assert.assertEquals(goldEnd, goldStart + sum);
    }
}
