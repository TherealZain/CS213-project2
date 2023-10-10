package project2;

public class MoneyMarket extends Savings{
    private int withdrawal; //number of withdrawals
    private static final double INTEREST_RATE = 0.0450;
    private static final double LOYAL_INTEREST_RATE = 0.0475;
    private static final double MIN_BALANCE_FEE_WAIVED = 2000;
    private static final double MONTHLY_FEE = 25.0;
    private static final double WITHDRAWALS_OVER_MIN_FEE = 10.0;
    private static final int MIN_WITHDRAWALS_ALLOWED = 3;
    private static final int NUM_MONTHS = 12;



    public MoneyMarket(Profile holder, double balance, boolean isLoyal) {
        super(holder, balance);
        isLoyal = true;
        this.isLoyal = isLoyal;

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
        if(withdrawal > MIN_WITHDRAWALS_ALLOWED){
            if(balance >= MIN_BALANCE_FEE_WAIVED){
                return WITHDRAWALS_OVER_MIN_FEE;
            }
            return MONTHLY_FEE + WITHDRAWALS_OVER_MIN_FEE;
        }
        if(balance >= MIN_BALANCE_FEE_WAIVED){
            return 0.0;
        }

        return MONTHLY_FEE;
    }

    public void incrementWithdrawals(){
        withdrawal++;
    }
}
