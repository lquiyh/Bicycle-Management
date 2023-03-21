package com.example.springboot.controller;

import com.example.springboot.common.Result;
import com.example.springboot.controller.request.UserPageRequest;
import com.example.springboot.model.entity.User;
import com.example.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {

    @Autowired
    UserService userService;


    @GetMapping("/page")
    public Result page(UserPageRequest userPageRequest) {
        return Result.success(userService.page(userPageRequest));
    }

    @GetMapping("/{id}")
    public Result getUser(@PathVariable Integer id) {
        return Result.success(userService.getUser(id));
    }

    @PutMapping("/update")
    public Result updateUser(@RequestBody User user) {
        userService.updateUser(user);
        return Result.success();
    }

}
