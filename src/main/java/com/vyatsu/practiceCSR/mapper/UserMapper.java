package com.vyatsu.practiceCSR.mapper;

import com.vyatsu.practiceCSR.dto.api.UserDTO;
import com.vyatsu.practiceCSR.dto.auth.SignUpDto;
import com.vyatsu.practiceCSR.dto.auth.UserAuthDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.vyatsu.practiceCSR.entity.api.User;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserAuthDto toUserAuthDto(User user);

    UserDTO toUserDTO(User user);

    User toUser(UserDTO userDTO);

    List<UserAuthDto> toListUserDTO(List<User> userList);

    @Mapping(target = "password", ignore = true)
    User signUpToUser(SignUpDto signUpDto);

}
