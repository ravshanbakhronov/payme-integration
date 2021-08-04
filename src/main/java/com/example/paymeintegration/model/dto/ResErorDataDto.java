package com.example.paymeintegration.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResErorDataDto <T>{
    private String message;
    private Integer code;
    private T data;
}
