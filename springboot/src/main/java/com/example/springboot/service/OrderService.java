package com.example.springboot.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.springboot.common.Constants;
import com.example.springboot.controller.request.OrderRequest;
import com.example.springboot.mapper.OrderMapper;
import com.example.springboot.model.entity.Bicycle;
import com.example.springboot.model.entity.Order;
import com.example.springboot.model.entity.User;
import com.example.springboot.utils.CostUtils;
import com.example.springboot.utils.DateUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    OrderMapper orderMapper;

    @Autowired
    BicycleService bicycleService;

    @Autowired
    UserService userService;

    public void rentBicycle(Order order) {
        order.setRentDate(new Date());
        order.setStatus(Constants.ORDER_NOT_RETURN);
        orderMapper.insert(order);
        bicycleService.minusBicycleAmount(order.getBicycleId(), 1);
    }

    public PageInfo<Order> returnPage(OrderRequest pageRequest) {
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userId", pageRequest.getUserId()).eq("status", Constants.ORDER_NOT_RETURN);
        PageHelper.startPage(pageRequest.getPageNum(), pageRequest.getPageSize());
        List<Order> list = orderMapper.selectList(queryWrapper);
        Date nowDate = new Date();
        DecimalFormat df = new DecimalFormat("#.##");
        for (Order order : list) {
            Bicycle currentBicycle = bicycleService.getBicycle(order.getBicycleId());
            User currentUser = userService.getUser(order.getUserId());
            order.setUsername(currentUser.getUsername());
            order.setBicycleModel(currentBicycle.getModel());
            order.setBicycleBrand(currentBicycle.getBrand());
            order.setPrice(bicycleService.getBicycle(order.getBicycleId()).getPrice());
            order.setRentHours(DateUtils.dateDifferenceInHours(order.getRentDate(), nowDate));
            order.setCost(CostUtils.getCost(order));
        }

        return new PageInfo<>(list);
    }

    public void returnBicycle(Order order) {
        order.setReturnDate(new Date());
        order.setStatus(Constants.ORDER_NOT_PAY);
        orderMapper.updateById(order);
        bicycleService.addBicycleAmount(order.getBicycleId(), 1);
    }

}
