package com.tolgaaksoy.accountingapp.model.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoResponseDto {
    private String username;
    private String email;
    private String role;
    private String name;
    private String surname;
}
