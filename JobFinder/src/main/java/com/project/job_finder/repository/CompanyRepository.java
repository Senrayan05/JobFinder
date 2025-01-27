//package com.project.job_finder.repository;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import com.project.job_finder.entities.Company;
//
//import java.util.Optional;
//
//public interface CompanyRepository extends JpaRepository<Company, Long> {
// Optional<Company> findByEmail(String email);
//}
//


package com.project.job_finder.repository;

import com.project.job_finder.entities.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    // Custom query method to find a Company by email and password
    Optional<Company> findByEmailAndPassword(String email, String password);
}
