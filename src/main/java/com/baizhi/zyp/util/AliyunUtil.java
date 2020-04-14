package com.baizhi.zyp.util;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

/**
 * Created by Intellij IDEA.
 *
 * @author 赵玉鹏
 * <p>
 * Date:  2020/4/1
 */
public class AliyunUtil {
    private static String endpoint = "http://oss-cn-beijing.aliyuncs.com";
    // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
    private static String accessKeyId = "LTAI4FeTqYGG8rcpGRjVP8uL";
    private static String accessKeySecret = "YlqD6b5gISJFaK0Pv7ybf4C3e4Dg6S";
    /*
     * 上传本地文件
     *fileName指定文件名   目录名/文件名
     * localFile指定本地文件路径
     * */

    public static void uploadFile(String fileName, String localFile) {
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        //工作存储空间
        String bucket = "yxue-zhao";
        // String fileName="photo/车窗外.jpg";//指定文件名
        //  String localFile="C:\\Users\\王墨\\Desktop\\新建文件夹\\车窗外.jpg";//指定本地文件路径
        // 创建PutObjectRequest对象。
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, fileName, new File(localFile));
        // 上传文件。
        ossClient.putObject(putObjectRequest);
        // 关闭OSSClient。
        ossClient.shutdown();
    }
    /**
    * headImg 指定MultipartFile类型的文件
    * fileName 文件名上传到云端的文件名
    * */
    public static void uploadFileByte(MultipartFile headImg,String fileName) {
        //将文件转为字节数组
        byte[] bytes = new byte[0];
        try {
            bytes = headImg.getBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //工作存储空间
        String bucket = "yxue-zhao";
        // String fileName="photo/车窗外.jpg";//指定文件名
        //  String localFile="C:\\Users\\王墨\\Desktop\\新建文件夹\\车窗外.jpg";//指定本地文件路径


        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId,accessKeySecret);

        // 上传Byte数组。
        //     byte[] content = "Hello OSS".getBytes();
        ossClient.putObject(bucket, fileName, new ByteArrayInputStream(bytes));

        // 关闭OSSClient。
        ossClient.shutdown();
    }
    public static void deleteFile(String objectName) {

        String bucketName = "yxue-zhao";
    //    String objectName = "车窗外.jpg";

// 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

// 删除文件。如需删除文件夹，请将ObjectName设置为对应的文件夹名称。如果文件夹非空，则需要将文件夹下的所有object删除后才能删除该文件夹。
        ossClient.deleteObject(bucketName, objectName);

// 关闭OSSClient。
        ossClient.shutdown();
    }
}
