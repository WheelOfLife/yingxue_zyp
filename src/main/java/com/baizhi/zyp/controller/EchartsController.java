package com.baizhi.zyp.controller;

import com.baizhi.zyp.service.UserService;
import com.baizhi.zyp.vo.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by Intellij IDEA.
 *
 * @author 赵玉鹏
 * <p>
 * Date:  2020/4/9
 */
@RestController
@RequestMapping("echarts")
public class EchartsController {
    @Resource
    UserService userService;
   @RequestMapping("queryByUserNum")
    public HashMap<String, Object> queryByUserNum(){
       //我要获取当前的日期
       Date date = new Date();
       //设置要获取到什么样的时间
       SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
       //获取String类型的时间
       String createdate = sdf.format(date);



       HashMap<String, Object> map = new HashMap<>();
        map.put("month", Arrays.asList("1月","2月","3月","4月","5月,6月"));
        map.put("boy",Arrays.asList(1,2,45,63,58,25));
        map.put("girl",Arrays.asList(66,45,12,45,57,2));
       return map;
    }
        //http 由页面发起 短链接
         //tcp/ip  长连接

    /*
    *   "city": [
      {"name": "北京","value": "310"},
      {"name": "天津","value": "220"},
      {"name": "上海","value": "320"},
      {"name": "重庆","value": "340"},
      {"name": "河北","value": "530"},
      {"name": "安徽","value": "630"},
      {"name": "新疆","value": "730"},
      {"name": "浙江","value": "370"},
      {"name": "江西","value": "430"},
      {"name": "山西","value": "390"},
      {"name": "内蒙古","value": "330"},
      {"name": "吉林","value": "360"},
      {"name": "福建","value": "330"},
      {"name": "广东","value": "330"},
      {"name": "西藏","value": "390"},
      {"name": "四川","value": "830"},
      {"name": "宁夏","value": "630"},
      {"name": "香港","value": "530"},
      {"name": "澳门","value": "370"}
    ]
  }
    * */
    @RequestMapping("queryByUserMap")
    public ArrayList<Model> queryByUserMap(){


/*
        ArrayList<Model> list = new ArrayList<>();
//根据性别分组查询   where=男 group by ="城市Name"
        ArrayList<City> boysCity = new ArrayList<>();
     boysCity.add(new City("北京","45"));
     boysCity.add(new City("上海","20"));
     boysCity.add(new City("山东","66"));
     boysCity.add(new City("河南","10"));


        Model model = new Model("男孩",boysCity);
        list.add(model);
        Model modelGirl = new Model("女孩",boysCity);
        list.add(modelGirl);*/

        ArrayList<Model> models = userService.selectCitySex();
        return models;
    }
}
