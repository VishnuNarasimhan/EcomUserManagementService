package com.example.usermanagementservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvalidTokenExceptionDto {
    private String message;
    private String detail;
}
