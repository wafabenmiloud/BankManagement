package org.example.controllers;

import java.util.List;

import org.example.models.Client;
import org.example.models.Message;
import org.example.services.ClientService;
import org.example.services.MessagingService;
import org.example.services.TransactionService;

public class ClientController {
    private ClientService clientService;
    private TransactionService transactionService;
    private MessagingService messagingService;

    public ClientController() {
        clientService = new ClientService();
        transactionService = new TransactionService();
        messagingService = new MessagingService();
    }

    public boolean createAccount(String name, String address, String phoneNumber, String email, String password) {
        return clientService.createAccount(name, address, phoneNumber, email, password);
    }

    public boolean updateUser(String phoneNumber, String newAddress, String newPhoneNumber) {
        return clientService.updateUser(phoneNumber, newAddress, newPhoneNumber);
    }

    public boolean changePassword(String phoneNumber, String newPassword) {
        return clientService.changePassword(phoneNumber, newPassword);
    }

    public Client getUser(String phoneNumber) {
        return clientService.getUser(phoneNumber);
    }

    public boolean deposit(String phoneNumber, double amount) {
        return clientService.deposit(phoneNumber, amount);
    }

    public boolean transfer(String fromPhoneNumber, String toPhoneNumber, double amount) {
        return transactionService.transfer(fromPhoneNumber, toPhoneNumber, amount);
    }

    public boolean sendMessage(String clientId, String messageContent) {
        return messagingService.sendMessage(clientId, messageContent);
    }

    public List<Message> getMessagesForClient(String clientId) {
        return messagingService.getMessagesForClient(clientId);
    }

    public boolean transferFunds(String fromPhoneNumber, String toPhoneNumber, double amount) {
        return clientService.transferFunds(fromPhoneNumber, toPhoneNumber, amount);
    }
}
