package com.recruiting.pros.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "employees")

public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String applied;
    @Column
    private String name;
    @Column
    private String email;
    @Column
    private String number;
    @Column
    private String gender;
    @Column
    private String student;
    @Column
    private String interest;
    @Column
    private String language;
    @Column
    private String skills;
    @Column
    private String work;
    @Column()
    private String status;
    @Transient
    private MultipartFile CVFile;
    @Lob
    @Column
    private byte[] cv;
    @Column
    private String cvFileName;

}
