package com.baizhi.zyp.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelCollection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by Intellij IDEA.
 *
 * @author 赵玉鹏
 * <p>
 * Date:  2020/4/8
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Teacher {
    @Excel(name = "Id",needMerge = true)
    private String id;
    @Excel(name = "名字" ,needMerge = true)
    private String name;
    @Excel(name = "年龄",needMerge = true)
    private Integer age;
   //关系属性
    @ExcelCollection(name = "学生信息")
    private List<Emp> empList;

}
