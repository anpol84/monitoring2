package com.example.courseWork.services;

import com.example.courseWork.models.User;
import com.example.courseWork.repo.UserRepo;
import com.example.courseWork.security.PersonDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    private final UserRepo userRepo;
    @Autowired
    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepo.findByLogin(username);
        if (user.isEmpty()){
            throw new UsernameNotFoundException("Пользователь не найден");
        }
        return new PersonDetails(user.get());
    }

    public Optional<User> findByEmail(String email){
        return userRepo.findByEmail(email);
    }
}
