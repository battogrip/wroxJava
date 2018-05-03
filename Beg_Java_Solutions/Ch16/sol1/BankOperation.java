// Chapter 16 Exercise 1
/* 
 This version generates either a debit or a credit transaction at random. The only
 changes are in this class. If you want to track the clerks in action you could
 put output statements in the run() method in the Clerk class that will indicate
 what the clerk is doing. You can output 'this' to identify each clerk.
*/
import java.util.Random;

public class BankOperation {
  public static void main(String[] args)   {
    int[] initialBalance = {500, 800};  	               // The initial account balances.
    int[] totalCredits = new int[initialBalance.length]; // Total credits.
    int[] totalDebits = new int[initialBalance.length];  // Total debits.
    int[] nCredits = new int[initialBalance.length]; 		 // Number of credits.
    int[] nDebits = new int[initialBalance.length];  		 // Number of debits.
    int transactionCount = 20;          	               // Number of debits and of credits.

    // Create the bank and the clerks:
    Bank theBank = new Bank();                 // Create a bank.
    Clerk clerk1 = new Clerk(theBank);         // Create the first clerk.
    Clerk clerk2 = new Clerk(theBank);         // Create the second clerk.

    // Create the accounts, and initialize total credits and debits:
    Account[] accounts = new Account[initialBalance.length];
    for(int i = 0; i < initialBalance.length; i++) {
      accounts[i] = new Account(i+1, initialBalance[i]); // Create accounts
      totalCredits[i] = totalDebits[i] = 0;
      nCredits[i] = nDebits[i] = 0;
    }

    // Create the threads for the clerks as daemon, and start them off:
    Thread clerk1Thread = new Thread(clerk1);
    Thread clerk2Thread = new Thread(clerk2);
    clerk1Thread.setDaemon(true);                    // Set first as daemon.
    clerk2Thread.setDaemon(true);                    // Set second as daemon.
    clerk1Thread.start();                            // Start the first.
    clerk2Thread.start();                            // Start the second.

    // Generate transactions of each type and pass to the clerks:
    Random rand = new Random();                      // Random number generator
    Transaction transaction;                         // Stores a transaction
    int amount;                                      // stores an amount of money
    int select;                                      // Selects an account
    for(int i = 1; i <= transactionCount; i++) {
      // Generate a credit or debit at random:
      // To get equally likely credit or debit we generate a random integer 
      // that can be 1 or 0. If it is 1 we create a credit and if it is 0 a debit.
      if(rand.nextInt(2)==1) {
        // Generate a random account index for credit operation:
        select = rand.nextInt(accounts.length);
        amount = 50 + rand.nextInt(26);                // Generate amount of $50 to $75
        transaction = new Transaction(accounts[select],       // Account
                                          Transaction.CREDIT, // Credit transaction
                                          amount);            //  of amount
        totalCredits[select] += amount;                // Keep total credit tally
        nCredits[select]++;

        clerk1.doTransaction(transaction);
      } else {
        // Generate a random account index for debit operation:
        select = rand.nextInt(accounts.length);
        amount = 30 + rand.nextInt(31);                	// Generate amount of $30 to $60.
        transaction = new Transaction(accounts[select],   // Account.
                                  Transaction.DEBIT,  	// Debit transaction of amount.
                                  amount);             
        totalDebits[select] += amount;                 	// Keep total debit tally.
        nDebits[select]++;

        clerk2.doTransaction(transaction);
      }
    }

    // Wait until both clerks are done:
    clerk1.isBusy();
    clerk2.isBusy();

    // Now output the results:
    for(int i = 0; i < accounts.length; i++)
      System.out.println("Account Number:"+accounts[i].getAccountNumber()+"\n"+
         "Number of credits   :  " + nCredits[i] + "\n" +
         "Number of debits    :  " + nDebits[i] + "\n" +
         "Original balance    : $" + initialBalance[i] + "\n" +
         "Total credits       : $" + totalCredits[i] + "\n" +
         "Total debits        : $" + totalDebits[i] + "\n" +
         "Final balance       : $" + accounts[i].getBalance() + "\n" +
         "Should be           : $" + (initialBalance[i] + totalCredits[i] -
                                                     totalDebits[i]) + "\n");
  }
}