package com.baizhi.zyp.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.HashOperations;
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
public class CacheMap {
   @Resource
    RedisTemplate redisTemplate;
@Resource
    StringRedisTemplate stringRedisTemplate;
    @Around("@annotation(com.baizhi.zyp.annotation.AddCache)")
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
        // String String   Hash<key，vale>
        // 大key 是  类权限定名  小key是 方法加参数  value是value
        //类 下方法


        //类的权限定名  大key
        String className = proceedingJoinPoint.getTarget().getClass().getName();

        //方法名
        String methodName = proceedingJoinPoint.getSignature().getName();
        stringBuilder.append(methodName);
        //获取参数
        Object[] args = proceedingJoinPoint.getArgs();

             for (Object arg : args) {
            stringBuilder.append(arg);
        }

        //拼接结果  小key
        String key = stringBuilder.toString();

        //取出key

        Boolean aBoolean = redisTemplate.opsForHash().hasKey(className, key);

        HashOperations hashOperations = redisTemplate.opsForHash();

        //去Redis判断key是否存在
        Object result =null;

        if (aBoolean) {
            //存在缓存中有数据 返回
            result = hashOperations.get(className,key);

        }else {

            //不存在 缓存中没有 查询
            try {
                result= proceedingJoinPoint.proceed();
                System.out.println("result = " + result);
            hashOperations.put(className,key,result);
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
        return result;
    }
@After("@annotation(com.baizhi.zyp.annotation.DelCache)")
    public void delCache(JoinPoint joinPoint){
        //清空缓存
    //com.baizhi.zyp.serviceImpl.UserServiceImpl    selectUserByPage13

    //获取类的权限定名
    String className = joinPoint.getTarget().getClass().getName();
//删除雷下所有的数据
redisTemplate.delete(className);

}
}
