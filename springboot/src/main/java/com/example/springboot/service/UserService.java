package com.example.springboot.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.example.springboot.controller.request.UserPageRequest;
import com.example.springboot.exception.ServiceException;
import com.example.springboot.mapper.UserMapper;
import com.example.springboot.model.entity.User;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    UserMapper userMapper;

    public List<User> listUsers() {
        List<User> users = userMapper.listUsers();
        return users;
    }

    public PageInfo<User> page(UserPageRequest baseRequest) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        Map<String, Object> map = new HashMap<>();
        map.put("role", "user");
        if (StringUtils.isNotBlank(baseRequest.getUsername())) {
            map.put("username", baseRequest.getUsername());
        }
        if (StringUtils.isNotBlank(baseRequest.getPhone())) {
            map.put("phone", baseRequest.getPhone());
        }
        wrapper.allEq(map, false);
        PageHelper.startPage(baseRequest.getPageNum(), baseRequest.getPageSize());
        List<User> list = userMapper.selectList(wrapper);
        return new PageInfo<>(list);
    }

    public void updateUserLoginDate(Integer id) {
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", id).set("loginDate", new Date());
        userMapper.update(null, updateWrapper);
    }

    public User getUser(Integer id) {
        return userMapper.selectById(id);
    }

    public void updateUser(User user) {
        try {
            userMapper.updateById(user);
        } catch (Exception e) {
            throw new ServiceException("用户数据更新错误", e);
        }
    }

}
