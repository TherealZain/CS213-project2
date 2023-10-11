package project2;

import java.util.Scanner;
import java.util.StringTokenizer;

public class TransactionManager {

    private static final int MIN_TOKENS_O_GENERAL = 5;
    private static final int MIN_TOKENS_O_CC_S = 5;
    private static final String LOYAL = "1";
    private static final String NOT_LOYAL = "0";
    private static final double ZERO_BALANCE = 0.0;
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
        if (tokenizer.countTokens() < MIN_TOKENS_O_GENERAL) {
            System.out.println("Missing data for opening an account.");
            return;
        }
        String accountType = tokenizer.nextToken();
        String firstName = tokenizer.nextToken();
        String lastName = tokenizer.nextToken();
        String dateOfBirth = tokenizer.nextToken();
        Date dob = parseDate(dateOfBirth);
        String initialDepositString = tokenizer.nextToken();
        if (isValidInitialDeposit(initialDepositString)) {
            double initialDeposit = Double.parseDouble(initialDepositString);
        }
        switch (accountType) {
            case "C" -> {
                openChecking(firstName, lastName, dob, initialDeposit);
            }
            case "CC" -> {
                openCollegeChecking(firstName, lastName, dob, initialDeposit, tokenizer);
            }
            case "S" -> {
                openSavings(firstName, lastName, dob, initialDeposit, tokenizer);
            }
            case "MM" -> {
                openMoneyMarket(firstName, lastName, dob, initialDeposit);
            }
        }
    }

    private void handleCCommand(StringTokenizer tokenizer) {

    }

    private void handleDCommand(StringTokenizer tokenizer) {

    }

    private void handleWCommand(StringTokenizer tokenizer) {

    }

    private void handlePCommand(StringTokenizer tokenizer) {
        System.out.println("Accounts sorted by account type and profile.");
        accountDatabase.printSorted();
    }

    private void handlePICommand(StringTokenizer tokenizer) {

    }

    private void handleUBCommand(StringTokenizer tokenizer) {

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

    public static boolean isValidInitialDeposit(String initialDepositString) {
        double initialDeposit = ZERO_BALANCE;
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

    public static boolean isValidCampus(String campusCode) {
        try {
            Campus.valueOf(campusCode);
            return true;
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid campus code.");
            return false;
        }
    }

    public void openChecking(String firstName, String lastName, Date dob, double initialDeposit) {
        Profile newProfile = new Profile(firstName, lastName, dob);
        Checking newChecking = new Checking(newProfile, initialDeposit);
        accountDatabase.open(newChecking);
    }
    public void openCollegeChecking(String firstName, String lastName, Date dob, double initialDeposit, StringTokenizer tokenizer) {
        Profile newProfile = new Profile(firstName, lastName, dob);
        String campusCode = tokenizer.nextToken();
        if (campusCode == null) {
            System.out.println("Missing data for opening an account.");
        }
        Campus campus = Campus.valueOf(campusCode);
        CollegeChecking newCollegeChecking = new CollegeChecking(newProfile, initialDeposit, campus);
        accountDatabase.open(newCollegeChecking);
    }
    public void openSavings(String firstName, String lastName, Date dob, double initialDeposit, StringTokenizer tokenizer) {
        Profile newProfile = new Profile(firstName, lastName, dob);
        Savings newSavings = new Savings(newProfile, initialDeposit);
        String loyalty = tokenizer.nextToken();
        if (loyalty == null) {
            System.out.println("Missing data for opening an account.");
        }
        else if (loyalty.equals(LOYAL)) {
            newSavings.setIsLoyal(true);
        }
        else if (loyalty.equals(NOT_LOYAL)) {
            newSavings.setIsLoyal(false);
        }
        accountDatabase.open(newSavings);
    }
    public void openMoneyMarket(String firstName, String lastName, Date dob, double initialDeposit) {
        if (initialDeposit < MoneyMarket.MIN_BALANCE_FEE_WAIVED) {
            System.out.println("Minimum of $2000 to open a Money Market account.");
        }
        Profile newProfile = new Profile(firstName, lastName, dob);
        MoneyMarket newMoneyMarket = new MoneyMarket(newProfile, initialDeposit, true);
        accountDatabase.open(newMoneyMarket);
    }



}
