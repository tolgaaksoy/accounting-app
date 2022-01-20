package com.tolgaaksoy.accountingapp.controller;

import com.tolgaaksoy.accountingapp.model.dto.user.*;
import com.tolgaaksoy.accountingapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signin")
    public ResponseEntity<SignInResponseDto> login(@RequestBody SignInRequestDto signInRequestDto) {
        return userService.signin(signInRequestDto);
    }

    @PostMapping("/signup")
    public ResponseEntity<SignUpResponseDto> signup(@RequestBody SignUpRequestDto signInRequestDto) {
        return userService.signup(signInRequestDto);
    }

    @GetMapping(value = "/{username}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<UserInfoResponseDto> search(@PathVariable String username) {
        return userService.search(username);
    }

    @GetMapping(value = "/me")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_ACCOUNTANT')")
    public ResponseEntity<UserInfoResponseDto> whoami(HttpServletRequest request) {
        return userService.whoami(request);
    }

    //
//    @DeleteMapping(value = "/{username}")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    public String delete(@ApiParam("Username") @PathVariable String username) {
//        userService.delete(username);
//        return username;
//    }
}