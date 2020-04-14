package com.baizhi.zyp.serviceImpl;

import com.baizhi.zyp.dao.Logdao;
import com.baizhi.zyp.entity.Log;
import com.baizhi.zyp.entity.LogExample;
import com.baizhi.zyp.entity.User;
import com.baizhi.zyp.service.LogService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Intellij IDEA.
 *
 * @author 赵玉鹏
 * <p>
 * Date:  2020/4/6
 */
@Service
@Transactional
public class LogServiceImpl implements LogService {
    @Resource
    Logdao logdao;
    @Override
    public void saveLogs(Log log) {

            logdao.insert(log);

    }

    @Override
    public HashMap<String,Object> queryAll(Integer page, Integer rows) {
        LogExample logExample = new LogExample();
        Integer start= (page-1)*rows;
        RowBounds rowBounds = new RowBounds(start,rows);
        //分页数据
        List<Log> logs = logdao.selectByExampleAndRowBounds(logExample, rowBounds);

        //返回总条数
        Integer records = logdao.selectCount(new Log());
        Integer total =records%rows==0?records/rows:records/rows+1;
        HashMap<String, Object> map = new HashMap<>(16);


        map.put("page",page);
        map.put("rows",logs);
        map.put("records",records);
        map.put("total",total);

        return map;
    }
}
