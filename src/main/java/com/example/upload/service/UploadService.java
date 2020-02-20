package com.example.upload.service;

import com.example.upload.entity.Upload;
import com.example.upload.repository.UploadRepository;
import com.example.upload.utils.CommUtil;
import com.example.upload.utils.Result;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;

@Service
public class UploadService {

    @Resource
    private UploadRepository uploadRepository;

    public Result upload(MultipartFile file) {
        try {
            return Result.ok(uploadRepository.save(Upload.builder().fileUrl(CommUtil.Method.uploadImage(file)).build()));
        } catch (IOException e) {
            e.printStackTrace();
            return Result.fail("上传图片异常，请联系管理员！");
        }
    }

    @SneakyThrows
    public Result download(String fileUrl) {
        if(CommUtil.Method.downloadFile(fileUrl)){
            return Result.ok("下载成功！");
        }else{
            return Result.fail("下载失败！");
        }
    }
}
