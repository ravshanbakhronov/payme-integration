package com.example.paymeintegration.model.dto;

import lombok.Data;

@Data
public class ReqCardsCreateDto {
    private Integer id;
    private String method;
    private ReqParamsDto params;
}
