package com.baizhi.zyp.controller;

import com.baizhi.zyp.entity.Category;
import com.baizhi.zyp.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;

/**
 * Created by Intellij IDEA.
 *
 * @author 赵玉鹏
 * <p>
 * Date:  2020/3/30
 */
@Controller
@RequestMapping("category")
public class CategoryControler {
    @Resource
    CategoryService categoryService;
    @ResponseBody
    @RequestMapping("queryByCategory")
    public HashMap<String,Object> queryByCategory(Integer page,Integer rows,String categoryStatus){

        HashMap<String, Object> map ;
            if (categoryStatus==null){
                 map = categoryService.queryByOneCategory(page, rows);
            }else {
                map = categoryService.queryByTwoCategory(page, rows, categoryStatus);
            }
        return map;
    }
    @ResponseBody
    @RequestMapping("editParent")
    public String editParent(Category category,String oper){
      String error=null;

        if ("add".equals(oper)){
            category.setLevels("1");
        categoryService.saveCategory(category);
        }
        if ("del".equals(oper)){
            Integer integer = categoryService.selectOne(category.getId());
            if (integer>0){
                error="删除失败一级类别下有二级类别，请先删除二级类别！！";
            }else {
                categoryService.deleteCategory(category);
            }

        }
        if ("edit".equals(oper)){
            categoryService.updateCategory(category);
        }
        return error;
    }
    @ResponseBody
    @RequestMapping("editSun")
    public String editSun(Category category,String oper,String categoryStatus){

        String error=null;

        if ("add".equals(oper)){
            category.setLevels("2");
            category.setParentId(categoryStatus);


            


            categoryService.saveCategory(category);
        }
        if ("del".equals(oper)){
            Boolean aBoolean = categoryService.deleteTwoCategory(category);
            if (!aBoolean){
                error="删除失败二级类别有视频，请先删除视频";
            }
        }
        if ("edit".equals(oper)){
        categoryService.updateCategory(category);
        }
        return error;
    }
}
