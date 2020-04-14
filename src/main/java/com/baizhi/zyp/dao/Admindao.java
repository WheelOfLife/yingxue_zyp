package com.baizhi.zyp.dao;

import com.baizhi.zyp.entity.Admin;
import com.baizhi.zyp.entity.AdminExample;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

public interface Admindao extends Mapper<Admin> {


    Admin queryByUsername(@Param("name")String username);
}