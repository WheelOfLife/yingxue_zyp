package com.baizhi.zyp;

import com.alibaba.fastjson.JSON;
import io.goeasy.GoEasy;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by Intellij IDEA.
 *
 * @author 赵玉鹏
 * <p>
 * Date:  2020/4/11
 */
/*@RunWith(SpringRunner.class)
@SpringBootTest*/
public class GoeasyTest {
    @Test
    public void goEasyTest(){
        //配置发送端参数 参数regionHost 服务器地址   自己的appkey
        GoEasy goEasy = new GoEasy( "http://rest-hangzhou.goeasy.io", "BC-de3f12ff1feb4992a6b5792b20b0250c");
             //配置发送消息 参数自定义
                goEasy.publish("186yingxxxx", "Hello, GoEasy!");
    }
    @Test
    public void goEasyEchartTest(){
        //获取随机数
        Random random = new Random();
        //随机数 参数i 50 0<=i<50
     //   int i = random.nextInt(50);



        HashMap<String, Object> map = new HashMap<>();
        map.put("month", Arrays.asList("1月","2月","3月","4月","5月,6月"));
        map.put("boy",Arrays.asList(random.nextInt(50),random.nextInt(50),random.nextInt(50),random.nextInt(50),random.nextInt(50),random.nextInt(50)));
        map.put("girl",Arrays.asList(random.nextInt(100),random.nextInt(100),random.nextInt(100),random.nextInt(100),random.nextInt(100),random.nextInt(100)));
        //对象转为Json
        String content = JSON.toJSONString(map);

        //配置发送端参数 参数regionHost 服务器地址   自己的appkey
        GoEasy goEasy = new GoEasy( "http://rest-hangzhou.goeasy.io", "BC-de3f12ff1feb4992a6b5792b20b0250c");
        //配置发送消息 参数自定义
        goEasy.publish("186yingxxxx", content);
    }
}
