package org.scytec.interview.test;

import org.junit.Test;
import org.scytec.interview.pojo.Clan;
import org.scytec.interview.pojo.Task;
import org.scytec.interview.pojo.User;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;

public class CommonTest {
    final HttpClient client = HttpClient.newBuilder().build();

    @Test
    public void listClans() throws IOException, InterruptedException {
        Clan[] clans = Util.getClans();
        System.out.println(Arrays.toString(clans));
    }

    @Test
    public void listUsers() throws IOException, InterruptedException {
        User[] users = Util.getUsers();
        System.out.println(Arrays.toString(users));
    }

    @Test
    public void listTasks() throws IOException, InterruptedException {
        Task[] tasks = Util.getTasks();
        System.out.println(Arrays.toString(tasks));
    }

    @Test
    public void generateData() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("http://localhost:8080/actions/generate"))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
    }
}
