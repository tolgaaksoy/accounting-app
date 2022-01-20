package com.tolgaaksoy.accountingapp.model.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignUpResponseDto {
    private String token;
    private String tokenType = "Bearer";
}
