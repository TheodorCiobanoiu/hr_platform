package com.dbproject.cvapp.model;


public enum JobType {
    DIRECTOR("Director", "Director al companiei"),
    HR("Resurse umane", "Angajat Resurse umane"),
    JUNIOR_ENGINEER("Junior Engineer", "Junior Engineer"),
    SENIOR_ENGINEER("Senior Engineer", "Senior Engineer"),
    QUALITY_ASSURANCE("Quality Assurance Tester", "Quality Assurance");

    private final String jobTitle;
    private final String jobDescription;


    JobType(String jobTitle, String jobDescription) {
        this.jobTitle = jobTitle;
        this.jobDescription = jobDescription;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public String getJobDescription() {
        return jobDescription;
    }
}
