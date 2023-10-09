package project2;

public class Savings extends Account{
    protected boolean isLoyal; //loyal customer status
    private static final double INTEREST_RATE = 0.04; // 1.0% annual interest rate
    private static final double LOYAL_INTEREST_FEE = 0.0425;
    private static final double MONTHLY_FEE = 25.0;
    private static final double MIN_BALANCE_FEE_WAIVED = 500;
    private static final int NUM_MONTHS = 12;

    public Savings(Profile holder, double balance, boolean isLoyal) {
        super(holder, balance);
        this.isLoyal = isLoyal;
    }

    @Override
    public double monthlyInterest() {
        if(isLoyal){
            return balance*LOYAL_INTEREST_FEE;
        }
        return balance*(INTEREST_RATE/NUM_MONTHS);
    }

    @Override
    public double monthlyFee() {
        if(balance >= MIN_BALANCE_FEE_WAIVED){
            return 0.0;
        }
        return MONTHLY_FEE;
    }

    @Override
    public int compareTo(Account o) {
        return 0;
    }
}
