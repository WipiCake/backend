package com.wipi.app.pick;

import com.wipi.domain.pick.PickCommand;
import com.wipi.domain.pick.PickInfo;
import com.wipi.domain.pick.PickService;
import com.wipi.domain.product.ProductInfo;
import com.wipi.domain.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PickFacade {
    
    private final PickService pickService;
    private final ProductService productService;

    public List<PickResult.GetUserPicks> getUserPicks(String userId) {

        List<Long> productIdList  = pickService.getUserPicks(
                PickCommand.GetUserPicks.of(userId)
        ).stream()
                .map(PickInfo.GetUserPicks::getProductId)
                .distinct()
                .toList();

        List<ProductInfo.GetPickProducts> pickProducts = productService.getPickProducts(productIdList);
        return PickResult.GetUserPicks.from(pickProducts);

    }



}
