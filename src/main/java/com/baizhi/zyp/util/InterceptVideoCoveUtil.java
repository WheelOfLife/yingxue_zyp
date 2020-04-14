package com.baizhi.zyp.util;


import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.bytedeco.javacv.OpenCVFrameConverter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Intellij IDEA.
 *
 * @author 赵玉鹏
 * <p>
 * Date:  2020/4/1
 */
public class InterceptVideoCoveUtil {
    /**
     * 获取指定视频的帧并保存为图片至指定目录
     * @param filePath 视频存放的地址
     * @param targerFilePath 截图存放的地址
     * @param targetFileName 截图保存的文件名称
     * @return
     * @throws Exception
     */

    /**
     * 获取指定视频的帧并保存为图片至指定目录
     * @param filePath 视频存放的地址
     * @param targerFilePath 截图存放的地址
     * @param targetFileName 截图保存的文件名称
     * @return
     * @throws Exception
     */

    public static boolean  executeCodecs(String filePath, String targerFilePath, String targetFileName) throws Exception {
        try{
            FFmpegFrameGrabber ff = FFmpegFrameGrabber.createDefault(filePath);
            ff.start();
            String rotate =ff.getVideoMetadata("rotate");
            Frame f;
            int i = 0;
            while (i <1) {
                f =ff.grabImage();
                opencv_core.IplImage src = null;
                if(null !=rotate &&rotate.length() > 1) {
                    OpenCVFrameConverter.ToIplImage converter =new OpenCVFrameConverter.ToIplImage();
                    src =converter.convert(f);
                    f =converter.convert(rotate(src, Integer.valueOf(rotate)));
                }
                doExecuteFrame(f,targerFilePath,targetFileName);
                i++;
            }
            ff.stop();
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    /*
     * 旋转角度的
     */
    public static opencv_core.IplImage rotate(opencv_core.IplImage src, int angle) {
        opencv_core.IplImage img = opencv_core.IplImage.create(src.height(), src.width(), src.depth(), src.nChannels());
        opencv_core.cvTranspose(src, img);
        opencv_core.cvFlip(img, img, angle);
        return img;
    }


    public static void doExecuteFrame(Frame f, String targerFilePath, String targetFileName) {

        if (null ==f ||null ==f.image) {
            return;
        }
        Java2DFrameConverter converter =new Java2DFrameConverter();
        String imageMat ="jpg";
        String FileName =targerFilePath + File.separator +targetFileName +"." +imageMat;
        BufferedImage bi =converter.getBufferedImage(f);
        System.out.println("width:" + bi.getWidth());//打印宽、高
        System.out.println("height:" + bi.getHeight());
        File output =new File(FileName);
        try {
            ImageIO.write(bi,imageMat,output);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args)  {
        String filePath="http://yxue-zhao.oss-cn-beijing.aliyuncs.com/video/15857228733859dd4d44d4364cfc350c66b00d4a26c58.mp4";
        String targerFilePath="C:\\Users\\王墨\\Desktop\\视频\\";
        String targetFileName="ofg.jpg";
        boolean b = false;
        try {
            b = executeCodecs(filePath, targerFilePath, targetFileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(b);
    }
}
