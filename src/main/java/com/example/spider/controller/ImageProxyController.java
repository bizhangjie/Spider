package com.example.spider.controller;

/**
 * @Author 章杰
 * @Date 2024/2/29 18:35
 * @Version 1.0.0
 */
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/image-proxy")
public class ImageProxyController {

    private final RestTemplate restTemplate;

    public ImageProxyController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/")
    public ResponseEntity<byte[]> proxyImage(@RequestParam String img) {
        // Use RestTemplate to fetch the image bytes from the original URL
        byte[] imageBytes = restTemplate.getForObject(img, byte[].class);

        // Set response headers to indicate the image content type
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);  // or MediaType.IMAGE_PNG, etc.

        // Return the image bytes and headers in the ResponseEntity
        return ResponseEntity.ok().headers(headers).body(imageBytes);
    }
}
