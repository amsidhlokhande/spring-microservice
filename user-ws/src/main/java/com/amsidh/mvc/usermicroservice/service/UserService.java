package com.amsidh.mvc.usermicroservice.service;

import com.amsidh.mvc.usermicroservice.ui.request.UserRequestModel;
import com.amsidh.mvc.usermicroservice.ui.request.UserUpdateRequestModel;
import com.amsidh.mvc.usermicroservice.ui.response.UserResponseModel;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    UserResponseModel createUser(UserRequestModel userRequestModel);

    UserResponseModel getUserByUserId(String userId);

    UserResponseModel updateUser(String userId, UserUpdateRequestModel userUpdateRequestModel);

    void deleteUser(String userId);

    List<UserResponseModel> getAllUsers();

    String getUserIdByEmailId(String emailId);
}
