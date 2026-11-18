package com.auca.librarybook;

import com.auca.interfaces.Identifiable;
import com.auca.interfaces.Registrable;
import com.auca.exceptions.ValidationException;

public class LibraryBook implements Identifiable, Registrable {

    private String bookId;
    private String isbn;
    private String title;
    private String author;
    private int totalCopies;
    private int availableCopies;

    public LibraryBook(String bookId, String isbn, String title,
                       String author, int totalCopies, int availableCopies) {

        this.bookId = bookId;
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.totalCopies = totalCopies;
        this.availableCopies = availableCopies;
    }

    @Override
    public String getId() {
        return bookId;
    }

    @Override
    public String getDisplayName() {
        return title;
    }

    @Override
    public boolean isActive() {
        return availableCopies > 0;
    }

    @Override
    public void validate() throws ValidationException {

        if (bookId == null || bookId.trim().isEmpty()) {
            throw new ValidationException("Book ID is required.");
        }

        if (isbn == null || isbn.trim().isEmpty()) {
            throw new ValidationException("ISBN is required.");
        }

        if (title == null || title.trim().length() < 2
                || title.trim().length() > 60) {
            throw new ValidationException("Book title must contain 2 to 60 characters.");
        }

        if (author == null || author.trim().isEmpty()) {
            throw new ValidationException("Author name is required.");
        }

        if (totalCopies < 0) {
            throw new ValidationException("Total copies cannot be negative.");
        }

        if (availableCopies < 0 || availableCopies > totalCopies) {
            throw new ValidationException(
                    "Available copies must be between zero and total copies.");
        }
    }

    @Override
    public void printDetails() {
        System.out.println("Book ID: " + bookId);
        System.out.println("ISBN: " + isbn);
        System.out.println("Title: " + title);
        System.out.println("Author: " + author);
        System.out.println("Total Copies: " + totalCopies);
        System.out.println("Available Copies: " + availableCopies);
    }

    @Override
    public String getRegistrationSummary() {
        return title + " by " + author
                + " (Available copies: " + availableCopies + ")";
    }

    public String getTitle() {
        return title;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getAuthor() {
        return author;
    }

    public int getTotalCopies() {
        return totalCopies;
    }

    public int getAvailableCopies() {
        return availableCopies;
    }

    public void setAvailableCopies(int availableCopies) {
        this.availableCopies = availableCopies;
    }

    @Override
    public String toString() {
        return getRegistrationSummary() + " - ID: " + bookId;
    }
}