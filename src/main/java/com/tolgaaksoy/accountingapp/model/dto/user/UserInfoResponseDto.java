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
public class UserInfoResponseDto {
    private String username;
    private String email;
    private List<Role> roleList;
    private String name;
    private String surname;
}
