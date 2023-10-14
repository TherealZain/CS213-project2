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



    public abstract boolean equalsForTransactions(Object obj);
}

