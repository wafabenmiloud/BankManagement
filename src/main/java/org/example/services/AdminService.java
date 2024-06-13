package org.example.services;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.example.config.MongoDBConnection;
import org.example.models.Client;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class AdminService {
    private MongoCollection<Document> userCollection;

    public AdminService() {
        MongoDatabase database = MongoDBConnection.getDatabase();
        userCollection = database.getCollection("users");
    }

    @SuppressWarnings("unchecked")
    public List<Client> getAllClients() {
        List<Client> clients = new ArrayList<>();
        for (Document doc : userCollection.find()) {
            Client client = new Client(
                    doc.getString("name"),
                    doc.getString("address"),
                    doc.getString("phoneNumber"),
                    doc.getString("hashedPassword")
            );
            client.setId(doc.getObjectId("_id").toString());
            client.setBalance(doc.getDouble("balance"));
            client.setTransactionHistory((List<String>) doc.get("transactionHistory"));
            clients.add(client);
        }
        return clients;
    }

    public boolean createAccount(Client client) {
        if (userCollection.find(eq("phoneNumber", client.getPhoneNumber())).first() != null) {
            return false; // User already exists
        }

        Document newUserDoc = new Document("name", client.getName())
                .append("address", client.getAddress())
                .append("phoneNumber", client.getPhoneNumber())
                .append("hashedPassword", client.getHashedPassword())
                .append("balance", client.getBalance())
                .append("transactionHistory", client.getTransactionHistory());

        userCollection.insertOne(newUserDoc);
        return true;
    }

    public boolean deactivateAccount(String phoneNumber) {
        Document userDoc = userCollection.find(eq("phoneNumber", phoneNumber)).first();
        if (userDoc == null) {
            return false; // User not found
        }

        userCollection.deleteOne(eq("phoneNumber", phoneNumber));
        return true;
    }

    public boolean updateClientInfo(Client client) {
        Document userDoc = userCollection.find(eq("phoneNumber", client.getPhoneNumber())).first();
        if (userDoc == null) {
            return false; // User not found
        }

        userCollection.updateOne(eq("phoneNumber", client.getPhoneNumber()),
                new Document("$set", new Document("name", client.getName())
                        .append("address", client.getAddress())
                        .append("phoneNumber", client.getPhoneNumber())
                        .append("hashedPassword", client.getHashedPassword())
                        .append("balance", client.getBalance())
                        .append("transactionHistory", client.getTransactionHistory())));
        return true;
    }
}
