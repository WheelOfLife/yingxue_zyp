package com.baizhi.zyp.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Created by Intellij IDEA.
 *
 * @author 赵玉鹏
 * <p>
 * Date:  2020/4/6
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VideoVo {
 private String id;
 private String videoTitle;
 private String cover;
 private String path;
 private Date uploadTime;
 private String description;
 private Integer likeCount;
 private String cateName;
 private String headImg;
}
