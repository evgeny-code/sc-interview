package org.scytec.interview.test;

import lombok.SneakyThrows;
import org.scytec.interview.pojo.Clan;
import org.scytec.interview.pojo.Task;
import org.scytec.interview.pojo.User;
import org.scytec.interview.services.JsonMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Random;

public class Util {
    private static final Random random = new Random(System.currentTimeMillis());
    private static final HttpClient client = HttpClient.newBuilder().build();

    @SneakyThrows
    public static Clan[] getClans() {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("http://localhost:8080/clans"))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return JsonMapper.INSTANCE.fromJson(response.body(), Clan[].class);
    }

    @SneakyThrows
    public static User[] getUsers() {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("http://localhost:8080/users"))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return JsonMapper.INSTANCE.fromJson(response.body(), User[].class);
    }

    @SneakyThrows
    public static Task[] getTasks() {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("http://localhost:8080/tasks"))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return JsonMapper.INSTANCE.fromJson(response.body(), Task[].class);
    }

    public static <T> T getRandom(T[] array) {
        return array[random.nextInt(array.length)];
    }
}
