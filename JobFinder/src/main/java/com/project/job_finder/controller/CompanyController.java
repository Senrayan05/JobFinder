package com.project.job_finder.controller;

import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.project.job_finder.entities.Company;
import com.project.job_finder.entities.JobSeeker;
import com.project.job_finder.service.CompanyService;
import com.project.job_finder.service.EmailService;
import com.project.job_finder.service.JobSeekerService;

import java.util.List;
import java.util.Optional;

@Controller
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @Autowired
    private EmailService emailService;
    
    @Autowired
    private JobSeekerService jobSeekerService;


    @PostMapping("/")
    public ResponseEntity<Company> registerCompany(@RequestBody Company company) {
        try {
            Company registeredCompany = companyService.registerCompany(company);
            return ResponseEntity.status(HttpStatus.CREATED).body(registeredCompany);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null); // Customize this message if needed
        }
    }

    @PutMapping("/verify/{id}")
    public ResponseEntity<?> verifyCompany(@PathVariable Long id) {
        Optional<Company> verifiedCompany = companyService.verifyCompany(id);
        if (verifiedCompany.isPresent()) {
            Company company = verifiedCompany.get();
            try {
                emailService.sendEmail(company.getEmail(), "Verification Successful",
                        "Your account has been successfully verified!");
                return ResponseEntity.ok(company);
            } catch (MessagingException e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Verification succeeded, but email sending failed.");
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Company not found.");
    }

    @GetMapping("/")
    public String home() {
        return "home"; // Ensure the file 'home.html' exists in the templates directory
    }
    
    @GetMapping("/login")
    public String showLoginPage() {
        return "login"; // Returns login page view
    }
    
    @GetMapping("/register/seeker")
    public String showJobSeekerRegistrationPage() {
        return "registerJobSeeker"; // Return registration page view
    }
    
    @GetMapping("/admin/view-job-seekers")
    public String viewJobSeekers(Model model) {
        List<JobSeeker> jobSeekers = jobSeekerService.getAllJobSeekers();
        model.addAttribute("jobSeekers", jobSeekers);
        return "viewJobSeekers";  // View to list job seekers
    }


   
    
    

}
