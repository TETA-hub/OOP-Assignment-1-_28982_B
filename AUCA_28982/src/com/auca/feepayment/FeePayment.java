package com.auca.feepayment;

import com.auca.interfaces.Identifiable;
import com.auca.interfaces.Registrable;
import com.auca.exceptions.ValidationException;
import com.auca.student.Student;

import java.math.BigDecimal;
import java.time.LocalDate;

public class FeePayment implements Identifiable, Registrable {

    private String paymentId;
    private Student student;
    private BigDecimal amount;
    private LocalDate paymentDate;
    private String paymentMethod;
    private String receiptNumber;

    public FeePayment(String paymentId, Student student, BigDecimal amount,
                      LocalDate paymentDate, String paymentMethod,
                      String receiptNumber) {

        this.paymentId = paymentId;
        this.student = student;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.paymentMethod = paymentMethod;
        this.receiptNumber = receiptNumber;
    }

    @Override
    public String getId() {
        return paymentId;
    }

    @Override
    public String getDisplayName() {
        return "Payment " + paymentId + " - " + student.getDisplayName();
    }

    @Override
    public boolean isActive() {
        return true;
    }

    @Override
    public void validate() throws ValidationException {

        if (paymentId == null || paymentId.trim().isEmpty()) {
            throw new ValidationException("Payment ID is required.");
        }

        if (student == null) {
            throw new ValidationException("Student is required.");
        }

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValidationException("Payment amount must be greater than zero.");
        }

        if (paymentDate == null || paymentDate.isAfter(LocalDate.now())) {
            throw new ValidationException("Payment date cannot be in the future.");
        }

        if (paymentMethod == null ||
                (!paymentMethod.equalsIgnoreCase("CASH")
                        && !paymentMethod.equalsIgnoreCase("MOBILE MONEY")
                        && !paymentMethod.equalsIgnoreCase("BANK TRANSFER")
                        && !paymentMethod.equalsIgnoreCase("CARD"))) {
            throw new ValidationException("Choose a valid payment method.");
        }

        if (receiptNumber == null || receiptNumber.trim().isEmpty()) {
            throw new ValidationException("Receipt number is required.");
        }
    }

    @Override
    public void printDetails() {
        System.out.println("Payment ID: " + paymentId);
        System.out.println("Student: " + student.getDisplayName());
        System.out.println("Amount: " + amount);
        System.out.println("Payment Date: " + paymentDate);
        System.out.println("Payment Method: " + paymentMethod);
        System.out.println("Receipt Number: " + receiptNumber);
    }

    @Override
    public String getRegistrationSummary() {
        return "Payment of " + amount + " by " + student.getDisplayName();
    }

    public Student getStudent() {
        return student;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public String getReceiptNumber() {
        return receiptNumber;
    }

    @Override
    public String toString() {
        return getRegistrationSummary() + " - ID: " + paymentId;
    }
}