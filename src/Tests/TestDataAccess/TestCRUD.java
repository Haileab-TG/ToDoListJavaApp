package Tests.TestDataAccess;

import Bussiness.Personal;
import Bussiness.Status;
import Bussiness.Task;
import Bussiness.Wishlist;
import DataAccess.CRUD;
import com.mongodb.client.result.UpdateResult;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

public class TestCRUD {

    @Test
    public void crud(){
//        Task task = new Task("task3", LocalDateTime.now(), Status.PENDING);
//        assertTrue(CRUD.insertOne(Personal.class.getSimpleName(), task));
//
//        assertTrue(CRUD.updateOne(
//                Personal.class.getSimpleName(), task.getId(),
//                "updateTest", LocalDateTime.now(), Status.COMPLETED)
//        );
        assertNotNull(CRUD.findOne(Personal.class.getSimpleName(), new ObjectId("644731c5406e196623874be0")));
       // assertNotNull(CRUD.deleteOne(Personal.class.getSimpleName(), task.getId()));
    }

}
