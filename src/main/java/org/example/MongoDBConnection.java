package org.example;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class MongoDBConnection {
    private static MongoClient mongoClient = MongoClients.create("mongodb://127.0.0.1:27017/?directConnection=true&serverSelectionTimeoutMS=2000&appName=mongosh+2.2.6");
    private static MongoDatabase database = mongoClient.getDatabase("banque");

    public static MongoDatabase getDatabase() {
        return database;
    }
}

