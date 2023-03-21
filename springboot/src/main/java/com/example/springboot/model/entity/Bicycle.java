package com.example.springboot.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Bicycle {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String brand;

    private String model;

    private Integer totalRatings;

    private Integer ratingTimes;

    private Double rate;

    private Double price;

    private Integer amount;

}
