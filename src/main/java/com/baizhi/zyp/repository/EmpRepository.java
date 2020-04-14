package com.baizhi.zyp.repository;

import com.baizhi.zyp.entity.Emp;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * Created by Intellij IDEA.
 *
 * @author 赵玉鹏
 * <p>
 * Date:  2020/4/12
 */
public interface EmpRepository extends ElasticsearchRepository<Emp, String> {
    List<Emp> findByName(String name);
}
