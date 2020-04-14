package com.baizhi.zyp.dao;

import com.baizhi.zyp.entity.Video;
import com.baizhi.zyp.entity.VideoExample;
import java.util.List;

import com.baizhi.zyp.po.VideoPo;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author 王墨
 */
public interface Videodao extends Mapper<Video> {
List<VideoPo> queryByReleaseTime();
}