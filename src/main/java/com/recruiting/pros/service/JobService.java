package com.recruiting.pros.service;

import com.recruiting.pros.dto.EmployeeDto;
import com.recruiting.pros.dto.JobDto;
import com.recruiting.pros.model.Job;

import java.util.List;

public interface JobService {
    void saveJob(JobDto jobDto);

    JobDto getJobById(Integer id);
    void updateJob(JobDto jobDto);
    List<Job> getAllJobs();
}
