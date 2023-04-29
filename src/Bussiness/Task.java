package Bussiness;

import org.bson.types.ObjectId;

import java.time.LocalDateTime;
import java.util.Objects;

public class Task {
    private ObjectId id;
    private String taskName;
    private LocalDateTime deadLine;
    private Status status;

    public Task(String taskName, LocalDateTime deadLine, Status status) {
        this.id = new ObjectId();
        this.taskName = taskName;
        this.deadLine = deadLine;
        this.status = status;
    }

    public Task(ObjectId id, String taskName, LocalDateTime deadLine, Status status) {
        this.id = id;
        this.taskName = taskName;
        this.deadLine = deadLine;
        this.status = status;
    }





    public ObjectId getId() {
        return id;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public LocalDateTime getDeadLine() {
        return deadLine;
    }

    public void setDeadLine(LocalDateTime deadLine) {
        this.deadLine = deadLine;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }



    @Override
    public String toString(){
        return String.format("%s,%s,%d,%d,%d,%d,%d,%s",  id,taskName, deadLine.getYear(), deadLine.getMonthValue(),
               deadLine.getDayOfMonth(), deadLine.getHour(), deadLine.getMinute(), status);
    }

    @Override
    public boolean equals(Object ob){
        if(ob == null) return false;
        if(ob.getClass() != this.getClass()) return false;
        Task aTask = (Task) ob;
        return  aTask.id.equals(id);
    }

    @Override
    public int hashCode(){
        return id.hashCode();
    }
}
