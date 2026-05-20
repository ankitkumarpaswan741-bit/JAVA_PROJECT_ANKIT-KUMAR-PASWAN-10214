import java.util.Scanner;

class Voter {
    String id, name, email, password;
    int age;

    Voter(String id, String name, int age, String email, String password) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.email = email;
        this.password = password;
    }

    class Candidate {
    String id, name, party, symbol;
    int votes = 0;

    Candidate(String id, String name, String party, String symbol) {
        this.id = id;
        this.name = name;
        this.party = party;
        this.symbol = symbol;
    }
}

class VotingSystem {
    private String electionName;
    private boolean electionActive = false;
    private List<Voter> voters = new ArrayList<>();
    private List<Candidate> candidates = new ArrayList<>();

    VotingSystem(String electionName) {
        this.electionName = electionName;
    }

    void registerVoter(Voter v) { voters.add(v); }
    void addCandidate(Candidate c) { candidates.add(c); }
    void startElection() { electionActive = true; }
    void endElection() { electionActive = false; }
    void castVote(String voterId, String password, String candidateId) {
        if (!electionActive) {
            System.out.println("Election not started!");
            return;
        }
        // authentication + vote logic
    }
    // other methods...
}


}



/**
 * Main entry point for the Object-Oriented Online Voting System.
 *
 * <p>Provides a menu-driven console interface that demonstrates all
 * features of the system as described in the project specification:</p>
 * <ul>
 *   <li>Voter Registration</li>
 *   <li>Candidate Registration</li>
 *   <li>Start / End Election</li>
 *   <li>Cast Vote (with authentication)</li>
 *   <li>Search Voter / Candidate</li>
 *   <li>Modify Records</li>
 *   <li>Live Vote Count</li>
 *   <li>Display All Records</li>
 *   <li>View Final Results</li>
 * </ul>
 *
 * @author Ankit Kumar Paswan
 * @version 1.0
 */
public class Main {

    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        System.out.println("╔══════════════════════════════════════════════════════════╗");
        System.out.println("║     OBJECT-ORIENTED ONLINE VOTING SYSTEM — JAVA         ║");
        System.out.println("║     Name   : Ankit Kumar Paswan                         ║");
        System.out.println("║     ERP    : RU-25-10214                                ║");
        System.out.println("║     Branch : B.Tech / CSE (AI/ML)                      ║");
        System.out.println("╚══════════════════════════════════════════════════════════╝");
        System.out.println();

        System.out.print("  Enter Election Name: ");
        String electionName = sc.nextLine().trim();
        if (electionName.isBlank()) electionName = "General Election 2025";

        VotingSystem vs = new VotingSystem(electionName);

        boolean running = true;
        while (running) {
            printMenu();
            System.out.print("  Enter choice: ");
            String input = sc.nextLine().trim();

            switch (input) {
                case "1"  -> registerVoter(vs);
                case "2"  -> addCandidate(vs);
                case "3"  -> vs.startElection();
                case "4"  -> castVote(vs);
                case "5"  -> vs.displayLiveCount();
                case "6"  -> vs.endElection();
                case "7"  -> searchVoter(vs);
                case "8"  -> searchCandidate(vs);
                case "9"  -> modifyVoter(vs);
                case "10" -> modifyCandidate(vs);
                case "11" -> vs.displayAllVoters();
                case "12" -> vs.displayAllCandidates();
                case "13" -> vs.displayResults();
                case "14" -> {
                    System.out.println("\n  Thank you for using the Online Voting System. Goodbye!\n");
                    running = false;
                }
                default -> System.out.println("  [!] Invalid choice. Please enter a number from 1 to 14.");
            }
        }

        sc.close();
    }

    // ─── Menu ─────────────────────────────────────────────────────────────────
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

    // ─── Feature Handlers ────────────────────────────────────────────────────

    /** Handles voter registration input. */
    private static void registerVoter(VotingSystem vs) {
        System.out.println("\n  -- Register New Voter --");
        System.out.print("  Voter ID  : "); String id   = sc.nextLine().trim();
        System.out.print("  Name      : "); String name = sc.nextLine().trim();
        System.out.print("  Age       : ");
        int age = 0;
        try { age = Integer.parseInt(sc.nextLine().trim()); }
        catch (NumberFormatException e) { System.out.println("  [X] Invalid age."); return; }
        System.out.print("  Email     : "); String email    = sc.nextLine().trim();
        System.out.print("  Password  : "); String password = sc.nextLine().trim();

        Voter voter = new Voter(id, name, age, email, password);
        vs.registerVoter(voter);
    }

    /** Handles candidate registration input. */
    private static void addCandidate(VotingSystem vs) {
        System.out.println("\n  -- Add New Candidate --");
        System.out.print("  Candidate ID : "); String id     = sc.nextLine().trim();
        System.out.print("  Name         : "); String name   = sc.nextLine().trim();
        System.out.print("  Party        : "); String party  = sc.nextLine().trim();
        System.out.print("  Symbol       : "); String symbol = sc.nextLine().trim();

        Candidate candidate = new Candidate(id, name, party, symbol);
        vs.addCandidate(candidate);
    }

    /** Handles vote casting input (with authentication). */
    private static void castVote(VotingSystem vs) {
        System.out.println("\n  -- Cast Your Vote --");
        vs.displayAllCandidates();
        System.out.print("  Your Voter ID    : "); String voterId     = sc.nextLine().trim();
        System.out.print("  Your Password    : "); String password    = sc.nextLine().trim();
        System.out.print("  Candidate ID     : "); String candidateId = sc.nextLine().trim();

        vs.castVote(voterId, password, candidateId);
    }

    /** Handles voter search input. */
    private static void searchVoter(VotingSystem vs) {
        System.out.print("\n  Enter Voter ID to search: ");
        String id = sc.nextLine().trim();
        vs.searchAndDisplayVoter(id);
    }

    /** Handles candidate search input. */
    private static void searchCandidate(VotingSystem vs) {
        System.out.print("\n  Enter Candidate ID to search: ");
        String id = sc.nextLine().trim();
        vs.searchAndDisplayCandidate(id);
    }

    /** Handles voter record modification. */
    private static void modifyVoter(VotingSystem vs) {
        System.out.println("\n  -- Modify Voter Record --");
        System.out.print("  Voter ID     : "); String id       = sc.nextLine().trim();
        System.out.print("  New Name     : "); String newName  = sc.nextLine().trim();
        System.out.print("  New Email    : "); String newEmail = sc.nextLine().trim();
        vs.updateVoter(id, newName, newEmail);
    }

    /** Handles candidate record modification. */
    private static void modifyCandidate(VotingSystem vs) {
        System.out.println("\n  -- Modify Candidate Record --");
        System.out.print("  Candidate ID : "); String id       = sc.nextLine().trim();
        System.out.print("  New Name     : "); String newName  = sc.nextLine().trim();
        System.out.print("  New Party    : "); String newParty = sc.nextLine().trim();
        vs.updateCandidate(id, newName, newParty);
    }
}
    

