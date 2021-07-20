package com.amsidh.mvc.usermicroservice.service.impl;

import com.amsidh.mvc.usermicroservice.exception.UserException;
import com.amsidh.mvc.usermicroservice.service.UserService;
import com.amsidh.mvc.usermicroservice.shared.UserMapper;
import com.amsidh.mvc.usermicroservice.shared.Utils;
import com.amsidh.mvc.usermicroservice.ui.request.UserRequestModel;
import com.amsidh.mvc.usermicroservice.ui.request.UserUpdateRequestModel;
import com.amsidh.mvc.usermicroservice.ui.response.UserResponseModel;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;

@Data
@RequiredArgsConstructor
@Slf4j
@Service
public class UserServiceImpl implements UserService {
    private static final Map<String, UserRequestModel> users = new HashMap<>();
    private final Utils utils;
    private final UserMapper userMapper;

    static {
        log.info("Loading default data......");
        String userId = UUID.randomUUID().toString();
        users.put(userId, new UserRequestModel(userId, "Anjali", "Lokhande", "test@gmail.com", "pass1234"));
    }

    @Override
    public UserResponseModel createUser(UserRequestModel userRequestModel) {
        log.debug("createUser method of UserServiceImpl is called");
        String userId = utils.generateUUID();
        userRequestModel.setUserId(userId);
        users.put(userId, userRequestModel);
        log.info("User created successfully!!!");
        return userMapper.userResponseModel(userRequestModel);
    }

    @Override
    public UserResponseModel getUserByUserId(String userId) {
        log.info("getUserByUserId method of UserServiceImpl is called");
        Objects.requireNonNull(userId, "UserId must not be null");
        return ofNullable(users.get(userId)).map(userMapper::userResponseModel)
                .orElseThrow(() -> new UserException(String.format("User with userId %s not found", userId)));
    }

    @Override
    public UserResponseModel updateUser(String userId, UserUpdateRequestModel userUpdateRequestModel) {
        log.info("updateUser method of UserServiceImpl is called");
        Objects.requireNonNull(userId, "UserId must not be null");
        return ofNullable(users.get(userId)).map(existingUserRequestModel -> {
            ofNullable(userUpdateRequestModel.getFirstName()).ifPresent(existingUserRequestModel::setFirstName);
            ofNullable(userUpdateRequestModel.getLastName()).ifPresent(existingUserRequestModel::setLastName);
            return userMapper.userResponseModel(existingUserRequestModel);
        }).orElseThrow(() -> new RuntimeException(String.format("User with userId %s not found.", userId)));
    }

    @Override
    public void deleteUser(String userId) {
        Objects.requireNonNull(userId, "UserId must not be null");
        UserRequestModel userRequestModel = users.remove(userId);
        if (userRequestModel == null) {
            throw new RuntimeException(String.format("User with userId %s not found.", userId));
        }

    }

    @Override
    public List<UserResponseModel> getAllUsers() {
        return users.values().stream().map(userMapper::userResponseModel).collect(Collectors.toList());
    }
}
