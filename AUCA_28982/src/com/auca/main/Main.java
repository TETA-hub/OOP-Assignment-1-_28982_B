package com.auca.main;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.auca.abstracts.Person;
import com.auca.administrator.Administrator;
import com.auca.assessment.Assessment;
import com.auca.bookloan.BookLoan;
import com.auca.classroom.Classroom;
import com.auca.course.Course;
import com.auca.department.Department;
import com.auca.enrollment.Enrollment;
import com.auca.exceptions.BookUnavailableException;
import com.auca.exceptions.DuplicateRecordException;
import com.auca.exceptions.PrerequisiteMissingException;
import com.auca.exceptions.RecordNotFoundException;
import com.auca.exceptions.ValidationException;
import com.auca.feepayment.FeePayment;
import com.auca.interfaces.Identifiable;
import com.auca.interfaces.Registrable;
import com.auca.librarybook.LibraryBook;
import com.auca.student.Student;
import com.auca.teacher.Teacher;

public class Main {
    private static final Scanner SC = new Scanner(System.in);

    private static final List<Department> DEPARTMENTS = new ArrayList<>();
    private static final List<Student> STUDENTS = new ArrayList<>();
    private static final List<Teacher> TEACHERS = new ArrayList<>();
    private static final List<Administrator> ADMINISTRATORS = new ArrayList<>();
    private static final List<Course> COURSES = new ArrayList<>();
    private static final List<Classroom> CLASSROOMS = new ArrayList<>();
    private static final List<Enrollment> ENROLLMENTS = new ArrayList<>();
    private static final List<Assessment> ASSESSMENTS = new ArrayList<>();
    private static final List<FeePayment> PAYMENTS = new ArrayList<>();
    private static final List<LibraryBook> BOOKS = new ArrayList<>();
    private static final List<BookLoan> LOANS = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println("====================================");
        System.out.println("      AUCA SCHOOL MANAGEMENT SYSTEM");
        System.out.println("====================================");

        boolean running = true;
        while (running) {
            printMenu();
            String input = prompt("Select an option: ");
            try {
                int option = Integer.parseInt(input.trim());
                switch (option) {
                    case 1 -> registerDepartment();
                    case 2 -> registerStudent();
                    case 3 -> registerTeacher();
                    case 4 -> registerAdministrator();
                    case 5 -> registerCourse();
                    case 6 -> registerClassroom();
                    case 7 -> enrollStudent();
                    case 8 -> recordAssessment();
                    case 9 -> recordFeePayment();
                    case 10 -> registerLibraryBook();
                    case 11 -> borrowLibraryBook();
                    case 12 -> returnLibraryBook();
                    case 13 -> viewAllRecords();
                    case 14 -> searchById();
                    case 15 -> updateRecord();
                    case 16 -> showStudentAcademicSummary();
                    case 17 -> showPaymentHistory();
                    case 18 -> showAvailableBooks();
                    case 19 -> demonstratePolymorphism();
                    case 0 -> {
                        System.out.println("Goodbye!");
                        running = false;
                    }
                    default -> System.out.println("Invalid menu option. Please choose a valid number.");
                }

            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid numeric option.");
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Use yyyy-MM-dd.");
            } catch (ValidationException | DuplicateRecordException | RecordNotFoundException | PrerequisiteMissingException | BookUnavailableException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Unexpected error: " + e.getMessage());
            }
        }
    }

    private static void printMenu() {

        System.out.println(" 1. Register Department          11. Borrow Library Book");
        System.out.println(" 2. Register Student             12. Return Library Book");
        System.out.println(" 3. Register Teacher             13. View All Records");
        System.out.println(" 4. Register Administrator       14. Search Record by ID");
        System.out.println(" 5. Register Course              15. Update Existing Record");
        System.out.println(" 6. Register Classroom           16. Student Academic Summary");
        System.out.println(" 7. Enroll Student               17. Student Payment History");
        System.out.println(" 8. Record Assessment            18. View Available Books");
        System.out.println(" 9. Record Fee Payment           19. Demonstrate Polymorphism");
        System.out.println("10. Register Library Book         0. Exit");
        System.out.println("=======================================================");
    }

    private static String prompt(String promptText) {
        System.out.print(promptText);
        return SC.nextLine().trim();
    }

    private static String readNonBlank(String fieldLabel) throws ValidationException {
        String value = prompt(fieldLabel + ": ");
        if (value == null || value.isBlank()) {
            throw new ValidationException(fieldLabel + " cannot be empty.");
        }
        return value;
    }

    private static int readInt(String fieldLabel) {
        while (true) {
            String input = prompt(fieldLabel + ": ");
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid integer.");
            }
        }
    }

    private static LocalDate readDate(String fieldLabel) {
        while (true) {
            String input = prompt(fieldLabel + " (yyyy-MM-dd): ");
            try {
                return LocalDate.parse(input);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date. Use yyyy-MM-dd.");
            }
        }
    }

    private static BigDecimal readBigDecimal(String fieldLabel) {
        while (true) {
            String input = prompt(fieldLabel + ": ");
            try {
                return new BigDecimal(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid amount. Please enter a valid number.");
            }
        }
    }

    private static boolean readYesNo(String message) {
        while (true) {
            String choice = prompt(message + " [Y/N]: ").toLowerCase();
            if (choice.equals("y") || choice.equals("yes")) {
                return true;
            }
            if (choice.equals("n") || choice.equals("no")) {
                return false;
            }
            System.out.println("Please answer Y for yes or N for no.");
        }
    }
    private static void printRecordList(String title, List<?> records) {
        System.out.println("\n--- " + title + " ---");
        if (records == null || records.isEmpty()) {
            System.out.println("No records available.");
            return;
        }

        for (int i = 0; i < records.size(); i++) {
            Object item = records.get(i);
            if (item instanceof Registrable registrable) {
                System.out.println((i + 1) + ". " + registrable.getRegistrationSummary());
            } else if (item instanceof Identifiable identifiable) {
                System.out.println((i + 1) + ". " + identifiable.getDisplayName());
            } else {
                System.out.println((i + 1) + ". " + item);
            }
        }
    }

    private static <T> T selectFromList(String title, List<T> items, String emptyMessage) throws ValidationException {
        if (items == null || items.isEmpty()) {
            System.out.println(emptyMessage);
            return null;
        }
        printRecordList(title, items);
        int choice = readInt("Select an option (0 to cancel)");
        if (choice == 0) {
            return null;
        }
        if (choice < 1 || choice > items.size()) {
            throw new ValidationException("Selection out of range.");
        }
        return items.get(choice - 1);
    }

    private static boolean hasDuplicateId(List<? extends Identifiable> items, String id) {
        for (Identifiable item : items) {
            if (item.getId() != null && item.getId().equalsIgnoreCase(id)) {
                return true;
            }
        }
        return false;
    }

    private static boolean personExists(String personId) {
        for (Student student : STUDENTS) {
            if (student.getId().equalsIgnoreCase(personId)) return true;
        }
        for (Teacher teacher : TEACHERS) {
            if (teacher.getId().equalsIgnoreCase(personId)) return true;
        }
        for (Administrator administrator : ADMINISTRATORS) {
            if (administrator.getId().equalsIgnoreCase(personId)) return true;
        }
        return false;
    }

    private static void registerDepartment() throws ValidationException, DuplicateRecordException {
        String departmentId = readNonBlank("Department ID");
        if (hasDuplicateId(DEPARTMENTS, departmentId)) {
            throw new DuplicateRecordException("Department ID already exists: " + departmentId);
        }
        String departmentName = readNonBlank("Department name");
        String departmentCode = readNonBlank("Department code");
        String officeLocation = readNonBlank("Office location");
        String contactEmail = readNonBlank("Contact email");
        Department department = new Department(departmentId, departmentName, departmentCode, officeLocation, contactEmail, null);
        department.validate();
        DEPARTMENTS.add(department);
        System.out.println("Department registered successfully: " + department.getRegistrationSummary());
    }

    private static void registerStudent() throws ValidationException, DuplicateRecordException, PrerequisiteMissingException {
        if (DEPARTMENTS.isEmpty()) {
            System.out.println("No department exists. Register a department first.");
            if (readYesNo("Register a department now")) {
                registerDepartment();
            } else {
                return;
            }
        }

        Department selectedDepartment = selectFromList("Select department", DEPARTMENTS, "No departments exist.");
        if (selectedDepartment == null) {
            System.out.println("Student registration cancelled.");
            return;
        }

        String personId = readNonBlank("Person ID");
        if (personExists(personId)) {
            throw new DuplicateRecordException("Person ID already exists: " + personId);
        }
        String firstName = readNonBlank("First name");
        String lastName = readNonBlank("Last name");
        String email = readNonBlank("Email");
        String phone = readNonBlank("Phone number");
        LocalDate dob = readDate("Date of birth");
        String memberNumber = readNonBlank("Member number");
        LocalDate joinDate = readDate("Join date");
        String academicStatus = readNonBlank("Academic status");
        String institutionalEmail = readNonBlank("Institutional email");
        String advisorName = readNonBlank("Advisor name");
        String studentNumber = readNonBlank("Student number");
        for (Student existing : STUDENTS) {
            if (existing.getStudentNumber().equalsIgnoreCase(studentNumber)) {
                throw new DuplicateRecordException("Student number already exists: " + studentNumber);
            }
        }
        String programName = readNonBlank("Program name");
        LocalDate admissionDate = readDate("Admission date");
        int studyYear = readInt("Study year");
        String guardianPhone = readNonBlank("Guardian phone");
        boolean scholarshipStatus = readYesNo("Scholarship status");

        Student student = new Student(personId, firstName, lastName, email, phone, dob, memberNumber, selectedDepartment,
                joinDate, academicStatus, institutionalEmail, advisorName, studentNumber, programName, admissionDate,
                studyYear, guardianPhone, scholarshipStatus);
        student.validate();
        STUDENTS.add(student);
        System.out.println("Student registered successfully: " + student.getRegistrationSummary());
    }

    private static void registerTeacher() throws ValidationException, DuplicateRecordException {
        if (DEPARTMENTS.isEmpty()) {
            System.out.println("No department exists. Register a department first.");
            if (readYesNo("Register a department now")) {
                registerDepartment();
            } else {
                return;
            }
        }

        Department selectedDepartment = selectFromList("Select department", DEPARTMENTS, "No departments exist.");
        if (selectedDepartment == null) {
            System.out.println("Teacher registration cancelled.");
            return;
        }

        String personId = readNonBlank("Person ID");
        if (personExists(personId)) {
            throw new DuplicateRecordException("Person ID already exists: " + personId);
        }
        String firstName = readNonBlank("First name");
        String lastName = readNonBlank("Last name");
        String email = readNonBlank("Email");
        String phone = readNonBlank("Phone number");
        LocalDate dob = readDate("Date of birth");
        String memberNumber = readNonBlank("Member number");
        LocalDate joinDate = readDate("Join date");
        String academicStatus = readNonBlank("Academic status");
        String institutionalEmail = readNonBlank("Institutional email");
        String advisorName = readNonBlank("Advisor name");
        String employeeNumber = readNonBlank("Employee number");
        for (Teacher existing : TEACHERS) {
            if (existing.getEmployeeNumber().equalsIgnoreCase(employeeNumber)) {
                throw new DuplicateRecordException("Employee number already exists: " + employeeNumber);
            }
        }
        String specialization = readNonBlank("Specialization");
        LocalDate hireDate = readDate("Hire date");
        BigDecimal salary = readBigDecimal("Salary");
        String officeNumber = readNonBlank("Office number");
        String employmentType = readNonBlank("Employment type");

        Teacher teacher = new Teacher(personId, firstName, lastName, email, phone, dob, memberNumber, selectedDepartment,
                joinDate, academicStatus, institutionalEmail, advisorName, employeeNumber, specialization, hireDate, salary,
                officeNumber, employmentType);
        teacher.validate();
        TEACHERS.add(teacher);
        System.out.println("Teacher registered successfully: " + teacher.getRegistrationSummary());
    }

    private static void registerAdministrator() throws ValidationException, DuplicateRecordException {
        String personId = readNonBlank("Person ID");
        if (personExists(personId)) {
            throw new DuplicateRecordException("Person ID already exists: " + personId);
        }
        String firstName = readNonBlank("First name");
        String lastName = readNonBlank("Last name");
        String email = readNonBlank("Email");
        String phone = readNonBlank("Phone number");
        LocalDate dob = readDate("Date of birth");
        String staffNumber = readNonBlank("Staff number");
        for (Administrator existing : ADMINISTRATORS) {
            if (existing.getStaffNumber().equalsIgnoreCase(staffNumber)) {
                throw new DuplicateRecordException("Staff number already exists: " + staffNumber);
            }
        }
        String jobTitle = readNonBlank("Job title");
        String office = readNonBlank("Assigned office");
        LocalDate employmentDate = readDate("Employment date");
        int accessLevel = readInt("Access level");
        boolean active = readYesNo("Active");

        Administrator administrator = new Administrator(personId, firstName, lastName, email, phone, dob, staffNumber,
                jobTitle, office, employmentDate, accessLevel, active);
        administrator.validate();
        ADMINISTRATORS.add(administrator);
        System.out.println("Administrator registered successfully: " + administrator.getRegistrationSummary());
    }

    private static void registerCourse() throws ValidationException, PrerequisiteMissingException, DuplicateRecordException {
        if (DEPARTMENTS.isEmpty()) {
            throw new PrerequisiteMissingException("No department exists. Register a department before creating a course.");
        }
        if (TEACHERS.isEmpty()) {
            throw new PrerequisiteMissingException("No teacher exists. Register a teacher before creating a course.");
        }

        Department department = selectFromList("Select department", DEPARTMENTS, "No departments exist.");
        if (department == null) {
            return;
        }
        Teacher teacher = selectFromList("Select teacher", TEACHERS, "No teachers exist.");
        if (teacher == null) {
            return;
        }

        String courseId = readNonBlank("Course ID");
        if (hasDuplicateId(COURSES, courseId)) {
            throw new DuplicateRecordException("Course ID already exists: " + courseId);
        }
        String courseCode = readNonBlank("Course code");
        String courseTitle = readNonBlank("Course title");
        int creditHours = readInt("Credit hours");

        Course course = new Course(courseId, courseCode, courseTitle, creditHours, department, teacher);
        course.validate();
        COURSES.add(course);
        System.out.println("Course registered successfully: " + course.getRegistrationSummary());
    }

    private static void registerClassroom() throws ValidationException, DuplicateRecordException {
        String classroomId = readNonBlank("Classroom ID");
        if (hasDuplicateId(CLASSROOMS, classroomId)) {
            throw new DuplicateRecordException("Classroom ID already exists: " + classroomId);
        }
        String buildingName = readNonBlank("Building name");
        String roomNumber = readNonBlank("Room number");
        int capacity = readInt("Capacity");
        int floorNumber = readInt("Floor number");
        boolean available = readYesNo("Available now");

        Classroom classroom = new Classroom(classroomId, buildingName, roomNumber, capacity, floorNumber, available);
        classroom.validate();
        CLASSROOMS.add(classroom);
        System.out.println("Classroom registered successfully: " + classroom.getRegistrationSummary());
    }

    private static void enrollStudent() throws ValidationException, DuplicateRecordException, PrerequisiteMissingException {
        if (STUDENTS.isEmpty()) {
            throw new PrerequisiteMissingException("No student exists. Register a student before enrolling.");
        }
        if (COURSES.isEmpty()) {
            throw new PrerequisiteMissingException("No course exists. Register a course before enrolling a student.");
        }

        Student student = selectFromList("Select student", STUDENTS, "No students exist.");
        if (student == null) {
            return;
        }
        Course course = selectFromList("Select course", COURSES, "No courses exist.");
        if (course == null) {
            return;
        }

        String enrollmentId = readNonBlank("Enrollment ID");
        if (hasDuplicateId(ENROLLMENTS, enrollmentId)) {
            throw new DuplicateRecordException("Enrollment ID already exists: " + enrollmentId);
        }
        LocalDate enrollmentDate = readDate("Enrollment date");
        String semester = readNonBlank("Semester");
        String status = "ACTIVE";

        for (Enrollment enrollment : ENROLLMENTS) {
            if (enrollment.getStudent().getId().equalsIgnoreCase(student.getId())
                    && enrollment.getCourse().getId().equalsIgnoreCase(course.getId())
                    && enrollment.getSemester().equalsIgnoreCase(semester)
                    && enrollment.isActive()) {
                throw new DuplicateRecordException("Active enrollment for same student, course and semester already exists.");
            }
        }

        Enrollment enrollment = new Enrollment(enrollmentId, student, course, enrollmentDate, semester, status);
        enrollment.validate();
        ENROLLMENTS.add(enrollment);
        System.out.println("Enrollment created successfully: " + enrollment.getRegistrationSummary());
    }

    private static void recordAssessment() throws ValidationException, DuplicateRecordException, PrerequisiteMissingException {
        if (ENROLLMENTS.isEmpty()) {
            throw new PrerequisiteMissingException("No enrollment exists. Enroll a student before recording an assessment.");
        }
        Enrollment selectedEnrollment = selectFromList("Select enrollment", ENROLLMENTS, "No enrollments exist.");
        if (selectedEnrollment == null) {
            return;
        }
        if (!selectedEnrollment.isActive()) {
            throw new ValidationException("Only active enrollments can be assessed.");
        }

        String assessmentId = readNonBlank("Assessment ID");
        if (hasDuplicateId(ASSESSMENTS, assessmentId)) {
            throw new DuplicateRecordException("Assessment ID already exists: " + assessmentId);
        }
        String assessmentType = readNonBlank("Assessment type");
        double maximumScore = readInt("Maximum score");
        double obtainedScore = readInt("Obtained score");
        LocalDate assessmentDate = readDate("Assessment date");

        Assessment assessment = new Assessment(assessmentId, selectedEnrollment, assessmentType, maximumScore, obtainedScore,
                assessmentDate);
        assessment.validate();
        ASSESSMENTS.add(assessment);
        System.out.println("Assessment recorded successfully: " + assessment.getRegistrationSummary());
    }

    private static void recordFeePayment() throws ValidationException, DuplicateRecordException, PrerequisiteMissingException {
        if (STUDENTS.isEmpty()) {
            throw new PrerequisiteMissingException("No student exists. Register a student before recording a payment.");
        }

        Student student = selectFromList("Select student", STUDENTS, "No students exist.");
        if (student == null) {
            return;
        }

        String paymentId = readNonBlank("Payment ID");
        if (hasDuplicateId(PAYMENTS, paymentId)) {
            throw new DuplicateRecordException("Payment ID already exists: " + paymentId);
        }
        String receiptNumber = readNonBlank("Receipt number");
        for (FeePayment payment : PAYMENTS) {
            if (payment.getReceiptNumber().equalsIgnoreCase(receiptNumber)) {
                throw new DuplicateRecordException("Receipt number already exists: " + receiptNumber);
            }
        }
        BigDecimal amount = readBigDecimal("Amount");
        LocalDate paymentDate = readDate("Payment date");
        String paymentMethod = readNonBlank("Payment method");

        FeePayment feePayment = new FeePayment(paymentId, student, amount, paymentDate, paymentMethod, receiptNumber);
        feePayment.validate();
        PAYMENTS.add(feePayment);
        System.out.println("Fee payment recorded successfully: " + feePayment.getRegistrationSummary());
    }

    private static void registerLibraryBook() throws ValidationException, DuplicateRecordException {
        String bookId = readNonBlank("Book ID");
        if (hasDuplicateId(BOOKS, bookId)) {
            throw new DuplicateRecordException("Book ID already exists: " + bookId);
        }
        String isbn = readNonBlank("ISBN");
        String title = readNonBlank("Title");
        String author = readNonBlank("Author");
        int totalCopies = readInt("Total copies");
        int availableCopies = readInt("Available copies");

        LibraryBook book = new LibraryBook(bookId, isbn, title, author, totalCopies, availableCopies);
        book.validate();
        BOOKS.add(book);
        System.out.println("Library book registered successfully: " + book.getRegistrationSummary());
    }

    private static void borrowLibraryBook() throws ValidationException, PrerequisiteMissingException, BookUnavailableException, DuplicateRecordException {
        if (STUDENTS.isEmpty()) {
            throw new PrerequisiteMissingException("No student exists. Register a student before borrowing a book.");
        }
        if (BOOKS.isEmpty()) {
            throw new PrerequisiteMissingException("No library book exists. Register a library book first.");
        }

        Student student = selectFromList("Select student", STUDENTS, "No students exist.");
        if (student == null) {
            return;
        }
        List<LibraryBook> availableBooks = new ArrayList<>();
        for (LibraryBook book : BOOKS) {
            if (book.getAvailableCopies() > 0) {
                availableBooks.add(book);
            }
        }
        LibraryBook book = selectFromList("Select available book", availableBooks, "No available book exists.");
        if (book == null) {
            return;
        }
        if (book.getAvailableCopies() <= 0) {
            throw new BookUnavailableException("Selected book is unavailable. No copies remain.");
        }

        String loanId = readNonBlank("Loan ID");
        if (hasDuplicateId(LOANS, loanId)) {
            throw new DuplicateRecordException("Loan ID already exists: " + loanId);
        }
        LocalDate borrowedDate = readDate("Borrowed date");
        LocalDate dueDate = readDate("Due date");
        BookLoan loan = new BookLoan(loanId, student, book, borrowedDate, dueDate, null);
        loan.validate();
        book.setAvailableCopies(book.getAvailableCopies() - 1);
        LOANS.add(loan);
        System.out.println("Book borrowed successfully: " + loan.getRegistrationSummary());
    }

    private static void returnLibraryBook() throws ValidationException, RecordNotFoundException {
        if (LOANS.isEmpty()) {
            throw new RecordNotFoundException("No active loan exists.");
        }
        List<BookLoan> activeLoans = new ArrayList<>();
        for (BookLoan loan : LOANS) {
            if (loan.getReturnedDate() == null) {
                activeLoans.add(loan);
            }
        }
        BookLoan selectedLoan = selectFromList("Select the loan to return", activeLoans,
                "No active loan was found.");
        if (selectedLoan == null) return;

        LocalDate returnedDate = readDate("Return date");
        selectedLoan.setReturnedDate(returnedDate);
        LibraryBook book = selectedLoan.getBook();
        book.setAvailableCopies(book.getAvailableCopies() + 1);
        System.out.println("Book returned successfully. Available copies updated to " + book.getAvailableCopies());
    }

    private static void viewAllRecords() {
        printRecordList("Departments", DEPARTMENTS);
        printRecordList("Students", STUDENTS);
        printRecordList("Teachers", TEACHERS);
        printRecordList("Administrators", ADMINISTRATORS);
        printRecordList("Courses", COURSES);
        printRecordList("Classrooms", CLASSROOMS);
        printRecordList("Enrollments", ENROLLMENTS);
        printRecordList("Assessments", ASSESSMENTS);
        printRecordList("Fee Payments", PAYMENTS);
        printRecordList("Library Books", BOOKS);
        printRecordList("Book Loans", LOANS);
    }

    private static void searchById() throws RecordNotFoundException, ValidationException {
        String id = readNonBlank("Search ID");
        boolean found = false;
        for (Department d : DEPARTMENTS) {
            if (d.getId().equalsIgnoreCase(id)) {
                System.out.println(d.getRegistrationSummary());
                found = true;
            }
        }
        for (Student s : STUDENTS) {
            if (s.getId().equalsIgnoreCase(id) || s.getStudentNumber().equalsIgnoreCase(id)) {
                System.out.println(s.getRegistrationSummary());
                found = true;
            }
        }
        for (Teacher t : TEACHERS) {
            if (t.getId().equalsIgnoreCase(id) || t.getEmployeeNumber().equalsIgnoreCase(id)) {
                System.out.println(t.getRegistrationSummary());
                found = true;
            }
        }
        for (Administrator a : ADMINISTRATORS) {
            if (a.getId().equalsIgnoreCase(id) || a.getStaffNumber().equalsIgnoreCase(id)) {
                System.out.println(a.getRegistrationSummary());
                found = true;
            }
        }
        for (Course c : COURSES) {
            if (c.getId().equalsIgnoreCase(id) || c.getCourseCode().equalsIgnoreCase(id)) {
                System.out.println(c.getRegistrationSummary());
                found = true;
            }
        }
        if (!found) {
            throw new RecordNotFoundException("No record was found for ID: " + id);
        }
    }

    private static void updateRecord() throws ValidationException, RecordNotFoundException {
        System.out.println("\n--- UPDATE EXISTING RECORD ---");
        System.out.println("1. Assign/change department head teacher");
        System.out.println("2. Change classroom availability");
        System.out.println("3. Change enrollment status");
        System.out.println("4. Correct library book available-copy count");
        System.out.println("0. Cancel");

        int option = readInt("Choose what to update");
        switch (option) {
            case 1 -> {
                Department department = selectFromList("Select department", DEPARTMENTS,
                        "No departments are registered.");
                if (department == null) return;
                Teacher teacher = selectFromList("Select new head teacher", TEACHERS,
                        "No teachers are registered.");
                if (teacher == null) return;
                department.setHeadTeacher(teacher);
                department.validate();
                System.out.println("Department updated: " + department.getRegistrationSummary());
            }
            case 2 -> {
                Classroom classroom = selectFromList("Select classroom", CLASSROOMS,
                        "No classrooms are registered.");
                if (classroom == null) return;
                classroom.setAvailable(readYesNo("Is the classroom available"));
                classroom.validate();
                System.out.println("Classroom updated: " + classroom.getRegistrationSummary());
            }
            case 3 -> {
                Enrollment enrollment = selectFromList("Select enrollment", ENROLLMENTS,
                        "No enrollments are registered.");
                if (enrollment == null) return;
                String status = readNonBlank("New enrollment status (ACTIVE, COMPLETED, or CANCELLED)");
                enrollment.setEnrollmentStatus(status);
                enrollment.validate();
                System.out.println("Enrollment updated: " + enrollment.getRegistrationSummary());
            }
            case 4 -> {
                LibraryBook book = selectFromList("Select library book", BOOKS,
                        "No library books are registered.");
                if (book == null) return;
                int copies = readInt("New available-copy count");
                if (copies < 0 || copies > book.getTotalCopies()) {
                    throw new ValidationException("Available copies must be between 0 and total copies ("
                            + book.getTotalCopies() + ").");
                }
                book.setAvailableCopies(copies);
                book.validate();
                System.out.println("Book updated: " + book.getRegistrationSummary());
            }
            case 0 -> System.out.println("Update cancelled.");
            default -> System.out.println("Invalid selection.");
        }
    }

    private static void showStudentAcademicSummary() throws RecordNotFoundException, ValidationException {
        if (STUDENTS.isEmpty()) {
            throw new RecordNotFoundException("No students registered.");
        }
        Student student = selectFromList("Select student", STUDENTS, "No students registered.");
        if (student == null) {
            return;
        }
        System.out.println("Academic summary for " + student.getDisplayName());
        for (Enrollment enrollment : ENROLLMENTS) {
            if (enrollment.getStudent().getId().equalsIgnoreCase(student.getId())) {
                System.out.println("- " + enrollment.getRegistrationSummary());
            }
        }
        for (Assessment assessment : ASSESSMENTS) {
            if (assessment.getEnrollment().getStudent().getId().equalsIgnoreCase(student.getId())) {
                System.out.println("- " + assessment.getRegistrationSummary());
            }
        }
    }

    private static void showPaymentHistory() throws RecordNotFoundException, ValidationException {
        if (STUDENTS.isEmpty()) {
            throw new RecordNotFoundException("No students registered.");
        }
        Student student = selectFromList("Select student", STUDENTS, "No students registered.");
        if (student == null) {
            return;
        }
        boolean found = false;
        for (FeePayment payment : PAYMENTS) {
            if (payment.getStudent().getId().equalsIgnoreCase(student.getId())) {
                System.out.println(payment.getRegistrationSummary());
                found = true;
            }
        }
        if (!found) {
            System.out.println("No payment history found for student: " + student.getDisplayName());
        }
    }

    private static void showAvailableBooks() {
        List<LibraryBook> available = new ArrayList<>();
        for (LibraryBook book : BOOKS) {
            if (book.getAvailableCopies() > 0) {
                available.add(book);
            }
        }
        printRecordList("Available Books", available);
    }

    private static void demonstratePolymorphism() {
        List<Person> people = new ArrayList<>();
        if (!STUDENTS.isEmpty()) {
            people.add(STUDENTS.get(0));
        }
        if (!TEACHERS.isEmpty()) {
            people.add(TEACHERS.get(0));
        }
        if (!ADMINISTRATORS.isEmpty()) {
            people.add(ADMINISTRATORS.get(0));
        }
        if (people.isEmpty()) {
            System.out.println("No person records exist yet. Add at least one student, teacher or administrator.");
            return;
        }
        System.out.println("Polymorphism demo using Person references:");
        for (Person person : people) {
            System.out.println(person.getRoleDescription() + " -> " + person.getDisplayName());
        }

        List<Identifiable> identifiableRecords = new ArrayList<>();
        identifiableRecords.addAll(DEPARTMENTS);
        identifiableRecords.addAll(COURSES);
        identifiableRecords.addAll(CLASSROOMS);
        identifiableRecords.addAll(BOOKS);
        System.out.println("\nInterface polymorphism demo:");
        for (Identifiable item : identifiableRecords) {
            if (item instanceof Registrable registrable) {
                System.out.println(registrable.getRegistrationSummary());
            } else {
                System.out.println(item.getDisplayName());
            }
        }
    }
}
