package com.example.springdemo.a01;

import lombok.Data;

import java.io.Serializable;

/**
 * @author leelixiangjun
 * @date 2022/8/2 23:00
 */
@Data
public class UserDao implements Serializable {

    private Integer id;

    private String name;

    private Long phone;

    private Integer messageType;
}
