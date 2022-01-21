package com.tolgaaksoy.accountingapp.model.dto.user;

import com.tolgaaksoy.accountingapp.model.entity.user.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequestDto {
    private String username;
    private String password;
    private String email;
    private List<Role> roleList;
    private String name;
    private String surname;
}
