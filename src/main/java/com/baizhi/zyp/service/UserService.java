package com.baizhi.zyp.service;

import com.baizhi.zyp.entity.User;
import com.baizhi.zyp.vo.Model;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Intellij IDEA.
 *
 * @author 赵玉鹏
 * <p>
 * Date:  2020/3/28
 */
public interface UserService {
    HashMap<String,Object> selectUserByPage(Integer page,Integer rows);
    String add(User user);
    void upload (MultipartFile headImg , String id , HttpServletRequest request);
    void deleteByid(String id);
    String update (User user);
    User selectCountById(String id);
    void uploadALiYun (MultipartFile headImg , String id , HttpServletRequest request);
    ArrayList<Model> selectCitySex();
}
