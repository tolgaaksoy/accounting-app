package com.tolgaaksoy.accountingapp.service;

import com.tolgaaksoy.accountingapp.exception.CustomException;
import com.tolgaaksoy.accountingapp.mapper.UserMapper;
import com.tolgaaksoy.accountingapp.model.dto.user.*;
import com.tolgaaksoy.accountingapp.model.entity.user.User;
import com.tolgaaksoy.accountingapp.repository.UserRepository;
import com.tolgaaksoy.accountingapp.response.APIResponse;
import com.tolgaaksoy.accountingapp.security.JwtTokenProvider;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    public UserServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           JwtTokenProvider jwtTokenProvider,
                           AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public ResponseEntity<APIResponse> signin(SignInRequestDto requestDto) {
        try {
            User user = getUserByUsername(requestDto.getUsername());
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(requestDto.getUsername(), requestDto.getPassword()));
            SignInResponseDto responseDto = SignInResponseDto.builder()
                    .username(user.getUsername())
                    .roleList(user.getRoleList())
                    .token(jwtTokenProvider.createToken(requestDto.getUsername(), user.getRoleList()))
                    .tokenType("Bearer")
                    .build();
            return new ResponseEntity<>(APIResponse.builder().data(responseDto)
                    .message("Success.")
                    .time(Instant.now())
                    .status(200)
                    .build(), HttpStatus.OK);
        } catch (AuthenticationException e) {
            throw new CustomException("Invalid username/password supplied", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @Override
    public ResponseEntity<APIResponse> signup(SignUpRequestDto requestDto) {
        existsByUsernameOrEmail(requestDto.getUsername(), requestDto.getEmail());
        User user = UserMapper.MAPPER.toUser(requestDto);
        user.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        userRepository.save(user);
        SignUpResponseDto responseDto = SignUpResponseDto.builder()
                .token(jwtTokenProvider.createToken(user.getUsername(), user.getRoleList()))
                .tokenType("Bearer")
                .build();
        return new ResponseEntity<>(APIResponse.builder().data(responseDto)
                .message("Success.")
                .time(Instant.now())
                .status(200)
                .build(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<APIResponse> search(String username) {
        User user = getUserByUsername(username);
        UserInfoResponseDto responseDto = UserMapper.MAPPER.toUserInfoResponseDto(user);
        return new ResponseEntity<>(APIResponse.builder().data(responseDto)
                .message("Success")
                .time(Instant.now())
                .status(200)
                .build(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<APIResponse> whoami(HttpServletRequest req) {
        User user = getUserByUsername(jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(req)));
        UserInfoResponseDto responseDto = UserMapper.MAPPER.toUserInfoResponseDto(user);
        return new ResponseEntity<>(APIResponse.builder().data(responseDto)
                .message("Success.")
                .time(Instant.now())
                .status(200)
                .build(), HttpStatus.OK);
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

    private void existsByUsernameOrEmail(String username, String email) {
        if (userRepository.existsByUsernameOrEmail(username, email)) {
            throw new CustomException("Username or email is already in use", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    public String getUsernameByHttpRequest(HttpServletRequest httpServletRequest) {
        String token = jwtTokenProvider.resolveToken(httpServletRequest);
        String username = jwtTokenProvider.getUsername(token);
        return getUserByUsername(username).getUsername();
    }
}