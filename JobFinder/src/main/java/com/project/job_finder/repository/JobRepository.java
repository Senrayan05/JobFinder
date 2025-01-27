package com.project.job_finder.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.project.job_finder.entities.Job;

public interface JobRepository extends JpaRepository<Job, Long> {
}

