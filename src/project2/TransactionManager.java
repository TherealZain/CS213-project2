package project2;

import java.util.Calendar;
import java.util.Scanner;
import java.util.StringTokenizer;

public class TransactionManager {
    private static final int MIN_TOKENS_O_D_W = 5;
    private static final int MIN_TOKENS_C = 4;
    private static final String LOYAL = "1";
    private static final String NOT_LOYAL = "0";
    private static final double ZERO_BALANCE = 0;
    private static final double MIN_AGE_TO_TO_OPEN = 16;

    private static final double MAX_AGE_TO_OPEN_CC = 24;
    private boolean isRunning;
    private AccountDatabase accountDatabase;

    public TransactionManager() {
        this.accountDatabase = new AccountDatabase();
        isRunning = true;
        System.out.println("Transaction Manager is running.");
    }

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

    private void handlePCommand(StringTokenizer tokenizer) {
        if (!(accountDatabase.isEmpty())) {
            System.out.println();
            System.out.println("Accounts sorted by account type and profile.");
            accountDatabase.printSorted();
            System.out.println("*end of list.");
            System.out.println();
        } else System.out.println("Account Database is empty!");

    }

    private void handlePICommand(StringTokenizer tokenizer) {
        if (!(accountDatabase.isEmpty())) {
            System.out.println();
            System.out.println("*list of accounts with fee and monthly interest");
            accountDatabase.printFeesAndInterests();
            System.out.println("*end of list.");
            System.out.println();
        } else System.out.println("Account Database is empty!");
    }

    private void handleUBCommand(StringTokenizer tokenizer) {
        if (!(accountDatabase.isEmpty())) {
            System.out.println();
            System.out.println("*list of accounts with fees and interests applied.");
            accountDatabase.printUpdatedBalances();
            System.out.println("*end of list.");
            System.out.println();
        } else System.out.println("Account Database is empty!");
    }

    private Date parseDate(String dateOfBirth) {
        String[] dateComponents = dateOfBirth.split("/");
        if (dateComponents.length == 3) {
            int year = Integer.parseInt(dateComponents[2]);
            int month = Integer.parseInt(dateComponents[0]);
            int day = Integer.parseInt(dateComponents[1]);
            return new Date(year, month, day);
        }
        return null;

    }

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

    private boolean ageCheck(Date date, String accountType){
        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH);
        currentMonth++;
        int currentDay = calendar.get(Calendar.DATE);
        Date currentDate = new Date(currentYear, currentMonth, currentDay);
        int age = currentYear - date.getYear();

        if (date.compareTo(currentDate) < 0) {
            age--;
        }
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

    public static boolean isValidInitialDeposit(String initialDepositString) {
        double initialDeposit;
        try {
            initialDeposit = Double.parseDouble(initialDepositString);
        } catch (NumberFormatException e) {
            System.out.println("Not a valid amount.");
            return false;
        }
        if (initialDeposit <= ZERO_BALANCE) {
            System.out.println("Initial deposit cannot be 0 or negative.");
            return false;
        }
        return true;
    }

    public static boolean isValidAmount(String amountString, String operationType) {
        double amount;
        try {
            amount = Double.parseDouble(amountString);
        } catch (NumberFormatException e) {
            System.out.println("Not a valid amount.");
            return false;
        }

        if (amount <= ZERO_BALANCE) {
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

    public void openChecking(String fName, String lName, Date dob,
                             double initialDeposit) {
        Profile newProfile = new Profile(fName, lName, dob);
        Checking newChecking = new Checking(newProfile, initialDeposit);
        // check if CC or C in database
        if (accountDatabase.open(newChecking)) {
            System.out.println(fName + " " + lName + " " +
                    dob.dateString() + "(C) opened.");
        } else {
            System.out.println(fName + " " + lName + " " +
                    dob.dateString() + "(C) is already in the database.");
        }
    }

    public void openCollegeChecking(String fName, String lName, Date dob,
                                    double initialDeposit, StringTokenizer tokenizer) {
        Profile newProfile = new Profile(fName, lName, dob);
        String campusCode = tokenizer.nextToken();
        if (campusCode == null) {
            System.out.println("Missing data for opening an account.");
            return;
        }
        Campus campus = Campus.fromCode(campusCode);
        if (campus == null) {
            System.out.println("Invalid campus code.");
            return;
        }
        CollegeChecking newCollegeChecking = new CollegeChecking(newProfile, initialDeposit, campus);

        if (accountDatabase.open(newCollegeChecking)) {
            System.out.println(fName + " " + lName + " " +
                    dob.dateString() + "(CC) opened.");
        } else {
            System.out.println(fName + " " + lName + " " +
                    dob.dateString() + "(CC) is already in the database.");
        }
    }

    public void openSavings(String fName, String lName, Date dob,
                            double initialDeposit, StringTokenizer tokenizer) {
        Profile newProfile = new Profile(fName, lName, dob);
        Savings newSavings = new Savings(newProfile, initialDeposit);
        String loyalty = tokenizer.nextToken();
        if (loyalty == null) {
            System.out.println("Missing data for opening an account.");
            return;
        } else if (loyalty.equals(LOYAL)) {
            newSavings.setIsLoyal(true);
        } else if (loyalty.equals(NOT_LOYAL)) {
            newSavings.setIsLoyal(false);
        }
        if (accountDatabase.open(newSavings)) {
            System.out.println(fName + " " + lName + " " +
                    dob.dateString() + "(S) opened.");
        } else {
            System.out.println(fName + " " + lName + " " +
                    dob.dateString() + "(S) is already in the database.");
        }
    }

    public void openMoneyMarket(String fName, String lName, Date dob,
                                double initialDeposit) {
        if (initialDeposit < MoneyMarket.MIN_BALANCE_FEE_WAIVED) {
            System.out.println("Minimum of $2000 to open a Money Market account.");
            return;
        }
        Profile newProfile = new Profile(fName, lName, dob);
        MoneyMarket newMoneyMarket = new MoneyMarket(newProfile, initialDeposit, true);
        if (accountDatabase.open(newMoneyMarket)) {
            System.out.println(fName + " " + lName + " " +
                    dob.dateString() + "(MM) opened.");
        } else {
            System.out.println(fName + " " + lName + " " +
                    dob.dateString() + "(MM) is already in the database.");
        }
    }

    public void closeChecking(String fName, String lName, Date dob) {
        Profile profileToClose = new Profile(fName, lName, dob);
        Checking accountToClose = new Checking(profileToClose, ZERO_BALANCE);
        closeAccount(fName, lName, dob, accountToClose, "C");
    }

    public void closeCollegeChecking(String fName, String lName, Date dob) {
        Profile profileToClose = new Profile(fName, lName, dob);
        CollegeChecking accountToClose = new CollegeChecking(profileToClose, ZERO_BALANCE, null);
        closeAccount(fName, lName, dob, accountToClose, "CC");
    }

    public void closeSavings(String fName, String lName, Date dob) {
        Profile profileToClose = new Profile(fName, lName, dob);
        Savings accountToClose = new Savings(profileToClose, ZERO_BALANCE);
        closeAccount(fName, lName, dob, accountToClose, "S");
    }

    public void closeMoneyMarket(String fName, String lName, Date dob) {
        Profile profileToClose = new Profile(fName, lName, dob);
        MoneyMarket accountToClose = new MoneyMarket(profileToClose, ZERO_BALANCE, true);
        closeAccount(fName, lName, dob, accountToClose, "MM");
    }

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

    public void depositChecking(String fName, String lName, Date dob, double deposit) {
        Profile profileToDeposit = new Profile(fName, lName, dob);
        Checking accountToDeposit = new Checking(profileToDeposit, deposit);
        depositAccount(fName, lName, dob, accountToDeposit, "C");
    }

    public void depositCollegeChecking(String fName, String lName, Date dob, double deposit) {
        Profile profileToDeposit = new Profile(fName, lName, dob);
        CollegeChecking accountToDeposit = new CollegeChecking(profileToDeposit, deposit, null);
        depositAccount(fName, lName, dob, accountToDeposit, "CC");
    }

    public void depositSavings(String fName, String lName, Date dob, double deposit) {
        Profile profileToDeposit = new Profile(fName, lName, dob);
        Savings accountToDeposit = new Savings(profileToDeposit, deposit);
        depositAccount(fName, lName, dob, accountToDeposit, "S");
    }

    public void depositMoneyMarket(String fName, String lName, Date dob, double deposit) {
        Profile profileToDeposit = new Profile(fName, lName, dob);
        MoneyMarket accountToDeposit = new MoneyMarket(profileToDeposit, deposit, true);
        depositAccount(fName, lName, dob, accountToDeposit, "M");
    }

    public void depositAccount(String fName, String lName, Date dob,
                               Account account, String accountType) {
        accountDatabase.deposit(account);
        System.out.println(fName + " " + lName + dob.dateString() + "("
                + accountType + ") Deposit - balance updated.");
    }

    public void withdrawChecking(String fName, String lname, Date dob, double withdraw) {

    }

    public void withdrawCollegeChecking(String fName, String lname, Date dob, double withdraw) {

    }

    public void withdrawSavings(String fName, String lname, Date dob, double withdraw) {

    }

    public void withdrawMoneyMarket(String fName, String lname, Date dob, double withdraw) {

    }
}
