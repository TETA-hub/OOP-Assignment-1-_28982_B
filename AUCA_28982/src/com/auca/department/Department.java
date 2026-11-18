
package com.auca.department;

import com.auca.interfaces.Identifiable;
import com.auca.interfaces.Registrable;
import com.auca.exceptions.ValidationException;
import com.auca.teacher.Teacher;

public class Department implements Identifiable, Registrable {

    private String departmentId;
    private String departmentName;
    private String departmentCode;
    private String officeLocation;
    private String contactEmail;
    private Teacher headTeacher;

    public Department(String departmentId, String departmentName,
                      String departmentCode, String officeLocation,
                      String contactEmail, Teacher headTeacher) {

        this.departmentId = departmentId;
        this.departmentName = departmentName;
        this.departmentCode = departmentCode;
        this.officeLocation = officeLocation;
        this.contactEmail = contactEmail;
        this.headTeacher = headTeacher;
    }

    @Override
    public String getId() {
        return departmentId;
    }

    @Override
    public String getDisplayName() {
        return departmentName;
    }

    @Override
    public boolean isActive() {
        return true;
    }

    @Override
    public void validate() throws ValidationException {

        if (departmentId == null || departmentId.trim().isEmpty()) {
            throw new ValidationException("Department ID is required.");
        }

        if (departmentName == null ||
                departmentName.trim().length() < 2 ||
                departmentName.trim().length() > 60) {
            throw new ValidationException("Department name must contain 2 to 60 characters.");
        }

        if (departmentCode == null || departmentCode.trim().isEmpty()) {
            throw new ValidationException("Department code is required.");
        }

        if (officeLocation == null || officeLocation.trim().isEmpty()) {
            throw new ValidationException("Office location is required.");
        }

        if (contactEmail == null || !contactEmail.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new ValidationException("Please enter a valid contact email.");
        }
    }

    @Override
    public void printDetails() {
        System.out.println("Department ID: " + departmentId);
        System.out.println("Department Name: " + departmentName);
        System.out.println("Department Code: " + departmentCode);
        System.out.println("Office Location: " + officeLocation);
        System.out.println("Contact Email: " + contactEmail);

        if (headTeacher == null) {
            System.out.println("Head Teacher: Not assigned");
        } else {
            System.out.println("Head Teacher: " + headTeacher.getDisplayName());
        }
    }

    @Override
    public String getRegistrationSummary() {
        return departmentName + " (" + departmentCode + ")";
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public String getDepartmentCode() {
        return departmentCode;
    }

    public String getOfficeLocation() {
        return officeLocation;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public Teacher getHeadTeacher() {
        return headTeacher;
    }

    public void setHeadTeacher(Teacher headTeacher) {
        this.headTeacher = headTeacher;
    }

    @Override
    public String toString() {
        return getRegistrationSummary() + " - ID: " + departmentId;
    }
}
