import java.util.concurrent.CompletableFuture;
import java.util.HashMap;
import java.util.Map;

public class Main {
    
    // Step 1A: Fetches how many shares the user owns (Fast 400ms DB query)
    public static Map<String, Integer> fetchWallet() {
        try { 
            Thread.sleep(400); 
        } catch (InterruptedException e) {

        }
        System.out.println("[" + Thread.currentThread().getName() + "] Fetched user wallet.");
        
        Map<String, Integer> wallet = new HashMap<>();
        wallet.put("AAPL", 10); // Owns 10 shares of Apple
        wallet.put("TSLA", 5);  // Owns 5 shares of Tesla
        return wallet;
    }

    // Step 1B: Fetches live market prices (Slow 1800ms External API call)
    public static Map<String, Double> fetchMarketPrices() {
        try { 
            Thread.sleep(1800); 
        } catch (InterruptedException e) {

        }
        System.out.println("[" + Thread.currentThread().getName() + "] Fetched market prices.");
        
        Map<String, Double> prices = new HashMap<>();
        prices.put("AAPL", 150.00); // Apple price
        prices.put("TSLA", 700.00); // Tesla price
        return prices;
    }

        // Step 3: Converts USD total to Euros (Simulates a fast 300ms calculation)
    public static double convertToEuros(double usdAmount) {
        try { Thread.sleep(300); } catch (InterruptedException e) {}
        System.out.println("[" + Thread.currentThread().getName() + "] Converted to EUR.");
        return usdAmount * 0.92; // Assuming exchange rate is 0.92
    }

    // Step 4: Sends an email report (Simulates a 1000ms network operation)
    // Note: It returns VOID because it just performs an action, it doesn't calculate data.
    public static void sendEmailReport(double euroAmount) {
        try { Thread.sleep(1000); } catch (InterruptedException e) {}
        System.out.println("[" + Thread.currentThread().getName() + "] Email report sent! Total Value: €" + euroAmount);
    }

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();

        // TODO: YOUR WORK GOES HERE

        CompletableFuture<Map<String, Integer>> walletFuture = CompletableFuture.supplyAsync(() -> fetchWallet());
        CompletableFuture<Map<String, Double>> priceFuture = CompletableFuture.supplyAsync(() -> fetchMarketPrices());

        CompletableFuture<Void> pipeline = walletFuture.thenCombine(priceFuture, (wallet, prices) -> {
            double totalValuation = 0.0;
            for (String stock : wallet.keySet()) {
                int shares = wallet.get(stock);
                double price = prices.get(stock);
                totalValuation+= (shares*price);
            }
            return totalValuation;
        })
        .thenApply(usdTotal -> convertToEuros(usdTotal))
        .thenAccept(euroTotal -> sendEmailReport(euroTotal));
        
        // Double totalPortfolioValuation = pipeline.join();
        // System.out.println("Total Portfolio Valuation: $" + totalPortfolioValuation);

        pipeline.join();

        long endTime = System.currentTimeMillis();
        System.out.println("Total execution time: " + (endTime - startTime) + " ms");
    }
    
}

// 💡 Why did this happen?
// In a fluent CompletableFuture chain, the very last method you call dictates the final type of the object.
// .thenCombine() outputs a Double
// .thenApply() outputs a Double (Euros)
// .thenAccept() consumes that data, prints it inside sendEmailReport, and outputs Void.
// Because sendEmailReport already handles printing out the final converted portfolio value to your console, you don't need to manually print it out in the main method anymore!

