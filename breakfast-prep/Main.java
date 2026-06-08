import java.util.concurrent.CompletableFuture;

public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("[Main] Making breakfast...");

        // YOUR PARALLEL CODE WILL GO HERE
        CompletableFuture<String> toastFuture = CompletableFuture.supplyAsync(() -> toastBread());
        CompletableFuture<String> coffeeFuture = CompletableFuture.supplyAsync(() -> brewCoffee());
        CompletableFuture<String> eggFuture = CompletableFuture.supplyAsync(() -> fryEggs());

        toastFuture.thenCombine(coffeeFuture, (toast, coffee) -> toast + " + " + coffee)
            .thenCombine(eggFuture, (currentMeal, eggs) -> currentMeal + " + " + eggs)
            .thenAccept(fullMeal -> System.out.println("Full Breakfast: " + fullMeal));
        
        /*
        runAsync(): Executes a task that does not return a result. It accepts a Runnable and returns a CompletableFuture<Void>. 
        Use this for "fire-and-forget" tasks (e.g., logging, triggering an event).

        supplyAsync(): Executes a task that does return a result. It accepts a Supplier<T> and returns a CompletableFuture<T>. 
        Use this when you need to fetch data or perform a computation and use the resulting value later

        You now understand:
        - supplyAsync to launch background worker tasks.
        - thenApply to chain dependent tasks sequentially.
        - thenCombine to merge independent parallel tasks together.
        - thenAccept to clean up and finish the pipeline.

         */

        // Keep main thread alive for 2 seconds
        Thread.sleep(2000);
    }

    public static String toastBread() {
        System.out.println("Toast is ready!");
        return "Crispy Toast";
    }

    public static String brewCoffee() {
        System.out.println("Coffee is ready!");
        return "Hot Coffee";
    }

    public static String fryEggs() {
    System.out.println("Eggs are ready!");
    return "Sunny-side Up Eggs";
    }
}