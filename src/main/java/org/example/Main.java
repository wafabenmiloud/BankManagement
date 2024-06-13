package org.example;

import org.example.controllers.AdminController;
import org.example.controllers.ClientController;
import org.example.models.Client;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static ClientController clientController = new ClientController();
    private static AdminController adminController = new AdminController();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("1. Create Account");
            System.out.println("2. Manage Account");
            System.out.println("3. Deposit");
            System.out.println("4. Transfer Funds");
            System.out.println("5. Send Message to Customer Service");
            System.out.println("6. Admin Operations");
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
                case 4:
                    transferFunds();
                    break;
                case 5:
                    sendMessage();
                    break;
                case 6:
                    adminOperations();
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

        boolean success = clientController.createAccount(name, address, phoneNumber, password);
        if (success) {
            System.out.println("Account created successfully!");
        } else {
            System.out.println("Account creation failed. Phone number already exists.");
        }
    }

    private static void manageAccount() {
        System.out.print("Enter phone number: ");
        String phoneNumber = scanner.nextLine();

        Client user = clientController.getUser(phoneNumber);
        if (user == null) {
            System.out.println("User not found.");
            return;
        }

        System.out.println("1. Update Address and Phone Number");
        System.out.println("2. Change Password");
        System.out.println("3. View Transaction History");
        System.out.println("4. View User Profile");
        int option = scanner.nextInt();
        scanner.nextLine(); // consume newline

        switch (option) {
            case 1:
                System.out.print("Enter new address: ");
                String newAddress = scanner.nextLine();
                System.out.print("Enter new phone number: ");
                String newPhoneNumber = scanner.nextLine();
                boolean updateSuccess = clientController.updateUser(phoneNumber, newAddress, newPhoneNumber);
                if (updateSuccess) {
                    System.out.println("User information updated successfully.");
                } else {
                    System.out.println("Failed to update user information.");
                }
                break;
            case 2:
                System.out.print("Enter new password: ");
                String newPassword = scanner.nextLine();
                boolean passwordChangeSuccess = clientController.changePassword(phoneNumber, newPassword);
                if (passwordChangeSuccess) {
                    System.out.println("Password changed successfully.");
                } else {
                    System.out.println("Failed to change password.");
                }
                break;
            case 3:
                user = clientController.getUser(phoneNumber);
                List<String> transactionHistory = user.getTransactionHistory();
                if (transactionHistory.isEmpty()) {
                    System.out.println("No transactions found.");
                } else {
                    System.out.println("Transaction History:");
                    for (String transaction : transactionHistory) {
                        System.out.println(transaction);
                    }
                }
                break;
            case 4:
                user = clientController.getUser(phoneNumber);
                System.out.println("Name: " + user.getName());
                System.out.println("Address: " + user.getAddress());
                System.out.println("Phone number: " + user.getPhoneNumber());
                System.out.println("Balance: " + user.getBalance());
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

        boolean success = clientController.deposit(phoneNumber, amount);
        if (success) {
            System.out.println("Deposit successful!");
        } else {
            System.out.println("Deposit failed. Invalid phone number or amount.");
        }
    }

    private static void transferFunds() {
        System.out.print("Enter your phone number: ");
        String fromPhoneNumber = scanner.nextLine();
        System.out.print("Enter recipient's phone number: ");
        String toPhoneNumber = scanner.nextLine();
        System.out.print("Enter amount to transfer: ");
        double amount = scanner.nextDouble();
        scanner.nextLine(); // consume newline

        boolean success = clientController.transferFunds(fromPhoneNumber, toPhoneNumber, amount);
        if (success) {
            System.out.println("Transfer successful!");
        } else {
            System.out.println("Transfer failed. Check the phone numbers and balance.");
        }
    }

    private static void sendMessage() {
        System.out.print("Enter your phone number: ");
        String phoneNumber = scanner.nextLine();
        System.out.print("Enter your message: ");
        String messageContent = scanner.nextLine();

        boolean success = clientController.sendMessage(phoneNumber, messageContent);
        if (success) {
            System.out.println("Message sent successfully!");
        } else {
            System.out.println("Failed to send message.");
        }
    }

    private static void adminOperations() {
        System.out.println("1. View All Clients");
        System.out.println("2. Create Account");
        System.out.println("3. Deactivate Account");
        System.out.print("Choose an option: ");
        int option = scanner.nextInt();
        scanner.nextLine(); // consume newline

        switch (option) {
            case 1:
                viewAllClients();
                break;
            case 2:
                createAccountByAdmin();
                break;
            case 3:
                deactivateAccount();
                break;
            default:
                System.out.println("Invalid option. Please try again.");
        }
    }

    private static void viewAllClients() {
        List<Client> clients = adminController.getAllClients();
        if (clients.isEmpty()) {
            System.out.println("No clients found.");
        } else {
            System.out.println("List of Clients:");
            for (Client client : clients) {
                System.out.println("ID: " + client.getId());
                System.out.println("Name: " + client.getName());
                System.out.println("Address: " + client.getAddress());
                System.out.println("Phone number: " + client.getPhoneNumber());
                System.out.println("Balance: " + client.getBalance());
                System.out.println("Transaction History: " + client.getTransactionHistory());
                System.out.println("---------------------------");
            }
        }
    }

    private static void createAccountByAdmin() {
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter address: ");
        String address = scanner.nextLine();
        System.out.print("Enter phone number: ");
        String phoneNumber = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        Client newClient = new Client(name, address, phoneNumber, password);
        boolean success = adminController.createAccount(newClient);
        if (success) {
            System.out.println("Account created successfully!");
        } else {
            System.out.println("Account creation failed. Phone number already exists.");
        }
    }

    private static void deactivateAccount() {
        System.out.print("Enter phone number to deactivate account: ");
        String phoneNumber = scanner.nextLine();

        boolean success = adminController.deactivateAccount(phoneNumber);
        if (success) {
            System.out.println("Account deactivated successfully.");
        } else {
            System.out.println("Failed to deactivate account. User not found.");
        }
    }
}
