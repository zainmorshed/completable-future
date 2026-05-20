import java.util.concurrent.CompletableFuture;

public class Main {

        // Step 1: Simulates taking the order
    public static String takeOrder(String foodItem) {
        try { 
            Thread.sleep(1000);
        } catch (InterruptedException e) {

            // if(foodItem.equalsIgnoreCase("Pizza")) {
            //     throw new RuntimeException("Sorry, we dont sell pizza!");
            }

        } // 1 sec
        System.out.println("[" + Thread.currentThread().getName() + "] Took order for: " + foodItem);
        return "Order_ID_1234:" + foodItem;
    }
        // Step 2: Simulates cooking the food (Takes the order string from Step 1)
    public static String cookFood(String orderDetails) {
        try { 
            Thread.sleep(2000);
        } catch (InterruptedException e) {

        } // 2 sec
        System.out.println("[" + Thread.currentThread().getName() + "] Cooked food for: " + orderDetails);
        return orderDetails + " (READY TO SERVE)";
    }

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();

        // TODO: YOUR TASKS GO HERE
        CompletableFuture<String> deliveryFuture = CompletableFuture.supplyAsync(() -> takeOrder("Burger"))
            .thenApply(orderDetails -> cookFood(orderDetails)); //'orderDetails' is passed from takeOrder
        
            //Why supplyAsync uses ()
            //The CompletableFuture.supplyAsync() method expects a functional interface called a Supplier
            //The rule of a supplier: IT takes zero inputs from the outside world and returns an output
            //The left side: BEcause it takes zero inputs from the tool, you must write empty parentheses ().

        deliveryFuture.join();  // Force the main thread to freeze and wait for the background worker to finish cooking!

        long endTime = System.currentTimeMillis();
        System.out.println("Total execution time: " + (endTime - startTime) + " ms");
    }

    
}

// 🕵️‍♂️ Why the join() is still mandatory here
// To visualize it, think of the main thread as a restaurant manager and the background thread pool as a chef in the kitchen:
// supplyAsync / .thenApply: The manager hands a ticket ("Make a Burger") to the chef in the kitchen.
// Without join(): The manager instantly closes the entire restaurant and goes home without waiting. The chef gets kicked out of the kitchen before they can finish cooking.
// With join(): The manager stands by the kitchen counter and waits until the chef finishes both taking the order and cooking the food before logging the final clock-out time.
