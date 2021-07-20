package com.amsidh.mvc.usermicroservice.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "UserInfo")
public class UserEntity implements Serializable {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true)
    private String userId;

    @Column(nullable = false, length = 50)
    private String firstName;

    @Column(nullable = false, length = 50)
    private String lastName;

    @Column(nullable = false, length = 120, unique = true)
    private String emailId;

    @Column(nullable = false)
    private String encryptedPassword;
}
