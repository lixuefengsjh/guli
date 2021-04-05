package com.lxf.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.UUID;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectResult;
import com.lxf.config.OssConfig;
import com.lxf.entity.GuliException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


@Service
@Slf4j
public class OssService {
    @Autowired
    private OssConfig ossConfig;

    public String uploadFile(MultipartFile file) {
        String path= DateUtil.format(DateUtil.date(), "yyyy-MM-dd")+ File.separator;

        if (null == file) {
            throw new GuliException(9999, "oss", "用户上传文件为空");
        }
        OSS ossClient = new OSSClientBuilder().build(ossConfig.getEndpoint(), ossConfig.getAccessKeyId(), ossConfig.getAccessKeySecret());

        // 填写网络流地址。
        InputStream inputStream = null;
        //防止文件覆盖，所以用了uuid
        String resultPath=path+ UUID.fastUUID()+file.getOriginalFilename();
        try {
            inputStream = file.getInputStream();
            // 填写Bucket名称和Object完整路径。Object完整路径中不能包含Bucket名称。
            PutObjectResult putObjectResult = ossClient.putObject(ossConfig.getBucketName(),resultPath, inputStream);
            // 关闭OSSClient
        } catch (IOException e) {
            log.error("文件上传过出现异常");
            e.printStackTrace();
        }finally {
            ossClient.shutdown();
        }
        try {
            resultPath= URLEncoder.encode(resultPath,"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "https://lxfoos.oss-cn-beijing.aliyuncs.com/"+resultPath;
    }
}
