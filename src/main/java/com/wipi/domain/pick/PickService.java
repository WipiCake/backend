package com.wipi.domain.pick;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PickService {

    private final PickRepository pickRepository;

    @Transactional
    public void save(PickCommand.Save command) {
        Pick pick = Pick.create(command.getProductId(), command.getUserId());
        pickRepository.save(pick);
    }




}
