package com.auca.bookloan;

import com.auca.interfaces.Identifiable;
import com.auca.interfaces.Registrable;
import com.auca.exceptions.ValidationException;
import com.auca.student.Student;
import com.auca.librarybook.LibraryBook;

import java.time.LocalDate;

public class BookLoan implements Identifiable, Registrable {

    private String loanId;
    private Student student;
    private LibraryBook book;
    private LocalDate borrowedDate;
    private LocalDate dueDate;
    private LocalDate returnedDate;

    public BookLoan(String loanId, Student student, LibraryBook book,
                    LocalDate borrowedDate, LocalDate dueDate,
                    LocalDate returnedDate) {

        this.loanId = loanId;
        this.student = student;
        this.book = book;
        this.borrowedDate = borrowedDate;
        this.dueDate = dueDate;
        this.returnedDate = returnedDate;
    }

    @Override
    public String getId() {
        return loanId;
    }

    @Override
    public String getDisplayName() {
        return book.getTitle() + " borrowed by " + student.getDisplayName();
    }

    @Override
    public boolean isActive() {
        return returnedDate == null;
    }

    @Override
    public void validate() throws ValidationException {

        if (loanId == null || loanId.trim().isEmpty()) {
            throw new ValidationException("Loan ID is required.");
        }

        if (student == null) {
            throw new ValidationException("Student is required.");
        }

        if (book == null) {
            throw new ValidationException("Library book is required.");
        }

        if (borrowedDate == null) {
            throw new ValidationException("Borrowed date is required.");
        }

        if (dueDate == null || !dueDate.isAfter(borrowedDate)) {
            throw new ValidationException("Due date must be after borrowed date.");
        }

        if (borrowedDate.isAfter(LocalDate.now())) {
            throw new ValidationException("Borrowed date cannot be in the future.");
        }

        if (returnedDate != null && returnedDate.isBefore(borrowedDate)) {
            throw new ValidationException("Returned date cannot be before borrowed date.");
        }
    }

    @Override
    public void printDetails() {
        System.out.println("Loan ID: " + loanId);
        System.out.println("Student: " + student.getDisplayName());
        System.out.println("Book: " + book.getTitle());
        System.out.println("Borrowed Date: " + borrowedDate);
        System.out.println("Due Date: " + dueDate);
        System.out.println("Returned Date: "
                + (returnedDate == null ? "Not yet returned" : returnedDate));
    }

    @Override
    public String getRegistrationSummary() {
        return book.getTitle() + " borrowed by " + student.getDisplayName()
                + (isActive() ? " (Not returned)" : " (Returned)");
    }

    public Student getStudent() {
        return student;
    }

    public LibraryBook getBook() {
        return book;
    }

    public LocalDate getBorrowedDate() {
        return borrowedDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public LocalDate getReturnedDate() {
        return returnedDate;
    }

    public void setReturnedDate(LocalDate returnedDate) {
        this.returnedDate = returnedDate;
    }

    @Override
    public String toString() {
        return getRegistrationSummary() + " - ID: " + loanId;
    }
}