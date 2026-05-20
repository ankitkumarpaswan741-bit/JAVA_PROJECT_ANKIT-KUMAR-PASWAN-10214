package src;

/**
 * Voter class represents a registered voter in the Online Voting System.
 *
 * <p>Demonstrates OOP Concepts:</p>
 * <ul>
 *   <li>Encapsulation – all fields are private, accessed via getters/setters</li>
 *   <li>Data Validation – age and credential checks before operations</li>
 * </ul>
 *
 * @author Ankit Kumar Paswan
 * @version 1.0
 */
public class Voter {

    // ─── Private Fields (Encapsulation) ─────────────────────────────────────
    private String voterId;
    private String name;
    private int    age;
    private String email;
    private String password;   // used for authentication
    private boolean hasVoted;

    // ─── Constructor ─────────────────────────────────────────────────────────
    /**
     * Creates a new Voter with the given details.
     *
     * @param voterId  unique voter ID
     * @param name     full name of the voter
     * @param age      age of the voter
     * @param email    email address
     * @param password login password
     */
    public Voter(String voterId, String name, int age, String email, String password) {
        this.voterId   = voterId;
        this.name      = name;
        this.age       = age;
        this.email     = email;
        this.password  = password;
        this.hasVoted  = false;
    }

    // ─── Getters ─────────────────────────────────────────────────────────────
    /** @return unique voter ID */
    public String getVoterId()  { return voterId; }

    /** @return voter's full name */
    public String getName()     { return name; }

    /** @return voter's age */
    public int getAge()         { return age; }

    /** @return voter's email */
    public String getEmail()    { return email; }

    /** @return true if voter has already voted */
    public boolean hasVoted()   { return hasVoted; }

    // ─── Setters (for modification feature) ──────────────────────────────────
    /** @param name updated name */
    public void setName(String name)       { this.name = name; }

    /** @param age updated age */
    public void setAge(int age)            { this.age = age; }

    /** @param email updated email */
    public void setEmail(String email)     { this.email = email; }

    /** @param password updated password */
    public void setPassword(String password) { this.password = password; }

    // ─── Business Methods ────────────────────────────────────────────────────

    /**
     * Checks if the voter meets the minimum age requirement (18+).
     *
     * @return true if eligible to vote
     */
    public boolean isEligible() {
        return age >= 18;
    }

    /**
     * Authenticates the voter by verifying the password.
     *
     * @param inputPassword password entered by the voter
     * @return true if password matches
     */
    public boolean authenticate(String inputPassword) {
        return this.password.equals(inputPassword);
    }

    /**
     * Marks the voter as having cast their vote.
     * Prevents duplicate voting.
     */
    public void markAsVoted() {
        this.hasVoted = true;
    }

    /**
     * Returns a formatted display string for this voter.
     *
     * @return voter details as a formatted string
     */
    @Override
    public String toString() {
        return String.format(
            "  Voter ID : %s%n  Name     : %s%n  Age      : %d%n  Email    : %s%n  Voted    : %s",
            voterId, name, age, email, hasVoted ? "Yes" : "No"
        );
    }
}