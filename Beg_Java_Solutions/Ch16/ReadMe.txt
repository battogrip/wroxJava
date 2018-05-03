SOLUTION 1. Modify the last example in the chapter so that each transaction is a debit or 
a credit at random. I added two variables (nCredits and nDebits) to track the total number 
of credits/debits created. I also commented out some of the System.out.println trace 
statements to make the output more readable and I reduced the wait() times in the Bank.java 
module from 150/100 ms to 15/10 ms respectively, just to speed things up.

SOLUTION 2. Modify the result of the previous exercise to incorporate an array of clerks, 
each running in their own thread, and each able to handle both debits and credits.
First, I added names to the clerks to help distinguish the threads. The name is another 
bit of member data of the Clerk object (this was a more open solution than simply
naming the threads themselves and using getName()). As far as the solution, I simply changed 
the hardcoded clerk1 and clerk2 variables to be an array of clerks with an array of threads. 
I then bumped it up to Four clerks, just to properly test the new code approach. Finally, 
I moved the clerk[i].doTransaction() call so that it is no longer dependent upon the
transaction type.

SOLUTION 3. Extend the result of the previous exercise to incorporate two supervisors for 
two teams of clerks, where the supervisors each run in their own thread. The supervisor 
threads should originate transactions and pass them to the clerks they supervise.

First, an assumption: each clerk belongs to one and only one supervisor. This exercise 
introduced a radical change in the BankOperation.java code. Up until now, the Bank was 
responsible for creating Clerks and generating transactions. Now, the Bank is responsible 
for creating Supervisors, and that's it. Once a supervisor has been created (in its own 
thread) then we can do some work. So all of the Clerk-creation and transaction-creation 
code that was previously the responsibility of the BankOperation object is now the 
responsibility of the Supervisor object. And whereas the Clerks have an inTray used to test 
isBusy, the Supervisor has no such object. So I added a boolean busy flag, as a private data 
member, which is set to true in the constructor and set to false when all of that particular's
clerks are no longer busy. That is, when the clerks are no longer busy, neither are the supers, 
and when the supers are no longer busy, we print out the end report, and exit the program.