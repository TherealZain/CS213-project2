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

}
