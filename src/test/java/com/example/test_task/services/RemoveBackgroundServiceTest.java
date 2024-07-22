package com.example.test_task.services;

import com.example.test_task.clients.RemoveBackgroundHttpClient;
import com.example.test_task.entities.Image;
import lombok.SneakyThrows;
import org.apache.coyote.BadRequestException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RemoveBackgroundServiceTest {
    @Mock
    private RemoveBackgroundHttpClient httpClient;
    @InjectMocks
    private RemoveBackgroundService removeBackgroundService;

    @Nested
    public class RemoveBackground {
        @Test
        @SneakyThrows
        void shouldSaveImageWhenResponseStatusIs200() {
            MockMultipartFile file = new MockMultipartFile("file", "filename.jpg",
                            "image/jpeg", "test image content".getBytes());
            byte[] bytes = file.getBytes();
            String encodedImage = Base64.getEncoder().encodeToString(bytes);
            String filePath = "src/main/resources/public/images/1_filename.jpg";
            String url = "http://localhost/images/1_filename.jpg";
            Path path = Path.of(filePath);
            Image expectedImage = new Image("1_filename.jpg", url);

            HttpResponse<byte[]> mockResponse = mock(HttpResponse.class);
            when(mockResponse.statusCode()).thenReturn(200);
            when(mockResponse.body()).thenReturn(bytes);
            when(httpClient.removeBackground(encodedImage)).thenReturn(mockResponse);

            Image actualImage = removeBackgroundService.removeBackground(file, 1L, "localhost");

            Assertions.assertTrue(Files.exists(path));
            byte[] actualContent = Files.readAllBytes(path);
            Assertions.assertArrayEquals(bytes, actualContent);
            Assertions.assertEquals(expectedImage, actualImage);

            Files.deleteIfExists(path);
        }

        @Test
        @SneakyThrows
        void shouldThrowExceptionWhenResponseStatusIs402() {
            MockMultipartFile file = new MockMultipartFile("file", "filename.jpg",
                    "image/jpeg", "test image content".getBytes());
            byte[] bytes = file.getBytes();
            String encodedImage = Base64.getEncoder().encodeToString(bytes);
            String filePath = "src/main/resources/public/images/1_filename.jpg";
            Path path = Path.of(filePath);

            HttpResponse<byte[]> mockResponse = mock(HttpResponse.class);
            when(mockResponse.statusCode()).thenReturn(402);
            when(httpClient.removeBackground(encodedImage)).thenReturn(mockResponse);

            Exception exception = Assertions.assertThrows(BadRequestException.class,
                    () -> removeBackgroundService.removeBackground(file, 1L, "localhost"));
            Assertions.assertEquals("There is no more credits for API request", exception.getMessage());
            Assertions.assertFalse(Files.exists(path));

            Files.deleteIfExists(path);
        }

        @Test
        @SneakyThrows
        void shouldThrowExceptionWhenResponseStatusIsNeither200Nor402() {
            MockMultipartFile file = new MockMultipartFile("file", "filename.jpg",
                    "image/jpeg", "test image content".getBytes());
            byte[] bytes = file.getBytes();
            String encodedImage = Base64.getEncoder().encodeToString(bytes);
            String filePath = "src/main/resources/public/images/1_filename.jpg";
            Path path = Path.of(filePath);

            HttpResponse<byte[]> mockResponse = mock(HttpResponse.class);
            when(mockResponse.statusCode()).thenReturn(405);
            when(httpClient.removeBackground(encodedImage)).thenReturn(mockResponse);

            Exception exception = Assertions.assertThrows(BadRequestException.class,
                    () -> removeBackgroundService.removeBackground(file, 1L, "localhost"));
            Assertions.assertEquals("Image could not be saved", exception.getMessage());
            Assertions.assertFalse(Files.exists(path));

            Files.deleteIfExists(path);
        }
    }
}
