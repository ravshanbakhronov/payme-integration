package com.example.paymeintegration.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReqCardDto {
    private String number;
    private String expire;
}
