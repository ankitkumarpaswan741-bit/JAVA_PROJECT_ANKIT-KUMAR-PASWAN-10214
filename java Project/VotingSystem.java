package src;

import java.util.ArrayList;

/**
 * VotingSystem is the central controller class of the Online Voting System.
 *
 * <p>It coordinates all interactions between Voter and Candidate objects,
 * managing the full election lifecycle:</p>
 * <ul>
 *   <li>Voter Registration &amp; Authentication</li>
 *   <li>Candidate Registration</li>
 *   <li>Secure Vote Casting (no duplicates)</li>
 *   <li>Search functionality (by ID or name)</li>
 *   <li>Record Modification</li>
 *   <li>Real-Time Vote Counting</li>
 *   <li>Result Generation</li>
 * </ul>
 *
 * <p>OOP Concepts Used:</p>
 * <ul>
 *   <li>Encapsulation – all data lists are private</li>
 *   <li>ArrayList – dynamic storage for voters and candidates</li>
 *   <li>Modularity – each method does exactly one job</li>
 * </ul>
 *
 * @author Ankit Kumar Paswan
 * @version 1.0
 */
public class VotingSystem {

    // ─── Private Data Members ────────────────────────────────────────────────
    private String           electionName;
    private boolean          electionActive;
    private ArrayList<Voter>     voters;
    private ArrayList<Candidate> candidates;

    // ─── Constructor ─────────────────────────────────────────────────────────
    /**
     * Initialises the VotingSystem with an election name.
     *
     * @param electionName name of the election
     */
    public VotingSystem(String electionName) {
        this.electionName   = electionName;
        this.electionActive = false;
        this.voters         = new ArrayList<>();
        this.candidates     = new ArrayList<>();
    }

    // ═════════════════════════════════════════════════════════════════════════
    // 1. ELECTION LIFECYCLE
    // ═════════════════════════════════════════════════════════════════════════

    /** Starts the election. Voting is only allowed when the election is active. */
    public void startElection() {
        if (electionActive) {
            System.out.println("[!] Election is already running.");
            return;
        }
        electionActive = true;
        printBanner("ELECTION STARTED: " + electionName);
    }

    /** Ends the election and triggers result generation. */
    public void endElection() {
        if (!electionActive) {
            System.out.println("[!] No active election to end.");
            return;
        }
        electionActive = false;
        printBanner("ELECTION ENDED: " + electionName);
        displayResults();
    }

    /** @return true if an election is currently active */
    public boolean isElectionActive() { return electionActive; }

    // ═════════════════════════════════════════════════════════════════════════
    // 2. VOTER REGISTRATION
    // ═════════════════════════════════════════════════════════════════════════

    /**
     * Registers a new voter after validating eligibility and ID uniqueness.
     *
     * @param voter Voter object to register
     * @return true if registration was successful
     */
    public boolean registerVoter(Voter voter) {
        // Age eligibility check
        if (!voter.isEligible()) {
            System.out.println("[X] Registration FAILED: " + voter.getName()
                + " is under 18 years old.");
            return false;
        }
        // Duplicate ID check
        if (findVoterById(voter.getVoterId()) != null) {
            System.out.println("[X] Registration FAILED: Voter ID '"
                + voter.getVoterId() + "' already exists.");
            return false;
        }
        voters.add(voter);
        System.out.println("[✓] Voter registered successfully: "
            + voter.getName() + " (ID: " + voter.getVoterId() + ")");
        return true;
    }

    // ═════════════════════════════════════════════════════════════════════════
    // 3. CANDIDATE REGISTRATION
    // ═════════════════════════════════════════════════════════════════════════

    /**
     * Adds a new candidate to the election.
     *
     * @param candidate Candidate object to add
     * @return true if added successfully
     */
    public boolean addCandidate(Candidate candidate) {
        if (findCandidateById(candidate.getCandidateId()) != null) {
            System.out.println("[X] Candidate ID '"
                + candidate.getCandidateId() + "' already exists.");
            return false;
        }
        candidates.add(candidate);
        System.out.println("[✓] Candidate added: " + candidate.getName()
            + " | Party: " + candidate.getParty()
            + " (ID: " + candidate.getCandidateId() + ")");
        return true;
    }

    // ═════════════════════════════════════════════════════════════════════════
    // 4. AUTHENTICATION & VOTE CASTING
    // ═════════════════════════════════════════════════════════════════════════

    /**
     * Authenticates a voter and casts their vote for a candidate.
     *
     * <p>Steps performed:</p>
     * <ol>
     *   <li>Check election is active</li>
     *   <li>Look up voter by ID</li>
     *   <li>Authenticate password</li>
     *   <li>Check voter hasn't already voted</li>
     *   <li>Look up candidate</li>
     *   <li>Record the vote</li>
     * </ol>
     *
     * @param voterId     voter's unique ID
     * @param password    voter's password
     * @param candidateId target candidate's ID
     * @return true if vote was cast successfully
     */
    public boolean castVote(String voterId, String password, String candidateId) {

        // Step 1: Election must be active
        if (!electionActive) {
            System.out.println("[X] Voting is not open at this time.");
            return false;
        }

        // Step 2: Find voter
        Voter voter = findVoterById(voterId);
        if (voter == null) {
            System.out.println("[X] Voter ID '" + voterId + "' not found.");
            return false;
        }

        // Step 3: Authenticate
        if (!voter.authenticate(password)) {
            System.out.println("[X] Authentication FAILED: Incorrect password for voter '"
                + voter.getName() + "'.");
            return false;
        }

        // Step 4: Duplicate vote check
        if (voter.hasVoted()) {
            System.out.println("[X] " + voter.getName()
                + " has ALREADY voted. Duplicate voting is not allowed.");
            return false;
        }

        // Step 5: Find candidate
        Candidate candidate = findCandidateById(candidateId);
        if (candidate == null) {
            System.out.println("[X] Candidate ID '" + candidateId + "' not found.");
            return false;
        }

        // Step 6: Record vote
        candidate.receiveVote();
        voter.markAsVoted();

        System.out.println("[✓] Vote CAST successfully!");
        System.out.println("    Voter     : " + voter.getName());
        System.out.println("    Candidate : " + candidate.getName()
            + " (" + candidate.getParty() + ")");
        return true;
    }

    // ═════════════════════════════════════════════════════════════════════════
    // 5. SEARCH FUNCTIONALITY
    // ═════════════════════════════════════════════════════════════════════════

    /**
     * Searches for a voter by their unique ID.
     *
     * @param voterId voter ID to search
     * @return Voter object if found, null otherwise
     */
    public Voter findVoterById(String voterId) {
        for (Voter v : voters) {
            if (v.getVoterId().equalsIgnoreCase(voterId)) {
                return v;
            }
        }
        return null;
    }

    /**
     * Searches for a voter by name (case-insensitive, partial match).
     *
     * @param name name to search
     * @return first matching Voter, or null if not found
     */
    public Voter findVoterByName(String name) {
        for (Voter v : voters) {
            if (v.getName().toLowerCase().contains(name.toLowerCase())) {
                return v;
            }
        }
        return null;
    }

    /**
     * Searches for a candidate by their unique ID.
     *
     * @param candidateId candidate ID to search
     * @return Candidate object if found, null otherwise
     */
    public Candidate findCandidateById(String candidateId) {
        for (Candidate c : candidates) {
            if (c.getCandidateId().equalsIgnoreCase(candidateId)) {
                return c;
            }
        }
        return null;
    }

    /**
     * Searches for a candidate by name (case-insensitive, partial match).
     *
     * @param name name to search
     * @return first matching Candidate, or null if not found
     */
    public Candidate findCandidateByName(String name) {
        for (Candidate c : candidates) {
            if (c.getName().toLowerCase().contains(name.toLowerCase())) {
                return c;
            }
        }
        return null;
    }

    /** Displays voter details for a given voter ID. */
    public void searchAndDisplayVoter(String voterId) {
        Voter v = findVoterById(voterId);
        if (v != null) {
            printSectionHeader("VOTER DETAILS");
            System.out.println(v);
            System.out.println();
        } else {
            System.out.println("[!] No voter found with ID: " + voterId);
        }
    }

    /** Displays candidate details for a given candidate ID. */
    public void searchAndDisplayCandidate(String candidateId) {
        Candidate c = findCandidateById(candidateId);
        if (c != null) {
            printSectionHeader("CANDIDATE DETAILS");
            System.out.println(c);
            System.out.println();
        } else {
            System.out.println("[!] No candidate found with ID: " + candidateId);
        }
    }

    // ═════════════════════════════════════════════════════════════════════════
    // 6. RECORD MODIFICATION
    // ═════════════════════════════════════════════════════════════════════════

    /**
     * Updates a voter's name and email.
     *
     * @param voterId  voter to update
     * @param newName  new name (pass null to keep unchanged)
     * @param newEmail new email (pass null to keep unchanged)
     * @return true if update was successful
     */
    public boolean updateVoter(String voterId, String newName, String newEmail) {
        Voter v = findVoterById(voterId);
        if (v == null) {
            System.out.println("[X] Voter ID '" + voterId + "' not found.");
            return false;
        }
        if (newName  != null && !newName.isBlank())  v.setName(newName);
        if (newEmail != null && !newEmail.isBlank()) v.setEmail(newEmail);
        System.out.println("[✓] Voter record updated: " + v.getName());
        return true;
    }

    /**
     * Updates a candidate's name and party.
     *
     * @param candidateId candidate to update
     * @param newName     new name (pass null to keep unchanged)
     * @param newParty    new party (pass null to keep unchanged)
     * @return true if update was successful
     */
    public boolean updateCandidate(String candidateId, String newName, String newParty) {
        Candidate c = findCandidateById(candidateId);
        if (c == null) {
            System.out.println("[X] Candidate ID '" + candidateId + "' not found.");
            return false;
        }
        if (newName  != null && !newName.isBlank())  c.setName(newName);
        if (newParty != null && !newParty.isBlank()) c.setParty(newParty);
        System.out.println("[✓] Candidate record updated: " + c.getName());
        return true;
    }

    // ═════════════════════════════════════════════════════════════════════════
    // 7. DISPLAY METHODS
    // ═════════════════════════════════════════════════════════════════════════

    /** Displays all registered voters. */
    public void displayAllVoters() {
        printSectionHeader("ALL REGISTERED VOTERS (" + voters.size() + ")");
        if (voters.isEmpty()) {
            System.out.println("  No voters registered yet.");
        } else {
            int i = 1;
            for (Voter v : voters) {
                System.out.println("  [" + i++ + "]");
                System.out.println(v);
                System.out.println();
            }
        }
    }

    /** Displays all registered candidates. */
    public void displayAllCandidates() {
        printSectionHeader("ALL CANDIDATES (" + candidates.size() + ")");
        if (candidates.isEmpty()) {
            System.out.println("  No candidates added yet.");
        } else {
            System.out.printf("  %-6s | %-22s | %-22s | %-10s | %s%n",
                "ID", "Name", "Party", "Symbol", "Votes");
            System.out.println("  " + "-".repeat(80));
            for (Candidate c : candidates) {
                System.out.println(c.toTableRow());
            }
        }
        System.out.println();
    }

    // ═════════════════════════════════════════════════════════════════════════
    // 8. REAL-TIME VOTE COUNT & RESULT GENERATION
    // ═════════════════════════════════════════════════════════════════════════

    /**
     * Displays the current vote tally for all candidates.
     * Available in real-time during the election.
     */
    public void displayLiveCount() {
        printSectionHeader("LIVE VOTE COUNT");
        int totalVotes = 0;
        for (Candidate c : candidates) {
            totalVotes += c.getVoteCount();
        }
        System.out.printf("  %-22s | %-22s | %-8s | %s%n",
            "Candidate", "Party", "Votes", "% Share");
        System.out.println("  " + "-".repeat(70));
        for (Candidate c : candidates) {
            double pct = (totalVotes == 0) ? 0.0
                : (c.getVoteCount() * 100.0 / totalVotes);
            System.out.printf("  %-22s | %-22s | %-8d | %.1f%%%n",
                c.getName(), c.getParty(), c.getVoteCount(), pct);
        }
        System.out.println("  " + "-".repeat(70));
        System.out.printf("  %-22s | %-22s | %-8d |%n", "TOTAL", "", totalVotes);
        System.out.println();
    }

    /**
     * Generates and displays the final election result.
     * Identifies the winner (candidate with the most votes).
     */
    public void displayResults() {
        printBanner("FINAL ELECTION RESULTS: " + electionName);

        if (candidates.isEmpty()) {
            System.out.println("  No candidates found.");
            return;
        }

        // Sort candidates by vote count (descending) using simple selection sort
        ArrayList<Candidate> sorted = new ArrayList<>(candidates);
        for (int i = 0; i < sorted.size() - 1; i++) {
            for (int j = i + 1; j < sorted.size(); j++) {
                if (sorted.get(j).getVoteCount() > sorted.get(i).getVoteCount()) {
                    Candidate temp = sorted.get(i);
                    sorted.set(i, sorted.get(j));
                    sorted.set(j, temp);
                }
            }
        }

        int totalVotes = 0;
        for (Candidate c : candidates) totalVotes += c.getVoteCount();

        int rank = 1;
        System.out.printf("  %-5s | %-22s | %-22s | %-8s | %s%n",
            "Rank", "Candidate", "Party", "Votes", "% Share");
        System.out.println("  " + "-".repeat(75));

        for (Candidate c : sorted) {
            double pct = (totalVotes == 0) ? 0.0
                : (c.getVoteCount() * 100.0 / totalVotes);
            System.out.printf("  %-5d | %-22s | %-22s | %-8d | %.1f%%%n",
                rank++, c.getName(), c.getParty(), c.getVoteCount(), pct);
        }
        System.out.println("  " + "-".repeat(75));
        System.out.printf("  %-5s   %-22s   %-22s   %-8d%n",
            "", "TOTAL VOTES", "", totalVotes);
        System.out.println();

        // Announce winner
        Candidate winner = sorted.get(0);
        if (winner.getVoteCount() == 0) {
            System.out.println("  No votes were cast. No winner declared.");
        } else {
            // Check for tie
            boolean tie = sorted.size() > 1
                && sorted.get(1).getVoteCount() == winner.getVoteCount();
            if (tie) {
                System.out.println("  *** RESULT: TIE DECLARED ***");
            } else {
                System.out.println("  *** WINNER: " + winner.getName()
                    + " (" + winner.getParty() + ") ***");
                System.out.println("  Votes Received : " + winner.getVoteCount());
            }
        }
        System.out.println();
    }

    // ═════════════════════════════════════════════════════════════════════════
    // 9. HELPER / UTILITY METHODS
    // ═════════════════════════════════════════════════════════════════════════

    /** Returns total votes cast so far. */
    public int getTotalVotesCast() {
        int total = 0;
        for (Voter v : voters) {
            if (v.hasVoted()) total++;
        }
        return total;
    }

    /** Returns total number of registered voters. */
    public int getTotalRegisteredVoters() { return voters.size(); }

    /** Returns total number of candidates. */
    public int getTotalCandidates() { return candidates.size(); }

    /** Prints a formatted section header. */
    private void printSectionHeader(String title) {
        System.out.println();
        System.out.println("  ── " + title + " ──────────────────────────────");
    }

    /** Prints a banner (used for major lifecycle events). */
    private void printBanner(String title) {
        String border = "═".repeat(60);
        System.out.println();
        System.out.println("  " + border);
        System.out.printf("  ║  %-56s║%n", title);
        System.out.println("  " + border);
        System.out.println();
    }
}
