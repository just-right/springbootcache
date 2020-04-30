package com.example.cache.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * (Student)实体类
 *
 * @author makejava
 * @since 2020-04-30 10:15:54
 */
@Data
public class Student implements Serializable {
    private static final long serialVersionUID = -41269457751686094L;
    
    private Integer id;
    
    private String name;
    
    private Integer age;
    
    private String email;
    
    private Integer sex;
    
    private String address;

    public Student() {
        super();
    }
}