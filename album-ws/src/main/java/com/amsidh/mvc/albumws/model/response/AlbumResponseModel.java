package com.amsidh.mvc.albumws.model.response;

import com.amsidh.mvc.albumws.model.response.dto.AlbumDto;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class AlbumResponseModel {
    private String id;
    private String name;
    private Double price;
    private String userId;

    public AlbumResponseModel(AlbumDto albumDto) {
        this.id = albumDto.getId();
        this.name = albumDto.getName();
        this.price = albumDto.getPrice();
        this.userId = albumDto.getUserId();
    }
}
