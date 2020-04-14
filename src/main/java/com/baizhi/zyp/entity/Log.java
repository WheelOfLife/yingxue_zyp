package com.baizhi.zyp.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;


/**
 * Created by Intellij IDEA.
 *
 * @author 赵玉鹏
 * <p>
 * Date:  2020/4/6
 */
@Table(name = "yx_log")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Log {
    @Id
    private String id;
    private String name;
    @JsonFormat(pattern = "yyyy年MM月dd日 hh:mm:ss")
    private Date date;
    private String operation;
    private String status;
}
