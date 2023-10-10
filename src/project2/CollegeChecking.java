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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        if (!super.equals(obj)) return false;
        CollegeChecking that = (CollegeChecking) obj;
        return that.campus == campus;
    }

    @Override
    public int compareTo(Account o){
        int superComparison = super.compareTo(o);
        if(superComparison != 0){
            return superComparison;
        }
        return this.campus.compareTo(((CollegeChecking) o).campus);
    }

}
