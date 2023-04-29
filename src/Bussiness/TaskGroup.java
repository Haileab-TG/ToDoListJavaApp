package Bussiness;

import DataAccess.CRUD;
import DataAccess.Fields;
import DataAccess.MongoDBConnect;
import com.mongodb.client.FindIterable;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.security.PublicKey;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TaskGroup {
    public List<Task> getAll() {
        FindIterable<Document> docs = CRUD.findAll(this.getClass().getSimpleName());
        List<Task> tasks = new ArrayList<>();
        docs.forEach((doc)-> tasks.add(docToTask(doc)));
        return tasks;
    }

    public Task add(Task task){
        if(task == null) return null;
        if(CRUD.insertOne(this.getClass().getSimpleName(), task)) return task;
        return null;
    }

    public Task delete(ObjectId id){
        if(id == null) return null;
        return docToTask(CRUD.deleteOne(this.getClass().getSimpleName(), id));
    }

    public Task update(ObjectId id, String taskName, LocalDateTime deadline, Status status){
        if(id == null) return null;
        if (CRUD.updateOne(this.getClass().getSimpleName(),id,taskName, deadline,status)) return getTask(id);
        return null;
    }

    public Task getTask(ObjectId id){
        Document doc = CRUD.findOne(this.getClass().getSimpleName(), id);
        System.out.println(this.getClass().getSimpleName());
        return docToTask(doc);
    }

    private Task docToTask(Document doc){
        if(doc == null) return null;
        return new Task(
                doc.getObjectId(Fields._id.name()),
                doc.get(Fields.taskName.name()).toString(),
                LocalDateTime.of(
                        (Integer) doc.get(Fields.year.name()),
                        (Integer) doc.get(Fields.Month.name()),
                        (Integer) doc.get(Fields.day.name()),
                        (Integer) doc.get(Fields.hour.name()),
                        (Integer) doc.get(Fields.minute.name())
                ),
                Status.valueOf(doc.get(Fields.status.name()).toString())
        );
    }

    public Task changeOverdue(Task task){
        if(!isOverdue(task) && task.getStatus() == Status.OVERDUE) {
            task.setStatus( Status.PENDING);
            CRUD.updateStatus(this.getClass().getSimpleName(),task.getId(), Status.PENDING);
            return task;
        }
        return null;
    }

    public boolean isOverdue(Task task){
        if (task.getDeadLine().isBefore(LocalDateTime.now()) && task.getStatus() == Status.PENDING) return true;
        return false;
    }

    public Status markOverdue(Task task){
        task.setStatus(Status.OVERDUE);
        CRUD.updateStatus(this.getClass().getSimpleName(), task.getId(), Status.OVERDUE);
        return Status.OVERDUE;
    }

    public Task changeStatus(Task task){
        if (task.getStatus()  == Status.OVERDUE) return null;
        if(task.getStatus()  == Status.PENDING) {
            task.setStatus(Status.COMPLETED);
            CRUD.updateStatus(this.getClass().getSimpleName(), task.getId(), Status.COMPLETED);
        }else {
            task.setStatus(Status.PENDING);
            CRUD.updateStatus(this.getClass().getSimpleName(), task.getId(), Status.PENDING);
        }
        return task;
    }

    public Task completeUnComplete(ObjectId id){
        Task toUpdate = getTask(id);
        if(toUpdate == null) return null;
        return changeStatus(toUpdate);
    }

}
