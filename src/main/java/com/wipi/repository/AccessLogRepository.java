package com.wipi.repository;


import com.wipi.model.entity.AccessLogEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccessLogRepository extends JpaRepository<AccessLogEntity,Long> {

    Page<AccessLogEntity> findAll(Pageable pageable);

}
