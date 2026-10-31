// Jake Drusetta ID 5164468 BIT504 Assessment 2 //

import java.util.Scanner;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        System.out.println("Starting BIT504 Assessment 2 Library Management System...");

        // Task 3: Automatically load files using Singleton LibraryManager  //
        LibraryManager manager = LibraryManager.getInstance();
        
        // Verification output to confirm files loaded successfully //
        System.out.println("\n--- Startup Verification ---");
        System.out.println("Successfully loaded " + manager.getBooks().size() + " books.");
        System.out.println("Successfully loaded " + manager.getMembers().size() + " members.");

        // Task 4: Interactive menu loop //
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n============================================#");
            System.out.println("          LIBRARY MANAGEMENT SYSTEM        #");
            System.out.println("============================================#");
            System.out.println("1. Book Management");
            System.out.println("2. Member Management");
            System.out.println("3. Loan Management");
            System.out.println("4. Search");
            System.out.println("5. Exit");
            System.out.print("Select an option (1-5): ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    handleBookManagement(manager, scanner);
                    break;
                case "2":
                    handleMemberManagement(manager, scanner);
                    break;
                case "3":
                    handleLoanManagement(manager, scanner);
                    break;
                case "4":
                    handleSearch(manager, scanner);
                    break;
                case "5":
                    System.out.println("Exiting application. Have a great day!");
                    running = false;
                    break;
                default:
                    System.out.println("Error: Invalid option selected. Please choose between 1 and 5,");
            }
        }
        scanner.close();
    } 

    // --- Task 4 sub-menu handlers --- //

    private static void handleBookManagement(LibraryManager manager, Scanner scanner) {
        boolean subRunning = true;
        while (subRunning) {
            System.out.println("\n--- Book Management ---");
            System.out.println("a. Display all books");
            System.out.println("b. Display borrowed books");
            System.out.println("c. Display unborrowed books");
            System.out.println("d. Add a new book");
            System.out.println("e. Return to main menu");
            System.out.print("Select an option (a-e): ");

            String choice = scanner.nextLine().trim().toLowerCase();
            switch (choice) {
                case "a":
                    System.out.println("\n==============================================================================================================================================");
                    System.out.printf("%-5s | %-18s | %-25s | %-18s | %-12s | %-18s | %-10s | %-10s%n",
                                      "ID", "ISBN", "Title", "Author", "Published", "Genre", "Age Rating", "Borrowed");
                    System.out.println("==============================================================================================================================================");
                    
                    if (manager.getBooks().isEmpty()) {
                        System.out.println("No books found in library system.");                        
                    } else {
                        for (Book book : manager.getBooks()) {
                            System.out.printf("%-5s | %-18s | %-25s | %-18s | %-12s | %-18s | %-10s | %-10s%n",
                                book.getBookId(),
                                book.getIsbn(),
                                book.getTitle(),
                                book.getAuthor(),
                                book.getPublicationDate(),
                                book.getGenre(),
                                book.getAgeRating(),
                                (book.isBorrowed() ? "Yes" : "No"));
                        }
                    }
                    System.out.println("==============================================================================================================================================");
                    break;
                case "b":
                    System.out.println("\n==============================================================================================================================================");
                    System.out.printf("%-5s | %-18s | %-25s | %-18s | %-12s | %-18s | %-10s | %-10s%n",
                                      "ID", "ISBN", "Title", "Author", "Published", "Genre", "Age Rating", "Borrowed");
                    System.out.println("==============================================================================================================================================");
                    
                    boolean foundBorrowed = false;
                    for (Book book : manager.getBooks()) {
                        if (book.isBorrowed()) {
                            System.out.printf("%-5s | %-18s | %-25s | %-18s | %-12s | %-18s | %-10s | %-10s%n",
                                    book.getBookId(),
                                    book.getIsbn(),
                                    book.getTitle(),
                                    book.getAuthor(),
                                    book.getPublicationDate(),
                                    book.getGenre(),
                                    book.getAgeRating(),
                                    "Yes");
                            foundBorrowed = true;    
                        }
                    }

                    if (!foundBorrowed) {
                        System.out.println("No books are currently borrowed.");
                    }
                    System.out.println("=============================================================================================================================================");
                    break;
                case "c":
                                        System.out.println("\n==============================================================================================================================================");
                    System.out.printf("%-5s | %-18s | %-25s | %-18s | %-12s | %-18s | %-10s | %-10s%n",
                                      "ID", "ISBN", "Title", "Author", "Published", "Genre", "Age Rating", "Borrowed");
                    System.out.println("==============================================================================================================================================");
                    
                    boolean foundUnborrowed = false;
                    for (Book book : manager.getBooks()) {
                        if (!book.isBorrowed()) {
                            System.out.printf("%-5s | %-18s | %-25s | %-18s | %-12s | %-18s | %-10s | %-10s%n",
                                    book.getBookId(),
                                    book.getIsbn(),
                                    book.getTitle(),
                                    book.getAuthor(),
                                    book.getPublicationDate(),
                                    book.getGenre(),
                                    book.getAgeRating(),
                                    "no");
                            foundUnborrowed = true;    
                        }
                    }

                    if (!foundUnborrowed) {
                        System.out.println("No unborrowed books.");
                    }
                    System.out.println("=============================================================================================================================================");
                    break;
                case "d":
                    System.out.println("\n--- Add a new book ---");

                    // 1. Get & validate ID //
                    String newId = "";
                    System.out.println("(Tip: Type 'r' and press Enter at any time to cancel and return to menu)");
                    while (true) {
                        System.out.print("Enter Book ID (numbers only): ");
                        newId = scanner.nextLine().trim();
                        if (newId.equalsIgnoreCase("r")) {
                            System.out.println("\nOperation cancelled. Returning to main menu...");
                            return;
                        }
                        if (newId.isEmpty()) {
                            System.out.println("Error: Book ID cannot be blank. Please try again.");
                        } else if (!newId.matches("\\d+")) {
                            System.out.println("error:Book ID must contain numbers only.");    
                        }else if (manager.bookIdExists(newId)) {
                            System.out.println("Error: A book with ID '" + newId + "' already exists. ID must be unique.");
                        } else {
                            break;
                        }    
                    }

                    // 2. Get & Validate ISBN
                    String newIsbn = "";
                    while (true) {
                        System.out.print("Enter ISBN (Format: 999-9-999-99999-99): ");
                        newIsbn = scanner.nextLine().trim();
                        if (newIsbn.equalsIgnoreCase("r")) {
                            System.out.println("\nOperation cancelled. Returning to main menu...");
                            return;
                        }
                        if (newIsbn.isEmpty()) {
                            System.out.println("Error: ISBN cannot be blank.");
                        } else if (!newIsbn.matches("\\d{3}-\\d-\\d{3}-\\d{5}-\\d{2}")) {
                            System.out.println("Error: Invalid ISBN format. Must have 14 characters hyphenated e.g. 978-1-234-56789-21.");    
                        } else if (manager.isbnExists(newIsbn)) {
                            System.out.println("Error: a book with ISBN '" + newIsbn + "' already exists. ISBN must be unique.");
                        } else {
                            break;
                        }
                    }

                    // 3. Get & Validate Title //
                    String newTitle = "";
                    while (true) {
                        System.out.print("Enter Book Title: ");
                        newTitle = scanner.nextLine().trim();
                        if (newTitle.equalsIgnoreCase("r")) {
                            System.out.println("\nOperation cancelled. Returning to main menu...");
                            return;
                        }
                        if (newTitle.isEmpty()) {
                            System.out.println("Error: Title cannot be blank.");
                        } else {
                            break;
                        }
                    }

                    // 4. Get & validate author //
                    String newAuthor = "";
                    while (true) {
                        System.out.print("Enter Author Name: ");
                        newAuthor = scanner.nextLine().trim();
                        if (newAuthor.equalsIgnoreCase("r")) {
                            return;
                        }
                        if (newAuthor.isEmpty()) {
                            System.out.println("Error: Author cannot be blank.");
                        } else if (!newAuthor.matches("[a-zA-Z\\s]+")) {
                            System.out.println("Error: Author name should contain letters only.");
                        } else {
                            break;
                        }
                    }

                    // 5. Get & validate publication date //
                    String newDate = "";
                    while (true) {
                        System.out.print("Enter Publication Date (YYYY-MM-DD): ");
                        newDate = scanner.nextLine().trim();
                        if (newDate.equalsIgnoreCase("r")) {
                            return;
                        }
                        if (newDate.isEmpty()) {
                            System.out.println("Error: Publication date cannot be blank.");
                        } else if (!newDate.matches("\\d{4}-\\d{2}-\\d{2}")) {
                            System.out.println("Error: Invalid format. Please use YYYY-MM-DD e.g. 2026-06-06.");    
                        } else {
                            break;
                        }
                    }

                    // 6. Get & validate pre-defined genre //
                    String[] genres = {"Fantasy", "Science Fiction", "Adventure", "Mystery", "History", "Historical Fiction", "Thriller", "Fairy Tale", "Steampunk", "Horror", "Romance", "Hard Sci-Fi"}; 
                    String newGenre = "";
                    while (true) {
                        System.out.println("\nSelect Genre:");
                        for (int i = 0; i < genres.length; i++) {
                            System.out.println((i + 1) + ". " + genres[i]);
                        }
                        System.out.print("Choose option (1-" + genres.length + "): ");
                        String genreChoiceInput = scanner.nextLine().trim();
                        if (genreChoiceInput.equalsIgnoreCase("r")) {
                            System.out.println("\nOperation cancelled. Returning to main menu...");
                            return;
                        }
                        try {
                            int genreChoice = Integer.parseInt(genreChoiceInput);
                            if (genreChoice >= 1 && genreChoice <= genres.length) {
                                newGenre = genres[genreChoice - 1];
                                break;
                            } else {
                                System.out.println("Error: Invalid selection. Choose a number between 1 and " + genres.length + ".");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Error: Please enter a valid number from the list.");
                        }
                    }
                    // 7. Get & validate age rating //
                    int newAgeRating = 0;
                    while (true) {
                        System.out.println("Enter Age rating (e.g., 0for all ages, 12, 16, 18):");
                        String newAgeRatingInput = scanner.nextLine().trim();
                        if (newAgeRatingInput.equalsIgnoreCase("r")) {
                            System.out.println("\nOperation cancelled. Returning to main menu...");
                            return;
                        }
                        if (newAgeRatingInput.isEmpty()) {
                            System.out.println("Error: Age rating cannot be blank. Please try again.");
                        } else {
                        try {
                            newAgeRating = Integer.parseInt(newAgeRatingInput);
                            if (newAgeRating < 0 || newAgeRating > 18) {
                                System.out.println("Error: Please enter a realistic age rating between 0 and 18.");
                                continue;
                            }
                            break;
                        } catch (NumberFormatException e) {
                            System.out.println("Error: Invalid input. Please enter numbers only for the age rating.");
                        }
                    }
                }
                // create & add the new book object to manager memory & write to file for persistence //
                Book newBook = new Book(newId, newIsbn, newTitle, newAuthor, newDate, newGenre, newAgeRating);
                manager.addBook(newBook);
                manager.saveBooksToFile();

                System.out.println("\nSuccess: '" + newTitle + "' has been added to the library archive system!");
                    break;
            case "e":
                subRunning = false;
                    break;
            default:
                System.out.println("Error: Invalid option. please select a, b, c, d, or e.");            
                }
            }
        }

    private static void handleMemberManagement(LibraryManager manager, Scanner scanner) {
        boolean subRunning = true;
        while (subRunning) {
            System.out.println("\n--- Memeber Management ---");
            System.out.println("a. Display all members");
            System.out.println("b. add a new member");
            System.out.println("c. Return to main menu");
            System.out.println("Select an option (a-c): ");

            String choice = scanner.nextLine().trim().toLowerCase();
            switch (choice) {
                case "a":
                    System.out.println("\n==============================================================");
                    System.out.printf("%-10s | %-18s | %-18s |%-5s%n", "ID", "First Name", "Last Name", "Age");
                    System.out.println("==============================================================");

                    for (Members member : manager.getMembers()) {
                        System.out.printf("%10s | %-18s | %-18s | %-5d%n",
                            member.getMemberId(),
                            member.getFirstName(),
                            member.getLastName(),
                            member.getAge());
                    }
                    System.out.println("==============================================================");
                    break;
                case "b":
                    String newmemberId = "";
                    System.out.println("(Tip: Type 'r' and press Enter at any time to cancel and return to menu)");
                    while (true) {
                        System.out.print("Enter new member ID (numbers only): ");
                        newmemberId = scanner.nextLine().trim();
                        if (newmemberId.equalsIgnoreCase("r")){
                            System.out.println("\nOperation cancelled. Returning to main menu...");
                            return;
                        }
                        if (newmemberId.isEmpty()) {
                            System.out.println("Error: New member ID must be created. Please try again.");
                        } else if (!newmemberId.matches("\\d+")) {
                            System.out.println("Error: Invalid ID Format. Must contain numbers only.");
                        } else if (manager.memberIdExists(newmemberId)) {
                            System.out.println("Error: Another member with ID '" + newmemberId + "' already exists. ID must be unique.");
                        } else {
                            break;
                        }
                    }

                    String newFirstName = "";
                    while (true) {
                        System.out.print("Enter first name (letters only): ");
                        newFirstName = scanner.nextLine().trim();   
                        if (newFirstName.equalsIgnoreCase("r")) {
                            System.out.println("\nOperation cancelled. Returning to main menu...");
                            return;
                        }
                        if (newFirstName.isEmpty()) {
                            System.out.println("Error: Must enter first name");
                        } else if (!newFirstName.matches("[a-zA-Z\\s]+")) {
                            System.out.println("Error: First name must contain letters only. ");
                        } else {
                            break;
                        }
                    }

                    String newLastName = "";
                    while (true) {
                        System.out.print("Enter last name (letters only): ");
                        newLastName = scanner.nextLine().trim();
                        if (newLastName.equalsIgnoreCase("r")) {
                            System.out.println("\nOperation cancelled. Returning to main menu... ");
                            return;
                        }
                        if (newLastName.isEmpty()) {
                            System.out.println("Error: Must enter last name");
                        } else if (!newLastName.matches("[a-zA-Z\\s]+")) {
                            System.out.println("Error: Last name must contain letters only. ");
                        } else {
                            break;
                        }
                    }
                    
                    int newAge = 0;
                    while (true) {
                        System.out.print("Enter age (numbers only): ");
                        String newAgeInput = scanner.nextLine().trim();
                        if (newAgeInput.equalsIgnoreCase("r")) {
                            System.out.println("\nOperation cancelled. Returning to main menu... ");
                            return;
                        }
                        if (newAgeInput.isEmpty()) {
                            System.out.println("Error: Age cannot be blank. ");
                        } else {
                            try {
                                newAge = Integer.parseInt(newAgeInput);
                                if (newAge <=0 || newAge > 120) {
                                    System.out.println("Error: Please enter a valid realistic age between 1 and 120.");
                                } else {
                                    break;
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("Error: Invalid number format. Age must be a whole number. ");
                            }
                        }
                    }

                    Members newMember = new Members(newmemberId, newFirstName, newLastName, newAge);
                    manager.addMember(newMember);
                    manager.appendMemberToFile(newMember);
                    break;

                case "c":
                    subRunning = false;
                    break;
                default:
                    System.out.println("Error: Invalid option. Please select a, b, orc,");

            }
        }
    }

    private static void handleLoanManagement(LibraryManager manager, Scanner scanner) {
        boolean subRunning = true;
        while (subRunning) {
            System.out.println("\n --- Loan Management ---");
            System.out.println("a. Check out a book");
            System.out.println("b. Check in a book");
            System.out.println("c. Return to main menu");
            System.out.println("Select an option (a-c): ");

            String choice = scanner.nextLine().trim().toLowerCase();
            switch (choice) {
                case "a":
                    System.out.println("\n[ --- Check out a book --- ]");
                    System.out.println("(Tip: Type 'r' and press Enter at any time to cancel and return to main menu)");
                    String checkoutBookId = "";
                    while (true) {
                        System.out.print("Enter Book ID to check out: (must be numerical) ");
                        checkoutBookId = scanner.nextLine().trim();
                        if (checkoutBookId.equalsIgnoreCase("r")) {
                            System.out.println("\nOperation cancelled. Returning to main menu...");
                            return;
                        }

                        if (checkoutBookId.isEmpty()) {
                            System.out.println("Error: Book ID cannot be blank.");
                        } else if (!manager.bookIdExists(checkoutBookId)) {
                            System.out.println("Error : Book '" + checkoutBookId + "' does not exist in the system.");
                        } else {
                            break;
                        }
                    }

                    // retrieve object //
                    Book targetBook = manager.getBookById(checkoutBookId);

                    // check if already borrowed //
                    if (targetBook.isBorrowed()) {
                        System.out.println("Error: This book is already checked out! It must be checked in before it can be borrow again");
                        break;
                    }

                    // validate id //
                    String checkoutMemberId = "";
                    while (true) {
                        System.out.println("Enter the member Id of person Borrowing: ");
                        checkoutMemberId = scanner.nextLine().trim();

                        if (checkoutMemberId.equalsIgnoreCase("r")) {
                            System.out.println("\nOperation cancelled. Returning to main menu...");
                            return;
                        }

                        if (checkoutMemberId.isEmpty()) {
                            System.out.println("Error: Member ID cannot be blank.");
                        } else if (!manager.memberIdExists(checkoutMemberId)) {
                            System.out.println("Error: Member ID '" + checkoutMemberId + "' does not exist.");
                        } else {
                            break;
                        }
                    }

                    Members targetMember = manager.getMemberById(checkoutMemberId);

                    if (targetMember.getAge() < targetBook.getAgeRating()) {
                        System.out.println("Error: Member (Age " + targetMember.getAge() + " is too young for this books age rating (" + targetBook.getAgeRating() + "). Checkout denied.");
                        break;
                    }

                    // confirmation prompt //
                    System.out.println("\n--- Checkout Summary ---");
                    System.out.println("Book: " + targetBook.getTitle());
                    System.out.println("Borrower: " + targetMember.getFirstName() + " " + targetMember.getLastName() + " (Age: " + targetMember.getAge() + ")");
                    System.out.print("Do you wish to confirm this checkout? (y/n): ");
                    String confirmation = scanner.nextLine().trim();

                    if (confirmation.equalsIgnoreCase("y")) {
                        targetBook.setBorrowedByMemberId(checkoutMemberId);
                        manager.saveBooksToFile();
                        System.out.println("\nSuccess: '" + targetBook.getTitle() + "' has been successfully checked out to " + targetMember.getFirstName() + "!");
                    } else {
                        System.out.println("\nCheckout cancelled.");
                    }
                    break;

                case "b":
                    System.out.println("\n[ --- Check in a book --- ]");
                    System.out.println("(Tip: Type 'r' and press Enter at any time to cancel and return to menu)");
                    String checkinBookId = "";
                    while(true) {
                        System.out.print("Enter Book ID to check in and (return): ");
                        checkinBookId = scanner.nextLine().trim();

                        if (checkinBookId.equalsIgnoreCase("r")) {
                            System.out.println("\nOperation cancelled. Returning to main menu...");
                            return;
                        }

                        if (checkinBookId.isEmpty()) {
                            System.out.println("Error: Book Id cannot be blank. ");
                        } else if (!manager.bookIdExists(checkinBookId)) {
                            System.out.println("Error: Book ID '" + checkinBookId + "' does not exist in the system.");
                        } else {
                            break;
                        }
                    }
                    
                    Book returnBook = manager.getBookById(checkinBookId);

                    // verify its checked out //
                    if (!returnBook.isBorrowed()) {
                        System.out.println("Error: this book is not currently checked out. It is already available on the shelf.");
                        break;
                    }

                    // perform check in //
                    returnBook.setBorrowedByMemberId(""); // clear borrower id //
                    manager.saveBooksToFile(); // update persistence //

                    System.out.println("\nSuccess: '" + returnBook.getTitle() + "' has been successfully checked in and is now available to borrow!");
                    break;
                    
                case "c":
                    subRunning = false;
                    break;
                default:
                    System.out.println("Error: invalid option. please select a, b, or c.");
            }
        }    
    }

    private static void handleSearch(LibraryManager manager, Scanner scanner) {
        boolean subRunning = true;
        while(subRunning) {
            System.out.println("\n--- Search ---");
            System.out.println("a. Find a member");
            System.out.println("b. Find a book");
            System.out.println("c. Return to main menu");
            System.out.println("Select an option (a-c): ");

            String choice = scanner.nextLine().trim().toLowerCase();
            switch (choice) {
                case "a":
                    System.out.println("\n[ --- Find a member by last name --- ]");
                    System.out.println("(Tip: Type 'r' and press Enter to cancel and return to the main menu)" );
                    System.out.print("Enter members last name (or partial name): ");
                    String lastNameQuery = scanner.nextLine().trim();

                    if (lastNameQuery.equalsIgnoreCase("r")) {
                        System.out.println("\nOperation cancelled. Returning to main menu!");
                        break;
                    }
                    if (lastNameQuery.isEmpty()) {
                        System.out.println("Error: Search query cannot be blank.");
                        break;
                    } else if (!lastNameQuery.matches("[a-zA-Z\\s]+")) {
                        System.out.println("Error: Last name should contain letters only.");
                        break;
                    }

                    List<Members> foundMembers = manager.searchMembersByLastName(lastNameQuery);
                    if (foundMembers.isEmpty()) {
                        System.out.println("Warning: No members found matching with last name '" + lastNameQuery + "'.");
                    } else {
                        System.out.println("\n --- Search Results: Members (" + foundMembers.size() + " found) --- ");
                        for (Members m : foundMembers) {
                            System.out.println("---------------------------------------------------");
                            System.out.println("ID:           " + m.getMemberId());
                            System.out.println("First Name:   " + m.getFirstName());
                            System.out.println("Last Name:    " + m.getLastName());
                            System.out.println("Age:          " + m.getAge());

                            // find books borrowed by this member //
                            List<Book> borrowedBooks = manager.getBooksByMemberId(m.getMemberId());
                            if (borrowedBooks.isEmpty()) {
                                System.out.println("Borrowed Books: None");
                            } else {
                                System.out.println("Borrowed Books: ");
                                for (Book b : borrowedBooks) {
                                    System.out.println(" -[ID: " + b.getBookId() + "] " + b.getTitle() + " (Author: " + b.getAuthor() + ")");
                                }
                            }
                        }
                        System.out.println("-----------------------------------------------------");
                    }    
                    break;
                
                case "b":
                    System.out.println("\n[ --- Find a book by title --- ]");
                    System.out.println("(Tip: Type 'r' and press Enter to cancel and return to main menu");
                    System.out.print("Enter book title (or partial): ");
                    String titleQuery = scanner.nextLine().trim();

                    if (titleQuery.equalsIgnoreCase("r")) {
                        System.out.println("\nOperation cancelled. Returning to search menu... ");
                        break;
                    }
                    if (titleQuery.isEmpty()) {
                        System.out.println("Error: Search query cannot be blank.");
                        break;
                    }

                    List<Book> foundBooks = manager.searchBooksByTitle(titleQuery);
                    if (foundBooks.isEmpty()) {
                        System.out.println("Warning: No books with title matching '" + titleQuery + "'.");
                    } else {
                        System.out.println("\n --- Search results: Books (" + foundBooks.size() + " found) --- ");
                        for (Book b : foundBooks) {
                            System.out.println("---------------------------------------------------");
                            System.out.println("ID:            " + b.getBookId());
                            System.out.println("ISBN:          " + b.getIsbn());
                            System.out.println("Title:         " + b.getTitle());
                            System.out.println("Author:        " + b.getAuthor());
                            System.out.println("Publish date:  " + b.getPublicationDate());
                            System.out.println("Genre:         " + b.getGenre());
                            System.out.println("Age rating:    " + b.getAgeRating());

                            if (b.isBorrowed()) {
                                Members borrower = manager.getMemberById(b.getBorrowedByMemberId());
                                String borrowerName = (borrower != null) ? borrower.getFullName() : "Unknown Member (ID: " + b.getBorrowedByMemberId() + ")";
                                System.out.println("Borrowed by: " + borrowerName);
                            } else {
                                System.out.println("Borrowed by: Available");
                            }
                        }
                        System.out.println("--------------------------------------------------");
                    }
                    break;

                case "c":
                    subRunning = false;
                    break;
                default:
                    System.out.println("Error: Invalid option. Please select a, b, or c.");
            }
        }
    }
}