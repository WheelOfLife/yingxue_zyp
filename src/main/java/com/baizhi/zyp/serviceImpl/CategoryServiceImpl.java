package com.baizhi.zyp.serviceImpl;

import com.baizhi.zyp.annotation.AddCache;
import com.baizhi.zyp.annotation.AddLog;
import com.baizhi.zyp.annotation.DelCache;
import com.baizhi.zyp.dao.Categorydao;
import com.baizhi.zyp.dao.Videodao;
import com.baizhi.zyp.entity.Category;
import com.baizhi.zyp.entity.CategoryExample;
import com.baizhi.zyp.entity.VideoExample;
import com.baizhi.zyp.service.CategoryService;
import com.baizhi.zyp.util.UUIDUtil;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Intellij IDEA.
 *
 * @author 赵玉鹏
 * <p>
 * Date:  2020/3/30
 */
@Service
@Transactional

public class CategoryServiceImpl implements CategoryService {
    @Resource
    Categorydao categorydao;
    @Resource
    Videodao videodao;
    @Override
    @AddCache
    public HashMap<String, Object> queryByOneCategory(Integer page, Integer rows) {
        CategoryExample categoryExample = new CategoryExample();
        categoryExample.createCriteria().andLevelsEqualTo("1");
        Integer start= (page-1)*rows;
        RowBounds rowBounds = new RowBounds(start,rows);
        List<Category> categories = categorydao.selectByExampleAndRowBounds(categoryExample, rowBounds);
        Category category = new Category();
        category.setLevels("1");
        Integer records = categorydao.selectCount(category);
        Integer total=records%rows==0?records/rows:records/rows+1;
        HashMap<String, Object> map = new HashMap<>(16);


        map.put("page",page);
        map.put("rows",categories);
        map.put("records",records);
        map.put("total",total);
        return map;
    }
    @AddCache
    @Override
    public HashMap<String, Object> queryByTwoCategory(Integer page, Integer rows,String id) {
        CategoryExample categoryExample = new CategoryExample();
        categoryExample.createCriteria().andParentIdEqualTo(id);
        Integer start= (page-1)*rows;
        RowBounds rowBounds = new RowBounds(start,rows);
        List<Category> categories = categorydao.selectByExampleAndRowBounds(categoryExample, rowBounds);
        Category category = new Category();
        category.setParentId(id);
        Integer records = categorydao.selectCount(category);
        Integer total=records%rows==0?records/rows:records/rows+1;
        HashMap<String, Object> map = new HashMap<>(16);


        map.put("page",page);
        map.put("rows",categories);
        map.put("records",records);
        map.put("total",total);
        return map;
    }
    @DelCache
    @Override
    public void saveCategory(Category category){
       category.setId(UUIDUtil.getUUID());
        categorydao.insert(category);
    }
    @DelCache
    @Override
    public void updateCategory(Category category){

        categorydao.updateByPrimaryKeySelective(category);
    }
    @DelCache
    @Override
    public void deleteCategory(Category category) {
        categorydao.delete(category);

    }
    @AddCache
    @AddLog()
    @Override
    public Integer selectOne(String id){
        CategoryExample categoryExample = new CategoryExample();
        categoryExample.createCriteria().andParentIdEqualTo(id);
        Integer i = categorydao.selectCountByExample(categoryExample);
        return i;
    }
    @DelCache
    @Override
    public Boolean deleteTwoCategory(Category category) {
        VideoExample videoExample = new VideoExample();
        videoExample.createCriteria().andCategoryIdEqualTo(category.getId());
        Integer i = videodao.selectCountByExample(videoExample);
        System.out.println( i+"查出来的视频数");
        if (i==0){
            categorydao.delete(category);
            return true;
        }else {
           return false;
        }



    }
}
