import java.io.IOException;

public class TryThread2 extends Thread {
  public TryThread2(String firstName, String secondName, long delay) {
    this.firstName = firstName;      // Store the first name
    this.secondName = secondName;    // Store the second name
    aWhile = delay;                  // Store the delay
//    setDaemon(true);                 // Thread is daemon
  }

  public static void main(String[] args) {
    // Create three threads
    Thread first = new TryThread("Hopalong ", "Cassidy ", 200L);
    Thread second = new TryThread("Marilyn ", "Monroe ", 300L);
    Thread third = new TryThread("Slim ", "Pickens ", 500L);

    System.out.println("Press Enter when you have had enough...\n");
    first.start();                      // Start the first thread
    second.start();                     // Start the second thread
    third.start();                      // Start the third thread

    try {
      System.in.read();                 // Wait until Enter key pressed
      System.out.println("Enter pressed...\n");

      // Interrupt the threads
      first.interrupt();
      second.interrupt();
      third.interrupt();

    } catch (IOException e) {           // Handle IO exception
      System.out.println(e);            // Output the exception
    }
    System.out.println("Ending main()");
    return;
  }

  // Method where thread execution will start
  public void run() {
    try {
      while(true) {                          // Loop indefinitely...
        System.out.print(firstName);         // Output first name
        sleep(aWhile);                       // Wait aWhile msec.
        System.out.print(secondName + "\n"); // Output second name
      }
    } catch(InterruptedException e) {        // Handle thread interruption
      System.out.println(firstName + secondName + e);     // Output the exception
    }
  }

  private String firstName;          // Store for first name
  private String secondName;         // Store for second name
  private long aWhile;               // Delay in milliseconds
}
