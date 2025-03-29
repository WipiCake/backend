package com.wipi.service.third;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wipi.model.entity.AccessLogEntity;
import com.wipi.repository.AccessLogJdbcRepository;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Profile("consumer")
@Slf4j
@Service
@RequiredArgsConstructor
public class ConsumerService {

    private final AccessLogJdbcRepository accessLogJdbcRepository;
    private final ObjectMapper objectMapper;
    private static final Integer BATCH_SIZE = 5;
    private final List<AccessLogEntity> listAccessLogEntity = new ArrayList<>();

    @RabbitListener(queues = "log.access", concurrency = "1")
    public void queueAccessLog(AccessLogEntity accessLogEntity) {

        String fullMethodName = "";
        try {
            fullMethodName = this.getClass().getSimpleName() + "." + new Object() {
            }.getClass().getEnclosingMethod().getName();
            log.info("{} 메세지 : {}", fullMethodName, objectMapper.writeValueAsString(accessLogEntity));
            listAccessLogEntity.add(accessLogEntity);

            if (listAccessLogEntity.size() >= BATCH_SIZE) {
                accessLogJdbcRepository.saveAll(listAccessLogEntity);
                listAccessLogEntity.clear();
            }
            log.info("\n {} : save", fullMethodName);

        } catch (Exception e) {
            log.error("ERROR[{}] : {}", fullMethodName, e.getMessage());
        }

    }

    @PreDestroy
    public void onShutdown() {
        log.info("Graceful Shutdown 시작 - 버퍼 데이터 저장 중...");
        accessLogJdbcRepository.saveAll(listAccessLogEntity);

        log.info("AccessLog 버퍼 SaveAll");


    }








}
