package com.baizhi.zyp;

/**
 * Created by Intellij IDEA.
 *
 * @author 赵玉鹏
 * <p>
 * Date:  2020/4/14
 */
public class TestGit {
  @Resource
    RedisTemplate redisTemplate;
    @Resource
    StringRedisTemplate stringRedisTemplate;
    @Test
    public void redisTest(){
      /*  Set keys = redisTemplate.keys("*");
        for (Object key : keys) {
            System.out.println(key);
        }*/
     // redisTemplate.opsForValue().set("namee","aaa");
        ValueOperations valueOperations = redisTemplate.opsForValue();
        Object name = valueOperations.get("name");
        System.out.println(name);
    }
    
}
