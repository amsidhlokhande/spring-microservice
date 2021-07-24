package com.amsidh.mvc.albumws.controller;

import com.amsidh.mvc.albumws.model.response.AlbumResponseModel;
import com.amsidh.mvc.albumws.service.AlbumService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Data
@RequiredArgsConstructor
@RestController
public class AlbumController {

    private final AlbumService albumService;

    @GetMapping(value = "/users/{userId}/albums", produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<List<AlbumResponseModel>> findAlbumsByUserId(@PathVariable("userId") String userId) {
        List<AlbumResponseModel> albumResponseModelList = albumService.getAlbumsByUserId(userId).parallelStream().map(AlbumResponseModel::new).collect(Collectors.toList());
        return ResponseEntity.ok(albumResponseModelList);
    }
}
