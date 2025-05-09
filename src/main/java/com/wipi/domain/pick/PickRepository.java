package com.wipi.domain.pick;

import java.util.List;

public interface PickRepository {

    void save(Pick pick);
    void delete(Long pickId);
    List<Pick> findAllByUserId(String userId);
}
