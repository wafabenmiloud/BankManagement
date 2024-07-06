package org.example.services;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.example.config.MongoDBConnection;

import static com.mongodb.client.model.Filters.eq;

public class TransactionService {
    private MongoCollection<Document> userCollection;

    public TransactionService() {
        MongoDatabase database = MongoDBConnection.getDatabase();
        userCollection = database.getCollection("users");
    }

    public boolean transfer(String fromPhoneNumber, String toPhoneNumber, double amount) {
        Document fromUserDoc = userCollection.find(eq("phoneNumber", fromPhoneNumber)).first();
        Document toUserDoc = userCollection.find(eq("phoneNumber", toPhoneNumber)).first();

        if (fromUserDoc == null || toUserDoc == null || amount <= 0 || fromUserDoc.getDouble("balance") < amount) {
            return false; 
        }

        double newFromBalance = fromUserDoc.getDouble("balance") - amount;
        double newToBalance = toUserDoc.getDouble("balance") + amount;

        userCollection.updateOne(eq("phoneNumber", fromPhoneNumber),
                new Document("$set", new Document("balance", newFromBalance))
                        .append("$push", new Document("transactionHistory", "Transferred: " + amount + " to " + toPhoneNumber)));
        
        userCollection.updateOne(eq("phoneNumber", toPhoneNumber),
                new Document("$set", new Document("balance", newToBalance))
                        .append("$push", new Document("transactionHistory", "Received: " + amount + " from " + fromPhoneNumber)));
        
        return true;
    }
}
