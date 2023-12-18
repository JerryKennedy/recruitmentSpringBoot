package com.recruiting.pros.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "deadline")
    @Temporal(TemporalType.DATE)
    private LocalDate deadline;
    @Column
    private LocalTime isaha;
    @Column
    private String description;
    @Column
    private String benefits;
    @Column
    private String title;
    @Column
    private String requirement;
    @Column
    private String position;
    @Column
    private String instruction;
    @Column
    private String department;
    @Column
    private boolean posted;

    public Job(LocalDate deadline) {
        this.deadline = deadline;
    }




}

