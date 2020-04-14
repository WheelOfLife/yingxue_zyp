package com.baizhi.zyp;


import com.baizhi.zyp.dao.Admindao;
import com.baizhi.zyp.dao.Categorydao;
import com.baizhi.zyp.dao.Userdao;
import com.baizhi.zyp.dao.Videodao;
import com.baizhi.zyp.entity.*;
import com.baizhi.zyp.po.VideoPo;
import com.baizhi.zyp.service.CategoryService;
import org.apache.ibatis.session.RowBounds;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class YingxueZypApplicationTests {
    @Resource
    Userdao userdao;
    @Resource
    Admindao admindao;
    @Resource
    Categorydao categorydao;
    @Resource
    Videodao videodao;
    @Test
    public void contextLoads() {
        //模板对象
        UserExample userExample = new UserExample();
userExample.createCriteria().andIdEqualTo("1");
        List<User> users = userdao.selectByExample(userExample);
        for (User user : users) {
            System.out.println(user);
        }
    }
    @Test
    public void save(){
        /*x

        userExample.createCriteria().andIdEqualTo("2");
        User user = new User();
        //没传值的全部改为空
        userdao.updateByExample(user,userExample);
        //只修改传了值得
        userdao.updateByExampleSelective(user,userExample);*/
        Admin qwe = admindao.queryByUsername("qwe");
        System.out.println(qwe);
    }
    @Test
    public void cdd(){

     /*  UserExample userExample = new UserExample();
        RowBounds rowBounds = new RowBounds(0,4);
        //RowBounds rowBounds = new RowBounds(1,3);
        List<User> users = userdao.selectByExampleAndRowBounds(userExample, rowBounds);*/
       Integer page =2;
       Integer rows=2;
        Integer start= (page-1)*rows;
        List<User> users = userdao.selectByPage(start,rows );
        for (User user : users) {
            System.out.println(user);
        }
    }
    @Test
    public void dd(){

        AdminExample adminExample = new AdminExample();
        RowBounds rowBounds = new RowBounds(6,4);
        //RowBounds rowBounds = new RowBounds(1,3);

        List<Admin>admins = admindao.selectByExampleAndRowBounds(adminExample, rowBounds);
        for (Admin admin : admins) {
            System.out.println(admin);
        }
    }

    @Test
    public void aVoidd(){
        User user = new User();
        user.setId("1");
        userdao.delete(user);
        /*      int i = userdao.selectCount(new User());
        System.out.println(i);*/
      /*  List<Admin> admins = admindao.selectAll();
        for (Admin admin : admins) {
            System.out.println(admin);
        }*/
    }
    @Test
    public void aVoid(){
        User user = new User();
        user.setId("bf3cc68b945444dfbaab46ed2673467a");
        int i = userdao.selectCount(user);
        System.out.println(i);
      /*  List<Admin> admins = admindao.selectAll();
        for (Admin admin : admins) {
            System.out.println(admin);
        }*/
    }
    @Test
    public void sdf(){
        String str = "abc/";
        boolean status = str.contains("/");
        if(status){
            System.out.println("包含");
        }else{
            System.out.println("不包含");
        }
    }


    @Test
    public void sdfs(){
        Integer page =1;
        Integer rows=3;
        CategoryExample categoryExample = new CategoryExample();
        categoryExample.createCriteria().andParentIdEqualTo("1");
        Integer start= (page-1)*rows;
        RowBounds rowBounds = new RowBounds(start,rows);
        List<Category> categories = categorydao.selectByExampleAndRowBounds(categoryExample, rowBounds);
        for (Category category : categories) {
            System.out.println(categories);
        }
    }


    @Test
    public void sdf5() {

        Category category = new Category();
        category.setParentId("1");
        Integer records = categorydao.selectCount(category);

    }
    @Test
    public void testPo() {
  /*      List<VideoPo> videoPos = videodao.queryByReleaseTime();
        for (VideoPo videoPo : videoPos) {
            System.out.println(videoPo);
        }*/

        List<City> 男 = userdao.selectCitySex("男");
        for (City city : 男) {
            System.out.println(city);
        }
    }
}
