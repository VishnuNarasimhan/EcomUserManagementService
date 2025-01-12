package com.example.usermanagementservice.services;

import com.example.usermanagementservice.exceptions.InvalidPasswordException;
import com.example.usermanagementservice.exceptions.InvalidTokenException;
import com.example.usermanagementservice.models.Token;
import com.example.usermanagementservice.models.User;
import com.example.usermanagementservice.repositories.TokenRepository;
import com.example.usermanagementservice.repositories.UserRepository;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

@Getter
@Setter
@Service
public class UserService {
    private UserRepository userRepository;
    private TokenRepository tokenRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    UserService(UserRepository userRepository, TokenRepository tokenRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public User signUp(String email, String password, String name) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        }
        User user = new User();
        user.setEmail(email);
        user.setName(name);
        user.setHashPassword(bCryptPasswordEncoder.encode(password));

        return userRepository.save(user);
    }

    public Token login(String email, String password) throws InvalidPasswordException {
        /*
        1. Check if the user exists with the given mail or not.
        2. If not, throw an exception or redirect the user to signup page.
        3. If yes, then compare the incoming password with the password stored in DB.
        4. If password matches then login successful and return new token.
         */
        Optional<User> optionalUser = userRepository.findByEmail(email);

        if (optionalUser.isEmpty()) {
            return null;
        }

        User user = optionalUser.get();
        if (!bCryptPasswordEncoder.matches(password, user.getHashPassword())) {
            // Throw an exception
            throw new InvalidPasswordException("Please Enter Correct Password");
        }
        // Login successful generate a new token
        Token token = generateToken(user);

        return tokenRepository.save(token);
    }

    private Token generateToken(User user) {
        LocalDate currentTime = LocalDate.now();
        LocalDate thirtyDaysFromCurrentTime = currentTime.plusDays(30);

        Date date = Date.from(thirtyDaysFromCurrentTime.atStartOfDay(ZoneId.systemDefault()).toInstant());

        Token token = new Token();
        token.setExpiryDate(date);

        // Token value is a randomly generated String of 128 characters.
        token.setValue(RandomStringUtils.randomAlphanumeric(128));
        token.setUser(user);

        return token;
    }

    public void logout(String value) throws InvalidTokenException {
        // Validate token - If the token is present in the database or not and as well isDeleted = false.
        Optional<Token> optionalToken = tokenRepository.findByValueAndDeleted(value, false);

        if (optionalToken.isEmpty()) {
            // Throw an exception
            throw new InvalidTokenException();
        }

        Token validToken = optionalToken.get();
        validToken.setDeleted(true);

        tokenRepository.save(validToken);
    }

    public User validateToken(String token) throws InvalidTokenException {
        Optional<Token> optionalToken = tokenRepository.findByValueAndDeleted(token, false);

        if (optionalToken.isEmpty()) {
            return null;
        }

        return optionalToken.get().getUser();
    }
}
