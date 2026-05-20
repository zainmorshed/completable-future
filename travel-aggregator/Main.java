import java.util.concurrent.CompletableFuture;

public class Main {

    public static double fetchFlightPrice() {
        try {
            Thread.sleep(2000);
        } catch(InterruptedException e) {}
        System.out.println("[" + Thread.currentThread().getName() + "] Fetched flight price");
        return 400.0;
    }

    public static double fetchHotelPrice() {
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {}
        System.out.println("[" + Thread.currentThread().getName() + "] Fetched hotel price");
        return 250.00;
    }

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();

        CompletableFuture<Double> flightFuture = CompletableFuture.supplyAsync(() -> fetchFlightPrice());
        CompletableFuture<Double> hotelFuture = CompletableFuture.supplyAsync(() -> fetchHotelPrice());

        CompletableFuture<Double> combinedFuture = flightFuture.thenCombine(hotelFuture, (flightPrice, hotelPrice) -> {
            double total = flightPrice + hotelPrice;
            System.out.println("Total package price: $" + total);
            return total;
        });

        combinedFuture.join(); 

        long endTime = System.currentTimeMillis();
        System.out.println("Total execution time: " + (endTime - startTime) + " ms");
    }
}