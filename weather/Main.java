import java.util.concurrent.CompletableFuture;

public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("[Main] Fetching dashboard data...");

        // YOUR PARALLEL CODE WILL GO HERE

        CompletableFuture<Integer> tempFuture = CompletableFuture.supplyAsync(() -> fetchTemperature());
        CompletableFuture<String> windFuture = CompletableFuture.supplyAsync(() -> fetchWind());

        tempFuture.thenCombine(windFuture, (temp, wind) -> temp + " degrees F" + " with winds at " + wind)
            .thenAccept(weather -> System.out.println(weather));

        //when using the + operator with a string, java automatically calls .toString() on the Integer. 


        // Keep main thread alive for 2 seconds
        Thread.sleep(2000);
    }

    // Simulated API call 1 (Takes 1 second)
    public static int fetchTemperature() {
        try { Thread.sleep(1000); } catch (InterruptedException e) {}
        System.out.println("Temperature service finished.");
        return 72; // 72 degrees
    }

    // Simulated API call 2 (Takes 1 second)
    public static String fetchWind() {
        try { Thread.sleep(1000); } catch (InterruptedException e) {}
        System.out.println("Wind service finished.");
        return "10 mph NW";
    }
}