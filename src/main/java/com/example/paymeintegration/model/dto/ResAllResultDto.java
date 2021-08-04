package com.example.paymeintegration.model.dto;

import lombok.Data;

@Data
public class ResAllResultDto {
    private String jsonrpc;
    private Integer id;
    private ResResultDto result;
}
