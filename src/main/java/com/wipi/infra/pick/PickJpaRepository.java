package com.wipi.infra.pick;

import com.wipi.domain.pick.Pick;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PickJpaRepository extends JpaRepository<Pick, Long> {
    List<Pick> findAllByUserId(String userId);
}
