import java.util.concurrent.CompletableFuture;

public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("[Main] Starting portfolio valuation pipeline...");

        // YOUR PIPELINE CODE WILL GO HERE
        CompletableFuture<Integer> stockQtyFuture = CompletableFuture.supplyAsync(() -> fetchStockQuantity());
        CompletableFuture<Double> stockPriceFuture = CompletableFuture.supplyAsync(() -> fetchLivePrice())
            .exceptionally(ex -> 100.0);

        stockQtyFuture.thenCombine(stockPriceFuture, (qty, price) -> qty * price)
            .thenAccept(portfolioValue -> System.out.println(portfolioValue));


        Thread.sleep(2000);
    }

    // Task 1: Database fetch (Takes 1 second)
    public static int fetchStockQuantity() {
        try { Thread.sleep(1000); } catch (InterruptedException e) {}
        System.out.println("Database: Found 50 shares.");
        return 50;
    }

    // Task 2: Live Web API fetch (Takes 1 second)
    public static double fetchLivePrice() {
        try { Thread.sleep(1000); } catch (InterruptedException e) {}
        
        // Simulating a network failure!
        boolean networkCrash = false;
        if (networkCrash) {
            throw new RuntimeException("Web API Timeout Error!");
        }
        return 150.50;
    }
}