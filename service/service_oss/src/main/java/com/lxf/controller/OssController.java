package com.lxf.controller;

import cn.hutool.http.server.HttpServerRequest;
import com.lxf.config.OssConfig;
import com.lxf.entity.R;
import com.lxf.service.OssService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;

@RestController
@Api(tags = "oss操作基本api")
@RequestMapping("oss")
@Slf4j
@CrossOrigin
public class OssController {
    @Autowired
    private OssService ossServiceImpl;
    @PostMapping("/upload")
    @ApiOperation("文件上传")
    public R<String> uploadFile(HttpServerRequest req, @RequestParam("file") MultipartFile file){
        log.info(String.format("进入方法OssController=====》uploadFile方法"));
        return R.ok().data(ossServiceImpl.uploadFile(file));
    }


}
