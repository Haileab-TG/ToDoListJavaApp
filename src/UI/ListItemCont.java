package UI;

import Bussiness.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ListCell;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import org.bson.types.ObjectId;

import java.io.IOException;
import java.time.LocalDateTime;

public class ListItemCont extends ListCell<Task> {


    @FXML
    private CheckBox completedChb;

    @FXML
    private Text dateTimetxt;

    @FXML
    private Text statusTxt;

    @FXML
    private Text taskNameTxt;

    private ObjectId id;

    public ListItemCont() {
        loadFXML();
    }

    @FXML
    public void checked(ActionEvent e){
        System.out.println(id);
//        completedChb.setOnAction(event -> {
//            if(completedChb.isSelected()) statusTxt.setText("Yes");
//            else statusTxt.setText("No");
//        });
    }



    private void loadFXML() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("listItem.fxml"));
            loader.setController(this);
            loader.setRoot(this);
            loader.load();
            completedChb.setOnAction(event -> {
                TaskGroup obj;
                if(new Personal().getTask(id) != null) obj = new Personal();
                else if(new Work().getTask(id) != null) obj = new Work();
                else obj = new Wishlist();
                if(!(statusTxt.getText().equals(Status.OVERDUE.toString()))){
                    obj.completeUnComplete(id);
                    if(completedChb.isSelected()) {
                        statusTxt.setText(Status.COMPLETED.toString());
                        taskNameTxt.setStrikethrough(true);
                    }
                    else {
                        statusTxt.setText(Status.PENDING.toString());
                        taskNameTxt.setStrikethrough(false);
                    }
                }

            });
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void updateItem(Task item, boolean empty) {
        super.updateItem(item, empty);

        if(empty || item == null) {
            setText(null);
            setContentDisplay(ContentDisplay.TEXT_ONLY);
        }
        else {
            String[] dateTime = item.getDeadLine().toString().split("T");

            TaskGroup obj;
            if(new Personal().getTask(item.getId()) != null) obj = new Personal();
            else if(new Work().getTask(item.getId()) != null) obj = new Work();
            else obj = new Wishlist();

            Status status = obj.isOverdue(item) ? obj.markOverdue(item)  : item.getStatus();

            dateTimetxt.setText(dateTime[0] + " " + dateTime[1]);
            taskNameTxt.setText(item.getTaskName());
            statusTxt.setText(status.toString());
            id = item.getId();

            if(status == Status.COMPLETED) {
                completedChb.setSelected(true);
                taskNameTxt.setStrikethrough(true);
            };
            if(status == Status.OVERDUE) {
                completedChb.setDisable(true);
                statusTxt.setFill(Color.RED);
            }

            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        }
    }
}