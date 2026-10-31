// Jake Drusetta ID 5164468 BIT504 Assessment 2 //

/**
 * Represents a library member/borrower
 */
public class Members {
    private String memberId;
    private String firstName;
    private String lastName;
    private int age;
    /**
     * Constructs new member object.
     * 
     * @param memberId Unique identifier for the member
     * @param firstName Members first name
     * @param lastName Members last name
     * @param age Members age in yrs
     */
    public Members(String memberId, String firstName, String lastName, int age) {
        this.memberId = memberId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    // --- getters & setters --- //

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    // --- helper methods --- //

    /**
     * Return the full name of the member.
     * 
     * @return First name and last name
     */
    public String getFullName() {
        return firstName + " " + lastName;
    }
}