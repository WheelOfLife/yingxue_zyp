package com.baizhi.zyp.service;

import com.baizhi.zyp.entity.Log;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Intellij IDEA.
 *
 * @author 赵玉鹏
 * <p>
 * Date:  2020/4/6
 */
public interface LogService {
    void saveLogs (Log log);
    HashMap<String,Object> queryAll(Integer page, Integer rows);
}
