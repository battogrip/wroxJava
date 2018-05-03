// Chapter 16 Exercise 2
/*
 The clerks now have names so we can identify who is doing what.
*/
import java.util.Random;

public class BankOperation {
  public static void main(String[] args) {
    int[] initialBalance = {500, 800};  		              // The initial account balances
    int[] totalCredits = new int[initialBalance.length];  // Total credits.
    int[] totalDebits = new int[initialBalance.length];   // Total debits.
    int[] nCredits = new int[initialBalance.length]; 	    // Number of credits.
    int[] nDebits = new int[initialBalance.length];  	    // Number of debits.
    int transactionCount = 20;          		              // Number of debits and of credits.

    // The clerks now have names so we can identify who is doing what
    String[] clerkNames = {"Allison", "Bob", "Claire", "Dave"};

    // Create the bank:
    Bank theBank = new Bank();            

    // Create the clerks:
    Clerk[] clerks = new Clerk[clerkNames.length];
    for(int i = 0; i < clerks.length; i++) {
      clerks[i] = new Clerk(theBank, clerkNames[i]);
    }

    // Create the accounts, and initialize total credits and debits:
    Account[] accounts = new Account[initialBalance.length];
    for(int i = 0; i < initialBalance.length; i++) {
      accounts[i] = new Account(i+1, initialBalance[i]); 	// Create accounts.
      totalCredits[i] = totalDebits[i] = 0;
      nCredits[i] = nDebits[i] = 0;
    }

    // Create the threads for the clerks as daemon, and start them off:
    Thread[] clerkThread = new Thread[clerks.length];
    for(int i = 0; i < clerks.length; i++) {
      clerkThread[i] = new Thread(clerks[i]);
      clerkThread[i].setDaemon(true);
      clerkThread[i].start();
    }

    // Generate transactions of each type and pass to the clerks:
    Random rand = new Random();                      // Random number generator.
    Transaction transaction;                         // Stores a transaction.
    int amount;                                      // stores an amount of money.
    int select;                                      // Selects an account.
    for(int i = 1; i<=transactionCount; i++) {
      // Generate a credit or debit at random:
      if(rand.nextInt(2)==1) {
        // Generate a random account index for credit operation:
        select = rand.nextInt(accounts.length);
        amount = 50 + rand.nextInt(26);                // Generate amount of $50 to $75.
        transaction = new Transaction(accounts[select],       	// Account.
                                          Transaction.CREDIT, 	// Credit transaction
                                          amount);            	//  of amount
        totalCredits[select] += amount;                		// Keep total credit tally.
        nCredits[select]++;
      } else {
        // Generate a random account index for debit operation:
        select = rand.nextInt(accounts.length);
        amount = 30 + rand.nextInt(31);                   // Generate amount of $30 to $60.
        transaction = new Transaction(accounts[select],   // Account.
                                  Transaction.DEBIT,  		// Debit transaction
                                  amount);            		//  of amount.
        totalDebits[select] += amount;                 		// Keep total debit tally.
        nDebits[select]++;
      }

      // Pick a random clerk to take care of the transaction:
      clerks[rand.nextInt(clerks.length)].doTransaction(transaction); 	// Now do the transaction.
    }

    // Wait until all clerks are done:
    for(Clerk clerk : clerks) {
      clerk.isBusy();
    }

    // Now output the results:
    for(int i = 0; i < accounts.length; i++) {
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
}