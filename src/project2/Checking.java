package project2;

public class Checking extends Account {
    private static final double INTEREST_RATE = 0.01; // 1.0% annual interest rate
    private static final double MONTHLY_FEE = 12.0;
    private static final double MIN_BALANCE_FEE_WAIVED = 1000;
    private static final int NUM_MONTHS = 12;

    @Override
    public double monthlyInterest() {
        return balance * (INTEREST_RATE / NUM_MONTHS);
    }

    @Override
    public double monthlyFee() {
        if (balance >= MIN_BALANCE_FEE_WAIVED) {
            return 0.0;
        }
        return MONTHLY_FEE;
    }


}
