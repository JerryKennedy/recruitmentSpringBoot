package com.recruiting.pros.service;

import com.recruiting.pros.dto.RoleDto;
import com.recruiting.pros.dto.UserDto;
import com.recruiting.pros.model.User;

import java.util.List;

public interface UserService {
    void saveUser(UserDto userDto);
    void createUserWithRole(UserDto userDto, String roleName);
//    List<UserDto> findAllUsersWithRoles();
public List<User> getAllUsers();
    UserDto findUserById(Long id);
    void deleteUserById(Long userId);
void updateUser(UserDto userDto);
    void updateUserWithRole(UserDto userDto, String roleName);
//    void updateUser(UserDto userDto, String roleName);

    User findUserByEmail(String email);
//    List<UserDto> findAllUsers();
}
