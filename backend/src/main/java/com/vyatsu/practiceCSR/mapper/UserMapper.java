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
    List<UserAuthDto> toListUserAuthDTO(List<User> userList);
    List<UserDTO> toListUserDTO(List<User> userList);
    List<User> toListUser(List<UserDTO> userDTOs);
    @Mapping(target = "password", ignore = true)
    User signUpToUser(SignUpDto signUpDto);
}
