package com.example.upload.controller;

import com.example.upload.service.UploadService;
import com.example.upload.utils.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

public class main {

    public static void main(String[] args) {
        String imageUrl = "http://localhost:8888/files/2020-02/7354c808-e765-435a-859e-a70b0aca0c56.png";
        System.out.printf(imageUrl.substring(imageUrl.lastIndexOf("/") + 1));
    }
}
