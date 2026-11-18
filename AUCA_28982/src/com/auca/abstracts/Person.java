
package com.auca.abstracts;

import com.auca.interfaces.Identifiable;
import com.auca.interfaces.Registrable;
import com.auca.exceptions.ValidationException;

import java.time.LocalDate;

public abstract class Person implements Identifiable, Registrable {

    protected String personId;
    protected String firstName;
    protected String lastName;
    protected String email;
    protected String phoneNumber;
    protected LocalDate dateOfBirth;

    public Person(String personId, String firstName, String lastName,
                  String email, String phoneNumber, LocalDate dateOfBirth) {

        this.personId = personId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.dateOfBirth = dateOfBirth;
    }

    public abstract String getRoleDescription();

    @Override
    public String getId() {
        return personId;
    }

    @Override
    public String getDisplayName() {
        return firstName + " " + lastName;
    }

    @Override
    public boolean isActive() {
        return true;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    @Override
    public void validate() throws ValidationException {
        if (personId == null || personId.trim().isEmpty()) {
            throw new ValidationException("Person ID is required.");
        }

        if (firstName == null || firstName.trim().length() < 2) {
            throw new ValidationException("First name must contain at least 2 characters.");
        }

        if (lastName == null || lastName.trim().length() < 2) {
            throw new ValidationException("Last name must contain at least 2 characters.");
        }

        if (email == null || !email.contains("@")) {
            throw new ValidationException("Please enter a valid email.");
        }

        if (phoneNumber == null || !phoneNumber.matches("\\+?\\d{10,15}")) {
            throw new ValidationException("Phone number must contain 10 to 15 digits.");
        }

        if (dateOfBirth == null || !dateOfBirth.isBefore(LocalDate.now())) {
            throw new ValidationException("Date of birth must be in the past.");
        }
    }

    @Override
    public void printDetails() {
        System.out.println("ID: " + personId);
        System.out.println("Name: " + getDisplayName());
        System.out.println("Email: " + email);
        System.out.println("Phone: " + phoneNumber);
        System.out.println("Date of Birth: " + dateOfBirth);
        System.out.println("Role: " + getRoleDescription());
    }

    @Override
    public String getRegistrationSummary() {
        return getDisplayName() + " - " + getRoleDescription()
                + " (ID: " + personId + ")";
    }
}
