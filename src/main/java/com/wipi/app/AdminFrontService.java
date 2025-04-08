package com.wipi.app;

import com.wipi.domain.jwt.JwtAuthRedis;
import com.wipi.domain.jwt.JwtService;
import com.wipi.inferfaces.dto.ResGetJwtInfoAll;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminFrontService {

    private final JwtService jwtService;

    public List<ResGetJwtInfoAll> getJwtInfoAll() {
        List<JwtAuthRedis> all = jwtService.getJwtInfoAll();

        return all.stream()
                .map(jwt -> new ResGetJwtInfoAll(
                        jwt.getId(),
                        jwt.getAccessToken(),
                        jwt.getRefreshToken(),
                        jwt.getEmail(),
                        jwt.getRefreshExpiration(),
                        jwt.getAccessExpiration(),
                        jwt.getCreateAt(),
                        jwt.getUpdateAt()
                ))
                .collect(Collectors.toList());
    }


}
