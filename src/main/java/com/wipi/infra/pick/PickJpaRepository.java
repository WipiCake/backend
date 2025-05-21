package com.wipi.infra.pick;

import com.wipi.domain.pick.Pick;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface PickJpaRepository extends JpaRepository<Pick, Long> {
    List<Pick> findAllByUserId(String userId);

    Pick findByProductId(Long productId);

    void deleteByProductIdAndUserId(Long productId, String userId);

    Optional<Pick> findByProductIdAndUserId(Long productId, String userId);
}
