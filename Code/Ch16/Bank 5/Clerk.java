public class Clerk implements Runnable {
  // Constructor
  public Clerk(Bank theBank) {
    this.theBank = theBank;           // Who the clerk works for
    inTray = null;                    // No transaction initially
  }

  // Receive a transaction
  synchronized public void doTransaction(Transaction transaction) {
    while(inTray != null) {
      try {
        wait();

      } catch(InterruptedException e) {
        System.out.println(e);
      }
    }
    inTray = transaction;
    notifyAll();
  }

  // The working clerk...
  synchronized public void run() {
    while(true) {
      while(inTray == null) {         // No transaction waiting?
        try {
          wait();                     // Then take a break until there is
        } catch(InterruptedException e) {
          System.out.println(e);
        }
      }
      theBank.doTransaction(inTray);
      inTray = null;                 // In-tray is empty
      notifyAll();                   // Notify other threads locked on this clerk
    }
  }

  // Busy check
  synchronized public void isBusy() {
    while(inTray != null) {           // Is this object busy?
      try {
        wait();                       // Yes, so wait for notify call
      } catch(InterruptedException e) {
        System.out.println(e);
      }
      return;                         // It is free now
    }
  }

  private Bank theBank;               // The employer - an electronic marvel
  private Transaction inTray;         // The in-tray holding a transaction
}
