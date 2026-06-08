import java.nio.channels.CompletionHandler;
import java.util.concurrent.CompletableFuture;

public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("[Main] Fetching user account details...");

        // YOUR ADVANCED CODE WILL GO HERE

        CompletableFuture<String> profileFuture = CompletableFuture.supplyAsync(() -> fetchProfile());
        CompletableFuture<Integer> scoreFuture = CompletableFuture.supplyAsync(() -> fetchCreditScore())
            .exceptionally(ex -> 0);

        profileFuture.thenCombine(scoreFuture, (profile, score) -> {
            if (score >= 600) {
                return profile + " Status: Approved";
            } else {
                return profile + " Status: High Risk (Fallback Applied";
            }
        })
        .thenAccept(summary -> System.out.println(summary));

            //if credit service crashes, catch the error and return 0 as the fallback score




        Thread.sleep(2000);
    }

    // Task 1: Always succeeds (Takes 1 second)
    public static String fetchProfile() {
        try { Thread.sleep(1000); } catch (InterruptedException e) {}
        return "Alice Smith";
    }

    // Task 2: Simulating an unreliable service (Takes 1 second)
    public static int fetchCreditScore() {
        try { Thread.sleep(1000); } catch (InterruptedException e) {}
        
        // Simulating a crash! Change this to 'false' later to test successful runs.
        boolean shouldCrash = true; 
        if (shouldCrash) {
            throw new RuntimeException("Credit Bureau Service is Down!");
        }
        return 720; 
    }
}