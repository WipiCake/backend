package com.wipi.infra.pick;

import com.wipi.domain.pick.Pick;
import com.wipi.domain.pick.PickRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PickRepositoryImpl implements PickRepository {

    private final PickJpaRepository pickJpaRepository;

    @Override
    public void save(Pick pick) {
        pickJpaRepository.save(pick);
    }
}
