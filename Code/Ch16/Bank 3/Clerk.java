public class Clerk implements Runnable {
  // Constructor
  public Clerk(Bank theBank) {
    this.theBank = theBank;           // Who the clerk works for
    inTray = null;                    // No transaction initially
  }

  // Receive a transaction
  public void doTransaction(Transaction transaction) {
    inTray = transaction;  
  }

  // The working clerk...
  public void run() {
    while(true) {
      while(inTray == null) {         // No transaction waiting?
        try {
          Thread.sleep(150);          // Then take a break...

        } catch(InterruptedException e) {
          System.out.println(e);
        }
      }
 
      theBank.doTransaction(inTray);
      inTray = null;                  // In-tray is empty
    }
  }

  // Busy check
  public boolean isBusy() {
    return inTray != null;            // A full in-tray means busy!
  }

  private Bank theBank;               // The employer - an electronic marvel
  private Transaction inTray;         // The in-tray holding a transaction
}
