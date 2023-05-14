package com.dbproject.cvapp.model;


public enum JobType {
    DIRECTOR("Director", "Director al companiei");

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