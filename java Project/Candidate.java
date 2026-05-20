public class Candidate {
    

/**
 * Candidate class represents an election candidate in the Online Voting System.
 *
 * <p>Demonstrates OOP Concepts:</p>
 * <ul>
 *   <li>Encapsulation – private fields with controlled access via getters/setters</li>
 *   <li>Abstraction – exposes only necessary methods to outside classes</li>
 * </ul>
 *
 * @author Ankit Kumar Paswan
 * @version 1.0
 */


    // ─── Private Fields (Encapsulation) ─────────────────────────────────────
    private String candidateId;
    private String name;
    private String party;
    private String symbol;
    private int    voteCount;

    // ─── Constructor ─────────────────────────────────────────────────────────
    /**
     * Creates a new Candidate with the given details.
     *
     * @param candidateId unique candidate ID
     * @param name        full name of the candidate
     * @param party       political party name
     * @param symbol      party election symbol
     */
    public Candidate(String candidateId, String name, String party, String symbol) {
        this.candidateId = candidateId;
        this.name        = name;
        this.party       = party;
        this.symbol      = symbol;
        this.voteCount   = 0;
    }

    // ─── Getters ─────────────────────────────────────────────────────────────
    /** @return unique candidate ID */
    public String getCandidateId() { return candidateId; }

    /** @return candidate's full name */
    public String getName()        { return name; }

    /** @return candidate's party */
    public String getParty()       { return party; }

    /** @return candidate's symbol */
    public String getSymbol()      { return symbol; }

    /** @return total votes received so far */
    public int getVoteCount()      { return voteCount; }

    // ─── Setters (for modification feature) ──────────────────────────────────
    /** @param name updated name */
    public void setName(String name)    { this.name = name; }

    /** @param party updated party name */
    public void setParty(String party)  { this.party = party; }

    /** @param symbol updated symbol */
    public void setSymbol(String symbol){ this.symbol = symbol; }

    // ─── Business Methods ────────────────────────────────────────────────────

    /**
     * Increments this candidate's vote count by 1.
     * Called every time a voter casts a vote for this candidate.
     */
    public void receiveVote() {
        this.voteCount++;
    }

    /**
     * Returns a formatted display string for this candidate.
     *
     * @return candidate details as a formatted table row
     */
    @Override
    public String toString() {
        return String.format(
            "  Candidate ID : %s%n  Name         : %s%n  Party        : %s%n  Symbol       : %s%n  Votes        : %d",
            candidateId, name, party, symbol, voteCount
        );
    }

    /**
     * Returns a compact single-line summary (used in result tables).
     *
     * @return one-line summary
     */
    public String toTableRow() {
        return String.format("  %-6s | %-22s | %-22s | %-10s | %d",
            candidateId, name, party, symbol, voteCount);
    }
}
    
}
