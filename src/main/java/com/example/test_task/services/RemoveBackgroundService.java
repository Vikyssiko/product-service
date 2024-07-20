package com.example.test_task.services;

import com.example.test_task.clients.RemoveBackgroundHttpClient;
import com.example.test_task.entities.Image;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.Base64;

@Service
@Slf4j
@RequiredArgsConstructor
public class RemoveBackgroundService {
    private final RemoveBackgroundHttpClient httpClient;

    public Image removeBackground(MultipartFile file, Long productId, String host) throws IOException, InterruptedException {
        byte[] bytes = file.getBytes();
        String encodedImage = Base64.getEncoder().encodeToString(bytes);
        String imageName = productId + "_" + file.getOriginalFilename();
        HttpResponse<byte[]> response = httpClient.removeBackground(encodedImage);
        if (response.statusCode() == 200) {
            String path = "src/main/resources/public/images/" + imageName;
            String url = String.join("/", "http:/", host, "images", imageName);
            try (FileOutputStream outputStream = new FileOutputStream(path)) {
                outputStream.write(response.body());
            }
            log.info("Image has been saved to " + path);
            return new Image(imageName, url);
        } else if (response.statusCode() == 402) {
            throw new BadRequestException("There is no more credits for API request");
        } else {
            throw new BadRequestException("Image could not be saved");
        }
    }
}
