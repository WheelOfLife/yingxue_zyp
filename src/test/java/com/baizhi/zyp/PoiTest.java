package com.baizhi.zyp;

import com.baizhi.zyp.entity.Emp;
import lombok.SneakyThrows;
import org.apache.poi.hssf.record.DVALRecord;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Intellij IDEA.
 *
 * @author 赵玉鹏
 * <p>
 * Date:  2020/4/8
 */
/*@RunWith(SpringRunner.class)
@SpringBootTest*/
public class PoiTest {
@Test
    public void poiTest() throws IOException {
        //创建一个Excel文档
    Workbook sheets = new HSSFWorkbook();
    //创建一个工作表   参数工作表的名字   不写默认名字 sheet0
    Sheet sheet = sheets.createSheet("学生信息表");
    Sheet sheet1 = sheets.createSheet("学生名单");
  //创建一行   参数：行下标下标从0开始
    Row row = sheet.createRow(0);
    //创建一个单元格 参数：单元格下标 从0开始
    Cell cell = row.createCell(0);
    //设置内容
    cell.setCellValue("第一个单元格");
    //导出单元格
    sheets.write(new FileOutputStream(new File("D:\\186.xls")));
   //释放资源
    sheets.close();
    }
    @Test
    public void poiTwoTest() throws IOException {
        ArrayList<Emp> emps =new ArrayList<>();
        emps.add(new Emp("1","张ti",3,new Date()));
        emps.add(new Emp("2","张三",33,new Date()));
        emps.add(new Emp("3","张sd",32,new Date()));
        emps.add(new Emp("4","张fgh",34 ,new Date()));
        //创建一个Excel文档
        Workbook sheets = new HSSFWorkbook();
        //创建一个工作表   参数工作表的名字   不写默认名字 sheet0
        Sheet sheet = sheets.createSheet("学生信息表");
       //合并单元格 参数 firstRow行开始 lastRow行结束 fistCol列开始 lastCol列结束
        CellRangeAddress cellAddresses = new CellRangeAddress(0, 0, 0, 3);
        //将设置好的合并单元格给sheet    就是给工作表
        sheet.addMergedRegion(cellAddresses);

        //创建字体样式
        Font font = sheets.createFont();
        //设置字号
        font.setFontHeightInPoints((short)20);
        //设置字体
        font.setFontName("微软雅黑");
        //加粗
        font.setBold(true);
        //颜色
        font.setColor(Font.COLOR_RED);
        font.setItalic(true);    //斜体
        font.setUnderline(FontFormatting.U_SINGLE);  //下划线

        //创建样式
        CellStyle cellStyle1 = sheets.createCellStyle();
       //设置居中
        cellStyle1.setAlignment(HorizontalAlignment.CENTER);
      //传设置好的样式
       cellStyle1.setFont(font);

        //指定列宽   参数：列索 引列宽
        //值相差256    1/256
        sheet.setColumnWidth(3,20*256);


        //创建标题行
        Row titlerow = sheet.createRow(0);
        //1/20相差值
        titlerow.setHeight((short) (30*20));
        //创建单元格赋值
        Cell cell1 = titlerow.createCell(0);
        cell1.setCellValue("学生信息表");
        cell1.setCellStyle(cellStyle1);


        //目录行
        String[] titles={"Id","名字","年龄","生日"};
        //创建目录行
        Row row = sheet.createRow(1);
        //1/20相差值
        row.setHeight((short) (20*20));
        for (int i = 0; i < titles.length; i++) {
            String title = titles[i];
            //创建一个单元格
            Cell cell = row.createCell(i);
            //给单元格赋值
            cell.setCellValue( titles[i]);
        }
        //创建一个日期格式对象
        DataFormat dataFormat = sheets.createDataFormat();
        //设置日期格式
        short dt = dataFormat.getFormat("yyyy年MM月dd日");
        //创建样式对象
        CellStyle cellStyle = sheets.createCellStyle();

        //将设置好的日期格式给样式对象
        cellStyle.setDataFormat(dt);

        //处理数据行
        // 遍历数据
        for (int i = 0; i < emps.size(); i++) {
            Row row1 = sheet.createRow(i + 2);
            row1.createCell(0).setCellValue(emps.get(i).getId());
            row1.createCell(1).setCellValue(emps.get(i).getName());
            row1.createCell(2).setCellValue(emps.get(i).getAge());
             //创建日期单元格
            Cell cell = row1.createCell(3);
            //给单元格设置指定样式
            cell.setCellStyle(cellStyle);
             //赋值
            cell.setCellValue(emps.get(i).getBir());

        }
        Sheet sheet1 = sheets.createSheet("学生名单");
      /*  //创建一行   参数：行下标下标从0开始
        Row row = sheet.createRow(0);
        //创建一个单元格 参数：单元格下标 从0开始
        Cell cell = row.createCell(0);
        //设置内容
        cell.setCellValue("第一个单元格");*/
        //导出单元格
        sheets.write(new FileOutputStream(new File("D:\\186.xls")));
        //释放资源
        sheets.close();
    }
    @SneakyThrows
    @Test
    public void inPort(){
        //获取表格中的数据 读入程序中
        //插入数据库

        //获取导入文件
        Workbook workbook = new HSSFWorkbook(new FileInputStream(new File("D:\\186.xls")));
        //根据文档获取工作表
        Sheet sheet = workbook.getSheet("学生信息表");
        //获取最后一行下标
      //  int lastRowNum = sheet.getLastRowNum();

        for (int i = 2; i <= sheet.getLastRowNum(); i++) {
            //获取行
            Row row = sheet.getRow(i);
            //获取数据
            String id = row.getCell(0).getStringCellValue();
            String name = row.getCell(1).getStringCellValue();
            double ages = row.getCell(2).getNumericCellValue();
            int age=(int)ages;

            Date bir = row.getCell(3).getDateCellValue();
            Emp emp = new Emp(id, name, age, bir);
            System.out.println("插入数据："+emp);
        }
    }

}
