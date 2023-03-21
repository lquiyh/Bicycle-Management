package com.example.springboot.controller;

import com.example.springboot.common.Result;
import com.example.springboot.controller.request.BicyclePageRequest;
import com.example.springboot.model.entity.Bicycle;
import com.example.springboot.service.BicycleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/bicycle")
public class BicycleController {

    @Autowired
    BicycleService bicycleService;

    @GetMapping("/list")
    public Result list() {
        List<Bicycle> list = bicycleService.list();
        return Result.success(list);
    }

    @GetMapping("/page")
    public Result page(BicyclePageRequest pageRequest) {
        return Result.success(bicycleService.page(pageRequest));
    }

    @GetMapping("/{id}")
    public Result getBicycle(@PathVariable Integer id) {
        return Result.success(bicycleService.getBicycle(id));
    }

    @PutMapping("/update")
    public Result updateBicycle(@RequestBody Bicycle bicycle) {
        bicycleService.updateBicycle(bicycle);
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    public Result deleteBicycle(@PathVariable Integer id) {
        bicycleService.deleteBicycle(id);
        return Result.success();
    }

    @PostMapping("/add")
    public Result addBicycle(@RequestBody Bicycle bicycle) {
        bicycleService.addBicycle(bicycle);
        return Result.success();
    }

}
