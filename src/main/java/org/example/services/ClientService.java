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

    public boolean createAccount(String name, String address, String phoneNumber, String email, String password) {
        if (userCollection.find(eq("phoneNumber", phoneNumber)).first() != null ||
            userCollection.find(eq("email", email)).first() != null) {
            return false; // User already exists
        }

        String hashedPassword = PasswordUtils.hashPassword(password);
        Client newUser = new Client(name, address, phoneNumber, email, hashedPassword);

        Document newUserDoc = new Document("name", newUser.getName())
                .append("address", newUser.getAddress())
                .append("phoneNumber", newUser.getPhoneNumber())
                .append("email", newUser.getEmail())
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

    @SuppressWarnings("unchecked")
    public Client getUser(String phoneNumber) {
        Document userDoc = userCollection.find(eq("phoneNumber", phoneNumber)).first();
        if (userDoc == null) {
            return null;
        }

        Client user = new Client(
                userDoc.getString("name"),
                userDoc.getString("address"),
                userDoc.getString("phoneNumber"),
                userDoc.getString("email"),
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

    public boolean transferFunds(String fromPhoneNumber, String toPhoneNumber, double amount) {
        Document fromUserDoc = userCollection.find(eq("phoneNumber", fromPhoneNumber)).first();
        Document toUserDoc = userCollection.find(eq("phoneNumber", toPhoneNumber)).first();
        if (fromUserDoc == null || toUserDoc == null || amount <= 0) {
            return false; 
        }

        double fromUserBalance = fromUserDoc.getDouble("balance");
        if (fromUserBalance < amount) {
            return false; 
        }

        double toUserBalance = toUserDoc.getDouble("balance");

        userCollection.updateOne(eq("phoneNumber", fromPhoneNumber),
                new Document("$set", new Document("balance", fromUserBalance - amount))
                        .append("$push", new Document("transactionHistory", "Transferred: " + amount + " to " + toPhoneNumber)));

        userCollection.updateOne(eq("phoneNumber", toPhoneNumber),
                new Document("$set", new Document("balance", toUserBalance + amount))
                        .append("$push", new Document("transactionHistory", "Received: " + amount + " from " + fromPhoneNumber)));

        return true;
    }
}
