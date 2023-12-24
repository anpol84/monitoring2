package com.example.courseWork.util;

import com.example.courseWork.models.User;
import com.example.courseWork.services.RegistrationService;
import com.example.courseWork.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

@Component
public class UserUpdateValidator implements Validator {
    private final RegistrationService registrationService;
    @Autowired
    public UserUpdateValidator(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;
        User user1 = registrationService.findByLogin(user.getLogin());
        if (user1 != null){
            if (user.getId() != user1.getId()){
                errors.rejectValue("login", "", "Человек с таким логином уже существует");
            }
        }
        User user2 = registrationService.findByEmail(user.getEmail());
        if (user2 != null){
            if (user.getId() != user2.getId()){
                errors.rejectValue("email", "", "Человек с такой почтой уже существует");
            }
        }

    }
}
