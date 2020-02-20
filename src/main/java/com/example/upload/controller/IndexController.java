package com.example.upload.controller;

import com.example.upload.service.UploadService;
import com.example.upload.utils.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@RestController
public class IndexController {

    @Resource
    private UploadService uploadService;

    @GetMapping
    public String hello(){
        return "Hello!";
    }

    @GetMapping("/upload")
    public Result upload(MultipartFile file) {
        return uploadService.upload(file);
    }

    @GetMapping("/download")
    public Result download(@RequestParam String fileUrl) {
        return uploadService.download(fileUrl);
    }
}
