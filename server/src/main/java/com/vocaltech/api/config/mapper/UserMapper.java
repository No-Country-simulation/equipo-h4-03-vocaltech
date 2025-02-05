package com.vocaltech.api.config.mapper;

import com.vocaltech.api.dto.response.auth.UserResponseDto;
import com.vocaltech.api.model.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponseDto toUserResponseDTO(User user);
    List<UserResponseDto> toUserResponseDTO(List<User> user);
}
