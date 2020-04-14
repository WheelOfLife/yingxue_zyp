package com.baizhi.zyp.serviceImpl;

import com.baizhi.zyp.dao.Admindao;
import com.baizhi.zyp.entity.Admin;
import com.baizhi.zyp.entity.AdminExample;
import com.baizhi.zyp.entity.UserExample;
import com.baizhi.zyp.service.AdminService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
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
public class AdminServiceImpl implements AdminService {
@Resource
    HttpSession httpSession;
@Resource
    Admindao admindao;
    @Override
    public  HashMap<String, Object> login(Admin admin, String enCode) {

        HashMap<String, Object> map = new HashMap<>(16);
        //1.获取验证码
        String imageCode =(String) httpSession.getAttribute("imageCode");

        // 验证验证码
        if (imageCode.equals(enCode)){
            // 验证用户
            Admin admin1 = admindao.queryByUsername(admin.getName());
            if (admin1==null){
                map.put("status","400");
                map.put("massage","用户名或密码错误");
            }else {
                //验证密码
                if (admin.getPassword().equals(admin1.getPassword())){
                 //登录成功存个标识
                    httpSession.setAttribute("admin",admin1);

                    map.put("status","200");
                    map.put("massage","登录成工");
                }else {
                    map.put("status","400");
                    map.put("massage","用户名或密码错误");
                }

            }

        }else {
            map.put("status","400");
            map.put("massage","验证码错误");
            //验证码不正确，返回错误信息
        }

        return map;
    }
}
