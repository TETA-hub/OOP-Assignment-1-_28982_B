
package com.auca.administrator;

import com.auca.abstracts.Person;
import com.auca.exceptions.ValidationException;

import java.time.LocalDate;

public class Administrator extends Person {

    private String staffNumber;
    private String jobTitle;
    private String assignedOffice;
    private LocalDate employmentDate;
    private int accessLevel;
    private boolean active;

    public Administrator(String personId, String firstName, String lastName,
                         String email, String phoneNumber, LocalDate dateOfBirth,
                         String staffNumber, String jobTitle,
                         String assignedOffice, LocalDate employmentDate,
                         int accessLevel, boolean active) {

        super(personId, firstName, lastName, email, phoneNumber, dateOfBirth);

        this.staffNumber = staffNumber;
        this.jobTitle = jobTitle;
        this.assignedOffice = assignedOffice;
        this.employmentDate = employmentDate;
        this.accessLevel = accessLevel;
        this.active = active;
    }

    @Override
    public String getRoleDescription() {
        return "Administrator";
    }

    @Override
    public boolean isActive() {
        return active;
    }

    @Override
    public void validate() throws ValidationException {
        super.validate();

        if (staffNumber == null || staffNumber.trim().isEmpty()) {
            throw new ValidationException("Staff number is required.");
        }

        if (jobTitle == null || jobTitle.trim().isEmpty()) {
            throw new ValidationException("Job title is required.");
        }

        if (assignedOffice == null || assignedOffice.trim().isEmpty()) {
            throw new ValidationException("Assigned office is required.");
        }

        if (employmentDate == null || employmentDate.isAfter(LocalDate.now())) {
            throw new ValidationException("Employment date cannot be in the future.");
        }

        if (accessLevel < 1 || accessLevel > 5) {
            throw new ValidationException("Access level must be between 1 and 5.");
        }
    }

    public String getStaffNumber() {
        return staffNumber;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public String getAssignedOffice() {
        return assignedOffice;
    }

    public LocalDate getEmploymentDate() {
        return employmentDate;
    }

    public int getAccessLevel() {
        return accessLevel;
    }

    @Override
    public void printDetails() {
        super.printDetails();

        System.out.println("Staff Number: " + staffNumber);
        System.out.println("Job Title: " + jobTitle);
        System.out.println("Assigned Office: " + assignedOffice);
        System.out.println("Employment Date: " + employmentDate);
        System.out.println("Access Level: " + accessLevel);
        System.out.println("Active: " + active);
    }

    @Override
    public String getRegistrationSummary() {
        return super.getRegistrationSummary()
                + " | Staff Number: " + staffNumber
                + " | Job Title: " + jobTitle;
    }

    @Override
    public String toString() {
        return getRegistrationSummary();
    }
}
