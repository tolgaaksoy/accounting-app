package com.tolgaaksoy.accountingapp.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class APIResponse {
    private Integer status;
    private Object data;
    private Object message;
    private Instant time;
}