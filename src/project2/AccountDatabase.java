package project2;

public class AccountDatabase {
    private static final int INCREMENT_AMOUNT = 4;
    private Account[] accounts; //list of various types of accounts
    private int numAcct; //number of accounts in the array
    private static final int NOT_FOUND = -1;
    private static final int ZERO = 0;
    private int find(Account account) {
        int index = NOT_FOUND;
        for (int i = 0; i < numAcct; i++) {
            if (accounts[i].equals(account)) {
                index = i;
            }
        }
        return index;
    } //search for an account in the array
    private void grow(){
        Account[] copy = new Account[numAcct + INCREMENT_AMOUNT];
        for(int i = 0; i < numAcct; i++){
            copy[i] = accounts[i];
        }
        accounts = copy;

    } //increase the capacity by 4
    public boolean contains(Account account){
        return find(account) != NOT_FOUND;
    }

    public boolean open(Account account){
       if(contains(account)){
           return false;
       }
        if (numAcct >= accounts.length) {
            grow();
        }
        accounts[numAcct] = account;
        numAcct++;
        return true;
    }
    public boolean close(Account account){
        return false;
    } //remove the given account
    //NEED TO CHECK TRANSACTION MANAGER IF ACCOUNT IS CONTAINED
    public boolean withdraw(Account account){
        return account.balance < ZERO;
    }

    public void deposit(Account account){}
    public void printSorted(){} //sort by account type and profile
    public void printFeesAndInterests(){} //calculate interests/fees
    public void printUpdatedBalances(){} //apply the interests/fees
}
