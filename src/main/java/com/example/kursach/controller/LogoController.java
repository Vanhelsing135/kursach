package com.example.kursach.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

@RestController
@RequestMapping("/kursach")
@RequiredArgsConstructor
@Slf4j
public class LogoController {
    @Value("${external.api.key}")
    private String apiKey;
    @GetMapping("/image-proxy")
    public ResponseEntity<byte[]> proxyImage(@RequestParam String url) throws IOException {

        URL imageUrl = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) imageUrl.openConnection();

        connection.setRequestMethod("GET");
        connection.setRequestProperty("X-Auth-Token", apiKey);
        connection.setConnectTimeout(10000);
        connection.setReadTimeout(10000);

        try (InputStream in = connection.getInputStream()) {
            byte[] bytes = in.readAllBytes();

            String contentType = connection.getContentType();

            return ResponseEntity.ok()
                    .header("Content-Type", contentType != null ? contentType : "image/png")
                    .body(bytes);
        }
    }
}