
import java.util.*;

// Candidate class
class Candidate {
    String name, party;
    int votes;
    Candidate(String name, String party) { this.name = name; this.party = party; }
    void castVote() { votes++; }
    public String toString() { return name + " (" + party + ") - Votes: " + votes; }
}

// Voter class
class Voter {
    int id;
    String name, password;
    boolean hasVoted;
    Voter(int id, String name, String password) { this.id = id; this.name = name; this.password = password; }
    boolean authenticate(String pwd) { return password.equals(pwd); }
}

// VotingSystem class (Central Controller)
class VotingSystem {
    List<Voter> voters = new ArrayList<>();
    List<Candidate> candidates = new ArrayList<>();

    void addVoter(int id, String name, String pwd) { voters.add(new Voter(id, name, pwd)); }
    void addCandidate(String name, String party) { candidates.add(new Candidate(name, party)); }

    Voter findVoter(int id) {
        return voters.stream().filter(v -> v.id == id).findFirst().orElse(null);
    }

    void castVote(int voterId, String pwd, int candidateIndex) {
        Voter v = findVoter(voterId);
        if (v == null) { System.out.println("Voter not found."); return; }
        if (!v.authenticate(pwd)) { System.out.println("Invalid password."); return; }
        if (v.hasVoted) { System.out.println("Already voted!"); return; }
        if (candidateIndex < 0 || candidateIndex >= candidates.size()) { System.out.println("Invalid candidate."); return; }
        candidates.get(candidateIndex).castVote();
        v.hasVoted = true;
        System.out.println("Vote cast successfully by " + v.name);
    }

    void showResults() {
        System.out.println("\n--- Election Results ---");
        candidates.forEach(System.out::println);
        candidates.stream().max(Comparator.comparingInt(c -> c.votes))
                  .ifPresent(c -> System.out.println("Winner: " + c.name));
    }

    void showCandidates() {
        System.out.println("\n--- Candidates ---");
        for (int i = 0; i < candidates.size(); i++)
            System.out.println(i + ". " + candidates.get(i));
    }
}

// Main class
public class OnlineVotingSystem {
    public static void main(String[] args) {
        VotingSystem vs = new VotingSystem();
        Scanner sc = new Scanner(System.in);

        // Sample data
        vs.addVoter(101, "Ankit", "pass1");
        vs.addVoter(102, "Riya",  "pass2");
        vs.addVoter(103, "Raj",   "pass3");
        vs.addCandidate("BJP", "Party A");
        vs.addCandidate("Congress",   "Party B");
        vs.addCandidate("COCKROACH JANTA PARTY", "Party C");

        int choice;
        do {
            System.out.println("\n1. Show Candidates  2. Cast Vote  3. Show Results  0. Exit");
            System.out.print("Choice: ");
            choice = sc.nextInt();
            switch (choice) {
                case 1 -> vs.showCandidates();
                case 2 -> {
                    System.out.print("Voter ID: ");   int id  = sc.nextInt();
                    System.out.print("Password: ");   String pwd = sc.next();
                    vs.showCandidates();
                    System.out.print("Candidate No: "); int ci = sc.nextInt();
                    vs.castVote(id, pwd, ci);
                }
                case 3 -> vs.showResults();
            }
        } while (choice != 0);
        sc.close();
    }
}