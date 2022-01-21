package com.tolgaaksoy.accountingapp.service;

import com.tolgaaksoy.accountingapp.model.dto.user.SignInRequestDto;
import com.tolgaaksoy.accountingapp.model.dto.user.SignUpRequestDto;
import com.tolgaaksoy.accountingapp.response.APIResponse;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

public interface UserService {
    public ResponseEntity<APIResponse> signin(SignInRequestDto requestDto);

    public ResponseEntity<APIResponse> signup(SignUpRequestDto requestDto);

    public ResponseEntity<APIResponse> search(String username);

    public ResponseEntity<APIResponse> whoami(HttpServletRequest req);
}
