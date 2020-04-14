package com.baizhi.zyp.aspect;

import com.baizhi.zyp.entity.Admin;
import com.baizhi.zyp.entity.Log;
import com.baizhi.zyp.service.LogService;
import com.baizhi.zyp.util.UUIDUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Intellij IDEA.
 *
 * @author 赵玉鹏
 * <p>
 * Date:  2020/4/6
 */
@Aspect
@Configuration
public class LogAspect {
    @Resource
    HttpSession session;
    @Resource
    LogService logService;
   // @Around("execution(* com.baizhi.zyp.serviceImpl.*.*(..))&&!execution(* com.baizhi.zyp.serviceImpl.*.query*(..))&&!execution(* com.baizhi.zyp.serviceImpl.*.select*(..))&&!execution(* com.baizhi.zyp.serviceImpl.LogServiceImpl.*(..))")
    public Object addLog(ProceedingJoinPoint joinPoint){
      //谁    什么时候  操作    是否成功
        Admin admin = (Admin)session.getAttribute("admin");
        //时间

        Date date = new Date();
     /*   SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh-mm-ss");
        String format = simpleDateFormat.format(date);*/
        //操作  哪个方法
        String methodName = joinPoint.getSignature().getName();

        try {
            Object proceed = joinPoint.proceed();
            String message ="success";
            System.out.println("管理员"+admin+"---时间"+date+"---操作"+methodName+"---状态"+message);
            if (admin!=null){
                Log log = new Log(UUIDUtil.getUUID(),admin.getName(),date,methodName,message);
                System.out.println(log);
                logService.saveLogs(log);
            }


            return proceed;
        } catch (Throwable throwable) {

            throwable.printStackTrace();
            String message="error";
            return null;
        }
    }
}
