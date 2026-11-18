
package com.auca.student;

import com.auca.abstracts.AcademicMember;
import com.auca.department.Department;
import com.auca.exceptions.ValidationException;

import java.time.LocalDate;

public class Student extends AcademicMember {

    private String studentNumber;
    private String programName;
    private LocalDate admissionDate;
    private int studyYear;
    private String guardianPhone;
    private boolean scholarshipStatus;

    public Student(String personId, String firstName, String lastName,
                   String email, String phoneNumber, LocalDate dateOfBirth,
                   String memberNumber, Department department,
                   LocalDate joinDate, String academicStatus,
                   String institutionalEmail, String advisorName,
                   String studentNumber, String programName,
                   LocalDate admissionDate, int studyYear,
                   String guardianPhone, boolean scholarshipStatus) {

        super(personId, firstName, lastName, email, phoneNumber,
                dateOfBirth, memberNumber, department, joinDate,
                academicStatus, institutionalEmail, advisorName);

        this.studentNumber = studentNumber;
        this.programName = programName;
        this.admissionDate = admissionDate;
        this.studyYear = studyYear;
        this.guardianPhone = guardianPhone;
        this.scholarshipStatus = scholarshipStatus;
    }

    @Override
    public String getRoleDescription() {
        return "Student";
    }

    @Override
    public String getAcademicCategory() {
        return "Undergraduate Student";
    }

    @Override
    public void validate() throws ValidationException {
        super.validate();

        if (studentNumber == null || studentNumber.trim().isEmpty()) {
            throw new ValidationException("Student number is required.");
        }

        if (programName == null ||
                programName.trim().length() < 2 ||
                programName.trim().length() > 60) {
            throw new ValidationException("Program name must contain 2 to 60 characters.");
        }

        if (admissionDate == null || admissionDate.isAfter(LocalDate.now())) {
            throw new ValidationException("Admission date cannot be in the future.");
        }

        if (studyYear < 1 || studyYear > 6) {
            throw new ValidationException("Study year must be between 1 and 6.");
        }

        if (guardianPhone == null ||
                !guardianPhone.matches("\\+?\\d{10,15}")) {
            throw new ValidationException("Guardian phone must contain 10 to 15 digits.");
        }
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public String getProgramName() {
        return programName;
    }

    public LocalDate getAdmissionDate() {
        return admissionDate;
    }

    public int getStudyYear() {
        return studyYear;
    }

    public String getGuardianPhone() {
        return guardianPhone;
    }

    public boolean isScholarshipStatus() {
        return scholarshipStatus;
    }

    @Override
    public void printDetails() {
        super.printDetails();

        System.out.println("Student Number: " + studentNumber);
        System.out.println("Program: " + programName);
        System.out.println("Admission Date: " + admissionDate);
        System.out.println("Study Year: " + studyYear);
        System.out.println("Guardian Phone: " + guardianPhone);
        System.out.println("Scholarship: " +
                (scholarshipStatus ? "Yes" : "No"));
    }

    @Override
    public String getRegistrationSummary() {
        return super.getRegistrationSummary()
                + " | Student Number: " + studentNumber
                + " | Program: " + programName;
    }

    @Override
    public String toString() {
        return getRegistrationSummary();
    }
}
