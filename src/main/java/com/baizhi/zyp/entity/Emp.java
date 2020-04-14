package com.baizhi.zyp.entity;



import cn.afterturn.easypoi.excel.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.Id;
import java.util.Date;

/**
 * Created by Intellij IDEA.
 *
 * @author 赵玉鹏
 * <p>
 * Date:  2020/4/8
 */
@Document(indexName = "yingxs",type = "emp")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Emp {
    @Id
    @Excel(name = "ID")
    private String id;
    @Field(type = FieldType.Text,analyzer = "ik_max_word",searchAnalyzer = "ik_max_word")
    @Excel(name ="名字")
    private String name;
    @Field(type = FieldType.Integer)
    @Excel(name = "年龄")
    private Integer age;
    @Field(type = FieldType.Date)
    @Excel(name = "生日",format = "yyyy年MM月dd日",width = 20)
    private Date bir;
}
