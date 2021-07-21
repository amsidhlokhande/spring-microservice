package com.amsidh.mvc.usermicroservice.ui.request;

import lombok.Data;

import java.io.Serializable;
@Data
public class UserLoginModel implements Serializable {
    private String emailId;
    private String password;
}
