package org.example.services;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.example.config.MongoDBConnection;
import org.example.config.PasswordUtils;
import org.example.models.Client;

import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class ClientService {
    private MongoCollection<Document> userCollection;

    public ClientService() {
        MongoDatabase database = MongoDBConnection.getDatabase();
        userCollection = database.getCollection("users");
    }

    public boolean createAccount(String name, String address, String phoneNumber, String password) {
        if (userCollection.find(eq("phoneNumber", phoneNumber)).first() != null) {
            return false; // User already exists
        }

        String hashedPassword = PasswordUtils.hashPassword(password);
        Client newUser = new Client(name, address, phoneNumber, hashedPassword);

        Document newUserDoc = new Document("name", newUser.getName())
                .append("address", newUser.getAddress())
                .append("phoneNumber", newUser.getPhoneNumber())
                .append("hashedPassword", newUser.getHashedPassword())
                .append("balance", newUser.getBalance())
                .append("transactionHistory", newUser.getTransactionHistory());

        userCollection.insertOne(newUserDoc);
        return true;
    }

    public boolean updateUser(String phoneNumber, String newAddress, String newPhoneNumber) {
        Document userDoc = userCollection.find(eq("phoneNumber", phoneNumber)).first();
        if (userDoc == null) {
            return false; // User not found
        }

        userCollection.updateOne(eq("phoneNumber", phoneNumber),
                new Document("$set", new Document("address", newAddress).append("phoneNumber", newPhoneNumber)));
        return true;
    }

    public boolean changePassword(String phoneNumber, String newPassword) {
        Document userDoc = userCollection.find(eq("phoneNumber", phoneNumber)).first();
        if (userDoc == null) {
            return false; // User not found
        }

        String hashedPassword = PasswordUtils.hashPassword(newPassword);
        userCollection.updateOne(eq("phoneNumber", phoneNumber),
                new Document("$set", new Document("hashedPassword", hashedPassword)));
        return true;
    }

    public Client getUser(String phoneNumber) {
        Document userDoc = userCollection.find(eq("phoneNumber", phoneNumber)).first();
        if (userDoc == null) {
            return null;
        }

        Client user = new Client(
                userDoc.getString("name"),
                userDoc.getString("address"),
                userDoc.getString("phoneNumber"),
                userDoc.getString("hashedPassword")
        );
        user.setBalance(userDoc.getDouble("balance"));
        user.setTransactionHistory((List<String>) userDoc.get("transactionHistory"));

        return user;
    }

    public boolean deposit(String phoneNumber, double amount) {
        Document userDoc = userCollection.find(eq("phoneNumber", phoneNumber)).first();
        if (userDoc == null || amount <= 0) {
            return false; // User not found or invalid amount
        }

        double newBalance = userDoc.getDouble("balance") + amount;
        userCollection.updateOne(eq("phoneNumber", phoneNumber),
                new Document("$set", new Document("balance", newBalance))
                        .append("$push", new Document("transactionHistory", "Deposited: " + amount)));
        return true;
    }
}
