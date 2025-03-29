package com.wipi.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "T_ACCESS_LOG"
)
public class AccessLogEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accessLogId;

    @Column(length = 20)
    private String method;

    @Column(length = 200)
    private String uri;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String queryString;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String queryParam;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String requestBody;


    @Column(length = 2000)
    private String headers;

    @Column(length = 200)
    private String userAgent;

    @Column(length = 200)
    private String referer;

    @Column(length = 50)
    private String clientIp;

    @Column(length = 200)
    private String host;

    @Column(length = 255)
    private String authorization;

    @Column
    private LocalDateTime requestAt;

    @Column
    private String threadId;

    @Column
    private LocalDateTime responseAt;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String responseBody;


    @Column(length = 20)
    private String status;

    @Column
    private Integer statusCode;

    @Column
    private Long elapsed;

    @CreationTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createAt;

    @UpdateTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateAt;


    public AccessLogEntity(String method, String uri, String queryString, String queryParam, String requestBody,
                           String headers, String userAgent, String referer, String clientIp, String host,
                           String authorization, LocalDateTime requestAt, String threadId, LocalDateTime responseAt,
                           String responseBody, String status, Integer statusCode, Long elapsed) {
        this.method = method;
        this.uri = uri;
        this.queryString = queryString;
        this.queryParam = queryParam;
        this.requestBody = requestBody;
        this.headers = headers;
        this.userAgent = userAgent;
        this.referer = referer;
        this.clientIp = clientIp;
        this.host = host;
        this.authorization = authorization;
        this.requestAt = requestAt;
        this.threadId = threadId;
        this.responseAt = responseAt;
        this.responseBody = responseBody;
        this.status = status;
        this.statusCode = statusCode;
        this.elapsed = elapsed;
    }
}