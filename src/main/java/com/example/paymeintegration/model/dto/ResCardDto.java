package com.example.paymeintegration.model.dto;

import lombok.Data;

@Data
public class ResCardDto {
    private String number;
    private String expire;
    private String token;
    private Boolean recurrent;
    private Boolean verify;
    private String type;
}
