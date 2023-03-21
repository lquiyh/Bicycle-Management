package com.example.springboot.model.DTO;

import com.example.springboot.model.entity.User;
import lombok.Data;

import java.io.Serializable;

@Data
public class LoginDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private User user;

    private String token;

}
