package com.baizhi.zyp.controller;


import com.baizhi.zyp.entity.Video;
import com.baizhi.zyp.service.VideoService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Intellij IDEA.
 *
 * @author 赵玉鹏
 * <p>
 * Date:  2020/4/1
 */
@Controller
@RequestMapping("video")
public class VideoController {
    @Resource
    VideoService videoService;

    @ResponseBody
    @RequestMapping("showVideo")
    public HashMap<String,Object> showVideo(Integer page, Integer rows){
        HashMap<String, Object> map = videoService.selectVideoByPage(page, rows);

        return map;

    }
    @ResponseBody
    @RequestMapping("editVideo")
    public String editVideo(Video video,String oper){
     String uuid =null;
        if ("add".equals(oper)) {
            uuid = videoService.addVideo(video);

        }
        if ("edit".equals(oper)){
            if("".equals(video.getPath())){
                video.setPath(null);
                videoService.updateVideo(video);

            }else {
                 uuid = videoService.updateVideo(video);
            }
        }
        if ("del".equals(oper)){
            videoService.deleteVideoById(video.getId());
        }
            return uuid;

    }
    @ResponseBody
    @RequestMapping("uploadVideo")
    public void uploadVideo(MultipartFile path , String id , HttpServletRequest request){
        videoService.uploadVideo(path,id,request);

    }
    @ResponseBody
    @RequestMapping("querySearch")
    public  List<Video>  querySearch(String name){
        List<Video> videos = videoService.querySearchs(name);
        return videos;
    }
}
