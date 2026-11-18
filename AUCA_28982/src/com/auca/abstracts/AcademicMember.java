
package com.auca.abstracts;

import com.auca.department.Department;
import com.auca.exceptions.ValidationException;

import java.time.LocalDate;

public abstract class AcademicMember extends Person {

    protected String memberNumber;
    protected Department department;
    protected LocalDate joinDate;
    protected String academicStatus;
    protected String institutionalEmail;
    protected String advisorName;

    public AcademicMember(String personId, String firstName, String lastName,
                          String email, String phoneNumber, LocalDate dateOfBirth,
                          String memberNumber, Department department,
                          LocalDate joinDate, String academicStatus,
                          String institutionalEmail, String advisorName) {

        super(personId, firstName, lastName, email, phoneNumber, dateOfBirth);

        this.memberNumber = memberNumber;
        this.department = department;
        this.joinDate = joinDate;
        this.academicStatus = academicStatus;
        this.institutionalEmail = institutionalEmail;
        this.advisorName = advisorName;
    }

    public abstract String getAcademicCategory();

    @Override
    public void validate() throws ValidationException {
        super.validate();

        if (memberNumber == null || memberNumber.trim().isEmpty()) {
            throw new ValidationException("Member number is required.");
        }

        if (department == null) {
            throw new ValidationException("Department is required.");
        }

        if (joinDate == null) {
            throw new ValidationException("Join date is required.");
        }

        if (academicStatus == null || academicStatus.trim().isEmpty()) {
            throw new ValidationException("Academic status is required.");
        }

        if (institutionalEmail == null || !institutionalEmail.contains("@")) {
            throw new ValidationException("Valid institutional email is required.");
        }

        if (advisorName == null || advisorName.trim().isEmpty()) {
            throw new ValidationException("Advisor name is required.");
        }
    }

    public String getMemberNumber() {
        return memberNumber;
    }

    public Department getDepartment() {
        return department;
    }

    public LocalDate getJoinDate() {
        return joinDate;
    }

    public String getAcademicStatus() {
        return academicStatus;
    }

    public String getInstitutionalEmail() {
        return institutionalEmail;
    }

    public String getAdvisorName() {
        return advisorName;
    }

    @Override
    public void printDetails() {
        super.printDetails();
        System.out.println("Member Number: " + memberNumber);
        System.out.println("Department: " + department.getDepartmentName());
        System.out.println("Join Date: " + joinDate);
        System.out.println("Academic Status: " + academicStatus);
        System.out.println("Institutional Email: " + institutionalEmail);
        System.out.println("Advisor: " + advisorName);
    }

    @Override
    public String getRegistrationSummary() {
        return super.getRegistrationSummary()
                + " | Member Number: " + memberNumber
                + " | Category: " + getAcademicCategory();
    }
}
