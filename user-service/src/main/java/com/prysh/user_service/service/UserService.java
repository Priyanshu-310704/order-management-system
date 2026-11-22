package com.prysh.user_service.service;

import com.prysh.user_service.dto.UserRequest;
import com.prysh.user_service.dto.UserResponse;
import com.prysh.user_service.entity.User;
import com.prysh.user_service.exception.DuplicateEmailException;
import com.prysh.user_service.exception.UserNotFoundException;
import com.prysh.user_service.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;

    // Create User
    @Transactional
    public UserResponse createUser(UserRequest request){
        String email = normalizeEmail(request.email());
        if(userRepository.existsByEmail(email)){
            throw new DuplicateEmailException("User Already Exists with email: "+email);
        }
        User user=new User();
        user.setName(request.name().trim());
        user.setEmail(email);
        user.setPhone(request.phone());
        User savedUser = userRepository.save(user);
        return mapToResponse(savedUser);
    }

    //Get User By Id
    public UserResponse getUserById(Long id){
        User user=userRepository.findById(id)
                .orElseThrow(()->
                        new UserNotFoundException("User Not found with id: "+id));
        return mapToResponse(user);
    }

    //Get all user
    public List<UserResponse> getAllUsers(){
        return userRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // update user
    @Transactional
    public UserResponse updateUser(UserRequest request,Long id){
        User user=userRepository.findById(id)
                        .orElseThrow(()->
                                new UserNotFoundException("User Not found with id: "+id));
        String email=normalizeEmail(request.email());
        if(userRepository.existsByEmailAndIdNot(email,id)){
            throw new DuplicateEmailException("Email already used by another user: "+email);
        }
        user.setName(request.name().trim());
        user.setEmail(email);
        user.setPhone(request.phone());
        User updatedUser=userRepository.save(user);
        return mapToResponse(updatedUser);
    }
    //delete user
    @Transactional
    public String deleteUser(Long id){
        User user = userRepository.findById(id)
                        .orElseThrow(()->
                                new UserNotFoundException("User not found with id: "+id));
        userRepository.delete(user);
        return "User Deleted Successfully";
    }
    public String normalizeEmail(String email){
        return email.trim().toLowerCase(Locale.ROOT);
    }

    public UserResponse mapToResponse(User user){
        UserResponse userResponse=new UserResponse(user.getId(),user.getName(),user.getEmail(),user.getPhone());
        return userResponse;
    }
}
