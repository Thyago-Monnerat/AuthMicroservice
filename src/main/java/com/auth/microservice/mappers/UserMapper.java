package com.auth.microservice.mappers;

import com.auth.microservice.dtos.UserRegisterDTO;
import com.auth.microservice.models.UserModel;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserModel registerToModel(UserRegisterDTO userRegisterDTO){
        UserModel user = new UserModel();

        BeanUtils.copyProperties(userRegisterDTO, user);

        return user;
    }
}
