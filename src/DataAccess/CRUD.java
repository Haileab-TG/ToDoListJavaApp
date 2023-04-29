package DataAccess;

import Bussiness.Status;
import Bussiness.Task;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;

public class CRUD {


    public static boolean insertOne(String collectionName, Task task) {

        MongoCollection<Document> collection = MongoDBConnect.getDB().getCollection(collectionName);

        Document taskDoc = new Document(Fields._id.name(), task.getId());
        taskDoc.append(Fields.taskName.name(), task.getTaskName())
                .append(Fields.year.name(), task.getDeadLine().getYear())
                .append(Fields.Month.name(), task.getDeadLine().getMonthValue())
                .append(Fields.day.name(), task.getDeadLine().getDayOfMonth())
                .append(Fields.hour.name(), task.getDeadLine().getHour())
                .append(Fields.minute.name(), task.getDeadLine().getMinute())
                .append(Fields.status.name(), task.getStatus());

        InsertOneResult res = collection.insertOne(taskDoc);
        return  res.wasAcknowledged() && res.getInsertedId() != null;
    }


    public static boolean updateOne(String collectionName, ObjectId id, String taskName, LocalDateTime deadline, Status status) {
        MongoCollection<Document> collection = MongoDBConnect.getDB().getCollection(collectionName);

       Bson updates = Updates.combine(
               Updates.set(Fields.taskName.name(), taskName),
               Updates.set(Fields.year.name(), deadline.getYear()),
               Updates.set(Fields.Month.name(), deadline.getMonthValue()),
               Updates.set(Fields.day.name(), deadline.getDayOfMonth()),
               Updates.set(Fields.hour.name(), deadline.getHour()),
               Updates.set(Fields.minute.name(), deadline.getMinute()),
               Updates.set(Fields.status.name(), status)
       );

       UpdateResult res = collection.updateOne(Filters.eq(Fields._id.name(), id), updates);
       return res.wasAcknowledged() && res.getModifiedCount() > 0 && res.getModifiedCount() > 0;
    }

    public static boolean updateStatus(String collectionName, ObjectId id, Status status) {
        MongoCollection<Document> collection = MongoDBConnect.getDB().getCollection(collectionName);

        Bson updates = Updates.combine(
                Updates.set(Fields.status.name(), status)
        );

        UpdateResult res = collection.updateOne(Filters.eq(Fields._id.name(), id), updates);
        return res.wasAcknowledged() && res.getModifiedCount() > 0 && res.getModifiedCount() > 0;
    }

    public static Document findOne(String collectionName, ObjectId id){
        MongoCollection<Document> collection = MongoDBConnect.getDB().getCollection(collectionName);

        return collection.find(Filters.eq(Fields._id.name(), id)).first();
    }

    public static Document deleteOne(String collectionName, ObjectId id){
        MongoCollection<Document> collection = MongoDBConnect.getDB().getCollection(collectionName);

        return collection.findOneAndDelete(Filters.eq(Fields._id.name(), id));
    }

    public static FindIterable<Document> findAll(String collectionName){
        MongoCollection<Document> collection = MongoDBConnect.getDB().getCollection(collectionName);
        return collection.find();
    }
}
