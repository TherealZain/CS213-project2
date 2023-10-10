package project2;

public class Savings extends Account{
    protected boolean isLoyal; //loyal customer status
    private static final double INTEREST_RATE = 0.04; // 1.0% annual interest rate
    private static final double LOYAL_INTEREST_RATE = 0.0425;
    private static final double MONTHLY_FEE = 25.0;
    private static final double MIN_BALANCE_FEE_WAIVED = 500;
    private static final int NUM_MONTHS = 12;

    public Savings(Profile holder, double balance) {
        super(holder, balance);

    }

    @Override
    public boolean equals(Object obj){
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Savings savings = (Savings) obj;
        return savings.balance == balance || savings.holder.equals(holder) || savings.isLoyal == isLoyal;
    }

    @Override
    public int compareTo(Account o) {
        int typeComparison =this.getClass().getSimpleName()
                .compareTo(o.getClass().getSimpleName());
        if(typeComparison > 0){
            return 1;
        }
        if(typeComparison < 0){
            return -1;
        }
        if(this.holder.compareTo(o.holder) > 0){
            return 1;
        }
        if(this.holder.compareTo(o.holder) < 0){
            return -1;
        }
        if(this.balance < o.balance) {
            return -1;
        }
        if (this.balance > o.balance) {
            return 1;
        }

        Savings savings = (Savings) o;

        return Boolean.compare(this.isLoyal, savings.isLoyal);
    }

    @Override
    public double monthlyInterest() {
        if(isLoyal){
            return balance*(LOYAL_INTEREST_RATE/NUM_MONTHS);
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


}
