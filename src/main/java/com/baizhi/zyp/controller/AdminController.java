package com.baizhi.zyp.controller;

import com.baizhi.zyp.entity.Admin;
import com.baizhi.zyp.service.AdminService;
import com.baizhi.zyp.util.ImageCodeUtil;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by Intellij IDEA.
 *
 * @author 赵玉鹏
 * <p>
 * Date:  2020/3/27
 */
@Controller
@RequestMapping("admin")

public class AdminController {
    @Resource
    AdminService adminService;
    @RequestMapping("getImageCode")
    public void getImageCode(HttpServletRequest request, HttpServletResponse response){
    //获取验证码
        //存随机字符
        String securityCode = ImageCodeUtil.getSecurityCode();
        System.out.println(securityCode);
        //在作用域存code
        request.getSession().setAttribute("imageCode",securityCode);
        //根据随机字符生成图片
        BufferedImage image = ImageCodeUtil.createImage(securityCode);
        try {
            ImageIO.write(image,"png",response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("login")
    @ResponseBody
    public HashMap<String, Object> login(Admin admin, String enCode){
        HashMap<String, Object> login = adminService.login(admin, enCode);
        return login;
    }
    @RequestMapping("logout")

    public String logout(HttpServletRequest request){
    request.getSession().removeAttribute("admin");
    return "redirect:/login/login.jsp";
    }
}
