package com.wipi.infra.pick;

import com.wipi.domain.pick.Pick;
import com.wipi.domain.pick.PickRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PickRepositoryImpl implements PickRepository {

    private final PickJpaRepository pickJpaRepository;

    @Override
    public void save(Pick pick) {
        pickJpaRepository.save(pick);
    }

    @Override
    public void delete(Long pickId) {
        pickJpaRepository.deleteById(pickId);
    }

    @Override
    public List<Pick> findAllByUserId(String userId) {
        return pickJpaRepository.findAllByUserId(userId);
    }
}
