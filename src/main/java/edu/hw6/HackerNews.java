package edu.hw6;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class HackerNews {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final int SUCCESS_STATUS = 200;
    private static final String TOP_STORIES_URL = "https://hacker-news.firebaseio.com/v0/topstories.json";
    private static final String ITEM_URL_FORMAT = "https://hacker-news.firebaseio.com/v0/item/%d.json";
    private final HttpClient httpClient;

    public HackerNews(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public long[] hackerNewsTopStories() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(TOP_STORIES_URL))
                .GET()
                .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            String[] idNews = response.body().replaceAll("[\\[\\]\"]", "").split(",");
            long[] result = new long[idNews.length];

            for (int i = 0; i < idNews.length; i++) {
                result[i] = Long.parseLong(idNews[i]);
            }

            return result;
        } catch (IOException | InterruptedException e) {
            return new long[] {};
        }
    }

    public String news(long id) {
        if (id < 0) {
            throw new IllegalArgumentException("Negative id");
        }

        try {
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(String.format(ITEM_URL_FORMAT, id)))
                .GET()
                .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == SUCCESS_STATUS) {
                if (response.body().equals("null")) {
                    throw new NullPointerException("There is no news for this id");
                }

                Pattern pattern = Pattern.compile("\"title\":\"(.*?)\"");
                Matcher matcher = pattern.matcher(response.body());

                if (matcher.find()) {
                    return matcher.group(1);
                } else {
                    return "Title not found";
                }
            }
        } catch (IOException | InterruptedException e) {
            LOGGER.error("Failed to send response");
        }

        throw new RuntimeException();
    }

}
