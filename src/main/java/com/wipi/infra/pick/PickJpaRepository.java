package com.wipi.infra.pick;

import com.wipi.domain.pick.Pick;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PickJpaRepository extends JpaRepository<Pick, Long> {

}
