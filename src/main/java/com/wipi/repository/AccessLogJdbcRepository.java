package com.wipi.repository;

import com.wipi.model.entity.AccessLogEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class AccessLogJdbcRepository {

    private final JdbcTemplate jdbcTemplate;

    /**
     * Batch Insert를 수행하여 성능 최적화
     */
    public void saveAll(List<AccessLogEntity> accessLogEntities) {
        String sql = "INSERT INTO t_access_log (method, uri, query_string, query_param, request_body, " +
                "headers, user_agent, referer, client_ip, host, authorization, request_at, " +
                "thread_id, response_at, response_body, status, status_code, elapsed, create_at) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                AccessLogEntity log = accessLogEntities.get(i);

                ps.setString(1, log.getMethod() != null ? log.getMethod() : "");
                ps.setString(2, log.getUri() != null ? log.getUri() : "");
                ps.setString(3, log.getQueryString() != null ? log.getQueryString() : "");
                ps.setString(4, log.getQueryParam() != null ? log.getQueryParam() : "");
                ps.setString(5, log.getRequestBody() != null ? log.getRequestBody() : "");
                ps.setString(6, log.getHeaders() != null ? log.getHeaders() : "");
                ps.setString(7, log.getUserAgent() != null ? log.getUserAgent() : "");
                ps.setString(8, log.getReferer() != null ? log.getReferer() : "");
                ps.setString(9, log.getClientIp() != null ? log.getClientIp() : "");
                ps.setString(10, log.getHost() != null ? log.getHost() : "");
                ps.setString(11, log.getAuthorization() != null ? log.getAuthorization() : "");
                ps.setTimestamp(12, log.getRequestAt() != null ? Timestamp.valueOf(log.getRequestAt()) : null);
                ps.setString(13, log.getThreadId() != null ? log.getThreadId() : "");
                ps.setTimestamp(14, log.getResponseAt() != null ? Timestamp.valueOf(log.getResponseAt()) : null);
                ps.setString(15, log.getResponseBody() != null ? log.getResponseBody() : "");
                ps.setString(16, log.getStatus() != null ? log.getStatus() : "");
                ps.setInt(17, log.getStatusCode() != null ? log.getStatusCode() : 0);
                ps.setLong(18, log.getElapsed() != null ? log.getElapsed() : 0L);
                ps.setTimestamp(19, Timestamp.valueOf(LocalDateTime.now()));

            }

            @Override
            public int getBatchSize() {
                return accessLogEntities.size();
            }
        });
    }
}
