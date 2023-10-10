package project2;

import java.util.Scanner;
import java.util.StringTokenizer;

public class TransactionManager {
    private boolean isRunning;

    public TransactionManager() {
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
            }
        }

        System.out.println("Transaction Manager is terminated.");
        scanner.close();
    }
}
