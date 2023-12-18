package com.recruiting.pros.dto;


import lombok.*;
import org.springframework.web.multipart.MultipartFile;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDto {

    private int id;
    private String applied;
    private String name;
    private String email;
    private String number;
    private String gender;
    private String student;
    private String interest;
    private String language;
    private String skills;
    private String work;
    private MultipartFile CVFile;
    private byte[] cv;
    private String cvFileName;
    private String status;
}
