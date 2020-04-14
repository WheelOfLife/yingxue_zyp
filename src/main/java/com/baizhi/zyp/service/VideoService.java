package com.baizhi.zyp.service;

import com.baizhi.zyp.entity.Video;
import com.baizhi.zyp.po.VideoPo;
import com.baizhi.zyp.vo.VideoVo;
import org.springframework.web.multipart.MultipartFile;

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
public interface VideoService {
    void uploadVideo(MultipartFile path , String id , HttpServletRequest request);
    public HashMap<String, Object> selectVideoByPage(Integer page, Integer rows) ;
    String addVideo (Video video);
    void deleteVideoById(String id);
    String updateVideo(Video video);
    List<VideoVo> queryByReleaseTime();
    List<Video>querySearch(String content);
    List<Video> querySearchs(String content);
}
