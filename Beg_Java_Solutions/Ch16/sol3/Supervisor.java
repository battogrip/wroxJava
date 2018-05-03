import java.util.Vector;
import java.util.Random;

public class Supervisor implements Runnable {
  // Constructor:
  public Supervisor(Bank theBank, String name) {
    this.theBank = theBank;
    this.name = name;
  }

  // Return the name of this supervisor
  public String getName() {
    return this.name;
  }

  // Add a clerk to this supervisor's team 
  public void addClerk(Clerk clerk) {
    clerkTeam.add(clerk.setSupervisor(this));
  }

  // The supervisor:
  synchronized public void run() {

    StringBuffer team = new StringBuffer();
    for(Clerk clerk : clerkTeam) {
      team.append(' ').append(clerk.getName());
    }
    System.out.println(name + " has started work!" 
                            + " My team is: " + team + "\n" );


    // Create the threads for the clerks as daemon, and start them off:
    Thread clerkThread = null;
    for(Clerk clerk : clerkTeam) {
      clerkThread = new Thread(clerk);
      clerkThread.setDaemon(true);
      clerkThread.start();
    }

    // Generate transactions of each type and pass to the clerks:
    Random rand = new Random();                      // Random number generator.
    Transaction transaction;                         // Stores a transaction.
    int amount = 0;                                  // Stores an amount of money
    int select = 0;                                  // Selects the account
    int transactionType = 0;                         // Credit or debit

    int transactionCount = 20;          // Number of transactions, debits or credits.

    for(int i = 1; i <= transactionCount; i++) {
      // Generate a credit or debit at random for a random account
      amount = 50 + rand.nextInt(26);                // Generate amount of $50 to $75.
      select = rand.nextInt(BankOperation.accounts.length);
      transactionType = rand.nextInt(2)==1 ? Transaction.CREDIT:     // Credit 
                                             Transaction.DEBIT;      // or Debit

      transaction = new Transaction(BankOperation.accounts[select], // Account.
                                    transactionType,                // Credit or debit
                                    amount);                        // of amount.
        
      switch(transactionType){
        case Transaction.CREDIT:
          BankOperation.totalCredits[select] += amount;        // Keep total credit tally.
          BankOperation.nCredits[select]++;
          break;
        case Transaction.DEBIT:
          BankOperation.totalDebits[select] += amount;         // Keep total debit tally.
          BankOperation.nDebits[select]++;
          break;
        default:
          assert false : "Invalid transaction type.";
      }

      // Pick a random clerk to take care of the transaction:
      assert clerkTeam.size() > 0 : "No clerks in the team!";
      clerkTeam.elementAt(rand.nextInt(clerkTeam.size())).doTransaction(transaction); 
    }


    // A supervisor must continue to work while any clerk in the team is working
    for(Clerk clerk : clerkTeam) {
      clerk.isBusy(); 
    }
 
    // All the clerks in the team are idle so we can make the supervisor idle
    busy = false;
    notifyAll();
    return;
  }

  // Supervisor busy check
  synchronized public void isBusy() {
    while(busy) {                         // While this supervisor is busy
      try {
            wait();                       // Wait for notify call.
      } catch(InterruptedException e) {
         System.out.println(e);
     }
  }
    return;
  }

  private Bank theBank;             // The bank we all work for
  private String name;              // This supervisor's name
  boolean busy = true;              // Supervisor is busy when created
  private Vector<Clerk> clerkTeam = new Vector<Clerk>();
}