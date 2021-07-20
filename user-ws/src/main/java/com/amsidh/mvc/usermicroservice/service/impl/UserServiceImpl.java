package com.amsidh.mvc.usermicroservice.service.impl;

import com.amsidh.mvc.usermicroservice.entity.UserEntity;
import com.amsidh.mvc.usermicroservice.exception.UserException;
import com.amsidh.mvc.usermicroservice.repository.UserRepository;
import com.amsidh.mvc.usermicroservice.service.UserService;
import com.amsidh.mvc.usermicroservice.shared.UserMapper;
import com.amsidh.mvc.usermicroservice.shared.Utils;
import com.amsidh.mvc.usermicroservice.ui.request.UserRequestModel;
import com.amsidh.mvc.usermicroservice.ui.request.UserUpdateRequestModel;
import com.amsidh.mvc.usermicroservice.ui.response.UserResponseModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static java.util.Optional.ofNullable;

@Data
@RequiredArgsConstructor
@Slf4j
@Service
public class UserServiceImpl implements UserService {
    private final Utils utils;
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;

    @Override
    public UserResponseModel createUser(UserRequestModel userRequestModel) {
        log.debug("createUser method of UserServiceImpl is called");
        userRequestModel.setUserId(utils.generateUUID());
        UserEntity userEntity = objectMapper.convertValue(userRequestModel, UserEntity.class);
        userEntity.setEncryptedPassword("testPassword");
        UserEntity savedUserEntity = userRepository.save(userEntity);
        log.info("User created successfully!!!");
        return objectMapper.convertValue(savedUserEntity, UserResponseModel.class);
    }

    @Override
    public UserResponseModel getUserByUserId(String userId) {
        log.info("getUserByUserId method of UserServiceImpl is called");
        Objects.requireNonNull(userId, "UserId must not be null");
        return ofNullable(userRepository.findByUserId(userId)).map(userEntity -> objectMapper.convertValue(userEntity, UserResponseModel.class))
                .orElseThrow(() -> new UserException(String.format("User with userId %s not found", userId)));
    }

    @Override
    public UserResponseModel updateUser(String userId, UserUpdateRequestModel userUpdateRequestModel) {
        log.info("updateUser method of UserServiceImpl is called");
        Objects.requireNonNull(userId, "UserId must not be null");
        return ofNullable(userRepository.findByUserId(userId)).map(userEntity -> {
            ofNullable(userUpdateRequestModel.getFirstName()).ifPresent(userEntity::setFirstName);
            ofNullable(userUpdateRequestModel.getLastName()).ifPresent(userEntity::setLastName);
            userRepository.save(userEntity);
            return objectMapper.convertValue(userEntity, UserResponseModel.class);
        }).orElseThrow(() -> new RuntimeException(String.format("User with userId %s not found.", userId)));
    }

    @Override
    public void deleteUser(String userId) {
        Objects.requireNonNull(userId, "UserId must not be null");
        Optional.ofNullable(userRepository.findByUserId(userId)).map(userEntity -> {
            userRepository.delete(userEntity);
            return objectMapper.convertValue(userEntity, UserResponseModel.class);
        }).orElseThrow(() -> new RuntimeException(String.format("User with userId %s not found.", userId)));

    }

    @Override
    public List<UserResponseModel> getAllUsers() {
        Iterator<UserEntity> iterator = userRepository.findAll().iterator();
        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(iterator, Spliterator.ORDERED), false).map(userEntity -> objectMapper.convertValue(userEntity, UserResponseModel.class))
                .collect(Collectors.toList());
    }
}
