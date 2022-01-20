package com.tolgaaksoy.accountingapp.service;

import com.tolgaaksoy.accountingapp.exception.CustomException;
import com.tolgaaksoy.accountingapp.mapper.UserMapper;
import com.tolgaaksoy.accountingapp.model.dto.user.*;
import com.tolgaaksoy.accountingapp.model.entity.user.User;
import com.tolgaaksoy.accountingapp.repository.UserRepository;
import com.tolgaaksoy.accountingapp.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    public ResponseEntity<SignInResponseDto> signin(SignInRequestDto requestDto) {
        try {
            User user = getUserByUsername(requestDto.getUsername());
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(requestDto.getUsername(), requestDto.getPassword()));
            SignInResponseDto responseDto = SignInResponseDto.builder()
                    .username(user.getUsername())
                    .roleList(user.getRoleList())
                    .token(jwtTokenProvider.createToken(requestDto.getUsername(), user.getRoleList()))
                    .build();
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        } catch (AuthenticationException e) {
            throw new CustomException("Invalid username/password supplied", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    public ResponseEntity<SignUpResponseDto> signup(SignUpRequestDto requestDto) {
        existsByUsername(requestDto.getUsername());
        User user = UserMapper.MAPPER.toUser(requestDto);
        user.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        userRepository.save(user);
        SignUpResponseDto signUpResponseDto = SignUpResponseDto.builder()
                .token(jwtTokenProvider.createToken(user.getUsername(), user.getRoleList()))
                .build();
        return new ResponseEntity<>(signUpResponseDto, HttpStatus.OK);
    }

    public ResponseEntity<UserInfoResponseDto> search(String username) {
        User user = getUserByUsername(username);
        return new ResponseEntity<>(UserMapper.MAPPER.toUserInfoResponseDto(user), HttpStatus.OK);
    }

    public ResponseEntity<UserInfoResponseDto> whoami(HttpServletRequest req) {
        User user = getUserByUsername(jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(req)));
        return new ResponseEntity<>(UserMapper.MAPPER.toUserInfoResponseDto(user), HttpStatus.OK);
    }

//    public void delete(String username) {
//        userRepository.deleteByUsername(username);
//    }

    private User getUserByUsername(String username) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isEmpty()) {
            throw new CustomException("User not found.", HttpStatus.NOT_FOUND);
        }
        return optionalUser.get();
    }

    private void existsByUsername(String username) {
        if (userRepository.existsByUsername(username)) {
            throw new CustomException("Username is already in use", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }
}