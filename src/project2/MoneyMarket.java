package project2;
/**
 * Represents a Money Market account,
 * which is a type of Savings account.
 * This class extends the Savings class and
 * provides additional functionality
 * specific to Money Market accounts.
 *
 * @author Zain Zulfiqar, Nicholas Yim
 */
public class MoneyMarket extends Savings{

    private int withdrawal; //number of withdrawals
    private static final double INTEREST_RATE = 0.0450;
    private static final double LOYAL_INTEREST_RATE = 0.0475;
    public static final double MIN_BALANCE_FEE_WAIVED = 2000;
    private static final double MONTHLY_FEE = 25.0;
    private static final double WITHDRAWALS_OVER_MIN_FEE = 10.0;
    private static final int MIN_WITHDRAWALS_ALLOWED = 3;
    private static final int NUM_MONTHS = 12;
    private static final int RESET_WITHDRAWALS = 0;

    /**
     * Constructor for MoneyMarket account.
     * @param holder   The profile of the account holder
     * @param balance  The initial balance
     * @param isLoyal  Whether the account holder is loyal
     */
    public MoneyMarket(Profile holder, double balance, boolean isLoyal) {
        super(holder, balance);
        isLoyal = true;
        this.isLoyal = isLoyal;
    }

    /**
     * Returns a string representation of the MoneyMarket account.
     * @return The string representation
     */
    @Override
    public String toString() {
        return String.format("%s::%s::withdrawal: %d",
                "Money Market",
                super.toString(),
                withdrawal);
    }

    /**
     * Generates a formatted string representation of the Money Market account,
     * including monthly fees and interest.
     * @return A string containing details of the Money Market account.
     */
    public String stringWithFees(){
        String feeStr = String.format("$%.2f", monthlyFee());
        String interestStr = String.format("$%.2f", monthlyInterest());
        String balanceStr = String.format("$%,.2f", balance);
        String loyalty = isLoyal ? "::is loyal" : "";
        return String.format("Money Market::Savings::%s %s %s::Balance %s%s::withdrawal: %d::fee %s::monthly interest %s",
                holder.getFname(), holder.getLname(), holder.getDob().dateString(),
                balanceStr, loyalty, withdrawal, feeStr, interestStr);
    }

    /**
     * Sets the number of withdrawals for this account.
     * @param withdrawal The number of withdrawals to set
     */
    public void setWithdrawal(int withdrawal) {
        this.withdrawal = withdrawal;
    }

    /**
     * Checks if this MoneyMarket account is equal to another object.
     * @param obj The object to compare with
     * @return true if they are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj){
        if(this == obj) return true;
        if(obj == null || getClass() != obj.getClass()) return false;
        if(!super.equals(obj)) return false;
        MoneyMarket mmAccount = (MoneyMarket) obj;
        return withdrawal == mmAccount.withdrawal;
    }

    /**
     * Checks if two MoneyMarket accounts are equal based on exact class.
     * @param obj The object to compare with.
     * @return true if both accounts have the same transactions, false otherwise.
     */
    @Override
    public boolean equalsForTransactions(Object obj){
        return super.equalsForTransactions(obj);
    }

    /**
     * Compares this MoneyMarket account with another Account object.
     * @param o The Account object to compare with.
     * @return -1, 0, or 1 based on the comparison.
     */
    @Override
    public int compareTo(Account o) {
        int superComparison= super.compareTo(o);
        if(superComparison != 0){
            return superComparison;
        }
        MoneyMarket mmAccount = (MoneyMarket) o;
        if(this.withdrawal < mmAccount.withdrawal){
            return -1;
        }
        if(this.withdrawal > mmAccount.withdrawal){
            return 1;
        }
        return 0;
    }

    /**
     * Calculates the monthly interest for this MoneyMarket account.
     * @return The monthly interest amount.
     */
    @Override
    public double monthlyInterest() {
        if(isLoyal){
            return balance*(LOYAL_INTEREST_RATE/NUM_MONTHS);
        }
        return balance*(INTEREST_RATE/NUM_MONTHS);
    }

    /**
     * Calculates the monthly fee for this MoneyMarket account.
     * @return The monthly fee amount.
     */
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

    /**
     * Increments the withdrawal count for this MoneyMarket account.
     */
    public void incrementWithdrawals(){
        withdrawal++;
    }
}
