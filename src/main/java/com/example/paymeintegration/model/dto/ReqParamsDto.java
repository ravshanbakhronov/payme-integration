package com.example.paymeintegration.model.dto;

import lombok.Data;

@Data
public class ReqParamsDto {
    private ReqCardDto card;
    private Boolean save;
}
