package com.project.job_finder.repository;

import com.project.job_finder.entities.JobSeeker;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface JobSeekerRepository extends JpaRepository<JobSeeker, Long> {
    Optional<JobSeeker> findByEmailAndPassword(String email, String password);
    
    boolean existsByEmail(String email);
}
