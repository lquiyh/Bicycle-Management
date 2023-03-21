package com.example.springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.springboot.model.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    @Select("select * from user")
    List<User> listUsers();

    @Select("select * from user where username=#{username}")
    User findLoginUser(String username);

}
