package com.baizhi.zyp.controller;

import com.baizhi.zyp.entity.User;
import com.baizhi.zyp.service.UserService;
import com.baizhi.zyp.util.PhoneCodesUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.util.HashMap;

/**
 * Created by Intellij IDEA.
 *
 * @author 赵玉鹏
 * <p>
 * Date:  2020/3/28
 */
@Controller
@RequestMapping("user")
public class UserController {
    @Resource
    UserService userService;
    @ResponseBody
    @RequestMapping("show")
    public HashMap<String,Object> userShow(Integer page,Integer rows){

        HashMap<String, Object> map = userService.selectUserByPage(page, rows);
        return map;
    }
    @ResponseBody
    @RequestMapping("edit")
    public String edit(User user,String oper){
        String uuid=null;
        if ("add".equals(oper)){

                uuid = userService.add(user);

        }
        if ("edit".equals(oper)){
        //    System.out.println(user.getHeadImg().equals(""));
             if (!user.getHeadImg().equals("")) {

                 uuid = userService.update(user);
                 System.out.println("uuid");
             }else {
                 user.setHeadImg(null);
                 userService.update(user);
                 System.out.println("没uuid");
             }
        }
        if ("del".equals(oper)){
            userService.deleteByid(user.getId());
        }
        if("status".equals(oper)){

            userService.update(user);
        }
        return uuid;
    }
    //文件上传
    @ResponseBody
    @RequestMapping("upload")
    public void upload (MultipartFile headImg , String id , HttpServletRequest request){
   //上传到本地
        //userService.upload(headImg, id, request);
    //上传到阿里云
        userService.uploadALiYun(headImg, id, request);
    }
    @ResponseBody
    @RequestMapping("sendPhoneCode")
    public String sendPhoneCode(String phoneNum){
        Integer random  = (int) ((Math.random() * 9 + 1) * 10000);
        String randoms = random.toString();
        System.out.println(randoms);
        String s = PhoneCodesUtil.sendCode(phoneNum, randoms);
        return s;
    }
}
