package org.example;

import org.example.controllers.AdminController;
import org.example.controllers.ClientController;
import org.example.models.Client;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {
    private static ClientController clientController = new ClientController();
    private static AdminController adminController = new AdminController();
    private static Scanner scanner = new Scanner(System.in);


    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public static void main(String[] args) {
        while (true) {
            printMenu();
            int option = getOptionFromUser();

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
                    System.out.println(ANSI_RED + "Invalid option. Please try again." + ANSI_RESET);
            }
        }
    }

    private static void printMenu() {
        System.out.println(ANSI_BLUE + "=========================================");
        System.out.println("            Banking Management           ");
        System.out.println("=========================================" + ANSI_RESET);
        System.out.println(ANSI_CYAN + "1. Create Account");
        System.out.println("2. Manage Account");
        System.out.println("3. Deposit");
        System.out.println("4. Transfer Funds");
        System.out.println("5. Send Message to Customer Service");
        System.out.println("6. Admin Operations");
        System.out.println(ANSI_BLUE + "=========================================" + ANSI_RESET);
        System.out.print(ANSI_YELLOW + "Choose an option: " + ANSI_RESET);
    }

    private static int getOptionFromUser() {
        int option = scanner.nextInt();
        scanner.nextLine();
        return option;
    }

    private static void createAccount() {
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter address: ");
        String address = scanner.nextLine();
        String phoneNumber = getPhoneNumberFromUser();
        String email = getEmailFromUser();
        String password = getPasswordFromUser();

        boolean success = clientController.createAccount(name, address, phoneNumber, email, password);
        if (success) {
            System.out.println(ANSI_GREEN + "Account created successfully!" + ANSI_RESET);
        } else {
            System.out.println(ANSI_RED + "Account creation failed. Phone number or email already exists." + ANSI_RESET);
        }
    }

    private static void manageAccount() {
        String phoneNumber = getPhoneNumberFromUser();

        Client user = clientController.getUser(phoneNumber);
        if (user == null) {
            System.out.println(ANSI_RED + "User not found." + ANSI_RESET);
            return;
        }

        System.out.println(ANSI_BLUE + "=========================================");
        System.out.println("           Manage Account Menu           ");
        System.out.println("=========================================" + ANSI_RESET);
        System.out.println(ANSI_CYAN + "1. Update Address and Phone Number");
        System.out.println("2. Change Password");
        System.out.println("3. View Transaction History");
        System.out.println("4. View User Profile");
        System.out.println(ANSI_BLUE + "=========================================" + ANSI_RESET);
        System.out.print(ANSI_YELLOW + "Choose an option: " + ANSI_RESET);

        int option = getOptionFromUser();

        switch (option) {
            case 1:
                System.out.print("Enter new address: ");
                String newAddress = scanner.nextLine();
                String newPhoneNumber = getPhoneNumberFromUser();
                boolean updateSuccess = clientController.updateUser(phoneNumber, newAddress, newPhoneNumber);
                if (updateSuccess) {
                    System.out.println(ANSI_GREEN + "User information updated successfully." + ANSI_RESET);
                } else {
                    System.out.println(ANSI_RED + "Failed to update user information." + ANSI_RESET);
                }
                break;
            case 2:
                String newPassword = getPasswordFromUser();
                boolean passwordChangeSuccess = clientController.changePassword(phoneNumber, newPassword);
                if (passwordChangeSuccess) {
                    System.out.println(ANSI_GREEN + "Password changed successfully." + ANSI_RESET);
                } else {
                    System.out.println(ANSI_RED + "Failed to change password." + ANSI_RESET);
                }
                break;
            case 3:
                user = clientController.getUser(phoneNumber);
                List<String> transactionHistory = user.getTransactionHistory();
                if (transactionHistory.isEmpty()) {
                    System.out.println(ANSI_YELLOW + "No transactions found." + ANSI_RESET);
                } else {
                    System.out.println(ANSI_GREEN + "Transaction History:" + ANSI_RESET);
                    for (String transaction : transactionHistory) {
                        System.out.println(transaction);
                    }
                }
                break;
            case 4:
                user = clientController.getUser(phoneNumber);
                System.out.println(ANSI_GREEN + "Name: " + user.getName());
                System.out.println("Address: " + user.getAddress());
                System.out.println("Phone number: " + user.getPhoneNumber());
                System.out.println("Email: " + user.getEmail());
                System.out.println("Balance: " + user.getBalance() + ANSI_RESET);
                break;
            default:
                System.out.println(ANSI_RED + "Invalid option." + ANSI_RESET);
        }
    }

    private static void deposit() {
        String phoneNumber = getPhoneNumberFromUser();
        double amount = getDoubleFromUser("Enter amount to deposit: ");

        boolean success = clientController.deposit(phoneNumber, amount);
        if (success) {
            System.out.println(ANSI_GREEN + "Deposit successful!" + ANSI_RESET);
        } else {
            System.out.println(ANSI_RED + "Deposit failed. Invalid phone number or amount." + ANSI_RESET);
        }
    }

    private static void transferFunds() {
        System.out.print("Enter your phone number: ");
        String fromPhoneNumber = scanner.nextLine();
        System.out.print("Enter recipient's phone number: ");
        String toPhoneNumber = scanner.nextLine();
        double amount = getDoubleFromUser("Enter amount to transfer: ");

        boolean success = clientController.transferFunds(fromPhoneNumber, toPhoneNumber, amount);
        if (success) {
            System.out.println(ANSI_GREEN + "Transfer successful!" + ANSI_RESET);
        } else {
            System.out.println(ANSI_RED + "Transfer failed. Check the phone numbers and balance." + ANSI_RESET);
        }
    }

    private static void sendMessage() {
        String phoneNumber = getPhoneNumberFromUser();
        System.out.print("Enter your message: ");
        String messageContent = scanner.nextLine();

        boolean success = clientController.sendMessage(phoneNumber, messageContent);
        if (success) {
            System.out.println(ANSI_GREEN + "Message sent successfully!" + ANSI_RESET);
        } else {
            System.out.println(ANSI_RED + "Failed to send message." + ANSI_RESET);
        }
    }

    private static void adminOperations() {
        System.out.println(ANSI_BLUE + "=========================================");
        System.out.println("           Admin Operations Menu         ");
        System.out.println("=========================================" + ANSI_RESET);
        System.out.println(ANSI_CYAN + "1. View All Clients");
        System.out.println("2. Create Account");
        System.out.println("3. Deactivate Account");
        System.out.println(ANSI_BLUE + "=========================================" + ANSI_RESET);
        System.out.print(ANSI_YELLOW + "Choose an option: " + ANSI_RESET);

        int option = getOptionFromUser();

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
                System.out.println(ANSI_RED + "Invalid option. Please try again." + ANSI_RESET);
        }
    }

    private static void viewAllClients() {
        List<Client> clients = adminController.getAllClients();
        if (clients.isEmpty()) {
            System.out.println(ANSI_YELLOW + "No clients found." + ANSI_RESET);
        } else {
            System.out.println(ANSI_GREEN + "List of Clients:" + ANSI_RESET);
            for (Client client : clients) {
                System.out.println("ID: " + client.getId());
                System.out.println("Name: " + client.getName());
                System.out.println("Address: " + client.getAddress());
                System.out.println("Phone number: " + client.getPhoneNumber());
                System.out.println("Email: " + client.getEmail());
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
        String phoneNumber = getPhoneNumberFromUser();
        String email = getEmailFromUser();
        String password = getPasswordFromUser();

        Client newClient = new Client(name, address, phoneNumber, email, password);
        boolean success = adminController.createAccount(newClient);
        if (success) {
            System.out.println(ANSI_GREEN + "Account created successfully!" + ANSI_RESET);
        } else {
            System.out.println(ANSI_RED + "Account creation failed. Phone number or email already exists." + ANSI_RESET);
        }
    }

    private static void deactivateAccount() {
        String phoneNumber = getPhoneNumberFromUser();

        boolean success = adminController.deactivateAccount(phoneNumber);
        if (success) {
            System.out.println(ANSI_GREEN + "Account deactivated successfully." + ANSI_RESET);
        } else {
            System.out.println(ANSI_RED + "Failed to deactivate account. User not found." + ANSI_RESET);
        }
    }

    private static String getPhoneNumberFromUser() {
        String phoneNumber;
        while (true) {
            System.out.print("Enter phone number: ");
            phoneNumber = scanner.nextLine();
            if (Pattern.matches("\\d{8}", phoneNumber)) {
                break;
            } else {
                System.out.println(ANSI_RED + "Invalid phone number. Please enter an 8-digit phone number." + ANSI_RESET);
            }
        }
        return phoneNumber;
    }

    private static String getEmailFromUser() {
        String email;
        while (true) {
            System.out.print("Enter email: ");
            email = scanner.nextLine();
            if (Pattern.matches("^[A-Za-z0-9+_.-]+@(.+)$", email)) {
                break;
            } else {
                System.out.println(ANSI_RED + "Invalid email. Please enter a valid email address." + ANSI_RESET);
            }
        }
        return email;
    }

    private static String getPasswordFromUser() {
        String password;
        while (true) {
            System.out.print("Enter password (at least 6 characters): ");
            password = scanner.nextLine();
            if (password.length() >= 6) {
                break;
            } else {
                System.out.println(ANSI_RED + "Invalid password. Password must be at least 6 characters long." + ANSI_RESET);
            }
        }
        return password;
    }

    private static double getDoubleFromUser(String prompt) {
        double value;
        while (true) {
            System.out.print(prompt);
            if (scanner.hasNextDouble()) {
                value = scanner.nextDouble();
                scanner.nextLine(); 
                break;
            } else {
                System.out.println(ANSI_RED + "Invalid input. Please enter a valid number." + ANSI_RESET);
                scanner.next(); 
            }
        }
        return value;
    }
}
