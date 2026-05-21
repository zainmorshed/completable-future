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
        //anyOF returns and Object: because CompletableFuture.anyOf() can take futures of completely different types (like a String and an Integer), Java automatically sets its generic return type to Object. 
        CompletableFuture<Void> raceFuture = CompletableFuture.anyOf(serverAFuture, serverBFuture)
            .thenAccept(winningPrice -> System.out.println("Winning price: " + winningPrice));
        //thenAccept returns void: since .thenAccept() consumes the data and returns nothing, your final variable type must be CompletableFuture<Void>

        raceFuture.join();
        serverAFuture.join(); //force main thread to wait for server A so we dont have a thread termination


        long endTime = System.currentTimeMillis();
        System.out.println("Total execution time: " + (endTime - startTime) + " ms");
    }
    
}
/*
Notes:
Initially we did not see "Server A finished!" printed in the console due to a race condition with the JVM shutting down. 
Because raceFuture.join() unblocks the main thread the exact millisecond Server B finishes Server B finishes (at ~600ms), the program immediately calculates endTime,
prints the total execution time, and exits. The JVM shuts down completely, cutting off Server A (which needed 1200ms) before it evers gets a chance to print to the console. 

How to prevent JVM from killing the background thread:
the JVM shuts down instantly because it reaches the end of the main() method. To stop this and let Server A finish printing, we have 3 different options
1. add a raw pause a the end - after raceFuture.join() add Thread.sleep(1000);
2. Join on Server A directly - serverAFuture.join(); - forces the program to stay alive until Server A finishes 
3. In prod environments (web servers) - In spring boot REST apis, you dont have to worry about this since a web server keeps the JVM running forver to lsiten for new user requests.
The server will naturally stay alive allowing slower background tasks to complete safelty without being cut off. 

*/