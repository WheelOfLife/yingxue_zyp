package com.baizhi.zyp.po;

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
public class VideoPo {

    private String id;
    private String vTitle;
    private String vBrief;
    private String vPath;
    private String vCover;
    private Date vPublishDate;

    private String cateName;

    private String headImg;

}
