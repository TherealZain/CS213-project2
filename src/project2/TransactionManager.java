package project2;

import java.util.Scanner;
import java.util.StringTokenizer;

public class TransactionManager {
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
        if (tokenizer.countTokens() < 6) {
            System.out.println("Missing data for opening an account.");
            return;
        }

        String accountType = tokenizer.nextToken();
        String firstName = tokenizer.nextToken();
        String lastName = tokenizer.nextToken();
        String dateOfBirth = tokenizer.nextToken();
        Date dob = parseDate(dateOfBirth);
        double initialDeposit = Double.parseDouble(tokenizer.nextToken());

        switch (accountType) {
            case "C" -> {
                Profile newProfile = new Profile(firstName, lastName, dob);
                Checking newChecking = new Checking(newProfile, initialDeposit);
                accountDatabase.open(newChecking);
            }
            case "CC" -> {
                Profile newProfile = new Profile(firstName, lastName, dob);
                String campusCode = tokenizer.nextToken();
                Campus campus = Campus.valueOf(campusCode);
                CollegeChecking newCollegeChecking = new CollegeChecking(newProfile, initialDeposit, campus);
                accountDatabase.open(newCollegeChecking);
            }
            case "S" -> {
                Profile newProfile = new Profile(firstName, lastName, dob);
                Savings newSavings = new Savings(newProfile, initialDeposit);
                String loyalty = tokenizer.nextToken();
                if (loyalty.equals("1")) {
                    newSavings.setIsLoyal(true);
                }
                else if (loyalty.equals("0")) {
                    newSavings.setIsLoyal(false);
                }
                accountDatabase.open(newSavings);

            }
            case "MM" -> {
                Profile newProfile = new Profile(firstName, lastName, dob);
                // valid check >= 2000
                MoneyMarket newMoneyMarket = new MoneyMarket(newProfile, initialDeposit, true);
                accountDatabase.open(newMoneyMarket);
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

    public static boolean isValidCampus(String campusCode) {
        try {
            Campus.valueOf(campusCode);
            return true;
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid campus code.");
            return false;
        }
    }

    public static boolean isValidMoneyMarket(String ) {

    }



}
