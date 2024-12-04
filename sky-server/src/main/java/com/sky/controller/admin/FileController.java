package com.sky.controller.admin;


import com.sky.result.Result;
import com.sky.service.FileService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Api("上传图片")
@RestController
@RequestMapping("/admin/common")
public class FileController {
    @Autowired
   private FileService fileService;

    @PostMapping("/upload")
    public Result<String> upload(MultipartFile file) {
        log.info("上传图片文件：{}", file.getOriginalFilename());
        String url=fileService.uploadFile(file);

       return Result.success(url);
    }
}
