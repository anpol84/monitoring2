package com.example.courseWork.util;


import com.example.courseWork.models.User;
import com.example.courseWork.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

@Component
public class UserValidator implements Validator {
    private final UserService userService;
    @Autowired
    public UserValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;
        try {
            userService.loadUserByUsername(user.getLogin());
        }catch (UsernameNotFoundException e){
            Optional<User> user1 = userService.findByEmail(user.getEmail());
            if (user1.isPresent()){
                errors.rejectValue("email", "", "Человек с такой почтой уже существует");
            }
            return;
        }
        errors.rejectValue("login", "", "Человек с таким логином уже существует");

    }
}
