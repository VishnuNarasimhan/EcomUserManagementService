package com.example.usermanagementservice.dtos;

import com.example.usermanagementservice.models.Token;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponseDto {
    private Token token;
}
