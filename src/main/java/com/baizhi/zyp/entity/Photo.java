package com.baizhi.zyp.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Created by Intellij IDEA.
 *
 * @author 赵玉鹏
 * <p>
 * Date:  2020/4/9
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Photo {
    @Excel(name = "ID")
    private String id;
    @Excel(name ="头像",type = 2)
    private String cover;
    @Excel(name = "年龄")
    private Integer age;
    @Excel(name = "生日",format = "yyyy年MM月dd日",width = 20)
    private Date bir;

}
