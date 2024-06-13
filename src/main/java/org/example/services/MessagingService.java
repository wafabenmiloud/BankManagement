package org.example.services;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.example.config.MongoDBConnection;
import org.example.models.Message;

import static com.mongodb.client.model.Filters.eq;

import java.util.ArrayList;
import java.util.List;

public class MessagingService {
    private MongoCollection<Document> messageCollection;

    public MessagingService() {
        MongoDatabase database = MongoDBConnection.getDatabase();
        messageCollection = database.getCollection("messages");
    }

    public boolean sendMessage(String clientId, String messageContent) {
        Message newMessage = new Message(clientId, messageContent);

        Document messageDoc = new Document("clientId", newMessage.getClientId())
                .append("message", newMessage.getMessage())
                .append("timestamp", newMessage.getTimestamp());

        messageCollection.insertOne(messageDoc);
        return true;
    }

    public List<Message> getMessagesForClient(String clientId) {
        List<Message> messages = new ArrayList<>();
        for (Document doc : messageCollection.find(eq("clientId", clientId))) {
            Message message = new Message(
                    doc.getString("clientId"),
                    doc.getString("message")
            );
            message.setTimestamp(doc.getDate("timestamp"));
            messages.add(message);
        }
        return messages;
    }
}
