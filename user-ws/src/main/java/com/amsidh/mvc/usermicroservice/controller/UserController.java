package com.amsidh.mvc.usermicroservice.controller;

import com.amsidh.mvc.usermicroservice.service.UserService;
import com.amsidh.mvc.usermicroservice.ui.request.UserRequestModel;
import com.amsidh.mvc.usermicroservice.ui.request.UserUpdateRequestModel;
import com.amsidh.mvc.usermicroservice.ui.response.UserResponseModel;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static java.net.URI.create;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.APPLICATION_XML_VALUE;
import static org.springframework.http.ResponseEntity.*;

@Data
@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final Environment environment;

    @GetMapping("/status/check")
    public String statusCheck() {
        return "Users Microservice is Working on port "+ environment.getProperty("local.server.port") + " and secret key is "+ environment.getProperty("jwt.secret.salt");
    }

    @GetMapping(path = {"/{userId}"}, produces = {APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE})
    public ResponseEntity<UserResponseModel> getUser(@PathVariable(name = "userId") String userId) {
        return ok(userService.getUserByUserId(userId));
    }

    @PostMapping(consumes = {APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE}, produces = {APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE})
    public ResponseEntity<UserResponseModel> createUser(@Valid @RequestBody UserRequestModel userRequestModel) {
        UserResponseModel userResponseModel = userService.createUser(userRequestModel);
        URI location = create(String.format("/users/%s", userResponseModel.getUserId()));
        return created(location).body(userResponseModel);
    }

    @PutMapping(path = {"/{userId}"}, consumes = {APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE}, produces = {APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE})
    public ResponseEntity<UserResponseModel> updateUser(@PathVariable(name = "userId") String userId, @Valid @RequestBody UserUpdateRequestModel userUpdateRequestModel) {
        return ok(userService.updateUser(userId, userUpdateRequestModel));
    }

    @DeleteMapping(path = {"/{userId}"})
    public ResponseEntity<Void> deleteUser(@PathVariable(name = "userId") String userId) {
        userService.deleteUser(userId);
        return noContent().build();
    }

    @GetMapping(produces = {APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE})
    public ResponseEntity<List<UserResponseModel>> getAllUser() {
        return ok(userService.getAllUsers());
    }
}
