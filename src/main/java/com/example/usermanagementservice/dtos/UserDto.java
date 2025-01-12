package com.example.usermanagementservice.dtos;

import com.example.usermanagementservice.models.Role;
import com.example.usermanagementservice.models.User;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserDto {
    private String name;
    private String email;
    private List<Role> roles;
    private boolean isEmailVerified;

    public static UserDto from(User user){
        UserDto userDto = new UserDto();
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        userDto.setEmailVerified(user.isEmailVerified());
        userDto.setRoles(user.getRoles());

        return userDto;
    }

}
