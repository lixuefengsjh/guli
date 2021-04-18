package com.lxf;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadVideoRequest;
import com.aliyun.vod.upload.resp.UploadVideoResponse;

/**
 * 以下Java示例代码演示了如何在服务端上传媒资文件至视频点播，媒资类型支持音频、视频和图片。
 * <p>
 * 一、音视频上传目前支持4种方式上传：
 *
 * 1.上传本地文件，使用分片上传，并支持断点续传，参见testUploadVideo函数。
 * 1.1 当断点续传关闭时，最大支持上传任务执行时间为3000秒，具体可上传文件大小与您的网络带宽及磁盘读写能力有关。
 * 1.2 当断点续传开启时，最大支持48.8TB的单个文件，注意，断点续传开启后，上传任务执行过程中，同时会将当前上传位置写入本地磁盘文件，影响您上传文件的速度，请您根据文件大小选择是否开启
 *
 * 2.上传网络流，可指定文件URL进行上传，支持断点续传，最大支持48.8TB的单个文件。
 *   该上传方式需要先将网络文件下载到本地磁盘，再进行上传，所以要保证本地磁盘有充足的空间。参见testUploadURLStream函数。
 *
 * 3.上传文件流，可指定本地文件进行上传，不支持断点续传，最大支持5GB的单个文件。参见testUploadFileStream函数。
 *
 * 4.流式上传，可指定输入流进行上传，支持文件流和网络流等，不支持断点续传，最大支持5GB的单个文件。参见testUploadStream函数。
 * <p>
 *
 * 二、图片上传目前支持2种方式上传：
 * 1.上传本地文件，不支持断点续传，最大支持5GB的单个文件，参见testUploadImageLocalFile函数
 * 2.上传文件流和网络流，InputStream参数必选，不支持断点续传，最大支持5GB的单个文件。参见testUploadImageStream函数。
 * 注：图片上传完成后，会返回图片ID和图片地址，也可通过GetImageInfo查询图片信息，参见接口文档 https://help.aliyun.com/document_detail/89742.html
 * <p>
 *
 * 三、m3u8文件上传目前支持2种方式：
 * 1.上传本地m3u8音视频文件（包括所有分片文件）到点播，需指定本地m3u8索引文件地址和所有分片地址。
 * 2.上传网络m3u8音视频文件（包括所有分片文件）到点播，需指定m3u8索引文件和分片文件的URL地址。
 *
 * 注：
 * 1) 上传网络m3u8音视频文件时需要保证地址可访问，如果有权限限制，请设置带签名信息的地址，且保证足够长的有效期，防止地址无法访问导致上传失败
 * 2) m3u8文件上传暂不支持进度回调
 * <p>
 *
 * 四、上传进度回调通知：
 * 1.默认上传进度回调函数：视频点播上传SDK内部默认开启上传进度回调函数，输出不同事件通知的日志，您可以设置关闭该上传进度通知及日志输出；
 * 2.自定义上传进度回调函数：您可根据自已的业务场景重新定义不同事件处理的方式，只需要修改上传回调示例函数即可。
 * <p>
 *
 * 五、辅助媒资上传目前支持2种方式：
 * 1.上传本地文件，不支持断点续传，最大支持5GB的单个文件，参见testUploadAttachedMediaLocalFile函数
 * 2.上传文件流和网络流，InputStream参数必选，不支持断点续传，最大支持5GB的单个文件。参见testUploadAttachedMediaStream函数。
 * <p>
 *
 * 六、支持STS方式上传：
 * 1.您需要实现VoDRefreshSTSTokenListener接口的onRefreshSTSToken方法，用于生成STS信息，
 * 当文件上传时间超过STS过期时间时，SDK内部会定期调用此方法刷新您的STS信息进行后续文件的上传。
 * <p>
 *
 * 七、可指定上传脚本部署的ECS区域(设置Request的EcsRegionId参数，取值参考存储区域标识：https://help.aliyun.com/document_detail/98194.html)，
 * 如果与点播存储（OSS）区域相同，则自动使用内网上传文件至存储，上传更快且更省公网流量
 * 由于点播API只提供外网域名访问，因此部署上传脚本的ECS服务器必须具有访问外网的权限。
 *
 * 注意：
 * 请替换示例中的必选参数，示例中的可选参数如果您不需要设置，请将其删除，以免设置无效参数值与您的预期不符。
 */

public class Test {
    //账号AK信息请填写(必选)
    private static final String accessKeyId = "LTAI4GHURXQrxTVQAVumWuqT";
    //账号AK信息请填写(必选)
    private static final String accessKeySecret = "q5ZxnxSVIMVK8WP9FR1KZtgQDjmfJ3";

    public static void main(String[] args) {
//1.音视频上传-本地文件上传
        //视频标题(必选)
        String title = "测试标题";
        //本地文件上传和文件流上传时，文件名称为上传文件绝对路径，如:/User/sample/文件名称.mp4 (必选)
        //文件名必须包含扩展名
        String fileName = "6 - What If I Want to Move Faster.mp4";
        //本地文件上传
        testUploadVideo(accessKeyId, accessKeySecret, title, fileName);

    }
    private static void testUploadVideo(String accessKeyId, String accessKeySecret, String title, String fileName) {
        UploadVideoRequest request = new UploadVideoRequest(accessKeyId, accessKeySecret, title, fileName);
        /*可指定分片上传时每个分片的大小，默认为1M字节*/
        request.setPartSize(1 * 1024 * 1024L);
        /*可指定分片上传时的并发线程数，默认为1（注：该配置会占用服务器CPU资源，需根据服务器情况指定）*/
        request.setTaskNum(1);
        /*是否开启断点续传，默认断点续传功能关闭。当网络不稳定或者程序崩溃时，再次发起相同上传请求，可以继续未完成的上传任务，适用于超时3000秒仍不能上传完成的大文件。
        注意: 断点续传开启后，会在上传过程中将上传位置写入本地磁盘文件，影响文件上传速度，请您根据实际情况选择是否开启*/
        request.setEnableCheckpoint(false);
        /*OSS慢请求日志打印超时时间，是指每个分片上传时间超过该阈值时会打印debug日志，如果想屏蔽此日志，请调整该阈值。单位: 毫秒，默认为300000毫秒*/
        //request.setSlowRequestsThreshold(300000L);
        /*可指定每个分片慢请求时打印日志的时间阈值，默认为300s*/
        //request.setSlowRequestsThreshold(300000L);
        /*是否使用默认水印（可选），指定模板组ID时，根据模板组配置确定是否使用默认水印*/
        //request.setIsShowWaterMark(true);
        /*自定义消息回调设置（可选），参数说明参考文档 https://help.aliyun.com/document_detail/86952.html#UserData*/
        // request.setUserData("{\"Extend\":{\"test\":\"www\",\"localId\":\"xxxx\"},\"MessageCallback\":{\"CallbackURL\":\"http://test.test.com\"}}");

        /*视频分类ID（可选）*/
        //request.setCateId(0);
        /*视频标签,多个用逗号分隔（可选）*/
        //request.setTags（"标签1,标签2"）;
        /*视频描述（可选）*/
        //request.setDescription("视频描述");
        /*封面图片（可选）*/
        //request.setCoverURL("http://cover.sample.com/sample.jpg");
        /*模板组ID（可选）*/
        //request.setTemplateGroupId("8c4792cbc8694*****d5330e56a33d");
        /*点播服务接入点*/
        //request.setApiRegionId("cn-shanghai");
        /*ECS部署区域，如果与点播存储（OSS）区域相同，则自动使用内网上传文件至存储*/
        // request.setEcsRegionId("cn-shanghai");
        /*存储区域（可选）*/
        //request.setStorageLocation("in-2017032*****18266-5sejdln9o.oss-cn-shanghai.aliyuncs.com");
        /*开启默认上传进度回调*/
        // request.setPrintProgress(true);
        /*设置自定义上传进度回调（必须继承 ProgressListener）*/
        // request.setProgressListener(new PutObjectProgressListener());
        UploadVideoImpl uploader = new UploadVideoImpl();
        UploadVideoResponse response = uploader.uploadVideo(request);
        System.out.print("RequestId="  + response.getRequestId() +  "\n");  //请求视频点播服务的请求ID
        if (response.isSuccess()) {
            System.out.print("VideoId=" +  response.getVideoId() +  "\n");
        } else {
            /* 如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因 */
            System.out.print("VideoId="+response.getVideoId()+"\n");
            System.out.print("ErrorCode="  + response.getCode()  + "\n");
            System.out.print("ErrorMessage="  + response.getMessage() +  "\n");
        }
    }

}