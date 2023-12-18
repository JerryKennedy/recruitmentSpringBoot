package com.recruiting.pros.service;


import com.recruiting.pros.dto.JobDto;
import com.recruiting.pros.model.Job;
import com.recruiting.pros.repository.JobRepo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobServiceImpl implements JobService {


    private final JobRepo jobRepo;


    public JobServiceImpl(JobRepo jobRepo) {
        this.jobRepo = jobRepo;
    }


    @Override
    public void saveJob(JobDto jobDto) {
            Job job = mapToJob(jobDto);
            jobRepo.save(job);

    }

    @Override
    public List<Job> getAllJobs() {
        List<Job> jobs = jobRepo.findAll();
        return jobs;
    }

    private JobDto mapToJobDto(Job job){
        JobDto jobDto = new JobDto();
        jobDto.setId(job.getId());
        jobDto.setDeadline(job.getDeadline());
        jobDto.setBenefits(job.getBenefits());
        jobDto.setIsaha(job.getIsaha());
        jobDto.setDepartment(job.getDepartment());
        jobDto.setDescription(job.getDescription());
        jobDto.setInstruction(job.getInstruction());
        jobDto.setRequirement(job.getRequirement());
        jobDto.setTitle(job.getTitle());
        jobDto.setPosition(job.getPosition());
        return jobDto;

    }
    private Job mapToJob(JobDto jobDto){
        Job job = new Job();
        job.setId(jobDto.getId());
        job.setDeadline(jobDto.getDeadline());
        job.setBenefits(jobDto.getBenefits());
        job.setIsaha(jobDto.getIsaha());
        job.setDepartment(jobDto.getDepartment());
        job.setDescription(jobDto.getDescription());
        job.setInstruction(jobDto.getInstruction());
        job.setRequirement(jobDto.getRequirement());
        job.setTitle(jobDto.getTitle());
        job.setPosition(jobDto.getPosition());
        return job;

    }


    @Override
    public void updateJob(JobDto jobDto) {
        Job existingJob = jobRepo.findById(jobDto.getId()).orElseThrow(() -> new EntityNotFoundException("Job not found"));

        // Update fields
        existingJob.setTitle(jobDto.getTitle());
        existingJob.setDescription(jobDto.getDescription());
        existingJob.setDeadline(jobDto.getDeadline());
        existingJob.setBenefits(jobDto.getBenefits());
        existingJob.setIsaha(jobDto.getIsaha());
        existingJob.setDepartment(jobDto.getDepartment());
        existingJob.setInstruction(jobDto.getInstruction());
        existingJob.setRequirement(jobDto.getRequirement());
        existingJob.setPosition(jobDto.getPosition());
        // ... Update other fields similarly

        jobRepo.save(existingJob);
    }

 @Override
public JobDto getJobById(Integer id) {
    Job job = jobRepo.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Job not found"));
    return mapToJobDto(job); // Implement mapJobToDto to convert Job entity to JobDto
}

}
