package com.amsidh.mvc.albumws.service.impl;

import com.amsidh.mvc.albumws.model.response.dto.AlbumDto;
import com.amsidh.mvc.albumws.service.AlbumService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class AlbumServiceImpl implements AlbumService {

    @Override
    public List<AlbumDto> getAlbumsByUserId(String userId) {
        List<AlbumDto> albumDtos = Arrays.asList(
                new AlbumDto("1", "Sonu Nigam", 1234.6, userId),
                new AlbumDto("1", "Alka Yangnik", 54729.6, userId)
        );
        return albumDtos;
    }
}
