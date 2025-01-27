//package com.project.job_finder.service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.project.job_finder.entities.Company;
//import com.project.job_finder.repository.CompanyRepository;
//
//import java.util.Optional;
//
//@Service
//public class CompanyService {
//
// @Autowired
// private CompanyRepository companyRepository;
//
// public Company registerCompany(Company company) {
//     company.setVerified(false);
//     return companyRepository.save(company);
// }
//
// public Optional<Company> verifyCompany(Long id) {
//     Optional<Company> company = companyRepository.findById(id);
//     company.ifPresent(c -> c.setVerified(true));
//     return company.map(companyRepository::save);
// }
//}

package com.project.job_finder.service;

import com.project.job_finder.entities.Company;
import com.project.job_finder.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    // Register a new company (rename register method to registerCompany)
    public Company registerCompany(Company company) {
        return companyRepository.save(company);
    }

    // Get a company by ID
    public Company getCompanyById(Long id) {
        return companyRepository.findById(id).orElse(null); // Return null if company not found
    }

    // Authenticate company by email and password
    public Optional<Company> authenticate(String email, String password) {
        return companyRepository.findByEmailAndPassword(email, password);
    }


    // Verify company account
    public Optional<Company> verifyCompany(Long companyId) {
        Optional<Company> company = companyRepository.findById(companyId);
        company.ifPresent(comp -> comp.setVerified(true)); // Set verified status to true
        companyRepository.save(company.orElse(null)); // Save changes to the company
        return company;
    }
    
    public List<Company> getAllCompanies() {
        return companyRepository.findAll(); // Assuming you are using JPA or MongoDB repository
    }
}
