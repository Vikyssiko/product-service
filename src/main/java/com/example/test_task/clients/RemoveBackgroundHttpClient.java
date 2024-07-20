package com.example.test_task.clients;

import com.example.test_task.configs.ApiConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Slf4j
@Component
@RequiredArgsConstructor
public class RemoveBackgroundHttpClient {
    private final ApiConfig apiConfig;
    private final HttpClient client;

    @Autowired
    public RemoveBackgroundHttpClient(ApiConfig apiConfig) {
        this.apiConfig = apiConfig;
        this.client = HttpClient.newHttpClient();
    }

    public HttpResponse<byte[]> removeBackground(String encodedImage) throws IOException, InterruptedException {
        String json = String.format("{\"image_file_b64\": \"%s\", \"size\": \"auto\"}", encodedImage);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.remove.bg/v1.0/removebg"))
                .header("Content-Type", "application/json")
                .header("X-Api-Key", apiConfig.getApiKey())
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        return sendRequest(request);
    }

    private HttpResponse<byte[]> sendRequest(final HttpRequest request) throws IOException, InterruptedException {
            HttpResponse<byte[]> response =
                    client.send(request, HttpResponse.BodyHandlers.ofByteArray());
            log.info("Request for removing background has been successfully sent");
            return response;
    }
}
