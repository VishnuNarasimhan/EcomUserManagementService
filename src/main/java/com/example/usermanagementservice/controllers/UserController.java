package com.example.usermanagementservice.controllers;

import com.example.usermanagementservice.dtos.*;
import com.example.usermanagementservice.exceptions.InvalidPasswordException;
import com.example.usermanagementservice.exceptions.InvalidTokenException;
import com.example.usermanagementservice.models.Token;
import com.example.usermanagementservice.models.User;
import com.example.usermanagementservice.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    UserController(UserService userService) {
        this.userService = userService;
    }

    //Sample endpoint for testing
//    @GetMapping("/hello/{name}")
//    public String sayHello(@PathVariable String name) {
//        return "You are a baddass 🔥🔥🔥🔥 " + name;
//    }

    @PostMapping("/signup")
    public UserDto signUp(@RequestBody SignUpRequestDto signUpRequestDto) {
        User user = userService.signUp(
                signUpRequestDto.getEmail(),
                signUpRequestDto.getPassword(),
                signUpRequestDto.getName()
        );
        return UserDto.from(user);
    }

    @PostMapping("/login")
    public LoginResponseDto login(@RequestBody LoginRequestDto loginRequestDto) throws InvalidPasswordException {
        Token token = userService.login(
                loginRequestDto.getEmail(),
                loginRequestDto.getPassword()
        );
        LoginResponseDto loginResponseDto = new LoginResponseDto();
        loginResponseDto.setToken(token);

        return loginResponseDto;
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody LogoutRequestDto token) {
        ResponseEntity<Void> responseEntity = null;
        try {
            userService.logout(token.getTokenValue());
            responseEntity = new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            responseEntity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return responseEntity;
    }

    @PostMapping("/validate/{tokenValue}")
    public UserDto validateToken(@PathVariable String tokenValue) throws InvalidTokenException {
        return UserDto.from(userService.validateToken(tokenValue));
    }

    @GetMapping("/{userId}")
    public String getUserDetails(@PathVariable("userId") Long userId) {
        System.out.println("Received the request for userDetails");
        return "Welcome user with id " + userId;
    }
}
