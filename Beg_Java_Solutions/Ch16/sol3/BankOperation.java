// Chapter 16 Exercise 3
import java.util.Random;

/*
 This exercise is a little tricky since we have supervisor threads to deal with
 as well as clerk threads. Clerks have names to identify who is doing what.
 Here there are sufficient names for the number of Clerk objects to be created
 but if you wanted to generalize the naming of clerks to allow for more clerks
 than names, you could reference elements in the clerkNames array using an 
 index modulo the size of the array.
 
 Once the two Supervisor objects have been created, they are each assigned their clerks
 by calling the addClerk() method for the supervisor for each clerk in their team.

 Each Supervisor object has the task of creating transactions and assigning 
 them to the clerks in the team. Once this work has been completed, nominally the
 supervisor is done, but we must make sure that each supervisor appears to be busy
 as long as any of their clerks are busy. This is accomplished through the busy
 member of each Supervisor object. Without this arrangement, the main thread could
 (and almost certainly would) complete execution before the clerks had processed the
 transactions and so the program would end without updating the accounts.
 
 */
 
public class BankOperation {
  // Bank Account information:
  static int[] initialBalance = {500, 800, 1500};  			      // The initial account balances.
  static int[] totalCredits = new int[initialBalance.length]; // Total credits.
  static int[] totalDebits = new int[initialBalance.length];  // Total debits.
  static int[] nCredits = new int[initialBalance.length]; 	  // Number of credits.
  static int[] nDebits = new int[initialBalance.length];  	  // Number of debits.
  static Account[] accounts = new Account[initialBalance.length];

  public static void main(String[] args) {
    String[] supervisorNames = {"Mr. Smith", "Mrs. Jones"};

    // Create the bank
    Bank theBank = new Bank();                                // Create a bank.

    // Create the accounts, and initialize total credits and debits:
    for(int i = 0; i < BankOperation.initialBalance.length; i++) {
      accounts[i] = new Account(i+1, BankOperation.initialBalance[i]); // Create accounts
      totalCredits[i] = BankOperation.totalDebits[i] = 0;
      nCredits[i] = nDebits[i] = 0;
    }

    // Create the supervisors:
    Supervisor[] supervisors = new Supervisor[supervisorNames.length];
    for(int i = 0; i < supervisors.length; i++) {
      supervisors[i] = new Supervisor(theBank, supervisorNames[i]);
    }
    
    // Create sufficient clerks for each supervisor to have a team
    // of four, and distribute the clerks between the supervisors
    String[] clerkNames = { "Allison", "Bob"      , "Claire", "Dave",
                            "Evadne" , "Ferdinand", "Gina"  , "Henry" }; 

    int pick = 0;       // Picks one or other supervisor
    for(String clerkName : clerkNames) {
     supervisors[pick++ % supervisors.length].addClerk(new Clerk(theBank, clerkName));
    }

    // Create the threads for the supervisors and start them off:
    Thread supervisorThread = null;
    for(Supervisor supervisor : supervisors) {
      supervisorThread = new Thread(supervisor);
      supervisorThread.setDaemon(true);
      supervisorThread.start();
    }

    // Sit here until the Supervisors say they're done:
    for(Supervisor supervisor : supervisors) {
      supervisor.isBusy();
    }

    // Now output the results:
    for(int i = 0; i < BankOperation.accounts.length; i++) {
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