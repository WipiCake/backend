package com.wipi.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Slf4j
public class AccessLog {

    private Long seq;
    private String memberId;
    private String email;
    private String threadId;
    private String host;
    private String authorization;
    private String method;
    private String uri;
    private String service;
    private String os;
    private String deviceClass;
    private String agentName;   // user-agent 에서 추출한 agent name
    private String agentClass;  // user-agent 에서 추출한 agent class
    private String clientIp;    // client ip
    private String country;     // client ip 에서 추출한 country
    private String city;        // client ip 에서 추출한 city
    private long elapsed;       // 요청부터 응답까지 걸린 시간 (ms)
    private String request;     // todo request parameter 와 request body 에 있는 데이터를 합쳐서 저장
    private String response;    // todo response body 에 있는 데이터를 저장
    private String status;      // 200, 400, 500 등
    private String deviceName;  // user-agent 에서 추출한 device name
    private String osName;      // user-agent 에서 추출한 os name
    private String osVersion;   // user-agent 에서 추출한 os version
    private String userAgent;   // user-agent
    private String referer;     // http request header "referer"
    private String errorId;     // 응답이 에러인 경우 에러 ID
    private LocalDateTime requestAt;    // 요청 시각
    private LocalDateTime responseAt;   // 응답 시각
    private String requestId;

    // 새로운 생성자 (테스트에서 사용)
    public AccessLog(String memberId, String status, String uri) {
        this.memberId = memberId;
        this.status = status;
        this.uri = uri;
        this.requestAt = LocalDateTime.now();
    }







}