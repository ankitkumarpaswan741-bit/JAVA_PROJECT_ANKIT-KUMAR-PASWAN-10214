import java.util.Scanner;

/**
 * Main entry point for the Object-Oriented Online Voting System.
 * @author Ankit Kumar Paswan
 * @version 1.0
 */
public class Main {

    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        System.out.println("╔══════════════════════════════════════════════════════════╗");
        System.out.println("║     OBJECT-ORIENTED ONLINE VOTING SYSTEM - JAVA         ║");
        System.out.println("║     Name   : Ankit Kumar Paswan                         ║");
        System.out.println("║     ERP    : RU-25-10214                                ║");
        System.out.println("║     Branch : B.Tech / CSE (AI/ML)                      ║");
        System.out.println("╚══════════════════════════════════════════════════════════╝");
        System.out.println();

        System.out.print("  Enter Election Name: ");
        String electionName = sc.nextLine().trim();
        if (electionName.isEmpty()) electionName = "General Election 2025";

        VotingSystem vs = new VotingSystem(electionName);

        boolean running = true;
        while (running) {
            printMenu();
            System.out.print("  Enter choice: ");
            String input = sc.nextLine().trim();

            if (input.equals("1"))       registerVoter(vs);
            else if (input.equals("2"))  addCandidate(vs);
            else if (input.equals("3"))  vs.startElection();
            else if (input.equals("4"))  castVote(vs);
            else if (input.equals("5"))  vs.displayLiveCount();
            else if (input.equals("6"))  vs.endElection();
            else if (input.equals("7"))  searchVoter(vs);
            else if (input.equals("8"))  searchCandidate(vs);
            else if (input.equals("9"))  modifyVoter(vs);
            else if (input.equals("10")) modifyCandidate(vs);
            else if (input.equals("11")) vs.displayAllVoters();
            else if (input.equals("12")) vs.displayAllCandidates();
            else if (input.equals("13")) vs.displayResults();
            else if (input.equals("14")) {
                System.out.println("\n  Thank you for using the Online Voting System. Goodbye!\n");
                running = false;
            } else {
                System.out.println("  [!] Invalid choice. Please enter a number from 1 to 14.");
            }
        }

        sc.close();
    }

    private static void printMenu() {
        System.out.println();
        System.out.println("  ┌─────────────────────────────────────────┐");
        System.out.println("  │           MAIN MENU                     │");
        System.out.println("  ├─────────────────────────────────────────┤");
        System.out.println("  │  REGISTRATION                           │");
        System.out.println("  │   1. Register Voter                     │");
        System.out.println("  │   2. Add Candidate                      │");
        System.out.println("  ├─────────────────────────────────────────┤");
        System.out.println("  │  ELECTION                               │");
        System.out.println("  │   3. Start Election                     │");
        System.out.println("  │   4. Cast Vote                          │");
        System.out.println("  │   5. Live Vote Count                    │");
        System.out.println("  │   6. End Election & Final Result        │");
        System.out.println("  ├─────────────────────────────────────────┤");
        System.out.println("  │  SEARCH & MODIFY                        │");
        System.out.println("  │   7. Search Voter by ID                 │");
        System.out.println("  │   8. Search Candidate by ID             │");
        System.out.println("  │   9. Modify Voter Record                │");
        System.out.println("  │  10. Modify Candidate Record            │");
        System.out.println("  ├─────────────────────────────────────────┤");
        System.out.println("  │  DISPLAY                                │");
        System.out.println("  │  11. Display All Voters                 │");
        System.out.println("  │  12. Display All Candidates             │");
        System.out.println("  │  13. Show Final Results                 │");
        System.out.println("  ├─────────────────────────────────────────┤");
        System.out.println("  │  14. Exit                               │");
        System.out.println("  └─────────────────────────────────────────┘");
    }

    private static void registerVoter(VotingSystem vs) {
        System.out.println("\n  -- Register New Voter --");
        System.out.print("  Voter ID  : "); String id = sc.nextLine().trim();
        System.out.print("  Name      : "); String name = sc.nextLine().trim();
        System.out.print("  Age       : ");
        int age = 0;
        try { age = Integer.parseInt(sc.nextLine().trim()); }
        catch (NumberFormatException e) { System.out.println("  [X] Invalid age."); return; }
        System.out.print("  Email     : "); String email = sc.nextLine().trim();
        System.out.print("  Password  : "); String password = sc.nextLine().trim();

        Voter voter = new Voter(id, name, age, email, password);
        vs.registerVoter(voter);
    }

    private static void addCandidate(VotingSystem vs) {
        System.out.println("\n  -- Add New Candidate --");
        System.out.print("  Candidate ID : "); String id = sc.nextLine().trim();
        System.out.print("  Name         : "); String name = sc.nextLine().trim();
        System.out.print("  Party        : "); String party = sc.nextLine().trim();
        System.out.print("  Symbol       : "); String symbol = sc.nextLine().trim();

        Candidate candidate = new Candidate(id, name, party, symbol);
        vs.addCandidate(candidate);
    }

    private static void castVote(VotingSystem vs) {
        System.out.println("\n  -- Cast Your Vote --");
        vs.displayAllCandidates();
        System.out.print("  Your Voter ID : "); String voterId = sc.nextLine().trim();
        System.out.print("  Your Password : "); String password = sc.nextLine().trim();
        System.out.print("  Candidate ID  : "); String candidateId = sc.nextLine().trim();
        vs.castVote(voterId, password, candidateId);
    }

    private static void searchVoter(VotingSystem vs) {
        System.out.print("\n  Enter Voter ID to search: ");
        vs.searchAndDisplayVoter(sc.nextLine().trim());
    }

    private static void searchCandidate(VotingSystem vs) {
        System.out.print("\n  Enter Candidate ID to search: ");
        vs.searchAndDisplayCandidate(sc.nextLine().trim());
    }

    private static void modifyVoter(VotingSystem vs) {
        System.out.println("\n  -- Modify Voter Record --");
        System.out.print("  Voter ID  : "); String id = sc.nextLine().trim();
        System.out.print("  New Name  : "); String newName = sc.nextLine().trim();
        System.out.print("  New Email : "); String newEmail = sc.nextLine().trim();
        vs.updateVoter(id, newName, newEmail);
    }

    private static void modifyCandidate(VotingSystem vs) {
        System.out.println("\n  -- Modify Candidate Record --");
        System.out.print("  Candidate ID : "); String id = sc.nextLine().trim();
        System.out.print("  New Name     : "); String newName = sc.nextLine().trim();
        System.out.print("  New Party    : "); String newParty = sc.nextLine().trim();
        vs.updateCandidate(id, newName, newParty);
    }
}
import java.util.ArrayList;

public class VotingSystem {

    private String electionName;
    private boolean electionActive;
    private ArrayList<Voter> voters;
    private ArrayList<Candidate> candidates;

    public VotingSystem(String electionName) {
        this.electionName = electionName;
        this.electionActive = false;
        this.voters = new ArrayList<Voter>();
        this.candidates = new ArrayList<Candidate>();
    }

    // ── Election Lifecycle ───────────────────────────────────────────────────

    public void startElection() {
        if (electionActive) {
            System.out.println("[!] Election is already running.");
            return;
        }
        electionActive = true;
        printBanner("ELECTION STARTED: " + electionName);
    }

    public void endElection() {
        if (!electionActive) {
            System.out.println("[!] No active election to end.");
            return;
        }
        electionActive = false;
        printBanner("ELECTION ENDED: " + electionName);
        displayResults();
    }

    public boolean isElectionActive() { return electionActive; }

    // ── Voter Registration ───────────────────────────────────────────────────

    public boolean registerVoter(Voter voter) {
        if (!voter.isEligible()) {
            System.out.println("[X] Registration FAILED: " + voter.getName()
                + " is under 18 years old.");
            return false;
        }
        if (findVoterById(voter.getVoterId()) != null) {
            System.out.println("[X] Registration FAILED: Voter ID '"
                + voter.getVoterId() + "' already exists.");
            return false;
        }
        voters.add(voter);
        System.out.println("[OK] Voter registered: " + voter.getName()
            + " (ID: " + voter.getVoterId() + ")");
        return true;
    }

    // ── Candidate Registration ───────────────────────────────────────────────

    public boolean addCandidate(Candidate candidate) {
        if (findCandidateById(candidate.getCandidateId()) != null) {
            System.out.println("[X] Candidate ID '"
                + candidate.getCandidateId() + "' already exists.");
            return false;
        }
        candidates.add(candidate);
        System.out.println("[OK] Candidate added: " + candidate.getName()
            + " | Party: " + candidate.getParty()
            + " (ID: " + candidate.getCandidateId() + ")");
        return true;
    }

    // ── Vote Casting with Authentication ─────────────────────────────────────

    public boolean castVote(String voterId, String password, String candidateId) {
        if (!electionActive) {
            System.out.println("[X] Voting is not open at this time.");
            return false;
        }
        Voter voter = findVoterById(voterId);
        if (voter == null) {
            System.out.println("[X] Voter ID '" + voterId + "' not found.");
            return false;
        }
        if (!voter.authenticate(password)) {
            System.out.println("[X] Authentication FAILED: Incorrect password.");
            return false;
        }
        if (voter.hasVoted()) {
            System.out.println("[X] " + voter.getName()
                + " has ALREADY voted. Duplicate voting not allowed.");
            return false;
        }
        Candidate candidate = findCandidateById(candidateId);
        if (candidate == null) {
            System.out.println("[X] Candidate ID '" + candidateId + "' not found.");
            return false;
        }
        candidate.receiveVote();
        voter.markAsVoted();
        System.out.println("[OK] Vote CAST successfully!");
        System.out.println("     Voter     : " + voter.getName());
        System.out.println("     Candidate : " + candidate.getName()
            + " (" + candidate.getParty() + ")");
        return true;
    }

    // ── Search ───────────────────────────────────────────────────────────────

    public Voter findVoterById(String voterId) {
        for (Voter v : voters) {
            if (v.getVoterId().equalsIgnoreCase(voterId)) return v;
        }
        return null;
    }

    public Candidate findCandidateById(String candidateId) {
        for (Candidate c : candidates) {
            if (c.getCandidateId().equalsIgnoreCase(candidateId)) return c;
        }
        return null;
    }

    public void searchAndDisplayVoter(String voterId) {
        Voter v = findVoterById(voterId);
        if (v != null) {
            System.out.println("\n  -- VOTER DETAILS --");
            System.out.println(v);
        } else {
            System.out.println("[!] No voter found with ID: " + voterId);
        }
    }

    public void searchAndDisplayCandidate(String candidateId) {
        Candidate c = findCandidateById(candidateId);
        if (c != null) {
            System.out.println("\n  -- CANDIDATE DETAILS --");
            System.out.println(c);
        } else {
            System.out.println("[!] No candidate found with ID: " + candidateId);
        }
    }

    // ── Modify Records ───────────────────────────────────────────────────────

    public boolean updateVoter(String voterId, String newName, String newEmail) {
        Voter v = findVoterById(voterId);
        if (v == null) {
            System.out.println("[X] Voter ID '" + voterId + "' not found.");
            return false;
        }
        if (newName != null && !newName.isEmpty())  v.setName(newName);
        if (newEmail != null && !newEmail.isEmpty()) v.setEmail(newEmail);
        System.out.println("[OK] Voter record updated: " + v.getName());
        return true;
    }

    public boolean updateCandidate(String candidateId, String newName, String newParty) {
        Candidate c = findCandidateById(candidateId);
        if (c == null) {
            System.out.println("[X] Candidate ID '" + candidateId + "' not found.");
            return false;
        }
        if (newName != null && !newName.isEmpty())  c.setName(newName);
        if (newParty != null && !newParty.isEmpty()) c.setParty(newParty);
        System.out.println("[OK] Candidate record updated: " + c.getName());
        return true;
    }

    // ── Display ──────────────────────────────────────────────────────────────

    public void displayAllVoters() {
        System.out.println("\n  -- ALL REGISTERED VOTERS (" + voters.size() + ") --");
        if (voters.isEmpty()) {
            System.out.println("  No voters registered yet.");
            return;
        }
        for (int i = 0; i < voters.size(); i++) {
            System.out.println("\n  [" + (i + 1) + "]");
            System.out.println(voters.get(i));
        }
        System.out.println();
    }

    public void displayAllCandidates() {
        System.out.println("\n  -- ALL CANDIDATES (" + candidates.size() + ") --");
        if (candidates.isEmpty()) {
            System.out.println("  No candidates added yet.");
            return;
        }
        System.out.printf("  %-6s | %-20s | %-20s | %-10s | %s%n",
            "ID", "Name", "Party", "Symbol", "Votes");
        System.out.println("  " + "─".repeat(72));
        for (Candidate c : candidates) {
            System.out.println(c.toTableRow());
        }
        System.out.println();
    }

    public void displayLiveCount() {
        System.out.println("\n  -- LIVE VOTE COUNT --");
        int total = 0;
        for (Candidate c : candidates) total += c.getVoteCount();

        System.out.printf("  %-20s | %-20s | %-8s | %s%n",
            "Candidate", "Party", "Votes", "% Share");
        System.out.println("  " + "─".repeat(62));
        for (Candidate c : candidates) {
            double pct = total == 0 ? 0.0 : (c.getVoteCount() * 100.0 / total);
            System.out.printf("  %-20s | %-20s | %-8d | %.1f%%%n",
                c.getName(), c.getParty(), c.getVoteCount(), pct);
        }
        System.out.println("  " + "─".repeat(62));
        System.out.printf("  %-20s   %-20s   %d%n", "TOTAL", "", total);
        System.out.println();
    }

    public void displayResults() {
        printBanner("FINAL RESULTS: " + electionName);
        if (candidates.isEmpty()) {
            System.out.println("  No candidates found.");
            return;
        }

        // Sort by votes descending (selection sort)
        ArrayList<Candidate> sorted = new ArrayList<Candidate>(candidates);
        for (int i = 0; i < sorted.size() - 1; i++) {
            for (int j = i + 1; j < sorted.size(); j++) {
                if (sorted.get(j).getVoteCount() > sorted.get(i).getVoteCount()) {
                    Candidate temp = sorted.get(i);
                    sorted.set(i, sorted.get(j));
                    sorted.set(j, temp);
                }
            }
        }

        int total = 0;
        for (Candidate c : candidates) total += c.getVoteCount();

        System.out.printf("  %-5s | %-20s | %-20s | %-8s | %s%n",
            "Rank", "Candidate", "Party", "Votes", "% Share");
        System.out.println("  " + "─".repeat(68));
        int rank = 1;
        for (Candidate c : sorted) {
            double pct = total == 0 ? 0.0 : (c.getVoteCount() * 100.0 / total);
            System.out.printf("  %-5d | %-20s | %-20s | %-8d | %.1f%%%n",
                rank++, c.getName(), c.getParty(), c.getVoteCount(), pct);
        }
        System.out.println("  " + "─".repeat(68));
        System.out.printf("  Total Votes: %d%n%n", total);

        Candidate winner = sorted.get(0);
        if (winner.getVoteCount() == 0) {
            System.out.println("  No votes cast. No winner.");
        } else if (sorted.size() > 1 && sorted.get(1).getVoteCount() == winner.getVoteCount()) {
            System.out.println("  *** RESULT: TIE DECLARED ***");
        } else {
            System.out.println("  *** WINNER: " + winner.getName()
                + " (" + winner.getParty() + ") with "
                + winner.getVoteCount() + " votes ***");
        }
        System.out.println();
    }

    // ── Helpers ──────────────────────────────────────────────────────────────

    private void printBanner(String title) {
        System.out.println("\n  ══════════════════════════════════════════════════════");
        System.out.println("    " + title);
        System.out.println("  ══════════════════════════════════════════════════════\n");
    }
}
public class Voter {
    private String voterId;
    private String name;
    private int age;
    private String email;
    private String password;
    private boolean hasVoted;

    public Voter(String voterId, String name, int age, String email, String password) {
        this.voterId = voterId;
        this.name = name;
        this.age = age;
        this.email = email;
        this.password = password;
        this.hasVoted = false;
    }

    public String getVoterId()  { return voterId; }
    public String getName()     { return name; }
    public int getAge()         { return age; }
    public String getEmail()    { return email; }
    public boolean hasVoted()   { return hasVoted; }

    public void setName(String name)         { this.name = name; }
    public void setAge(int age)              { this.age = age; }
    public void setEmail(String email)       { this.email = email; }
    public void setPassword(String password) { this.password = password; }

    public boolean isEligible() {
        return age >= 18;
    }

    public boolean authenticate(String inputPassword) {
        return this.password.equals(inputPassword);
    }

    public void markAsVoted() {
        this.hasVoted = true;
    }

    @Override
    public String toString() {
        return "  Voter ID : " + voterId + "\n" +
               "  Name     : " + name + "\n" +
               "  Age      : " + age + "\n" +
               "  Email    : " + email + "\n" +
               "  Voted    : " + (hasVoted ? "Yes" : "No");
    }
}
public class Candidate {
    private String candidateId;
    private String name;
    private String party;
    private String symbol;
    private int voteCount;

    public Candidate(String candidateId, String name, String party, String symbol) {
        this.candidateId = candidateId;
        this.name = name;
        this.party = party;
        this.symbol = symbol;
        this.voteCount = 0;
    }

    public String getCandidateId() { return candidateId; }
    public String getName()        { return name; }
    public String getParty()       { return party; }
    public String getSymbol()      { return symbol; }
    public int getVoteCount()      { return voteCount; }

    public void setName(String name)     { this.name = name; }
    public void setParty(String party)   { this.party = party; }
    public void setSymbol(String symbol) { this.symbol = symbol; }

    public void receiveVote() {
        this.voteCount++;
    }

    @Override
    public String toString() {
        return "  Candidate ID : " + candidateId + "\n" +
               "  Name         : " + name + "\n" +
               "  Party        : " + party + "\n" +
               "  Symbol       : " + symbol + "\n" +
               "  Votes        : " + voteCount;
    }

    public String toTableRow() {
        return String.format("  %-6s | %-20s | %-20s | %-10s | %d",
            candidateId, name, party, symbol, voteCount);
    }
}