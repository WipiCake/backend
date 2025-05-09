package com.wipi.infra.access;

import com.wipi.domain.access.AccessLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccessLogJpaRepository extends JpaRepository<AccessLog, Long> {

}
