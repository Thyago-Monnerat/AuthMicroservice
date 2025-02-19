package com.auth.microservice.services;

import com.auth.microservice.dtos.UserLoginDTO;
import com.auth.microservice.dtos.UserRegisterDTO;
import com.auth.microservice.exceptions.PasswordsUnmatchedException;
import com.auth.microservice.exceptions.UserAlreadyRegisteredException;
import com.auth.microservice.exceptions.UserNotRegisteredException;
import com.auth.microservice.mappers.UserMapper;
import com.auth.microservice.models.UserModel;
import com.auth.microservice.repositories.UserRepository;
import com.auth.microservice.utils.CryptoPassword;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final TokenService tokenService;

    public UserService(UserRepository userRepository, UserMapper userMapper, TokenService tokenService) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.tokenService = tokenService;
    }

    public String login(UserLoginDTO userLoginDTO) {

        Optional<UserModel> userModel = userRepository.findByUsername(userLoginDTO.username());

        if (userModel.isEmpty() || !CryptoPassword.verifyPasswords(userLoginDTO.password(), userModel.get().getPassword())) {
            throw new UserNotRegisteredException("Invalid credentials");
        }

        return tokenService.generateToken(userLoginDTO);
    }

    public String register(UserRegisterDTO userRegisterDTO) {
        Optional<UserModel> userByUsername = userRepository.findByUsername(userRegisterDTO.username());
        Optional<UserModel> userByEmail = userRepository.findByEmail(userRegisterDTO.email());

        if (userByUsername.isPresent()) {
            throw new UserAlreadyRegisteredException("Username already registered");
        }

        if (userByEmail.isPresent()) {
            throw new UserAlreadyRegisteredException("Email already registered");
        }

        if(!userRegisterDTO.password().equals(userRegisterDTO.confirmPassword())){
            throw new PasswordsUnmatchedException("Passwords unmatched");
        }

        UserModel user = userMapper.registerToModel(userRegisterDTO);

        user.setPassword(CryptoPassword.hashPassword(user.getPassword()));

        userRepository.save(user);

        return "Account created";
    }

}
