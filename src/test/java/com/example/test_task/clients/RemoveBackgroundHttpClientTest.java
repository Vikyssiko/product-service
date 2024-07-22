package com.example.test_task.clients;

import com.example.test_task.configs.ApiConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RemoveBackgroundHttpClientTest {
    @Mock
    private ApiConfig apiConfig;
    @Mock
    private HttpClient client;
    @InjectMocks
    private RemoveBackgroundHttpClient httpClient;

    @Nested
    public class RemoveBackground {
        @Test
        void shouldReturn200WhenApiKeyExists() throws IOException, InterruptedException {
            HttpResponse<byte[]> mockResponse = mock(HttpResponse.class);
            when(apiConfig.getApiKey()).thenReturn("test-api-key");
            when(mockResponse.statusCode()).thenReturn(200);
            when(client.send(any(HttpRequest.class), eq(HttpResponse.BodyHandlers.ofByteArray())))
                    .thenReturn(mockResponse);

            HttpResponse<byte[]> response = httpClient.removeBackground("image");

            Assertions.assertEquals(200, response.statusCode());
        }
    }
}
