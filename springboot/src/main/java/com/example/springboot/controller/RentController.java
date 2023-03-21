package com.example.springboot.controller;

import com.example.springboot.common.Result;
import com.example.springboot.controller.request.BicyclePageRequest;
import com.example.springboot.controller.request.OrderRequest;
import com.example.springboot.model.entity.Order;
import com.example.springboot.service.BicycleService;
import com.example.springboot.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/rent")
public class RentController {

    @Autowired
    BicycleService bicycleService;

    @Autowired
    OrderService orderService;

    @GetMapping("/page")
    public Result page(BicyclePageRequest pageRequest) {
        return Result.success(bicycleService.rentPage(pageRequest));
    }

    @PostMapping("/rentBicycle")
    public Result rentBicycle(@RequestBody Order order) {
        orderService.rentBicycle(order);
        return Result.success();
    }

    @GetMapping("/returnPage")
    public Result returnPage(OrderRequest orderRequest) {
        return Result.success(orderService.returnPage(orderRequest));
    }

    @PostMapping("returnBicycle")
    public Result returnBicycle(@RequestBody Order order) {
        orderService.returnBicycle(order);
        return Result.success();
    }

}
