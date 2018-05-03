import java.util.Random;

public class BankOperation {
  public static void main(String[] args) {
    int initialBalance = 500;                          // The initial account balance
    int totalCredits = 0;                              // Total credits on the account
    int totalDebits =0;                                // Total debits on the account
    int transactionCount = 20;                         // Number of debits and credits

    // Create the account, the bank, and the clerks...
    Bank theBank = new Bank();                         // Create a bank
    Clerk clerk1 = new Clerk(theBank);                 // Create the first clerk
    Clerk clerk2 = new Clerk(theBank);                 // Create the second clerk
    Account account = new Account(1, initialBalance);  // Create an account

    // Create the threads for the clerks as daemon, and start them off
    Thread clerk1Thread = new Thread(clerk1);
    Thread clerk2Thread = new Thread(clerk2);
    clerk1Thread.setDaemon(true);                      // Set first as daemon
    clerk2Thread.setDaemon(true);                      // Set second as daemon
    clerk1Thread.start();                              // Start the first
    clerk2Thread.start();                              // Start the second

    // Generate transactions of each type and pass to the clerks
    Random rand = new Random();                        // Random number generator
    Transaction transaction;                           // Stores a transaction
    int amount = 0;                                    // stores an amount of money
    for(int i = 1; i <= transactionCount; i++) {
      amount = 50 + rand.nextInt(26);                  // Generate amount of $50 to $75
      transaction = new Transaction(account,           // Account
                                    Transaction.CREDIT,// Credit transaction
                                    amount);           //  of amount
      totalCredits += amount;                          // Keep total credit tally

      // Wait until the first clerk is free
      while(clerk1.isBusy()) {
        try {
          Thread.sleep(25);                            // Busy so try later

        } catch(InterruptedException e) {
          System.out.println(e);
        }
      }
      clerk1.doTransaction(transaction);             // Now do the credit

      amount = 30 + rand.nextInt(31);                // Generate amount of $30 to $60
      transaction = new Transaction(account,            // Account
                                    Transaction.DEBIT,  // Debit transaction
                                    amount);            //  of amount
      totalDebits += amount;                         // Keep total debit tally
      // Wait until the second clerk is free
      while(clerk2.isBusy()) {
        try {
          Thread.sleep(25);                            // Busy so try later

        } catch(InterruptedException e) {
          System.out.println(e);
        }
      }
      clerk2.doTransaction(transaction);             // Now do the debit
    }

    // Wait until both clerks are done
    while(clerk1.isBusy() || clerk2.isBusy()) {
      try {
        Thread.sleep(25);

      } catch(InterruptedException e) {
        System.out.println(e);
      }
    }

    // Now output the results
    System.out.println(
              "Original balance : $" + initialBalance+"\n" +
              "Total credits    : $" + totalCredits+"\n" +
              "Total debits     : $" + totalDebits+"\n" +
              "Final balance    : $" + account.getBalance() + "\n" +
              "Should be        : $" + (initialBalance + totalCredits - totalDebits));
  }
}
