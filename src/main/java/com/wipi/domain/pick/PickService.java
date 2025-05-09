package com.wipi.domain.pick;

import com.wipi.domain.product.ProductRepository;
import com.wipi.infra.comm.CommFileService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PickService {

    private final PickRepository pickRepository;

    @Transactional
    public void save(PickCommand.Save command) {
        Pick pick = Pick.create(command.getProductId(), command.getUserId());
        pickRepository.save(pick);
    }

    @Transactional
    public void delete(PickCommand.Delete command) {
        pickRepository.delete(command.getPickId());
    }

    public List<PickInfo.GetUserPicks> getUserPicks(PickCommand.GetUserPicks command) {
        List<Pick> list = pickRepository.findAllByUserId(command.getUserId());

        return list.stream()
                .map(pick -> PickInfo.GetUserPicks.of(pick.getProductId(), pick.getUserId()))
                .toList();
    }



}
