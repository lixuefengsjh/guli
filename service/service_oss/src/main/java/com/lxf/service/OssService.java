package com.lxf.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.UUID;
import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectResult;
import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.vod.model.v20170321.CreateUploadVideoRequest;
import com.aliyuncs.vod.model.v20170321.CreateUploadVideoResponse;
import com.lxf.config.OssConfig;
import com.lxf.entity.GuliException;
import com.lxf.entity.UploadInfo;
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

    public void testUploadStream(MultipartFile file) {
        log.info(String.format("上传的文件OriginalFilename:【%s】,Name:【%s】",file.getOriginalFilename(),file.getName()));
        try {
            testUploadStream(ossConfig.getAccessKeyId(), ossConfig.getAccessKeySecret(), file.getOriginalFilename(), file.getOriginalFilename(), file.getInputStream());
        } catch (IOException e) {
            log.error("获取文件流失败");
            e.printStackTrace();
        }
    }
    /**
     * 流式上传接口
     *
     * @param accessKeyId
     * @param accessKeySecret
     * @param title
     * @param fileName
     * @param inputStream
     */
    private  void testUploadStream(String accessKeyId, String accessKeySecret, String title, String fileName, InputStream inputStream) {
        UploadStreamRequest request = new UploadStreamRequest(accessKeyId, accessKeySecret, title, fileName, inputStream);
        /* 是否使用默认水印(可选)，指定模板组ID时，根据模板组配置确定是否使用默认水印*/
        request.setShowWaterMark(true);
        /* 自定义消息回调设置，参数说明参考文档 https://help.aliyun.com/document_detail/86952.html#UserData */
        //request.setUserData(""{\"Extend\":{\"test\":\"www\",\"localId\":\"xxxx\"},\"MessageCallback\":{\"CallbackURL\":\"http://test.test.com\"}}"");
        /* 视频分类ID(可选) */
        request.setCateId(Long.valueOf(1000294072));
        /* 视频标签,多个用逗号分隔(可选) */
        //request.setTags("标签1,标签2");
        /* 视频描述(可选) */
        //request.setDescription("视频描述");
        /* 封面图片(可选) */
        //request.setCoverURL("http://cover.sample.com/sample.jpg");
        /* 模板组ID(可选) */
        request.setTemplateGroupId("555b103783544bce68be377c09b8d000");
        /* 工作流ID(可选) */
        //request.setWorkflowId("d4430d07361f0*be1339577859b0177b");
        /* 存储区域(可选) */
        //request.setStorageLocation("in-201703232118266-5sejdln9o.oss-cn-shanghai.aliyuncs.com");
        /* 开启默认上传进度回调 */
        // request.setPrintProgress(true);
        /* 设置自定义上传进度回调 (必须继承 VoDProgressListener) */
        // request.setProgressListener(new PutObjectProgressListener());
        /* 设置应用ID*/
        //request.setAppId("app-1000000");
        /* 点播服务接入点 */
        //request.setApiRegionId("cn-shanghai");
        /* ECS部署区域*/
        // request.setEcsRegionId("cn-shanghai");
        UploadVideoImpl uploader = new UploadVideoImpl();
        UploadStreamResponse response = uploader.uploadStream(request);
        System.out.print("RequestId=" + response.getRequestId() + "\n");  //请求视频点播服务的请求ID
        if (response.isSuccess()) {
            System.out.print("成功");
            System.out.print("VideoId=" + response.getVideoId() + "\n");
        } else { //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
            System.out.print("失败");
            System.out.print("VideoId=" + response.getVideoId() + "\n");
            System.out.print("ErrorCode=" + response.getCode() + "\n");
            System.out.print("ErrorMessage=" + response.getMessage() + "\n");
        }
    }

    public UploadInfo getUploadInfo() {
        UploadInfo uploadInfo = new UploadInfo();
        DefaultAcsClient client = initVodClient(ossConfig.getAccessKeyId(), ossConfig.getAccessKeySecret());
        CreateUploadVideoResponse response ;
        try {
            response = createUploadVideo(client);
            uploadInfo.setUploadAddress(response.getUploadAddress());
            uploadInfo.setUploadAuth(response.getUploadAuth());
            uploadInfo.setVideoId(response.getVideoId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return uploadInfo;
    }

    public DefaultAcsClient initVodClient(String accessKeyId, String accessKeySecret) throws ClientException {
        String regionId = "cn-shanghai";  // 点播服务接入区域
        DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);
        DefaultAcsClient client = new DefaultAcsClient(profile);
        return client;
    }
    public  CreateUploadVideoResponse createUploadVideo(DefaultAcsClient client) throws Exception {
        CreateUploadVideoRequest request = new CreateUploadVideoRequest();
        request.setTitle("this is a sample");
        request.setFileName("filename.mp4");

        //UserData，用户自定义设置参数，用户需要单独回调URL及数据透传时设置(非必须)
        //JSONObject userData = new JSONObject();

        //UserData回调部分设置
        //JSONObject messageCallback = new JSONObject();
        //messageCallback.put("CallbackURL", "http://192.168.0.0/16");
        //messageCallback.put("CallbackType", "http");
        //userData.put("MessageCallback", messageCallback.toJSONString());

        //UserData透传数据部分设置
        //JSONObject extend = new JSONObject();
        //extend.put("MyId", "user-defined-id");
        //userData.put("Extend", extend.toJSONString());

        //request.setUserData(userData.toJSONString());

        return client.getAcsResponse(request);
    }
}
