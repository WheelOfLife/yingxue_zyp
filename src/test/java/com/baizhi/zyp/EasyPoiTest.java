package com.baizhi.zyp;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.baizhi.zyp.entity.Emp;
import com.baizhi.zyp.entity.Photo;
import com.baizhi.zyp.entity.Teacher;
import lombok.SneakyThrows;
import org.apache.poi.ss.usermodel.*;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Intellij IDEA.
 *
 * @author 赵玉鹏
 * <p>
 * Date:  2020/4/8
 */
/*@RunWith(SpringRunner.class)
@SpringBootTest*/
public class EasyPoiTest {
@Test
    public void easyPoiTest() throws IOException {
    ArrayList<Emp> emps =new ArrayList<>();
    emps.add(new Emp("1","张ti",3,new Date()));
    emps.add(new Emp("2","张三",33,new Date()));
    emps.add(new Emp("3","张sd",32,new Date()));
    emps.add(new Emp("4","张fgh",34 ,new Date()));

    // 导出参数：标题，工作表名称
    ExportParams exportParams = new ExportParams("计算机一班学生", "学生");
   // 配置工作参数：   实体类类对象（类型），导出的集合
    Workbook workbook = ExcelExportUtil.exportExcel(exportParams,Emp.class, emps);
  //导出
   workbook.write(new FileOutputStream(new File("D:\\学生.xls")));
    //释放资源
   workbook.close();
    }
    @Test
    public void poiTwoTest() throws IOException {
        ArrayList<Emp> emps =new ArrayList<>();
        emps.add(new Emp("1","张ti",3,new Date()));
        emps.add(new Emp("2","张三",33,new Date()));
        emps.add(new Emp("3","张sd",32,new Date()));
        emps.add(new Emp("4","张fgh",34 ,new Date()));
        ArrayList<Emp> empss =new ArrayList<>();
        empss.add(new Emp("1","张ti",3,new Date()));
        empss.add(new Emp("2","张三",33,new Date()));
        empss.add(new Emp("3","张sd",32,new Date()));
        empss.add(new Emp("4","张fgh",34 ,new Date()));

        Teacher teacher1 = new Teacher("2","huox",56,empss);
        Teacher teacher = new Teacher("1","suns",29,emps);


        ArrayList<Teacher> teacherArrayList =new ArrayList<>();
        teacherArrayList.add(teacher);
        teacherArrayList.add(teacher1);
        // 导出参数：标题，工作表名称
        ExportParams exportParams = new ExportParams("计算机一班学生", "学生");
        // 配置工作参数：   实体类类对象（类型），导出的集合
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, Teacher.class, teacherArrayList);
        //导出
        workbook.write(new FileOutputStream(new File("D:\\学生.xls")));
        //释放资源
        workbook.close();
    }
    @SneakyThrows
    @Test
    public void easyPoiInPort() {
        //创建导入对象
        ImportParams params = new ImportParams();
        params.setTitleRows(1); //表格标题行数,默认0
        params.setHeadRows(2);  //表头行数,默认1

        //获取导入数据
        List<Teacher> teachers = ExcelImportUtil.importExcel(new FileInputStream(new File("D:\\学生.xls")),Teacher.class, params);

        for (Teacher teacher : teachers) {
            System.out.println(teacher);
        }

    }

        @Test
        public void easyPoiTestPhoto() {
            ArrayList<Photo> photos =new ArrayList<>();
            photos.add(new Photo("1","C:\\Users\\王墨\\Desktop\\头像\\2.jpg",3,new Date()));
            photos.add(new Photo("2","C:\\Users\\王墨\\Desktop\\头像\\3.jpg",33,new Date()));
            photos.add(new Photo("3","",32,new Date()));
            photos.add(new Photo("4","http://yxue-zhao.oss-cn-beijing.aliyuncs.com/photo/158566918323317.jpg",34 ,new Date()));

            // 导出参数：标题，工作表名称
            ExportParams exportParams = new ExportParams("图片", "学生");
            // 配置工作参数：   实体类类对象（类型），导出的集合
            Workbook workbook = ExcelExportUtil.exportExcel(exportParams,Photo.class, photos);
            //导出
            try {
                workbook.write(new FileOutputStream(new File("D:\\图片.xls")));
            } catch (IOException e) {
                e.printStackTrace();
            }
            //释放资源
            try {
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        @Test
    public void easyPoiInPortphotoo() {
        //创建导入对象
        ImportParams params = new ImportParams();
        params.setTitleRows(1); //表格标题行数,默认0
        params.setHeadRows(1);  //表头行数,默认1

        //获取导入数据
        List<Photo> photo = null;
        try {
            photo = ExcelImportUtil.importExcel(new FileInputStream(new File("D:\\图片.xls")), Photo.class, params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (Photo photo1 : photo) {
            System.out.println(photo1);

        }
    }
}
