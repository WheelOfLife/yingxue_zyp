package com.baizhi.zyp.vo;

import com.baizhi.zyp.entity.City;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by Intellij IDEA.
 *
 * @author 赵玉鹏
 * <p>
 * Date:  2020/4/11
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Model {
    private String title;
    private List<City>  city;
}
