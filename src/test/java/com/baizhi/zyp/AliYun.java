package com.baizhi.zyp;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.Bucket;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.PutObjectRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;
import java.util.List;

/**
 * Created by Intellij IDEA.
 *
 * @author 赵玉鹏
 * <p>
 * Date:  2020/3/31
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AliYun {
  private   String endpoint = "http://oss-cn-beijing.aliyuncs.com";
    // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
  private   String accessKeyId = "LTAI4FeTqYGG8rcpGRjVP8uL";
  private   String accessKeySecret = "YlqD6b5gISJFaK0Pv7ybf4C3e4Dg6S";

    @Test
    public void creatBucket() {
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = "http://oss-cn-beijing.aliyuncs.com";
        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
        String accessKeyId = "LTAI4FeTqYGG8rcpGRjVP8uL";
        String accessKeySecret = "YlqD6b5gISJFaK0Pv7ybf4C3e4Dg6S";
        String bucketName = "yingxue-asdf";

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 创建存储空间。
        ossClient.createBucket(bucketName);

        // 关闭OSSClient。
        ossClient.shutdown();
    }
    @Test
    public void uploadFile() {
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = "http://oss-cn-beijing.aliyuncs.com";
        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
        String accessKeyId = "LTAI4FeTqYGG8rcpGRjVP8uL";
        String accessKeySecret = "YlqD6b5gISJFaK0Pv7ybf4C3e4Dg6S";

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        String bucket="yxue-zhao";//工作存储空间
        String fileName="photo/车窗外.jpg";//指定文件名
        String localFile="C:\\Users\\王墨\\Desktop\\新建文件夹\\车窗外.jpg";//指定本地文件路径



        // 创建PutObjectRequest对象。
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, fileName, new File(localFile));

        // 如果需要上传时设置存储类型与访问权限，请参考以下示例代码。
        // ObjectMetadata metadata = new ObjectMetadata();
        // metadata.setHeader(OSSHeaders.OSS_STORAGE_CLASS, StorageClass.Standard.toString());
        // metadata.setObjectAcl(CannedAccessControlList.Private);
        // putObjectRequest.setMetadata(metadata);

        // 上传文件。
        ossClient.putObject(putObjectRequest);

        // 关闭OSSClient。
        ossClient.shutdown();
    }
    @Test
    public void querAllBucket(){
        // Endpoint以杭州为例，其它Region请按实际情况填写。
     //   String endpoint = "http://oss-cn-hangzhou.aliyuncs.com";
// 阿里//云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
     //   String accessKeyId = "<yourAccessKeyId>";
       // String accessKeySecret = "<yourAccessKeySecret>";

// 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

// 列举存储空间。
        List<Bucket> buckets = ossClient.listBuckets();
        for (Bucket bucket : buckets) {
            System.out.println(" - " + bucket.getName());
        }

// 关闭OSSClient。
        ossClient.shutdown();
    }
    @Test
    public void  downLoad(){
        // Endpoint以杭州为例，其它Region请按实际情况填写。
       // String endpoint = "http://oss-cn-hangzhou.aliyuncs.com";
// 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
      //  String accessKeyId = "<yourAccessKeyId>";
      //  String accessKeySecret = "<yourAccessKeySecret>";
        String bucketName = "yxue-zhao";
        String objectName = "photo/车窗外.jpg";
        String localFile ="C:\\Users\\王墨\\Desktop\\databases-incognito\\"+objectName;
// 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

// 下载OSS文件到本地文件。如果指定的本地文件存在会覆盖，不存在则新建。
        ossClient.getObject(new GetObjectRequest(bucketName, objectName), new File(localFile));

// 关闭OSSClient。
        ossClient.shutdown();
    }
@Test
    public void deleteFile() {
    // Endpoint以杭州为例，其它Region请按实际情况填写。
    //String endpoint = "http://oss-cn-hangzhou.aliyuncs.com";
// 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
 //  // String accessKeyId = "<yourAccessKeyId>";
   // String accessKeySecret = "<yourAccessKeySecret>";
    String bucketName = "yxue-zhao";
    String objectName = "车窗外.jpg";

// 创建OSSClient实例。
    OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

// 删除文件。如需删除文件夹，请将ObjectName设置为对应的文件夹名称。如果文件夹非空，则需要将文件夹下的所有object删除后才能删除该文件夹。
    ossClient.deleteObject(bucketName, objectName);

// 关闭OSSClient。
    ossClient.shutdown();
    }
@Test
    public void updatea() throws FileNotFoundException {
        // Endpoint以杭州为例，其它Region请按实际情况填写。
      //  String endpoint = "http://oss-cn-hangzhou.aliyuncs.com";
// 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，创建并使用RAM子账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建。
       // String accessKeyId = "<yourAccessKeyId>";
       // String accessKeySecret = "<yourAccessKeySecret>";

// 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        String bucketName = "yxue-zhao";
        String objectName = "photo/3.jpg";
         String localFile="C:\\Users\\王墨\\Desktop\\新建文件夹\\3.jpg";
        // 上传文件流。
        InputStream inputStream = new FileInputStream(localFile);
        ossClient.putObject(bucketName, objectName, inputStream);

// 关闭OSSClient。
        ossClient.shutdown();
    }

}
