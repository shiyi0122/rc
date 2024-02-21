package com.hna.hka.archive.management.system.util;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.CreateBucketRequest;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author zhang
 * @Date 2022/1/17 17:15
 */
@Component
public class FileUploadUtil {

    @Value("${aliyun.oss.endpoint}") //读取配置文件
    private String endpoint;

    @Value("${aliyun.oss.accessKeyId}")
    private String accessKeyId;

    @Value("${aliyun.oss.accessKeySecret}")
    private String accessKeySecret;

    @Value("${aliyun.oss.bucketName}")
    private String bucketName;



    public String upload(MultipartFile file , String filename) {

        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        try {
            //容器不存在，就创建
            if (!ossClient.doesBucketExist(bucketName)) {
                ossClient.createBucket(bucketName);
                CreateBucketRequest createBucketRequest = new CreateBucketRequest(bucketName);
                createBucketRequest.setCannedACL(CannedAccessControlList.PublicRead);
                ossClient.createBucket(createBucketRequest);
            }

            //此处设置Content-type = image/jpg(解决上传图片访问链接强制性下载的情况,不设置则访问的路径打开效果是下载)
            ObjectMetadata objectMetadata = new ObjectMetadata();
//            objectMetadata.setContentType("image/jpg");
            objectMetadata.setContentType(getcontentType(filename.substring(filename.lastIndexOf("."))));
            //上传文件
            PutObjectResult result = ossClient.putObject(bucketName, filename, file.getInputStream(),objectMetadata);
            //设置权限 这里是公开读
            ossClient.setObjectAcl(bucketName, filename, CannedAccessControlList.PublicRead);

            //关闭
            ossClient.shutdown();

            if (null != result) {
                return "https://" + bucketName + "." + endpoint + "/" + filename;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    public static String getcontentType(String FilenameExtension) {
        if (".bmp".equalsIgnoreCase(FilenameExtension)) {
            return "image/bmp";
        }
        if (".gif".equalsIgnoreCase(FilenameExtension)) {
            return "image/gif";
        }
        if (".jpeg".equalsIgnoreCase(FilenameExtension) ||
                ".jpg".equalsIgnoreCase(FilenameExtension) ||
                ".png".equalsIgnoreCase(FilenameExtension)) {
            return "image/jpg";
        }
        if (".html".equalsIgnoreCase(FilenameExtension)) {
            return "text/html";
        }
        if (".txt".equalsIgnoreCase(FilenameExtension)) {
            return "text/plain";
        }
        if (".vsd".equalsIgnoreCase(FilenameExtension)) {
            return "application/vnd.visio";
        }
        if (".pptx".equalsIgnoreCase(FilenameExtension) ||
                ".ppt".equalsIgnoreCase(FilenameExtension)) {
            return "application/vnd.ms-powerpoint";
        }
        if (".docx".equalsIgnoreCase(FilenameExtension) ||
                ".doc".equalsIgnoreCase(FilenameExtension)) {
            return "application/msword";
        }
        if (".xml".equalsIgnoreCase(FilenameExtension)) {
            return "text/xml";
        }
        if (".p12".equalsIgnoreCase(FilenameExtension)){
            return "application/x-pkcs12";
        }
        if (".mp3".equalsIgnoreCase(FilenameExtension)){
            return "audio/mp3";
        }
        if (".mp4".equalsIgnoreCase(FilenameExtension)){
            return "video/mp4";
        }
        if (".apk".equalsIgnoreCase(FilenameExtension)){
            return "application/vnd.android.package-archive";
        }
        if (".pdf".equalsIgnoreCase(FilenameExtension)){
            return "application/pdf";
        }
        if (".avi".equalsIgnoreCase(FilenameExtension)){
            return "video/avi";
        }
        if (".rm".equalsIgnoreCase(FilenameExtension)){
            return "application/vnd.rn-realmedia";
        }
        if (".rmvb".equalsIgnoreCase(FilenameExtension)){
            return "application/vnd.rn-realmedia-vbr";
        }
        if (".wmv".equalsIgnoreCase(FilenameExtension)){
            return "video/x-ms-wmv";
        }
        return "";
    }

}
