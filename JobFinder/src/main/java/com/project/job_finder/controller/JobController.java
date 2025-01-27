package com.project.job_finder.controller;

import com.project.job_finder.entities.Company;
import com.project.job_finder.entities.Job;
import com.project.job_finder.entities.JobSeeker;
import com.project.job_finder.service.CompanyService;
import com.project.job_finder.service.EmailService;
import com.project.job_finder.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.project.job_finder.service.JobSeekerService;



import jakarta.mail.MessagingException;
import java.util.List;
import java.util.Optional;

@Controller
public class JobController {

    @Autowired
    private CompanyService companyService;

    @Autowired
    private JobSeekerService jobSeekerService;

    @Autowired
    private JobService jobService;

    @Autowired
    private EmailService emailService;
    

   
  
    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password, Model model) {
        Optional<JobSeeker> jobSeeker = jobSeekerService.authenticate(email, password);
        Optional<Company> company = companyService.authenticate(email, password);

        if (jobSeeker.isPresent() && jobSeeker.get().isAdmin()) {
            model.addAttribute("jobSeeker", jobSeeker.get());
            return "adminDashboard"; 
        } else if (company.isPresent() && company.get().isAdmin()) {
            model.addAttribute("company", company.get());
            return "adminDashboard"; 
        } else if (jobSeeker.isPresent()) {
            model.addAttribute("jobSeeker", jobSeeker.get());
            return "jobSeekerDashboard"; 
        } else if (company.isPresent()) {
            model.addAttribute("company", company.get());
            return "companyDashboard"; 
        } else {
            model.addAttribute("error", "Invalid email or password!");
            return "login"; 
        }
    }





   
    
    
    @PostMapping("/register/seeker")
    public String registerJobSeeker(@ModelAttribute JobSeeker jobSeeker, @RequestParam(defaultValue = "false") boolean isAdmin) {
        jobSeeker.setAdmin(isAdmin);
        jobSeekerService.register(jobSeeker);
        return "redirect:/login"; 
    }


    @GetMapping("/register/company")
    public String showCompanyRegistrationPage() {
        return "registerCompany"; 
    }

    @PostMapping("/register/company")
    public String registerCompany(@ModelAttribute Company company, @RequestParam(defaultValue = "false") boolean isAdmin) {
        company.setAdmin(isAdmin);  
        companyService.registerCompany(company);
        return "redirect:/login"; 
    }


    @GetMapping("/company/jobs/post")
    public String showJobPostingPage(Model model) {
        return "postJob"; 
    }

    @PostMapping("/company/jobs/post")
    public String postJob(@ModelAttribute Job job, @RequestParam Long companyId) {
        Company company = companyService.getCompanyById(companyId);
        job.setCompany(company);
        jobService.postJob(job);
        return "redirect:/api/company/jobs"; 
    }

    
    @GetMapping("/jobs")
    public String listJobs(Model model) {
        List<Job> jobs = jobService.getAllJobs();
        model.addAttribute("jobs", jobs);
        return "jobList"; 
    }

  
    @PutMapping("/verify/company/{companyId}")
    public String verifyCompany(@PathVariable Long companyId) throws Exception {
        Optional<Company> verifiedCompany = companyService.verifyCompany(companyId);
        if (verifiedCompany.isPresent()) {
            Company company = verifiedCompany.get();
            emailService.sendEmail(company.getEmail(), "Verification Successful", 
                    "Your company account has been successfully verified.");
            return "redirect:/api/company/dashboard"; 
        }
        return "error"; 
    }

    
    @PostMapping("/company/jobs/notify")
    public String notifyCandidates(@RequestParam List<String> emails, @RequestBody String message)
            throws MessagingException {
        for (String email : emails) {
            emailService.sendEmail(email, "Job Notification", message);
        }
        return "Emails Sent Successfully!";
    }
    
    @GetMapping("/admin/job-seekers")
    public String viewJobSeekers(Model model) {
        List<JobSeeker> jobSeekers = jobSeekerService.getAllJobSeekers();
        model.addAttribute("jobSeekers", jobSeekers);
        return "viewJobSeekers";  
    }

    
    @GetMapping("/admin/view-companies") 
    public String viewCompanies(Model model) {
        List<Company> companies = companyService.getAllCompanies();
        model.addAttribute("companies", companies);
        return "viewCompanies";  
    }

    
}
