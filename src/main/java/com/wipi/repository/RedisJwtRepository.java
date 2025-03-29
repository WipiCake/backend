package com.wipi.repository;

import com.wipi.model.entity.RedisJwtEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RedisJwtRepository extends CrudRepository<RedisJwtEntity, String> {
    Optional<RedisJwtEntity> findByAccessToken(String accessToken);
}
