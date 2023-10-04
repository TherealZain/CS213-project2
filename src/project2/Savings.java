package project2;

public class Savings extends Account{
    protected boolean isLoyal; //loyal customer status

    @Override
    public double monthlyInterest() {
        return 0;
    }

    @Override
    public double monthlyFee() {
        return 0;
    }

    @Override
    public int compareTo(Account o) {
        return 0;
    }
}
