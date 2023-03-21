package com.example.springboot.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@TableName("`order`")
public class Order {

    @TableId(type = IdType.AUTO)
    Integer id;

    Integer userId;

    @TableField(exist = false)
    String username;

    Integer bicycleId;

    @TableField(exist = false)
    String bicycleBrand;

    @TableField(exist = false)
    String bicycleModel;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    Date rentDate;

    Date returnDate;

    Integer rentHours;

    Date payDate;

    Double price;

    Double cost;

    @TableField(value = "`status`")
    Integer status;

}
