package com.amsidh.mvc.usermicroservice.repository;

import com.amsidh.mvc.usermicroservice.entity.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {

    @Query("SELECT u FROM UserEntity u WHERE u.userId = :userId")
    UserEntity findByUserId(@Param("userId") String userId);

    @Query("SELECT u FROM UserEntity u WHERE u.emailId = :emailId")
    UserEntity findByEmailId(@Param("emailId") String emailId);
}
