// Jake Drusetta ID 5164468 BIT504 Assessment 2 //

/**
 * Represents a library book in the library management system.
 */
public class Book {
    private String bookId;
    private String isbn;
    private String title;
    private String author;
    private String publicationDate;
    private String genre;
    private int ageRating;

    // Loan tracking field: holds members id if borrowed or null/empty if available //
    private String borrowedByMemberId;

    /**
     * Constructs a new book object.
     * 
     * @param bookId unique identifier for the book
     * @param isbn international standard book number (10 characters)
     * @param title book title
     * @param author book author
     * @param publicationDate date the book was published
     * @param genre genre/catagory
     * @param ageRating minimum age required to borrow the book
     */
    public Book(String bookId, String isbn, String title, String author, String publicationDate, String genre, int ageRating) {
        this.bookId = bookId;
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.publicationDate = publicationDate;
        this.genre = genre;
        this.ageRating = ageRating;
        this.borrowedByMemberId = null; // Initially unborrowed //    
    }

    // Getters & setters //
    public String getBookId() {
        return bookId;    
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(String publicationDate) {
        this.publicationDate = publicationDate;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
    
    public int getAgeRating() {
        return ageRating;
    }

    public void setAgeRating(int ageRating) {
        this.ageRating = ageRating;
    }

    public String getBorrowedByMemberId() {
        return borrowedByMemberId;
    }

    public void setBorrowedByMemberId(String borrowedByMemberId) {
        this.borrowedByMemberId = borrowedByMemberId;
    }

    // --- Helper methos --- //

    /**
     * Checks if the book is currently borrowed.
     * 
     * @return true if borrowed, false if available
     */
    public boolean isBorrowed() {
        return borrowedByMemberId != null && !borrowedByMemberId.isEmpty();
    }
}