package com.wipi.service.persist;


import com.wipi.model.entity.AccessLogEntity;
import com.wipi.repository.AccessLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogService {

    private final AccessLogRepository accessLogRepository;

    public Page<AccessLogEntity> getAccessLogs(Pageable pageable){
        return accessLogRepository.findAll(pageable);
    }

}
