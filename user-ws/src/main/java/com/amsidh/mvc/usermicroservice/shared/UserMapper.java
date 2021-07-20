package com.amsidh.mvc.usermicroservice.shared;

import com.amsidh.mvc.usermicroservice.ui.request.UserRequestModel;
import com.amsidh.mvc.usermicroservice.ui.response.UserResponseModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@Component
public class UserMapper {
    private final ObjectMapper objectMapper;

    public UserResponseModel userResponseModel(UserRequestModel userRequestModel) {
        UserResponseModel userResponseModel = objectMapper.convertValue(userRequestModel, UserResponseModel.class);
        return userResponseModel;
    }

    public UserRequestModel userRequestModel(UserResponseModel userResponseModel) {
        UserRequestModel userRequestModel = objectMapper.convertValue(userResponseModel, UserRequestModel.class);
        return userRequestModel;
    }
}
