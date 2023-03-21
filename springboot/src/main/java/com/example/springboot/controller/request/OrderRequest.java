package com.example.springboot.controller.request;

import lombok.Data;

@Data
public class OrderRequest extends BaseRequest {

    Integer userId;

    String brand;

    String model;

}
