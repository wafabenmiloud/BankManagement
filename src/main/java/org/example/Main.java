package org.example;

import java.util.Scanner;

import org.example.models.Client;
import org.example.services.ClientService;

public class Main {
    private static ClientService userService = new ClientService();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("1. Create Account");
            System.out.println("2. Manage Account");
            System.out.println("3. Deposit");
            System.out.print("Choose an option: ");
            int option = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (option) {
                case 1:
                    createAccount();
                    break;
                case 2:
                    manageAccount();
                    break;
                case 3:
                    deposit();
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void createAccount() {
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter address: ");
        String address = scanner.nextLine();
        System.out.print("Enter phone number: ");
        String phoneNumber = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        boolean success = userService.createAccount(name, address, phoneNumber, password);
        if (success) {
            System.out.println("Account created successfully!");
        } else {
            System.out.println("Account creation failed. Phone number already exists.");
        }
    }

    private static void manageAccount() {
        System.out.print("Enter phone number: ");
        String phoneNumber = scanner.nextLine();

        Client user = userService.getUser(phoneNumber);
        if (user == null) {
            System.out.println("User not found.");
            return;
        }

        System.out.println("1. Update Address and Phone Number");
        System.out.println("2. Change Password");
        System.out.println("3. View Transaction History");
        int option = scanner.nextInt();
        scanner.nextLine(); // consume newline

        switch (option) {
            case 1:
                System.out.print("Enter new address: ");
                String newAddress = scanner.nextLine();
                System.out.print("Enter new phone number: ");
                String newPhoneNumber = scanner.nextLine();
                boolean updateSuccess = userService.updateUser(phoneNumber, newAddress, newPhoneNumber);
                if (updateSuccess) {
                    System.out.println("User information updated successfully.");
                } else {
                    System.out.println("Failed to update user information.");
                }
                break;
            case 2:
                System.out.print("Enter new password: ");
                String newPassword = scanner.nextLine();
                boolean passwordChangeSuccess = userService.changePassword(phoneNumber, newPassword);
                if (passwordChangeSuccess) {
                    System.out.println("Password changed successfully.");
                } else {
                    System.out.println("Failed to change password.");
                }
                break;
            case 3:
                user = userService.getUser(phoneNumber);
                System.out.println("Transaction History: " + user.getTransactionHistory());
                break;
            default:
                System.out.println("Invalid option.");
        }
    }

    private static void deposit() {
        System.out.print("Enter phone number: ");
        String phoneNumber = scanner.nextLine();
        System.out.print("Enter amount to deposit: ");
        double amount = scanner.nextDouble();
        scanner.nextLine(); // consume newline

        boolean success = userService.deposit(phoneNumber, amount);
        if (success) {
            System.out.println("Deposit successful!");
        } else {
            System.out.println("Deposit failed. Invalid phone number or amount.");
        }
    }
}
