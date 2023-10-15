package project2;

public abstract class Account implements Comparable<Account> {
    protected Profile holder;
    protected double balance;
    public abstract double monthlyInterest();
    public abstract double monthlyFee();

    public Account(Profile holder, double balance) {
        this.holder = holder;
        this.balance = balance;
    }

    public abstract String stringWithFees();

    public boolean equalsForTransactions(Object obj){
        if (this == obj) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;

        Account otherAccount = (Account) obj;
        return this.holder.equals(otherAccount.holder) && this.balance == otherAccount.balance;
    }


}

