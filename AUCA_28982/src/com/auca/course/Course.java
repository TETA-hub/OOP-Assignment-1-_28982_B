package com.auca.course;

import com.auca.department.Department;
import com.auca.teacher.Teacher;
import com.auca.interfaces.Identifiable;
import com.auca.interfaces.Registrable;
import com.auca.exceptions.ValidationException;

public class Course implements Identifiable, Registrable {

    private String courseId;
    private String courseCode;
    private String courseTitle;
    private int creditHours;
    private Department department;
    private Teacher assignedTeacher;

    public Course(String courseId, String courseCode, String courseTitle,
                  int creditHours, Department department, Teacher assignedTeacher) {

        this.courseId = courseId;
        this.courseCode = courseCode;
        this.courseTitle = courseTitle;
        this.creditHours = creditHours;
        this.department = department;
        this.assignedTeacher = assignedTeacher;
    }

    @Override
    public String getId() {
        return courseId;
    }

    @Override
    public String getDisplayName() {
        return courseTitle;
    }

    @Override
    public boolean isActive() {
        return true;
    }

    @Override
    public void validate() throws ValidationException {

        if (courseId == null || courseId.trim().isEmpty()) {
            throw new ValidationException("Course ID is required.");
        }

        if (courseCode == null || courseCode.trim().isEmpty()) {
            throw new ValidationException("Course code is required.");
        }

        if (courseTitle == null || courseTitle.trim().length() < 2
                || courseTitle.trim().length() > 60) {
            throw new ValidationException("Course title must contain 2 to 60 characters.");
        }

        if (creditHours < 1 || creditHours > 6) {
            throw new ValidationException("Credit hours must be between 1 and 6.");
        }

        if (department == null) {
            throw new ValidationException("Department is required.");
        }

        if (assignedTeacher == null) {
            throw new ValidationException("An assigned teacher is required.");
        }
    }

    @Override
    public void printDetails() {
        System.out.println("Course ID: " + courseId);
        System.out.println("Course Code: " + courseCode);
        System.out.println("Course Title: " + courseTitle);
        System.out.println("Credit Hours: " + creditHours);
        System.out.println("Department: " + department.getDepartmentName());
        System.out.println("Assigned Teacher: " + assignedTeacher.getDisplayName());
    }

    @Override
    public String getRegistrationSummary() {
        return courseCode + " - " + courseTitle;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public int getCreditHours() {
        return creditHours;
    }

    public Department getDepartment() {
        return department;
    }

    public Teacher getAssignedTeacher() {
        return assignedTeacher;
    }

    @Override
    public String toString() {
        return getRegistrationSummary() + " (ID: " + courseId + ")";
    }
}