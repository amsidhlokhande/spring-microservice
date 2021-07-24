package com.amsidh.mvc.usermicroservice.ui.response;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Album {
    private String id;
    private String name;
    private Double price;
    private String userId;
}
