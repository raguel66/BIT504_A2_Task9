// Jake Drusetta ID 5164468 BIT504 Assessment 2 //

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Singleton manager class responsible fopr loading, storing and managing
 * library books and members data in accordance with the singleton pattern.
 */
public class LibraryManager {
    // Single instance for the singleton pattern //
    private static LibraryManager instance;

    // Global constants for file paths //
    public static final String BOOKS_FILE = "Book.txt";
    public static final String MEMBERS_FILE = "Members.txt";

    // Collections to store data objects //
    private List<Book> books;
    private List<Members> members;

    /**
     * Private constructor to prevent direct instantiation (singleton pattern)
     * Automatically loads data files upon initialization.
     */
    private LibraryManager() {
        books = new ArrayList<>();
        members = new ArrayList<>();
        loadBooks(BOOKS_FILE);
        loadMembers(MEMBERS_FILE);
    }

    /**
     * Public accessor method to get the single instance of LibraryManager.
     * 
     * @return The singleton library manager instance.
     */
    public static synchronized LibraryManager getInstance() {
        if (instance == null) {
            instance = new LibraryManager();
        }
        return instance;
    }
    
    /**
     * Reads books from the specified file path and populates the book list.
     * Includes error handling to prevent application crashes.
     * 
     * @param filePath path to books text file
     */
    public void loadBooks(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length < 7) {
                    continue; // Skip malformed lines //
                }

                String id = values[0].trim();
                String isbn = values[1].trim();
                String title = values[2].trim();
                String author = values[3].trim();
                String pubDate = values[4].trim();
                String genre = values[5].trim();
                int ageRating = Integer.parseInt(values[6].trim());
                // check for 8th value //
                String borrowedBy = (values.length>= 8) ? values[7].trim() : "";

                Book book = new Book(id, isbn, title, author, pubDate, genre, ageRating);
                book.setBorrowedByMemberId(borrowedBy);
                books.add(book);
            }
        } catch (IOException e) {
            System.out.println("Error: Failed to read books data file (" + filePath + "). Please ensure 'Book.txt' is in the project ROOT directory.");
            e.printStackTrace();    
        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid numeric format found in books data file.");
        }
    }

    /**
     * Reads member from the specified file path and populates the member list.
     * Includes error handling to prevent application crashes[cite: 6].
     * 
     * @param filePath Path to the members text file
     */
    public void loadMembers(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length < 4) {
                    continue; // Skip malformed lines //
                }

                String id = values[0].trim();
                String firstName = values[1].trim();
                String lastName = values[2].trim();
                int age = Integer.parseInt(values[3].trim());
            
                Members member = new Members(id, firstName, lastName, age);
                members.add(member);
            }
        } catch (IOException e) {
            System.out.println("Error: Failed to read the members data file (" + filePath + "). Please ensure 'Members.txt' is in the project root directory.");
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid numeric format found in members data file.");
        }
    }

    /**
     * Overwrites Book.txt with the current list of books and their loan states // 
     */
    public void saveBooksToFile() {
        try (PrintWriter pw = new PrintWriter(new FileWriter("Book.txt", false))) {
            for (Book book : books) {
                String lineData = String.format("%s,%s,%s,%s,%s,%s,%d,%s%n",
                    book.getBookId(),
                    book.getIsbn(),
                    book.getTitle(),
                    book.getAuthor(),
                    book.getPublicationDate(),
                    book.getGenre(),
                    book.getAgeRating(),
                    book.getBorrowedByMemberId() != null ? book.getBorrowedByMemberId() : ""
                );
                pw.print(lineData);
            }
        } catch (IOException e) {
            System.out.println("Error: Could not save books to file - " + e.getMessage());
        }
    }    
    /**
     * Adds a new member to the array list.
     */
    public void addMember(Members member) {
        members.add(member);
        System.out.println("\nSuccess: Member added to the system successfully!");
    }

    /**
     * Appends a new member record to members.txt for persistence.
     */
    public void appendMemberToFile(Members member) {
        String lineData = String.format("%n%s,%s,%s,%d",
            member.getMemberId(),
            member.getFirstName(),
            member.getLastName(),
            member.getAge());

        try(FileWriter fw = new FileWriter("Members.txt", true);
            PrintWriter pw = new PrintWriter(fw)) {
           pw.print(lineData);                
        } catch (IOException e) {
            System.out.println("Error: Could not save member to file - " + e.getMessage());
        }
    }

    /**
     * Checks if member id already exists.
     */
    public boolean memberIdExists(String memberId) {
        for (Members m : members) {
            if (m.getMemberId().equals(memberId)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if a book ID already exists.
     */
    public boolean bookIdExists(String bookId) {
        for (Book b : books) {
            if (b.getBookId().equalsIgnoreCase(bookId)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if a book ISBN already exists.
     */
    public boolean isbnExists(String isbn) {
        for (Book b : books) {
            if (b.getIsbn().equalsIgnoreCase(isbn)) {
                return true;
            }
        }
        return false;
    }

    public boolean checkoutBook(String bookId, String memberId) {
        Book book = getBookById(bookId);
        Members member = getMemberById(memberId);

        // Safety check: Ensure both exist and book isnt already borrowed //
        if (book == null || member == null || book.isBorrowed() || member.getAge() < book.getAgeRating()) {
            return false;
        }

        // set state save to file //
        book.setBorrowedByMemberId(memberId);
        saveBooksToFile();
        return true;

    }

    public boolean checkinBook(String bookId) {
        Book book = getBookById(bookId);

        // Safety check: Ensure book exists and is actually borrowed //
        if (book == null || !book.isBorrowed()) {
            return false;
        }

        book.setBorrowedByMemberId("");
        saveBooksToFile();
        return true;
    }

    public boolean updateBookLoanStatus(String bookId, String borrowerMemberId) {
        Book targetBook = getBookById(bookId);

        if (targetBook == null) {
            return false;
        }

        targetBook.setBorrowedByMemberId(borrowerMemberId != null ? borrowerMemberId : "");
        saveBooksToFile();
        return true;
    }

    public Book getBookById(String bookId) {
        for (Book b : books) {
            if (b.getBookId().equalsIgnoreCase(bookId)) {
                return b;
            }
        }
        return null;
    }

    public Members getMemberById(String memberId) {
        for (Members m : members) {
            if (m.getMemberId().equals(memberId)) {
                return m;
            }
        }
        return null;
    }

    /**
     * Searches for member whose last name contains a given query string (case in-sensitive/partial match)
    
     */
    public List<Members> searchMembersByLastName(String query) {
        List<Members> results = new ArrayList<>();
        String lowerQuery = query.toLowerCase().trim();
        for (Members m : members) {
            if (m.getLastName().trim().toLowerCase().contains(lowerQuery)) {
                results.add(m);
            }
        }
        return results;
    }

    /**
     * Searches for books whose title contains the given query string //
     */
    public List<Book> searchBooksByTitle(String query) {
        List<Book> results = new ArrayList<>();
        String lowerQuery = query.toLowerCase();
        for (Book b : books) {
            if (b.getTitle().toLowerCase().contains(lowerQuery)) {
                results.add(b);
            }
        }
        return results;
    }

    /**
     * Returns a list of books cuerrently borrowed by a specific member ID.
     */
    public List<Book> getBooksByMemberId(String memberId) {
        List<Book> results = new ArrayList<>();
        for (Book b : books) {
            if (b.isBorrowed() && b.getBorrowedByMemberId().equals(memberId)) {
                results.add(b);
            }
        }
        return results;
    }

    /**
     * Adds a new book to the library system.
     */
    public void addBook(Book book) {
        books.add(book);
    }


    // --- Data access getters --- //

    public List<Book> getBooks() {
        return books;
    }

    public List<Members> getMembers() {
        return members;
    }
}