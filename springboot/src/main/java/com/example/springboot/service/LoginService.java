package com.example.springboot.service;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.example.springboot.exception.ServiceException;
import com.example.springboot.mapper.UserMapper;
import com.example.springboot.model.DTO.LoginDTO;
import com.example.springboot.model.DTO.UserRequest;
import com.example.springboot.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    UserService userService;

    public LoginDTO login(UserRequest userRequest) {
        if (StringUtils.isBlank(userRequest.getUsername())) {
            throw new ServiceException("用户名为空");
        }
        if (StringUtils.isBlank(userRequest.getPassword())) {
            throw new ServiceException("密码为空");
        }
        User user = userMapper.findLoginUser(userRequest.getUsername());
        if (user == null) {
            throw new ServiceException("用户不存在");
        }
        if (!user.getPassword().equals(userRequest.getPassword())) {
            throw new ServiceException("密码错误");
        }

        StpUtil.login(user.getId());
        //StpUtil.getSession().set(Constants.LOGIN_USER_KEY, user);
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setUser(user);
        String tokenValue = StpUtil.getTokenInfo().getTokenValue();
        loginDTO.setToken(tokenValue);
        userService.updateUserLoginDate(user.getId());
        return loginDTO;
    }

}
