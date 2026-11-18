package com.auca.assessment;

import com.auca.interfaces.Identifiable;
import com.auca.interfaces.Registrable;
import com.auca.exceptions.ValidationException;
import com.auca.enrollment.Enrollment;

import java.time.LocalDate;

public class Assessment implements Identifiable, Registrable {

    private String assessmentId;
    private Enrollment enrollment;
    private String assessmentType;
    private double maximumScore;
    private double obtainedScore;
    private LocalDate assessmentDate;

    public Assessment(String assessmentId, Enrollment enrollment,
                      String assessmentType, double maximumScore,
                      double obtainedScore, LocalDate assessmentDate) {

        this.assessmentId = assessmentId;
        this.enrollment = enrollment;
        this.assessmentType = assessmentType;
        this.maximumScore = maximumScore;
        this.obtainedScore = obtainedScore;
        this.assessmentDate = assessmentDate;
    }

    @Override
    public String getId() {
        return assessmentId;
    }

    @Override
    public String getDisplayName() {
        return assessmentType + " - " + enrollment.getStudent().getDisplayName();
    }

    @Override
    public boolean isActive() {
        return true;
    }

    @Override
    public void validate() throws ValidationException {

        if (assessmentId == null || assessmentId.trim().isEmpty()) {
            throw new ValidationException("Assessment ID is required.");
        }

        if (enrollment == null || !enrollment.isActive()) {
            throw new ValidationException("An active enrollment is required.");
        }

        if (assessmentType == null || assessmentType.trim().isEmpty()) {
            throw new ValidationException("Assessment type is required.");
        }

        if (maximumScore <= 0) {
            throw new ValidationException("Maximum score must be greater than zero.");
        }

        if (obtainedScore < 0 || obtainedScore > maximumScore) {
            throw new ValidationException("Obtained score must be between 0 and maximum score.");
        }

        if (assessmentDate == null || assessmentDate.isAfter(LocalDate.now())) {
            throw new ValidationException("Assessment date cannot be in the future.");
        }
    }

    @Override
    public void printDetails() {
        System.out.println("Assessment ID: " + assessmentId);
        System.out.println("Student: " + enrollment.getStudent().getDisplayName());
        System.out.println("Course: " + enrollment.getCourse().getCourseTitle());
        System.out.println("Assessment Type: " + assessmentType);
        System.out.println("Maximum Score: " + maximumScore);
        System.out.println("Obtained Score: " + obtainedScore);
        System.out.println("Assessment Date: " + assessmentDate);
    }

    @Override
    public String getRegistrationSummary() {
        return assessmentType + " for "
                + enrollment.getStudent().getDisplayName()
                + " - Score: " + obtainedScore + "/" + maximumScore;
    }

    public Enrollment getEnrollment() {
        return enrollment;
    }

    public String getAssessmentType() {
        return assessmentType;
    }

    public double getMaximumScore() {
        return maximumScore;
    }

    public double getObtainedScore() {
        return obtainedScore;
    }

    public LocalDate getAssessmentDate() {
        return assessmentDate;
    }

    @Override
    public String toString() {
        return getRegistrationSummary() + " - ID: " + assessmentId;
    }
}