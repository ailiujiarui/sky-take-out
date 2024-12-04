package com.sky.service.impl;

import com.sky.properties.AliOssProperties;
import com.sky.service.FileService;
import com.sky.utils.AliOssUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
@Slf4j
public class FileServiceImpl implements FileService {
    @Autowired
    private AliOssUtil aliOssUtil;


    @Override
    public String uploadFile(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
       String  extension = originalFilename.substring(originalFilename.lastIndexOf("."));//切割出文件扩展名
        UUID uuid = UUID.randomUUID();
        String objectname = uuid.toString()+extension;
        String url=null;
        try {
            url = aliOssUtil.upload(file.getBytes(), objectname);
            return url;
        } catch (IOException e) {
            log.error("文件上传失败");
            return null;
        }
    }
}
