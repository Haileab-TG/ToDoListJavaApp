package DataAccess;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class MongoDBConnect {
    private static MongoDatabase database = connectDB();
    private static MongoClient mongoClient;

    public static MongoDatabase getDB(){
        if(database != null) return database;
        return getDB();
    }

    public static MongoClient getmongoClient(){
        return mongoClient;
    }
    public static MongoDatabase connectDB() {
        String connectionString = "mongodb+srv://admin:admin@todolistappdb.0vqr7ui.mongodb.net/?retryWrites=true&w=majority";



        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(connectionString))
                .build();

        // Create a new client and connect to the server
        try {
            mongoClient = MongoClients.create(settings);
            try {
                // Send a ping to confirm a successful connection
                MongoDatabase database = mongoClient.getDatabase("ToDoListAppDB");
//                database.runCommand(new Document("ping", 1));
                System.out.println("Pinged your deployment. You successfully connected to MongoDB!");
                return database;
            } catch (MongoException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}

