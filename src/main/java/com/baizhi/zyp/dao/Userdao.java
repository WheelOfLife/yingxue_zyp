package com.baizhi.zyp.dao;


import com.baizhi.zyp.entity.City;
import com.baizhi.zyp.entity.User;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface Userdao extends Mapper<User> {
    List<User> selectByPage(@Param("page") Integer page,@Param("rows") Integer rows);
    List<City> selectCitySex(@Param("sex") String sex);

}