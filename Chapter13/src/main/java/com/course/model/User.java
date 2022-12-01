package com.course.model;

import lombok.Data;

//和数据库里的表对应

/**
 * 一个Model有很多属性时，之前需要使用快捷键或者手动方式实现属性的getter和setter方法，
 * 当新增属性时，需要重新增加，工作繁琐，
 * 现在可以直接在类上使用@Data注解，这样可以不写getter和setter方法，直接使用即可。
 * 系统自动生成了getter、setter方法，以及equals(),hashCode(),toString()方法。
 */
@Data
public class User {
    private int id;
    private String userName;
    private String password;
    private String age;
    private String sex;
    private String permission;
    private String isDelete;

    public User(){}
}
