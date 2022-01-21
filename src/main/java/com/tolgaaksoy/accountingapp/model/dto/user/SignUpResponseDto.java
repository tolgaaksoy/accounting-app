package com.tolgaaksoy.accountingapp.model.dto.user;

import com.tolgaaksoy.accountingapp.model.entity.user.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignUpResponseDto {
    private String token;
    private String tokenType = "Bearer";
    private List<Role> roleList;
}
