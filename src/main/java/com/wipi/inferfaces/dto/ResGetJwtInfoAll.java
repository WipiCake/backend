package com.wipi.inferfaces.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ResGetJwtInfoAll {
    @Schema(example = "JWT:1a2b3c4d-5678-9101-abcdefabcdef1234")
    private String id;

    @Schema(example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyQGVtYWlsLmNvbSIsImlhdCI6MTcwMDAwMDAwMCwiZXhwIjoxNzAwMDAzNjAwfQ.WLE4PfhlMpmsAYbJTHvbRSH5NFbZqX3CpHrbIfQQVj4")
    private String accessToken;

    @Schema(example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyQGVtYWlsLmNvbSIsImlhdCI6MTcwMDAwMDAwMCwiZXhwIjoxNzAwMDA4MDAwfQ.ZNw3StbFlGQe4OVz3SLTu6mQzC2-hYRMfArAtzjtfoc")
    private String refreshToken;

    @Schema(example = "user@email.com")
    private String email;

    @Schema(example = "2025-04-09T23:59:59")
    private LocalDateTime refreshExpiration;

    @Schema(example = "2025-04-08T23:30:00")
    private LocalDateTime accessExpiration;

    @Schema(example = "2025-04-08T23:00:00")
    private LocalDateTime createAt;

    @Schema(example = "2025-04-08T23:20:00")
    private LocalDateTime updateAt;
}
