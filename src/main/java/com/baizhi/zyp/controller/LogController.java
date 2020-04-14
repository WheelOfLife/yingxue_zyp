package com.baizhi.zyp.controller;

import com.baizhi.zyp.entity.Log;
import com.baizhi.zyp.service.LogService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
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
@Controller
@RequestMapping("log")
public class LogController {
    @Resource
    LogService logService;
    @ResponseBody
    @RequestMapping("queryAll")
    public HashMap<String,Object> queryAll(Integer page, Integer rows){
        HashMap<String, Object> stringObjectMap = logService.queryAll(page, rows);
        return stringObjectMap;
    }
}
