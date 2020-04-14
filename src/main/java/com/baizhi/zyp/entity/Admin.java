package com.baizhi.zyp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import javax.persistence.Table;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "yx_admin")
public class Admin {
    @Id
    private String id;

    private String name;

    private String password;


}