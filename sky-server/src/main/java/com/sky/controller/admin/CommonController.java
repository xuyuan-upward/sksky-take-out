package com.sky.controller.admin;

import com.sky.result.Result;
import com.sky.utils.AliOssUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/admin/common")
@Api(tags = "通用的接口")
@Slf4j
public class CommonController {
    @Autowired
    private AliOssUtil aliOssUtil;

    @PostMapping("/upload")
    @ApiOperation("文件的上传")
    public Result<String> upload(MultipartFile file) {
        log.info("文件上传：{}", file);
        //原始文件名
        String originalFilename = file.getOriginalFilename();
        // 获取文件的后缀名
        String exteion = originalFilename.substring(originalFilename.lastIndexOf("."));

        //新的文件名
        String newFlieName = UUID.randomUUID().toString() + exteion;
        try {
            //返回上传阿里云的文件路径（文件请求的文件路径）
            String url = aliOssUtil.upload(file.getBytes(), newFlieName);
            return Result.success(url);
        } catch (IOException e) {
            log.info("文件上传失败");
        }
        return Result.error("文件上传失败");
    }
}
