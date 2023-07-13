package com.vyatsu.practiceCSR.mapper;

import com.vyatsu.practiceCSR.dto.api.RegionDTO;
import com.vyatsu.practiceCSR.dto.api.UserDTO;
import com.vyatsu.practiceCSR.dto.auth.SignUpDto;
import com.vyatsu.practiceCSR.dto.auth.UserAuthDto;
import com.vyatsu.practiceCSR.entity.api.Region;
import com.vyatsu.practiceCSR.entity.api.User;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-07-13T13:23:31+0300",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 20.0.1 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserAuthDto toUserAuthDto(User user) {
        if ( user == null ) {
            return null;
        }

        UserAuthDto.UserAuthDtoBuilder userAuthDto = UserAuthDto.builder();

        if ( user.getId() != null ) {
            userAuthDto.id( user.getId().longValue() );
        }
        userAuthDto.firstname( user.getFirstname() );
        userAuthDto.surname( user.getSurname() );
        userAuthDto.email( user.getEmail() );

        return userAuthDto.build();
    }

    @Override
    public UserDTO toUserDTO(User user) {
        if ( user == null ) {
            return null;
        }

        UserDTO.UserDTOBuilder userDTO = UserDTO.builder();

        userDTO.id( user.getId() );
        userDTO.firstname( user.getFirstname() );
        userDTO.surname( user.getSurname() );
        userDTO.patronymic( user.getPatronymic() );
        userDTO.email( user.getEmail() );
        userDTO.region( regionToRegionDTO( user.getRegion() ) );

        return userDTO.build();
    }

    @Override
    public User toUser(UserDTO userDTO) {
        if ( userDTO == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.id( userDTO.getId() );
        user.email( userDTO.getEmail() );
        user.region( regionDTOToRegion( userDTO.getRegion() ) );
        user.surname( userDTO.getSurname() );
        user.firstname( userDTO.getFirstname() );
        user.patronymic( userDTO.getPatronymic() );

        return user.build();
    }

    @Override
    public List<UserAuthDto> toListUserAuthDTO(List<User> userList) {
        if ( userList == null ) {
            return null;
        }

        List<UserAuthDto> list = new ArrayList<UserAuthDto>( userList.size() );
        for ( User user : userList ) {
            list.add( toUserAuthDto( user ) );
        }

        return list;
    }

    @Override
    public List<UserDTO> toListUserDTO(List<User> userList) {
        if ( userList == null ) {
            return null;
        }

        List<UserDTO> list = new ArrayList<UserDTO>( userList.size() );
        for ( User user : userList ) {
            list.add( toUserDTO( user ) );
        }

        return list;
    }

    @Override
    public List<User> toListUser(List<UserDTO> userDTOs) {
        if ( userDTOs == null ) {
            return null;
        }

        List<User> list = new ArrayList<User>( userDTOs.size() );
        for ( UserDTO userDTO : userDTOs ) {
            list.add( toUser( userDTO ) );
        }

        return list;
    }

    @Override
    public User signUpToUser(SignUpDto signUpDto) {
        if ( signUpDto == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.email( signUpDto.getEmail() );
        user.surname( signUpDto.getSurname() );
        user.firstname( signUpDto.getFirstname() );
        user.patronymic( signUpDto.getPatronymic() );

        return user.build();
    }

    protected RegionDTO regionToRegionDTO(Region region) {
        if ( region == null ) {
            return null;
        }

        RegionDTO.RegionDTOBuilder regionDTO = RegionDTO.builder();

        if ( region.getId() != null ) {
            regionDTO.id( region.getId() );
        }
        regionDTO.name( region.getName() );

        return regionDTO.build();
    }

    protected Region regionDTOToRegion(RegionDTO regionDTO) {
        if ( regionDTO == null ) {
            return null;
        }

        Region region = new Region();

        region.setId( regionDTO.getId() );
        region.setName( regionDTO.getName() );

        return region;
    }
}
