package com.recruiting.pros.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long Id;
    private String name;
    private String email;
    private String password;
    private List<RoleDto> roles;
}
