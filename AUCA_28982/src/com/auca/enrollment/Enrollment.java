package com.auca.enrollment;

import com.auca.interfaces.Identifiable;
import com.auca.interfaces.Registrable;
import com.auca.exceptions.ValidationException;
import com.auca.student.Student;
import com.auca.course.Course;

import java.time.LocalDate;

public class Enrollment implements Identifiable, Registrable {

    private String enrollmentId;
    private Student student;
    private Course course;
    private LocalDate enrollmentDate;
    private String semester;
    private String enrollmentStatus;

    public Enrollment(String enrollmentId, Student student, Course course,
                      LocalDate enrollmentDate, String semester,
                      String enrollmentStatus) {

        this.enrollmentId = enrollmentId;
        this.student = student;
        this.course = course;
        this.enrollmentDate = enrollmentDate;
        this.semester = semester;
        this.enrollmentStatus = enrollmentStatus;
    }

    @Override
    public String getId() {
        return enrollmentId;
    }

    @Override
    public String getDisplayName() {
        return student.getDisplayName() + " - " + course.getCourseTitle();
    }

    @Override
    public boolean isActive() {
        return "ACTIVE".equalsIgnoreCase(enrollmentStatus);
    }

    @Override
    public void validate() throws ValidationException {

        if (enrollmentId == null || enrollmentId.trim().isEmpty()) {
            throw new ValidationException("Enrollment ID is required.");
        }

        if (student == null) {
            throw new ValidationException("Student is required.");
        }

        if (course == null) {
            throw new ValidationException("Course is required.");
        }

        if (enrollmentDate == null || enrollmentDate.isAfter(LocalDate.now())) {
            throw new ValidationException("Enrollment date cannot be in the future.");
        }

        if (semester == null || semester.trim().isEmpty()) {
            throw new ValidationException("Semester is required.");
        }

        if (enrollmentStatus == null ||
                (!enrollmentStatus.equalsIgnoreCase("ACTIVE")
                        && !enrollmentStatus.equalsIgnoreCase("COMPLETED")
                        && !enrollmentStatus.equalsIgnoreCase("CANCELLED"))) {
            throw new ValidationException("Status must be ACTIVE, COMPLETED, or CANCELLED.");
        }
    }

    @Override
    public void printDetails() {
        System.out.println("Enrollment ID: " + enrollmentId);
        System.out.println("Student: " + student.getDisplayName());
        System.out.println("Course: " + course.getCourseTitle());
        System.out.println("Enrollment Date: " + enrollmentDate);
        System.out.println("Semester: " + semester);
        System.out.println("Status: " + enrollmentStatus);
    }

    @Override
    public String getRegistrationSummary() {
        return student.getDisplayName() + " enrolled in "
                + course.getCourseTitle() + " (" + semester + ")";
    }

    public Student getStudent() {
        return student;
    }

    public Course getCourse() {
        return course;
    }

    public LocalDate getEnrollmentDate() {
        return enrollmentDate;
    }

    public String getSemester() {
        return semester;
    }

    public String getEnrollmentStatus() {
        return enrollmentStatus;
    }

    public void setEnrollmentStatus(String enrollmentStatus) {
        this.enrollmentStatus = enrollmentStatus;
    }

    @Override
    public String toString() {
        return getRegistrationSummary() + " - ID: " + enrollmentId;
    }
}