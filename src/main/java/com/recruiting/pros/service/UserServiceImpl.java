package com.recruiting.pros.service;

import com.recruiting.pros.dto.RoleDto;
import com.recruiting.pros.dto.UserDto;
import com.recruiting.pros.model.Job;
import com.recruiting.pros.model.Role;
import com.recruiting.pros.model.User;
import com.recruiting.pros.repository.RoleRepo;
import com.recruiting.pros.repository.UserRepo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{
    private final UserRepo userRepo;
    private final RoleRepo roleRepo;

    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepo userRepo,
                           RoleRepo roleRepo,
                           PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void saveUser(UserDto userDto) {
        User user = new User();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        // Create an employee  role and add it to the user's role list
        Role employeeRole = roleRepo.findByName("EMPLOYEE");
        if (employeeRole == null) {
            employeeRole = new Role();
            employeeRole.setName("EMPLOYEE");
            roleRepo.save(employeeRole);

        }
        List<Role> roles = new ArrayList<>();
        roles.add(employeeRole);
        System.out.println("I'm not null " + employeeRole.toString() );
        user.setRoles(roles);
        userRepo.save(user);
    }


    @Override
    public void deleteUserById(Long userId) {
        User existingUser = userRepo.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        // Delete user roles (if desired)
        existingUser.getRoles().clear();

        // Delete the user
        userRepo.delete(existingUser);
    }


    @Override
    public User findUserByEmail(String email) {

        return userRepo.findByEmail(email);
    }


    private UserDto mapToUserDto(User user){
        UserDto userDto = new UserDto();
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        userDto.setId(user.getId());
        List<Role> roles = user.getRoles();
        List<RoleDto> roleDtos = new ArrayList<>();
        for(Role role:roles){
            RoleDto roleDto = new RoleDto();
            roleDto.setId(role.getId());
            roleDto.setName(role.getName());
            roleDtos.add(roleDto);
        }
        userDto.setRoles(roleDtos);
        System.out.println(roleDtos.size());
        return userDto;
    }


    @Override
    public void createUserWithRole(UserDto userDto, String roleName) {
        User user = new User();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        Role role = roleRepo.findByName(roleName);
        if (role == null) {
            role = new Role();
            role.setName(roleName);
            roleRepo.save(role);
        }

        List<Role> roles = new ArrayList<>();
        roles.add(role);
        user.setRoles(roles);

        userRepo.save(user);
    }

    @Override
    public void updateUserWithRole(UserDto userDto, String roleName) {
        User existingUser = userRepo.findById(userDto.getId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        // Update user properties
        existingUser.setName(userDto.getName());
        existingUser.setEmail(userDto.getEmail());

        // Update password only if provided
        if (!StringUtils.isEmpty(userDto.getPassword())) {
            existingUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
        }

        // Update user roles
        Role role = roleRepo.findByName(roleName);
        if (role == null) {
            role = new Role();
            role.setName(roleName);
            roleRepo.save(role);
        }

        // Clear existing roles and add the new role
        existingUser.getRoles().clear();
        existingUser.getRoles().add(role);

        userRepo.save(existingUser);
    }
    @Override
    public List<User> getAllUsers() {
        List<User> user = userRepo.findAll();
        return user;
    }

    @Override
    public void updateUser(UserDto userDto) {
        User existingUser = userRepo.findById(userDto.getId())
                .orElseThrow(() -> new EntityNotFoundException("Event not found"));
        User updatedUser = mapToUser(userDto);
        updatedUser.setId(existingUser.getId());
        userRepo.save(updatedUser);
    }

    public User mapToUser(UserDto userDto) {
        User user = new User();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());

        List<RoleDto> roleDtos = userDto.getRoles();
        List<Role> roles = roleDtos.stream()
                .map(roleDto -> roleRepo.findById(roleDto.getId()).orElse(null))
                .collect(Collectors.toList());

        user.setRoles(roles);
        return user;
    }
//    private UserDto mapToUserDtos(User user){
//        UserDto userDto = new UserDto();
//        userDto.setName(user.getName());
//        userDto.setEmail(user.getEmail());
//        userDto.setPassword(user.getPassword());
//
//        List<Role> roles = user.getRoles();
//        List<RoleDto> roleDtos = roles.stream()
//                .map(role -> {
//                    RoleDto roleDto = new RoleDto();
//                    roleDto.setId(role.getId());
//                    roleDto.setName(role.getName());
//                    return roleDto;
//                })
//                .collect(Collectors.toList());
//
//        userDto.setRoles(roleDtos);
//        return userDto;
//    }
    @Override
    public UserDto findUserById(Long id) {
        User user = userRepo.findById(id).orElse(null);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }
        return mapToUserDto(user);
    }


//    public User updateUser(Long id, User updatedUser) {
//        User existingUser = userRepo.findById(id).orElse(null);
//
//        if (existingUser != null) {
//            existingUser.setName(updatedUser.getName());
//            existingUser.setEmail(updatedUser.getEmail());
//            existingUser.setPassword(updatedUser.getPassword());
//            // Update other fields as needed
//
//            // Update roles
//            existingUser.setRoles(updatedUser.getRoles());
//
//            return userRepo.save(existingUser);
//        } else {
//            throw new EntityNotFoundException("User not found with id: " + id);
//        }
//    }

    }
