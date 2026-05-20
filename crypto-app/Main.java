import java.util.concurrent.CompletableFuture;

public class Main {

        // Simulates Server A (Takes 1200ms)
    public static double fetchPriceFromServerA() {
        try { 
            Thread.sleep(1200); 
        } catch (InterruptedException e) {

        }
        System.out.println("[" + Thread.currentThread().getName() + "] Server A finished!");
        return 65000.00;
    }

    // Simulates Server B (Takes 600ms - Way faster!)
    public static double fetchPriceFromServerB() {
        try { 
            Thread.sleep(600); 
        } catch (InterruptedException e) {}
        System.out.println("[" + Thread.currentThread().getName() + "] Server B finished!");
        return 65005.00;
    }

    //goal: race the two servers against each other

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();

        // TODO: YOUR WORK GOES HERE
        CompletableFuture<Double> serverAFuture = CompletableFuture.supplyAsync(() -> fetchPriceFromServerA());
        CompletableFuture<Double> serverBFuture = CompletableFuture.supplyAsync(() -> fetchPriceFromServerB());

        

        long endTime = System.currentTimeMillis();
        System.out.println("Total execution time: " + (endTime - startTime) + " ms");
    }
    
}
