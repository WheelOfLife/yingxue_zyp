package com.baizhi.zyp.service;

import com.baizhi.zyp.entity.Category;

import java.util.HashMap;

/**
 * Created by Intellij IDEA.
 *
 * @author 赵玉鹏
 * <p>
 * Date:  2020/3/30
 */
public interface CategoryService {
    HashMap<String,Object> queryByOneCategory(Integer page,Integer rows);
    HashMap<String,Object> queryByTwoCategory(Integer page,Integer rows,String id);
    void saveCategory(Category category);
    public void updateCategory(Category category);
    void deleteCategory(Category category);
    public Integer selectOne(String id);
    public Boolean deleteTwoCategory(Category category);
}
