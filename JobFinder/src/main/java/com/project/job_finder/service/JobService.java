package com.project.job_finder.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.job_finder.entities.Job;
import com.project.job_finder.repository.JobRepository;

import java.util.List;

@Service
public class JobService {

 @Autowired
 private JobRepository jobRepository;

 public Job postJob(Job job) {
     return jobRepository.save(job);
 }

 public List<Job> getAllJobs() {
     return jobRepository.findAll();
 }
}
