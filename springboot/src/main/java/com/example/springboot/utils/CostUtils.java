package com.example.springboot.utils;

import com.example.springboot.model.entity.Order;

import java.text.DecimalFormat;

public class CostUtils {

    public static Double getCost(Order order) {
        DecimalFormat df = new DecimalFormat("#.##");
        double cost = order.getPrice() * order.getRentHours();
        cost = Double.parseDouble(df.format(cost));
        return cost;
    }

}
