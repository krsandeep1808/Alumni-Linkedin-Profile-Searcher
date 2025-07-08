package com.example.alumni.dto;

import jakarta.validation.constraints.NotBlank;

public class SearchRequest {
    
    @NotBlank(message = "University name is required")
    private String university;
    
    @NotBlank(message = "Designation is required")
    private String designation;
    
    @NotBlank(message = "Passed year is required")
    private String passedYear;

    // Constructors
    public SearchRequest() {}

    public SearchRequest(String university, String designation, String passedYear) {
        this.university = university;
        this.designation = designation;
        this.passedYear = passedYear;
    }

    // Getters and Setters
    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getPassedYear() {
        return passedYear;
    }

    public void setPassedYear(String passedYear) {
        this.passedYear = passedYear;
    }
}
