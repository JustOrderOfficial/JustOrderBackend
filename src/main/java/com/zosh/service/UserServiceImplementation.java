package com.zosh.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.zosh.dto.UserDTO;
import com.zosh.exception.UserException;
import com.zosh.mapper.UserMapper;
import com.zosh.repository.UserRepository;
import com.zosh.modal.User;

@Service
public class UserServiceImplementation implements UserService {

    private final UserRepository userRepository;

    public UserServiceImplementation(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Fetch user by ID
    @Override
    public UserDTO findUserById(Long userId) throws UserException {
        // Get the currently authenticated user
        org.springframework.security.core.userdetails.User principal = 
            (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = principal.getUsername();  // The email is stored as the username in the principal

        // Get the user's role from the database
        Optional<User> authenticatedUser = userRepository.findByEmail(email);
        User user = authenticatedUser.orElseThrow(() -> new UserException("User does not exist with email " + email));

        // If the authenticated user is not an ADMIN, they can only access their own profile
        if (!"ADMIN".equals(user.getRole()) && !user.getId().equals(userId)) {
            throw new UserException("You are not authorized to view this user's details.");
        }

        Optional<User> userToFind = userRepository.findById(userId);

        if (userToFind.isPresent()) {
            return UserMapper.toDTO(userToFind.get());
        } else {
            throw new UserException("User not found with id " + userId);
        }
    }

    // Fetch user profile based on the currently authenticated user (via SecurityContext)
    @Override
    public UserDTO findUserProfileByAuth() throws UserException {
        // Get the currently authenticated user from SecurityContext (this is the email)
        org.springframework.security.core.userdetails.User principal = 
            (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = principal.getUsername();  // The email is stored as the username in the principal

        // Find the user by email in the repository
        Optional<User> user = userRepository.findByEmail(email);

        // If the user is not found, throw an exception
        User foundUser = user.orElseThrow(() -> new UserException("User does not exist with email " + email));

        // Convert the found user to UserDTO and return it
        return UserMapper.toDTO(foundUser);
    }

    // Fetch all users (only for admin role)
    @Override
    public List<UserDTO> findAllUsers() throws UserException {
        // Get the currently authenticated user
        org.springframework.security.core.userdetails.User principal = 
            (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = principal.getUsername();  // The email is stored as the username in the principal

        // Get the user's role from the database
        Optional<User> authenticatedUser = userRepository.findByEmail(email);
        User user = authenticatedUser.orElseThrow(() -> new UserException("User does not exist with email " + email));

        // If the authenticated user is not an ADMIN, they cannot access the list of all users
        if (!"ADMIN".equals(user.getRole())) {
            throw new UserException("You are not authorized to view all users.");
        }

        // Fetch all users if the authenticated user is an ADMIN
        List<User> users = userRepository.findAllByOrderByCreatedAtDesc();
        return users.stream().map(UserMapper::toDTO).collect(Collectors.toList());
    }

    // Fetch all users with pagination (only for admin role)
    @Override
    public Page<UserDTO> findAllUsers(Pageable pageable) throws UserException {
        // Get the currently authenticated user
        org.springframework.security.core.userdetails.User principal = 
            (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = principal.getUsername();  // The email is stored as the username in the principal

        // Get the user's role from the database
        Optional<User> authenticatedUser = userRepository.findByEmail(email);
        User user = authenticatedUser.orElseThrow(() -> new UserException("User does not exist with email " + email));

        // If the authenticated user is not an ADMIN, they cannot access the list of all users
        if (!"ADMIN".equals(user.getRole())) {
            throw new UserException("You are not authorized to view all users.");
        }

        // Fetch paginated users if the authenticated user is an ADMIN
        Page<User> users = userRepository.findAll(pageable);
        return users.map(UserMapper::toDTO);
    }
}
