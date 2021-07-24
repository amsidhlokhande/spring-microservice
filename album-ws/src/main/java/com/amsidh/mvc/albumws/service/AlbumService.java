package com.amsidh.mvc.albumws.service;

import com.amsidh.mvc.albumws.model.response.dto.AlbumDto;

import java.util.List;

public interface AlbumService {
    List<AlbumDto> getAlbumsByUserId(String userId);
}
