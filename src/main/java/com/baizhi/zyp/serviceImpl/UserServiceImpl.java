package com.baizhi.zyp.serviceImpl;

import com.alibaba.fastjson.JSON;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.baizhi.zyp.annotation.AddCache;
import com.baizhi.zyp.annotation.DelCache;
import com.baizhi.zyp.dao.Userdao;
import com.baizhi.zyp.entity.City;
import com.baizhi.zyp.entity.User;
import com.baizhi.zyp.entity.UserExample;
import com.baizhi.zyp.service.UserService;
import com.baizhi.zyp.util.AliyunUtil;
import com.baizhi.zyp.util.UUIDUtil;
import com.baizhi.zyp.vo.Model;
import io.goeasy.GoEasy;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Intellij IDEA.
 *
 * @author 赵玉鹏
 * <p>
 * Date:  2020/3/28
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Resource
    Userdao userdao;
    @AddCache
    @Override
    public HashMap<String, Object> selectUserByPage(Integer page,Integer rows) {
        UserExample userExample = new UserExample();
        Integer start= (page-1)*rows;
        RowBounds rowBounds = new RowBounds(start,rows);
       //分页数据
        List<User> users = userdao.selectByExampleAndRowBounds(userExample, rowBounds);
        //返回总条数
        Integer records = userdao.selectCount(new User());
        Integer total =records%rows==0?records/rows:records/rows+1;
        HashMap<String, Object> map = new HashMap<>(16);


        map.put("page",page);
        map.put("rows",users);
        map.put("records",records);
        map.put("total",total);

        return map;
    }
@DelCache
    @Override
    public String add(User user) {
        String uuid = UUIDUtil.getUUID();
        user.setId(uuid);
        user.setStatus("1");
        user.setCreateDate(new Date());
        userdao.insert(user);
        return uuid;
    }

@DelCache
    @Override
    public void upload(MultipartFile headImg, String id, HttpServletRequest request) {
        try {
            byte[] bytes = headImg.getBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }


        //1.根据相对路径获取绝对路径

        String realPath = request.getSession().getServletContext().getRealPath("/upload/photo");
        //创建目录
        File file = new File(realPath);
        //!file.exists()如果不存在路径创建文件夹
        if (!file.exists()){
            file.mkdirs();
        }
        //文件名
            String filename = headImg.getOriginalFilename();

            String newFileName = System.currentTimeMillis()+filename;

        try {

            //2.文件上传
            headImg.transferTo(new File(realPath,newFileName));

            //3.图片修改
            //修改的条件
            UserExample userExample = new UserExample();
            userExample.createCriteria().andIdEqualTo(id);

            User user = new User();
                //设置修改的结果
                user.setHeadImg(newFileName);

            //修改
            userdao.updateByExampleSelective(user,userExample);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
@DelCache
    @Override
    public void deleteByid(String id) {
        User user = new User();
        user.setId(id);
        User user1 = userdao.selectOne(user);
        String headImg = user1.getHeadImg();
        String[] img = headImg.split("/");
        String ImgName =img[img.length - 2]+"/"+img[img.length - 1];
        System.out.println("头像删除路径"+ImgName);
        AliyunUtil.deleteFile(ImgName);
        userdao.delete(user);
    }
@DelCache
    @Override
    public String update(User user) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andIdEqualTo(user.getId());

        userdao.updateByExampleSelective(user,userExample);
        return user.getId();
    }
    @AddCache
    @Override
    public User selectCountById(String id) {
        User user = new User();
        user.setId(id);
        return userdao.selectOne(user);
    }

    @Override
    public void uploadALiYun(MultipartFile headImg, String id, HttpServletRequest request) {
      //将文件转为数组
        byte[] bytes=null;
        try {
            bytes = headImg.getBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //获取文件名
        String filename = headImg.getOriginalFilename();
        String newName= System.currentTimeMillis()+filename;
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = "http://oss-cn-beijing.aliyuncs.com";
        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
        String accessKeyId = "LTAI4FeTqYGG8rcpGRjVP8uL";
        String accessKeySecret = "YlqD6b5gISJFaK0Pv7ybf4C3e4Dg6S";

        String bucketName="yxue-zhao";

        String objectName="photo/"+newName;
        String dbName="http://yxue-zhao.oss-cn-beijing.aliyuncs.com/photo/"+newName;
        // 创建OSSClient实例。http://yxue-zhao.oss-cn-beijing.aliyuncs.com/photo/3.jpg
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId,accessKeySecret);

        // 上传Byte数组。

        ossClient.putObject(bucketName, objectName, new ByteArrayInputStream(bytes));

        // 关闭OSSClient。
        ossClient.shutdown();
        //3.图片修改
        //修改的条件
        UserExample userExample = new UserExample();
        userExample.createCriteria().andIdEqualTo(id);

        User user = new User();
        //设置修改的结果
        user.setHeadImg(dbName);

        //修改
        userdao.updateByExampleSelective(user,userExample);
    }
    @AddCache
    @Override
    public ArrayList<Model> selectCitySex() {
        ArrayList<Model> models = new ArrayList<>();

        List<City> cityBoy = userdao.selectCitySex("男");

        List<City> cityGirl = userdao.selectCitySex("女");
        Model modelBoy = new Model("男孩",cityBoy);
        Model modelGirl = new Model("女孩",cityGirl);
        models.add(modelBoy);
        models.add(modelGirl);
        String content = JSON.toJSONString(models);

        //配置发送端参数 参数regionHost 服务器地址   自己的appkey
        GoEasy goEasy = new GoEasy( "http://rest-hangzhou.goeasy.io", "BC-de3f12ff1feb4992a6b5792b20b0250c");
        //配置发送消息 参数自定义
        goEasy.publish("186yingxxxx", content);
        return models;
    }


}
