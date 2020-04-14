package com.baizhi.zyp.aspect;

import org.apache.poi.hssf.record.DVALRecord;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.annotation.Resource;
import java.util.Set;

/**
 * Created by Intellij IDEA.
 *
 * @author 赵玉鹏
 * <p>
 * Date:  2020/4/12
 */
@Configuration
@Aspect
public class AddCache {
   @Resource
    RedisTemplate redisTemplate;
@Resource
    StringRedisTemplate stringRedisTemplate;
    //@Around("execution(* com.baizhi.zyp.serviceImpl.*.select*(..))")
    public Object addCache(ProceedingJoinPoint proceedingJoinPoint){


        System.out.println("环绕通知");
        //序列化
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
         redisTemplate.setKeySerializer(stringRedisSerializer);
         redisTemplate.setHashKeySerializer(stringRedisSerializer);
        //拼接实参
        StringBuilder stringBuilder = new StringBuilder();
        //key，value 类型


        //key 类权限定名+方法名+参数名（实参）
        //value ：缓存数据  String

        //类的权限定名
        String className = proceedingJoinPoint.getTarget().getClass().getName();
        stringBuilder.append(className);

        //方法名
        String methodName = proceedingJoinPoint.getSignature().getName();
        stringBuilder.append(methodName);
        //获取参数
        Object[] args = proceedingJoinPoint.getArgs();
     //   stringBuilder.append(args.toString());
             for (Object arg : args) {
            stringBuilder.append(arg);
        }

        //拼接结果
        String key = stringBuilder.toString();

        //取出key
        Boolean aBoolean = redisTemplate.hasKey(key);
        ValueOperations valueOperations = redisTemplate.opsForValue();
        //去Redis判断key是否存在
        Object result =null;

        if (aBoolean) {
            //存在缓存中有数据 返回
            result = valueOperations.get(key);

        }else {

            //不存在 缓存中没有 查询
            try {
                result= proceedingJoinPoint.proceed();
                System.out.println("result = " + result);
                valueOperations.set(key,result);
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
        return result;
    }
//@After("execution(* com.baizhi.zyp.serviceImpl.*.*(..))&&!execution(* com.baizhi.zyp.serviceImpl.*.query*(..))&&!execution(* com.baizhi.zyp.serviceImpl.*.select*(..))")
    public void delCache(JoinPoint joinPoint){
        //清空缓存
    //com.baizhi.zyp.serviceImpl.UserServiceImpl    selectUserByPage13

    //获取类的权限定名
    String className = joinPoint.getTarget().getClass().getName();
    System.out.println("后置通知");
    //获得所有的key
    Set<String> keys = stringRedisTemplate.keys("*");
    for (String key : keys) {
        if(key.contains(className)){
        stringRedisTemplate.delete(key);
        }
    }

}
}
