package com.tolgaaksoy.accountingapp.mapper;

import com.tolgaaksoy.accountingapp.model.dto.user.SignUpRequestDto;
import com.tolgaaksoy.accountingapp.model.dto.user.UserInfoResponseDto;
import com.tolgaaksoy.accountingapp.model.entity.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper MAPPER = Mappers.getMapper(UserMapper.class);

    User toUser(SignUpRequestDto dto);

    UserInfoResponseDto toUserInfoResponseDto(User user);
}
