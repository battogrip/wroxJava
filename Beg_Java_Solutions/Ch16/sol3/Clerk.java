import java.util.LinkedList;
import java.util.List;
import java.util.Collections;

public class Clerk implements Runnable {
  // Constructor:
  public Clerk(Bank theBank, String name) {
    this.theBank = theBank;                  // Who the clerk works for.
    this.name = name;
  }

  // getName method:
  public String getName() {
    return this.name;
  }

  public Clerk setSupervisor(Supervisor supervisor) {
    this.supervisor = supervisor;
    return this;
  }

  // Receive a transaction:
  synchronized public void doTransaction(Transaction transaction) {
    while(inTray.size() >= maxTransactions)
    try {
      wait();
    } catch(InterruptedException e) {
      System.out.println(e);
    }
    inTray.add(transaction);
    notifyAll();
  }

  // The working clerk:
  synchronized public void run() {
    while(true) {
      while(inTray.size() == 0) {     // No transaction waiting?
        System.out.println(name + " (supervised by " + supervisor.getName() 
                                                     + ") is idle.");
        try {
          wait();                   // Then take a break until there is.
        } catch(InterruptedException e) {
          System.out.println(e);
        }
      }
      System.out.println(name + " (supervised by " + supervisor.getName() 
                                                   + ") is working!");
      theBank.doTransaction(inTray.remove(0));
      notifyAll();                  // Notify other threads locked on this clerk.
    }
  }

  // Busy check:
  synchronized public void isBusy() {
    while(inTray.size() != 0)         // Is this object busy?
      try {
        wait();                       // Yes, so wait for notify call.
      } catch(InterruptedException e) {
        System.out.println(e);
      }
    return;                           // It is free now.
  }

  private Bank theBank;

  // The in-tray holding transactions
  private List<Transaction> inTray = Collections.synchronizedList(new LinkedList<Transaction>());

  private String name;
  private Supervisor supervisor;
  private int maxTransactions = 8;      // Maximum transactions in the in-tray.

}
