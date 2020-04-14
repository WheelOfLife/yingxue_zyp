package com.baizhi.zyp.service;

import com.baizhi.zyp.entity.Admin;

import java.util.HashMap;

/**
 * Created by Intellij IDEA.
 *
 * @author 赵玉鹏
 * <p>
 * Date:  2020/3/28
 */
public interface AdminService {
    HashMap<String, Object> login(Admin admin, String enCode);
}
