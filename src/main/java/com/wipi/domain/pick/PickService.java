package com.wipi.domain.pick;

import com.wipi.domain.product.ProductRepository;
import com.wipi.infra.comm.CommFileService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PickService {

    private final PickRepository pickRepository;

    @Transactional
    public void save(PickCommand.Save command) {
        final Long productId = command.getProductId();
        final String userId = command.getUserId();

        findPick(productId, userId)
                .ifPresent(pick -> {
                    throw new RuntimeException("이미 찜한 상품입니다");
                });

        Pick pick = Pick.create(command.getProductId(), command.getUserId());
        pickRepository.save(pick);
    }

    @Transactional
    public void delete(PickCommand.Delete command) {
        findPick(command.getProductId(), command.getUserId()).orElseThrow(
                () -> new RuntimeException("찜상품이 존재하지 않습니다")
        );

        pickRepository.deleteByProductIdAndUserId(command.getProductId(),command.getUserId());
    }

    public List<PickInfo.GetUserPicks> getUserPicks(PickCommand.GetUserPicks command) {
        List<Pick> list = pickRepository.findAllByUserId(command.getUserId());

        return list.stream()
                .map(pick -> PickInfo.GetUserPicks.of(pick.getProductId(), pick.getUserId()))
                .toList();
    }

    private Optional<Pick> findPick(Long productId, String userId) {
        return pickRepository.findByProductIdAndUserId(productId, userId);
    }

}
