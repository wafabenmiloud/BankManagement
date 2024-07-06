package org.example.controllers;

import org.example.models.Client;
import org.example.services.AdminService;

import java.util.List;

public class AdminController {
    private AdminService adminService;

    public AdminController() {
        adminService = new AdminService();
    }

    public List<Client> getAllClients() {
        return adminService.getAllClients();
    }

    public boolean createAccount(Client client) {
        return adminService.createAccount(client);
    }

    public boolean deactivateAccount(String phoneNumber) {
        return adminService.deactivateAccount(phoneNumber);
    }

    public boolean updateClientInfo(Client client) {
        return adminService.updateClientInfo(client);
    }
}
