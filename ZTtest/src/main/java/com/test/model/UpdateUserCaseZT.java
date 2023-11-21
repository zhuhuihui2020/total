package com.test.model;

import lombok.Data;

@Data
public class UpdateUserCaseZT {
    private Integer userId;
    private String name;
    private String password;
    private String phone;
    private String photoUrl;
    private String expected;
}
