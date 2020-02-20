package com.example.upload;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UploadApplicationTests {

    @Test
    void contextLoads() {
        String imageUrl = "1213,123123.jpg";
        imageUrl = imageUrl.endsWith(",") ? imageUrl.substring(0,imageUrl.length() - 1) : imageUrl;
        System.out.printf(imageUrl);
    }

}
