import java.util.*;

public class Main {
    private List<Candidate> candidates = new ArrayList<>();
    private Map<Integer, Integer> votes = new HashMap<>();
    private Set<Integer> voterIds = new HashSet<>();

    public static void main(String[] args) {
        Main votingSystem = new Main();
        votingSystem.addCandidates();
        votingSystem.displayCandidates();
        votingSystem.castVotes();
        votingSystem.tallyVotes();
        votingSystem.determineWinner();
        System.out.println("\nThank you for voting!");
    }

    private void addCandidates() {
        Scanner scanner = new Scanner(System.in);
        int numCandidates = 0;

        while (numCandidates <= 0) {
            try {
                System.out.print("Enter the number of candidates (must be a positive integer): ");
                numCandidates = Integer.parseInt(scanner.nextLine());
                if (numCandidates <= 0) {
                    System.out.println("Please enter a positive integer.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a positive integer.");
            }
        }

        for (int i = 0; i < numCandidates; i++) {
            System.out.print("Enter the name of candidate " + (i + 1) + ": ");
            String name = scanner.nextLine();
            candidates.add(new Candidate(i + 1, name));
        }
    }

    private void displayCandidates() {
        System.out.println("Candidates:");
        for (Candidate candidate : candidates) {
            System.out.println(candidate.getId() + ". " + candidate.getName());
        }
    }

    private void castVotes() {
        Scanner scanner = new Scanner(System.in);
        int voterId = 1;

        while (true) {
            displayCandidates();
            System.out.print("Enter the ID of the candidate you want to vote for (or enter 0 to stop): ");
            String input = scanner.nextLine().trim();

            if (input.equals("0")) {
                break;
            }

            try {
                int candidateId = Integer.parseInt(input);
                if (candidateId < 1 || candidateId > candidates.size()) {
                    System.out.println("Invalid candidate ID. Please enter a valid ID.");
                    continue;
                }

                if (!voterIds.add(voterId)) {
                    System.out.println("You have already voted. Try again.");
                    continue;
                }

                Candidate selectedCandidate = candidates.get(candidateId - 1);
                votes.put(selectedCandidate.getId(), votes.getOrDefault(selectedCandidate.getId(), 0) + 1);
                System.out.println("Vote registered for " + selectedCandidate.getName());
                voterId++;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid candidate ID.");
            }
        }
    }

    private void tallyVotes() {
        for (Candidate candidate : candidates) {
            if (votes.containsKey(candidate.getId())) {
                candidate.addVote();
            }
        }
    }

    private void determineWinner() {
        int maxVotes = 0;
        List<Candidate> winners = new ArrayList<>();

        for (Candidate candidate : candidates) {
            if (votes.getOrDefault(candidate.getId(), 0) > maxVotes) {
                maxVotes = votes.get(candidate.getId());
                winners.clear();
                winners.add(candidate);
            } else if (votes.getOrDefault(candidate.getId(), 0) == maxVotes) {
                winners.add(candidate);
            }
        }

        System.out.println("\nWinners:");
        for (Candidate winner : winners) {
            System.out.println(winner);
        }
    }

    // Candidate class definition
    private static class Candidate {
        private int id;
        private String name;
        private int votes;

        public Candidate(int id, String name) {
            this.id = id;
            this.name = name;
            this.votes = 0;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public int getVotes() {
            return votes;
        }

        public void addVote() {
            this.votes++;
        }

        @Override
        public String toString() {
            return "ID: " + id + ", Name: " + name + ", Votes: " + votes;
        }
    }
}
