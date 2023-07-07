package com.vyatsu.practiceCSR.mapper;

import com.vyatsu.practiceCSR.dto.auth.SignUpDto;
import com.vyatsu.practiceCSR.dto.auth.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.vyatsu.practiceCSR.entity.api.User;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toUserDto(User user);

    List<UserDto> toListUserDTO(List<User> userList);

    @Mapping(target = "password", ignore = true)
    User signUpToUser(SignUpDto signUpDto);

}
