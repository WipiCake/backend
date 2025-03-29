package com.wipi.service.front;

import com.wipi.model.entity.AccessLogEntity;
import com.wipi.model.param.AccessLogGetAllParam;
import com.wipi.model.result.RestResult;
import com.wipi.service.persist.LogService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class LogFrontService {
    private final LogService logService;

    public RestResult getAccessLogs(AccessLogGetAllParam param){
        if(param.getPage() == null){
            param.setPage(0);
        }
        if(param.getSize() == null){
            param.setSize(10);
        }

        Pageable pageable = PageRequest.of(param.getPage(), param.getSize());
        Page<AccessLogEntity> logs = logService.getAccessLogs(pageable);

        return new RestResult("All Access Log","200", Map.of("logs",logs));
    }

}
