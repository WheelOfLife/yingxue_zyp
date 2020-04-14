package com.baizhi.zyp;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@MapperScan("com.baizhi.zyp.dao")
@SpringBootApplication
public class YingxueZypApplication {

    public static void main(String[] args) {
        SpringApplication.run(YingxueZypApplication.class, args);
    }

}
