package com.example.paymeintegration.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GeneralResultData<T> {
    private boolean success;
    private Integer errorCode;
    private String errorDescription;
    private T data;
}
