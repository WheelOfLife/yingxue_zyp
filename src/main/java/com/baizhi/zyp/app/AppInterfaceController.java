package com.baizhi.zyp.app;

import com.baizhi.zyp.common.CommonResult;
import com.baizhi.zyp.service.VideoService;
import com.baizhi.zyp.util.PhoneCodesUtil;
import com.baizhi.zyp.vo.VideoVo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Intellij IDEA.
 *
 * @author 赵玉鹏
 * <p>
 * Date:  2020/4/6
 */
@RestController
@RequestMapping("app")
public class AppInterfaceController {
    @Resource
    VideoService videoServicel;
    @RequestMapping("getPhoneCode")
    public CommonResult getPhoneCodes(String phone){

/*
* 发送验证码接口
* */
            Integer random  = (int) ((Math.random() * 9 + 1) * 10000);
            String randoms = random.toString();
            System.out.println("存储验证码："+randoms);
            String message = PhoneCodesUtil.sendCode(phone, randoms);
            System.out.println(message);

       if ("发送成功".equals(message)){

           return new CommonResult().success("100","发送成功",phone);
       }else {

           return new CommonResult().filed("发送失败",null);
       }



    }

    /*
    *视频信息接口
    * */
    @RequestMapping("queryByReleaseTime")
    public CommonResult queryByReleaseTime(){
        try {
            List<VideoVo> videoVos = videoServicel.queryByReleaseTime();
            return new CommonResult().success(videoVos);
        } catch (Exception e) {
            e.printStackTrace();
            return new CommonResult().filed();
        }


    }
}
