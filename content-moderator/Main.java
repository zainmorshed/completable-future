import java.util.concurrent.CompletableFuture;


public class Main {
    
    // Task 1: Check text for spam (Simulates a fast 500ms database lookup)
    public static boolean checkSpam(String text) {
        try { 
            Thread.sleep(500); 
        } catch (InterruptedException e) {

        }
        System.out.println("[" + Thread.currentThread().getName() + "] Spam check complete.");
        return text.contains("buy now"); // Returns true if it contains spam keywords
    }

    // Task 2: Translate text (Simulates a slow 1500ms external API call)
    public static String translateText(String text) {
        try { 
            Thread.sleep(1500); 
        } catch (InterruptedException e) {

        }
        System.out.println("[" + Thread.currentThread().getName() + "] Translation complete.");
        return text.toUpperCase(); // Simulates translation by making it uppercase
    }

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        // String userPost = "Hello world, buy now!";
        String userPost = "Hello world, I love coding!";   

        
        // TODO: YOUR WORK GOES HERE
        CompletableFuture<Boolean> spamFuture = CompletableFuture.supplyAsync(() -> checkSpam(userPost));
        CompletableFuture<String> translateFuture = CompletableFuture.supplyAsync(() -> translateText(userPost));


        CompletableFuture<String> combinedFuture = spamFuture.thenCombine(translateFuture, (isSpam, text) -> {
            if (isSpam) {
                return "POST REJECTED: SPAM DETECTED";
            } else {
                return "POST APPROVED: " + text;
            }
        });

        String finalResult = combinedFuture.join();
        System.out.println("Moderator Verdict -> " + finalResult);

        long endTime = System.currentTimeMillis();
        System.out.println("Total execution time: " + (endTime - startTime) + " ms");
    }
    
}
