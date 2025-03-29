package com.wipi.model.result;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Map;

@Data
@JsonIgnoreProperties(ignoreUnknown=true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RestResult {

    private Map<String, Object> data;
    private String message;
    private String status;
    public RestResult() { }


    public RestResult(Map<String, Object> data) {
        this.data = data;
    }

    public RestResult setData(Map<String, Object> data) {

        this.data = data;
        return this;
    }

    public RestResult(String message, String status, Map<String,Object> data){
        this.message = message;
        this.status = status;
        this.data = data;
    }

    public RestResult(String message, String status){
        this.message = message;
        this.status = status;
    }




}