package com.example.springboot.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class User {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String username;

    private String realname;

    private String password;

    private String role;

    private String email;

    private String address;

    private String phone;

    private String loginDate;

}
