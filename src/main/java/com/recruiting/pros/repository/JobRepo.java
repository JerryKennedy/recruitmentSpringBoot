package com.recruiting.pros.repository;

import com.recruiting.pros.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepo extends JpaRepository<Job, Integer> {
}
