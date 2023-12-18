package com.recruiting.pros.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JobDto {
    private int id;
    private boolean posted;
    private LocalDate deadline;
    private LocalTime isaha;
    private String description;
    private String benefits;
    private String title;
    private String requirement;
    private String position;
    private String instruction;
    private String department;
}
