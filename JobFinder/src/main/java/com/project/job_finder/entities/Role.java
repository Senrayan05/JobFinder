package com.project.job_finder.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Role {
    @Id
    private Long id;
    private String roleName; // e.g., "admin", "user"
}



