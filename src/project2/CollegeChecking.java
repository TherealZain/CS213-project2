package project2;

public class CollegeChecking extends Checking{
    private Campus campus; //campus code
    private static final double INTEREST_RATE = 0.01; // 1.0% annual interest rate

    public CollegeChecking(Profile holder, double balance,  Campus campus) {
        super(holder, balance);
        this.campus = campus;
    }

    @Override
    public double monthlyFee() {
        return 0.0; // No monthly fee for College Checking
    }

}
