package com.wipi.infra.filter;

import com.wipi.domain.jwt.JwtService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AuthLogoutFilter {

    private final JwtService jwtService;

}
