package project2;

import java.util.Calendar;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * Takes user commands relating to managing transactions for RU Bank
 * First checks if user command is valid
 * Contains separate functionality for commands starting with
 * O, C, D, WP, PI, UB, and Q
 * @author Nicholas Yim
 * @author Zain Zulfiqar
 */

public class TransactionManager {
    private static final int MIN_TOKENS_O_D_W = 5;
    private static final int MIN_TOKENS_C = 4;
    private static final int INITIAL_CAPACITY_CLOSED = 4;
    private static final String LOYAL = "1";
    private static final double ZERO_QUANTITY = 0;
    private static final double MIN_AGE_TO_TO_OPEN = 16;
    private static final double MAX_AGE_TO_OPEN_CC = 24;
    private boolean isRunning;
    private AccountDatabase accountDatabase;

    private Account[] closedAccounts;

    /**
     * Instantiates new account database for all open accounts and
     * a closed accounts array to keep track of closed accounts.
     * Sets isRunning to true to receive inputs.
     */
    public TransactionManager() {
        this.accountDatabase = new AccountDatabase();
        this.closedAccounts = new Account[INITIAL_CAPACITY_CLOSED];
        isRunning = true;
        System.out.println("Transaction Manager is running.");
    }

    /**
     * Initiates scanner and begins program
     * Continuously reads command lines from console until "Q" command is entered
     */
    public void run() {
        Scanner scanner = new Scanner(System.in);

        while (isRunning) {
            String command = scanner.nextLine();
            if (command.isEmpty()) {
                continue;
            }
            StringTokenizer tokenizer = new StringTokenizer(command);
            String firstToken = tokenizer.nextToken();
            switch (firstToken) {
                case "Q" -> isRunning = false;
                case "O" -> handleOCommand(tokenizer);
                case "C" -> handleCCommand(tokenizer);
                case "D" -> handleDCommand(tokenizer);
                case "W" -> handleWCommand(tokenizer);
                case "P" -> handlePCommand(tokenizer);
                case "PI" -> handlePICommand(tokenizer);
                case "UB" -> handleUBCommand(tokenizer);
                default -> System.out.println("Invalid command!");
            }
        }

        System.out.println("Transaction Manager is terminated.");
        scanner.close();
    }

    /**
     * Handles "O" command
     * @param tokenizer as StringTokenizer
     */
    private void handleOCommand(StringTokenizer tokenizer) {
        if (tokenizer.countTokens() < MIN_TOKENS_O_D_W) {
            System.out.println("Missing data for opening an account.");
            return;
        }
        String accountType = tokenizer.nextToken();
        String fName = tokenizer.nextToken();
        String lName = tokenizer.nextToken();
        Date dob = parseDate(tokenizer.nextToken());
        if (!isValidDOB(dob, accountType)) {
            return;
        }

        String initialDepositString = tokenizer.nextToken();
        double initialDeposit;
        if (isValidInitialDeposit(initialDepositString)) {
            initialDeposit = Double.parseDouble(initialDepositString);
        } else return;

        switch (accountType) {
            case "C" -> openChecking(fName, lName, dob, initialDeposit);
            case "CC" -> openCollegeChecking(fName, lName, dob,
                    initialDeposit, tokenizer);
            case "S" -> openSavings(fName, lName, dob,
                    initialDeposit, tokenizer);
            case "MM" -> openMoneyMarket(fName, lName, dob, initialDeposit);
        }
    }

    private void handleCCommand(StringTokenizer tokenizer) {
        if (tokenizer.countTokens() < MIN_TOKENS_C) {
            System.out.println("Missing data for closing an account.");
            return;
        }
        String accountType = tokenizer.nextToken();
        String fName = tokenizer.nextToken();
        String lName = tokenizer.nextToken();
        Date dob = parseDate(tokenizer.nextToken());

        if (futureDateCheck(dob)) {
            System.out.println("DOB invalid: " + dob.dateString()
                    + " cannot be today or a future day.");
            return;
        }

        switch (accountType) {
            case "C" -> closeChecking(fName, lName, dob);
            case "CC" -> closeCollegeChecking(fName, lName, dob);
            case "S" -> closeSavings(fName, lName, dob);
            case "MM" -> closeMoneyMarket(fName, lName, dob);
        }
    }

    private void handleDCommand(StringTokenizer tokenizer) {
        if (tokenizer.countTokens() < MIN_TOKENS_O_D_W) {
            System.out.println("Missing data for opening an account.");
            return;
        }
        String accountType = tokenizer.nextToken();
        String fName = tokenizer.nextToken();
        String lName = tokenizer.nextToken();
        Date dob = parseDate(tokenizer.nextToken());

        String depositString = tokenizer.nextToken();
        double deposit;
        if (isValidAmount(depositString, "Deposit")) {
            deposit = Double.parseDouble(depositString);
        } else return;

        switch (accountType) {
            case "C" -> depositChecking(fName, lName, dob, deposit);
            case "CC" -> depositCollegeChecking(fName, lName, dob, deposit);
            case "S" -> depositSavings(fName, lName, dob, deposit);
            case "MM" -> depositMoneyMarket(fName, lName, dob, deposit);
        }
    }

    private void handleWCommand(StringTokenizer tokenizer) {
        if (tokenizer.countTokens() < MIN_TOKENS_O_D_W) {
            System.out.println("Missing data for opening an account.");
            return;
        }
        String accountType = tokenizer.nextToken();
        String fName = tokenizer.nextToken();
        String lName = tokenizer.nextToken();
        Date dob = parseDate(tokenizer.nextToken());

        String withdrawString = tokenizer.nextToken();
        double withdraw;
        if (isValidAmount(withdrawString, "Withdraw")) {
            withdraw = Double.parseDouble(withdrawString);
        } else return;

        switch (accountType) {
            case "C" -> withdrawChecking(fName, lName, dob, withdraw);
            case "CC" -> withdrawCollegeChecking(fName, lName, dob, withdraw);
            case "S" -> withdrawSavings(fName, lName, dob, withdraw);
            case "MM" -> withdrawMoneyMarket(fName, lName, dob, withdraw);
        }

    }

    /**
     * Handles "P" command whether account database is empty or not.
     * Prints entire account database sorted by account type and profile
     * if account database is not empty.
     * Prints error message if account database is empty.
     * @param tokenizer as StringTokenizer
     */
    private void handlePCommand(StringTokenizer tokenizer) {
        if (!(accountDatabase.isEmpty())) {
            System.out.println();
            System.out.println("Accounts sorted by account type and profile.");
            accountDatabase.printSorted();
            System.out.println("*end of list.");
            System.out.println();
        } else System.out.println("Account Database is empty!");

    }

    /**
     * Handles "PI" command whether account database is empty or not.
     * Prints entire account database sorted as in "P" command, along with
     * fees and monthly interests if account database is not empty.
     * Prints error message if account database is empty.
     * @param tokenizer as StringTokenizer
     */
    private void handlePICommand(StringTokenizer tokenizer) {
        if (!(accountDatabase.isEmpty())) {
            System.out.println();
            System.out.println("*list of accounts with fee and monthly interest");
            accountDatabase.printFeesAndInterests();
            System.out.println("*end of list.");
            System.out.println();
        } else System.out.println("Account Database is empty!");
    }

    /**
     * Handles "UB" command whether account database is empty or not.
     * Updates the account balance for all accounts by applying fees and
     * interests earned and prints entire account database if account database
     * is not empty.
     * Prints error message if account database is empty.
     * @param tokenizer as StringTokenizer
     */
    private void handleUBCommand(StringTokenizer tokenizer) {
        if (!(accountDatabase.isEmpty())) {
            System.out.println();
            System.out.println("*list of accounts with fees and interests applied.");
            accountDatabase.printUpdatedBalances();
            System.out.println("*end of list.");
            System.out.println();
        } else System.out.println("Account Database is empty!");
    }

    /**
     * Parses date string from command line and converts to Date object
     *
     * @param dateString date of event as String
     * @return created date as Date object
     */
    private Date parseDate(String dateString) {
        String[] dateComponents = dateString.split("/");
        if (dateComponents.length == 3) {
            int year = Integer.parseInt(dateComponents[2]);
            int month = Integer.parseInt(dateComponents[0]);
            int day = Integer.parseInt(dateComponents[1]);
            return new Date(year, month, day);
        }
        return null;

    }

    /**
     * Checks if birthday user is inputting is in the future
     *
     * @param date of birthday
     * @return true if date is today or in the future, false otherwise
     */
    private boolean futureDateCheck(Date date) {
        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH);
        currentMonth++;
        int currentDay = calendar.get(Calendar.DATE);

        if (date.getYear() > currentYear) {
            return true;
        } else if (date.getYear() == currentYear) {
            if (date.getMonth() > currentMonth) {
                return true;
            } else if (date.getMonth() == currentMonth) {
                return date.getDay() >= currentDay;
            }
        }
        return false;
    }

    /**
     * Checks if user has valid date of birth (age) to open an account
     * User must be at least 16 years old to open any account and must be
     * under 24 years old to open a College Checking account
     * Prints error message if date of birth is invalid
     * @param date as Date
     * @param accountType as String
     * @return true if valid DOB, false if invalid DOB
     */
    private boolean ageCheck(Date date, String accountType){
        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH);
        currentMonth++;
        int currentDay = calendar.get(Calendar.DATE);
        Date currentDate = new Date(currentYear, currentMonth, currentDay);
        int age = calculateAge(currentDate, date);
        if (age < MIN_AGE_TO_TO_OPEN) {
            System.out.println("DOB invalid: " + date.dateString()
                    + " under 16.");
            return false;
        }
        if ("CC".equals(accountType) && age >= MAX_AGE_TO_OPEN_CC) {
            System.out.println("DOB invalid: " + date.dateString()
                    + " over 24.");
            return false;
        }
        return true;
    }

    /**
     * Calculates age of user using Calendar class
     * @param currentDate as Date
     * @param ageDate as Date
     * @return age as int
     */
    private int calculateAge(Date currentDate, Date ageDate) {
        int age = currentDate.getYear() - ageDate.getYear();

        if (currentDate.getMonth() < ageDate.getMonth() ||
                (currentDate.getMonth() == ageDate.getMonth()
                        && currentDate.getDay() < ageDate.getDay())) {
            age--;
        }
        return age;
    }

    /**
     * Checks if date of birth is valid or not
     * Three separate checks: if DOB is a valid calendar date, if it is
     * not today or a future day, and if age is valid
     * Prints corresponding error message for valid calendar date
     * and today/future day.
     * @param dob as Date
     * @param accountType as String
     * @return true if all checks pass, false if any of them fail
     */
    private boolean isValidDOB(Date dob, String accountType) {
        if (!(dob.isValid())) {
            System.out.println("DOB invalid: " + dob.dateString()
                    + " not a valid calendar date!");
            return false;
        }
        if (futureDateCheck(dob)) {
            System.out.println("DOB invalid: " + dob.dateString()
                    + " cannot be today or a future day.");
            return false;
        }
        if(!(ageCheck(dob, accountType))){
            return false;
        }
        return true;
    }

    /**
     * Checks if initial deposit is valid
     * Two separate checks: if deposit is a double and if deposit is
     * greater than 0
     * Prints corresponding error message if initial deposit is invalid
     * @param initialDepositString as String
     * @return true if all checks pass, false if any of them fail
     */
    public static boolean isValidInitialDeposit(String initialDepositString) {
        double initialDeposit;
        try {
            initialDeposit = Double.parseDouble(initialDepositString);
        } catch (NumberFormatException e) {
            System.out.println("Not a valid amount.");
            return false;
        }
        if (initialDeposit <= ZERO_QUANTITY) {
            System.out.println("Initial deposit cannot be 0 or negative.");
            return false;
        }
        return true;
    }

    /**
     * Checks if amount (withdraw or deposit) is valid
     * Two separate checks: if amount is a double and if amount is
     * greater than 0
     * Prints corresponding error message if amount is invalid
     * @param amountString as String
     * @param operationType as String ("Withdraw" or "Deposit")
     * @return true if all checks pass, false if any of them fail
     */
    public static boolean isValidAmount(String amountString, String operationType) {
        double amount;
        try {
            amount = Double.parseDouble(amountString);
        } catch (NumberFormatException e) {
            System.out.println("Not a valid amount.");
            return false;
        }

        if (amount <= ZERO_QUANTITY) {
            System.out.println(operationType + " - amount cannot be 0 or negative.");
            return false;
        }
        return true;
    }


    public static boolean isValidCampus(String campusCode) {
        try {
            Campus.valueOf(campusCode);
            return true;
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid campus code.");
            return false;
        }
    }

    /**
     * Opens Checking account by calling general open account method
     * @param fName as String
     * @param lName as String
     * @param dob as Date
     * @param initialDeposit as double
     */
    public void openChecking(String fName, String lName, Date dob,
                             double initialDeposit) {
        Checking newChecking = new Checking(new Profile(fName, lName, dob),
                initialDeposit);
        openAccount(fName, lName, dob, newChecking, "C");
    }

    /**
     * Opens College Checking account by calling general open account method
     * Checks if there is missing data and if the campus code is valid
     * Prints corresponding error message if any of the checks fail
     * @param fName as String
     * @param lName as String
     * @param dob as Date
     * @param initialDeposit as double
     */
    public void openCollegeChecking(String fName, String lName, Date dob,
                                    double initialDeposit, StringTokenizer
                                            tokenizer) {
        if (!tokenizer.hasMoreTokens()) {
            System.out.println("Missing data for opening an account.");
            return;
        }
        String campusCode = tokenizer.nextToken();
        Campus campus = Campus.fromCode(campusCode);
        if (campus == null) {
            System.out.println("Invalid campus code.");
            return;
        }
        CollegeChecking newCollegeChecking = new CollegeChecking(new
                Profile(fName, lName, dob), initialDeposit, campus);
        openAccount(fName, lName, dob, newCollegeChecking, "CC");
    }

    /**
     * Opens Savings account by calling general open account method
     * Checks if there is missing data
     * Prints error message if there is missing data
     * @param fName as String
     * @param lName as String
     * @param dob as Date
     * @param initialDeposit as double
     */
    public void openSavings(String fName, String lName, Date dob, double
            initialDeposit, StringTokenizer tokenizer) {
        if (!tokenizer.hasMoreTokens()) {
            System.out.println("Missing data for opening an account.");
            return;
        }
        Savings newSavings = new Savings(new Profile(fName, lName, dob),
                initialDeposit);
        String loyalty = tokenizer.nextToken();
        newSavings.setIsLoyal(loyalty.equals(LOYAL));
        openAccount(fName, lName, dob, newSavings, "S");
    }

    /**
     * Opens Money Market account by calling general open account method
     * Checks if initial deposit it at least 2000
     * Prints error message if initial deposit is less than 2000
     * @param fName as String
     * @param lName as String
     * @param dob as Date
     * @param initialDeposit as double
     */
    public void openMoneyMarket(String fName, String lName, Date dob,
                                double initialDeposit) {
        if (initialDeposit < MoneyMarket.MIN_BALANCE_FEE_WAIVED) {
            System.out.println("Minimum of $2000 to open a Money " +
                    "Market account.");
            return;
        }
        MoneyMarket newMoneyMarket = new MoneyMarket(new
                Profile(fName, lName, dob), initialDeposit, true);
        openAccount(fName, lName, dob, newMoneyMarket, "MM");
    }

    /**
     * General open account method to handle opening an account given
     * identifying parameters
     * Calls 'open' method and checks if account is already in the database
     * Prints message indicating if account is opened or if it is already
     * in the database
     * @param fName as String
     * @param lName as String
     * @param dob as Date
     * @param account as Account
     * @param accountType as String
     */
    public void openAccount(String fName, String lName, Date dob,
                            Account account, String accountType) {
        if (accountDatabase.open(account)) {
            System.out.println(fName + " " + lName + " " +
                    dob.dateString() + "(" + accountType + ") opened.");
        } else {
            System.out.println(fName + " " + lName + " " + dob.dateString()
                    + "(" + accountType + ") is already in the database.");
        }
    }

    /**
     * Closes Checking account by calling general close account method
     * @param fName as String
     * @param lName as String
     * @param dob as Date
     */
    public void closeChecking(String fName, String lName, Date dob) {
        Profile profileToClose = new Profile(fName, lName, dob);
        Checking accountToClose = new Checking(profileToClose, ZERO_QUANTITY);
        closeAccount(fName, lName, dob, accountToClose, "C");
    }

    /**
     * Closes College Checking account by calling general close account method
     * @param fName as String
     * @param lName as String
     * @param dob as Date
     */
    public void closeCollegeChecking(String fName, String lName, Date dob) {
        Profile profileToClose = new Profile(fName, lName, dob);
        CollegeChecking accountToClose = new CollegeChecking(profileToClose, ZERO_QUANTITY, null);
        closeAccount(fName, lName, dob, accountToClose, "CC");
    }

    /**
     * Closes Savings account by calling general close account method
     * @param fName as String
     * @param lName as String
     * @param dob as Date
     */
    public void closeSavings(String fName, String lName, Date dob) {
        Profile profileToClose = new Profile(fName, lName, dob);
        Savings accountToClose = new Savings(profileToClose, ZERO_QUANTITY);
        closeAccount(fName, lName, dob, accountToClose, "S");
    }

    /**
     * Closes Money Market account by calling general close account method
     * @param fName as String
     * @param lName as String
     * @param dob as Date
     */
    public void closeMoneyMarket(String fName, String lName, Date dob) {
        Profile profileToClose = new Profile(fName, lName, dob);
        MoneyMarket accountToClose = new MoneyMarket(profileToClose, ZERO_QUANTITY, true);
        closeAccount(fName, lName, dob, accountToClose, "MM");
    }

    /**
     * General close account method to handle closing an account given
     * identifying parameters
     * Calls 'close' method and checks if account is in database
     * Prints message indicating if account has been closed or if is
     * not in the database
     * @param fName as String
     * @param lName as String
     * @param dob as Date
     * @param account as Account
     * @param accountType as String
     */
    public void closeAccount(String fName, String lName, Date dob,
                             Account account, String accountType) {
        if (accountDatabase.close(account)) {
            System.out.println(fName + " " + lName + " " + dob.dateString()
                    + "(" + accountType + ") has been closed.");
        } else {
            System.out.println(fName + " " + lName + " " + dob.dateString()
                    + "(" + accountType + ") is not in the database.");
        }
    }

    /**
     * Deposits value into Checking account by calling general deposit
     * account method
     * @param fName as String
     * @param lName as String
     * @param dob as Date
     * @param deposit as double
     */
    public void depositChecking(String fName, String lName, Date dob, double deposit) {
        Profile profileToDeposit = new Profile(fName, lName, dob);
        Checking accountToDeposit = new Checking(profileToDeposit, deposit);
        depositAccount(fName, lName, dob, accountToDeposit, "C");
    }

    /**
     * Deposits value into College Checking account by calling general
     * deposit account method
     * @param fName as String
     * @param lName as String
     * @param dob as Date
     * @param deposit as double
     */
    public void depositCollegeChecking(String fName, String lName, Date dob, double deposit) {
        Profile profileToDeposit = new Profile(fName, lName, dob);
        CollegeChecking accountToDeposit = new CollegeChecking(profileToDeposit, deposit, null);
        depositAccount(fName, lName, dob , accountToDeposit,"CC");
    }


    /**
     * Deposits value into Savings account by calling general deposit
     * account method
     * @param fName as String
     * @param lName as String
     * @param dob as Date
     * @param deposit as double
     */
    public void depositSavings(String fName, String lName, Date dob, double deposit) {
        Profile profileToDeposit = new Profile(fName, lName, dob);
        Savings accountToDeposit = new Savings(profileToDeposit, deposit);
        depositAccount(fName, lName, dob, accountToDeposit, "S");
    }


    /**
     * Deposits value into Money Market account by calling general deposit
     * account method
     * @param fName as String
     * @param lName as String
     * @param dob as Date
     * @param deposit as double
     */
    public void depositMoneyMarket(String fName, String lName, Date dob, double deposit) {
        Profile profileToDeposit = new Profile(fName, lName, dob);
        MoneyMarket accountToDeposit = new MoneyMarket(profileToDeposit, deposit, true);
        depositAccount(fName, lName, dob, accountToDeposit, "MM");
    }

    /**
     * General deposit account to handle depositing value into an account
     * given identifying parameters
     * Checks if account is in account database and calls 'deposit' method
     * Prints message indicating if deposit is successful or if the account
     * is not in the database
     * @param fName as String
     * @param lName as String
     * @param dob as Date
     * @param account as Account
     * @param accountType as String
     */
    public void depositAccount(String fName, String lName, Date dob, Account
            account, String accountType) {
        if(accountDatabase.containsForTransactions(account)) {
            accountDatabase.deposit(account);
            System.out.println(fName + " " + lName + " " + dob.dateString() +
                    "(" + accountType + ") Deposit - balance updated.");
        } else
            System.out.println(fName + " " + lName + " " +
                    dob.dateString() + "(" + accountType + ") " +
                    "is not in the database.");

    }

    /**
     * Withdraws value from Checking account by calling general withdraw
     * account method
     * @param fName as String
     * @param lName as String
     * @param dob as Date
     * @param withdraw as double
     */
    public void withdrawChecking(String fName, String lName, Date dob, double withdraw) {
        Profile profileToWithdraw = new Profile(fName, lName, dob);
        Checking accountToWithdraw = new Checking(profileToWithdraw, withdraw);
        withdrawAccount(fName, lName, dob, accountToWithdraw, withdraw, "C");
    }

    /**
     * Withdraws value from College Checking account by calling general
     * withdraw account method
     * @param fName as String
     * @param lName as String
     * @param dob as Date
     * @param withdraw as double
     */
    public void withdrawCollegeChecking(String fName, String lName, Date dob, double withdraw) {
        Profile profileToWithdraw = new Profile(fName, lName, dob);
        CollegeChecking accountToWithdraw = new CollegeChecking(profileToWithdraw, withdraw, null);
        withdrawAccount(fName, lName, dob, accountToWithdraw, withdraw, "CC");
    }

    /**
     * Withdraws value from Savings account by calling general withdraw
     * account method
     * @param fName as String
     * @param lName as String
     * @param dob as Date
     * @param withdraw as double
     */
    public void withdrawSavings(String fName, String lName, Date dob, double withdraw) {
        Profile profileToWithdraw = new Profile(fName, lName, dob);
        Savings accountToWithdraw = new Savings(profileToWithdraw, withdraw);
        withdrawAccount(fName, lName, dob, accountToWithdraw, withdraw, "S");
    }

    /**
     * Withdraws value from Money Market account by calling general withdraw
     * account method
     * @param fName as String
     * @param lName as String
     * @param dob as Date
     * @param withdraw as double
     */
    public void withdrawMoneyMarket(String fName, String lName, Date dob, double withdraw) {
        Profile profileToWithdraw = new Profile(fName, lName, dob);
        MoneyMarket accountToWithdraw = new MoneyMarket(profileToWithdraw, withdraw, true);
        withdrawAccount(fName, lName, dob, accountToWithdraw, withdraw, "MM");
    }

    /**
     * General withdraw account method to handle withdrawing value from an
     * account given identifying parameters
     * Calls 'withdraw' method and performs two checks (insufficient fund
     * and not in database) then performs the withdrawal action if account and
     * withdraw value are valid
     * Prints message indicating to specific error or indicating if withdrawal
     * is successful
     * @param fName as String
     * @param lName as String
     * @param dob as Date
     * @param account as Account
     * @param withdraw as double
     * @param accountType as String
     */
    public void withdrawAccount(String fName, String lName, Date dob,
                                Account account, double withdraw, String accountType) {
        if (!accountDatabase.withdraw(account)) {
            if (withdraw > account.balance) {
                System.out.println(fName + " " + lName + " " + dob.dateString()
                        + "(" + accountType + ") " + "Withdraw - " +
                        "insufficient fund.");
            }
            else {
                System.out.println(fName + " " + lName + " " + dob.dateString()
                        + "(" + accountType + ") is not in the database.");
            }
            return;
        }
        System.out.println(fName + " " + lName + " " + dob.dateString() + "("
                + accountType + ") Withdraw - balance updated.");
    }
}
