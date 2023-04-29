package Tests.TestBussiness;

import Bussiness.Status;
import Bussiness.Task;
import Bussiness.Wishlist;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class TestTaskGroup {
    @Test
    public void testTaskGroup(){
        Wishlist wishlist = new Wishlist();
        Task task = new Task("taskGroupTest", LocalDateTime.now(), Status.PENDING);
        assertEquals(wishlist.add(task), task);
        assertEquals(wishlist.getTask(task.getId()), task);
        assertEquals(wishlist.update(task.getId(), "updatedTask", LocalDateTime.now(), Status.COMPLETED), task);
        assertFalse(wishlist.getAll().isEmpty());
        assertEquals(wishlist.delete(task.getId()), task);
    }
}
