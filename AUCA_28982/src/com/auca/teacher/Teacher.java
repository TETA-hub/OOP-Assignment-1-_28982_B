
package com.auca.teacher;

import com.auca.abstracts.AcademicMember;
import com.auca.department.Department;
import com.auca.exceptions.ValidationException;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Teacher extends AcademicMember {

    private String employeeNumber;
    private String specialization;
    private LocalDate hireDate;
    private BigDecimal salary;
    private String officeNumber;
    private String employmentType;

    public Teacher(String personId, String firstName, String lastName,
                   String email, String phoneNumber, LocalDate dateOfBirth,
                   String memberNumber, Department department,
                   LocalDate joinDate, String academicStatus,
                   String institutionalEmail, String advisorName,
                   String employeeNumber, String specialization,
                   LocalDate hireDate, BigDecimal salary,
                   String officeNumber, String employmentType) {

        super(personId, firstName, lastName, email, phoneNumber,
                dateOfBirth, memberNumber, department, joinDate,
                academicStatus, institutionalEmail, advisorName);

        this.employeeNumber = employeeNumber;
        this.specialization = specialization;
        this.hireDate = hireDate;
        this.salary = salary;
        this.officeNumber = officeNumber;
        this.employmentType = employmentType;
    }

    @Override
    public String getRoleDescription() {
        return "Teacher";
    }

    @Override
    public String getAcademicCategory() {
        return "Academic Staff";
    }

    @Override
    public void validate() throws ValidationException {
        super.validate();

        if (employeeNumber == null || employeeNumber.trim().isEmpty()) {
            throw new ValidationException("Employee number is required.");
        }

        if (specialization == null || specialization.trim().isEmpty()) {
            throw new ValidationException("Specialization is required.");
        }

        if (hireDate == null || hireDate.isAfter(LocalDate.now())) {
            throw new ValidationException("Hire date cannot be in the future.");
        }

        if (salary == null || salary.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValidationException("Salary must be greater than zero.");
        }

        if (officeNumber == null || officeNumber.trim().isEmpty()) {
            throw new ValidationException("Office number is required.");
        }

        if (employmentType == null || employmentType.trim().isEmpty()) {
            throw new ValidationException("Employment type is required.");
        }
    }

    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public String getSpecialization() {
        return specialization;
    }

    public LocalDate getHireDate() {
        return hireDate;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public String getOfficeNumber() {
        return officeNumber;
    }

    public String getEmploymentType() {
        return employmentType;
    }

    @Override
    public void printDetails() {
        super.printDetails();

        System.out.println("Employee Number: " + employeeNumber);
        System.out.println("Specialization: " + specialization);
        System.out.println("Hire Date: " + hireDate);
        System.out.println("Salary: " + salary);
        System.out.println("Office Number: " + officeNumber);
        System.out.println("Employment Type: " + employmentType);
    }

    @Override
    public String getRegistrationSummary() {
        return super.getRegistrationSummary()
                + " | Employee Number: " + employeeNumber
                + " | Specialization: " + specialization;
    }

    @Override
    public String toString() {
        return getRegistrationSummary();
    }
}
