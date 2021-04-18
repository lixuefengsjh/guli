package com.lxf.controller;

import cn.hutool.http.server.HttpServerRequest;
import com.lxf.entity.R;
import com.lxf.entity.UploadInfo;
import com.lxf.service.OssService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    @PostMapping("/uploadVod")
    @ApiOperation("视频音频文件上传")
    public R<String> testUploadStream( @RequestParam("file") MultipartFile file){
        log.info(String.format("进入方法OssController=====》testUploadStream方法"));
        ossServiceImpl.testUploadStream(file);
        return R.ok();
    };
    @GetMapping("/getAuth")
    @ApiOperation("获取视频音频文件上传相关凭证信息")
    public R<UploadInfo> getUploadInfo(){
        return R.ok().data(ossServiceImpl.getUploadInfo());
    }
    @GetMapping("/getAuth/{videoId}")
    @ApiOperation("获取视频音频文件上传相关凭证信息")
    public R<UploadInfo> getReFreshUploadInfo(@PathVariable("videoId") String videoId){
        return R.ok().data(ossServiceImpl.getReFreshUploadInfo(videoId));
    }

}
