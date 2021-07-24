package com.amsidh.mvc.albumws.model.response.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class AlbumDto {
    private String id;
    private String name;
    private Double price;
    private String userId;
}
