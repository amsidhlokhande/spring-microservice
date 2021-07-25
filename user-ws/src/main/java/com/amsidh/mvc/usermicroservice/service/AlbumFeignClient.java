package com.amsidh.mvc.usermicroservice.service;

import com.amsidh.mvc.usermicroservice.ui.response.Album;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "albums-ws")
public interface AlbumFeignClient {

    @GetMapping("/users/{userId}/albums")
    List<Album> getAlbumsByUserId(@PathVariable(name = "userId") String userId);
}

