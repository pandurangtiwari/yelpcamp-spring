package com.yelpcamp.service;

import com.yelpcamp.exception.ResourceNotFoundException;
import com.yelpcamp.exception.UserAlreadyExistException;
import com.yelpcamp.model.User;
import com.yelpcamp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public User getUser(Long id){
        var user = userRepository.findById(id);
        if(user.isPresent()) return user.get();
        throw new ResourceNotFoundException("User Not Found");
    }

    @Transactional
    public User registerUser(User user) throws UserAlreadyExistException {
        if (isUserPresent(user.getEmail())) {
            throw new UserAlreadyExistException("There is an account with that email address: " + user.getEmail());
        }
        user.setPassword(getEncodedPassword(user.getPassword()));
        return userRepository.save(user);

    }

    @Transactional
    public User getUserByEmail(String email){
        return userRepository.findByEmail(email);
    }

    private boolean isUserPresent(String email){
        return getUserByEmail(email) != null;
    }

    private String getEncodedPassword(String password){
        return bCryptPasswordEncoder.encode(password);
    }
}
