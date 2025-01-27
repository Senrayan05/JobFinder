package com.project.job_finder.service;

import com.project.job_finder.entities.JobSeeker;
import com.project.job_finder.repository.JobSeekerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JobSeekerService {
    @Autowired
    private JobSeekerRepository jobSeekerRepository;

    public void register(JobSeeker jobSeeker) {
        if (jobSeekerRepository.existsByEmail(jobSeeker.getEmail())) {
            throw new RuntimeException("Email is already registered");
        }
        jobSeekerRepository.save(jobSeeker); // Save to the database
    }

    // Authenticate a Job Seeker (login)
    public Optional<JobSeeker> authenticate(String email, String password) {
        return jobSeekerRepository.findByEmailAndPassword(email, password);
    }
    
    public List<JobSeeker> getAllJobSeekers() {
        return jobSeekerRepository.findAll(); // Assuming you are using JPA or MongoDB repository
    }

}
