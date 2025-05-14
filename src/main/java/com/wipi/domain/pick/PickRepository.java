package com.wipi.domain.pick;

import java.util.List;
import java.util.Optional;

public interface PickRepository {

    void save(Pick pick);
    void delete(Long pickId);
    List<Pick> findAllByUserId(String userId);
    Pick findByProductId(Long productId);
    void deleteByProductIdAndUserId(Long productId, String userId);
    Optional<Pick> findByProductIdAndUserId(Long productId, String userId);
}
