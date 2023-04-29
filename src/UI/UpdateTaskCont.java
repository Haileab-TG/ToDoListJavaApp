package UI;

import Bussiness.*;
import UI.Utilites.Callers;
import UI.Utilites.ModalWindow;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.bson.types.ObjectId;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Stream;

public class UpdateTaskCont extends ModalWindow implements Initializable {

    private ObjectId taskID;

    @FXML
    private DatePicker datePicker;

    @FXML
    private ChoiceBox<Integer> hourChb;

    @FXML
    private ChoiceBox<Integer> minuteChb;

    @FXML
    private TextField taskNameTxtF;

    @FXML
    private Button updateBtn;

    @FXML
    private Button deleteBtn;

    @FXML
    void update() {
        String taskN = taskNameTxtF.getText();
        LocalDateTime date = LocalDateTime.of(datePicker.getValue(), LocalTime.of(hourChb.getValue(), minuteChb.getValue()));
        TaskGroup obj;
        if(caller == Callers.PERSONAL) obj = new Personal();
        else if(caller == Callers.WISHLIST) obj = new Wishlist();
        else obj = new Work();
        Status status = obj.getTask(taskID).getStatus() == Status.OVERDUE && (date.isAfter(LocalDateTime.now())) ? Status.PENDING : obj.getTask(taskID).getStatus();
        obj.update(taskID, taskN, date, status);
        loadable.loadData();
        super.stage.close();
    }

    @FXML
    void delete(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Warning!");
        alert.setContentText("You are about to delete a task!");

        ButtonType res = alert.showAndWait().orElse(ButtonType.CANCEL);
        if(res == ButtonType.OK) {
            TaskGroup obj;
            if(caller == Callers.PERSONAL) obj = new Personal();
            else if(caller == Callers.WISHLIST) obj = new Wishlist();
            else obj = new Work();
            obj.delete(taskID);
            loadable.loadData();
            stage.close();
        }

    }

    public void setTaskID(ObjectId id){
        this.taskID = id;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(() -> {
            TaskGroup obj;
            if(caller == Callers.PERSONAL) obj = new Personal();
            else if(caller == Callers.WISHLIST) obj = new Wishlist();
            else obj = new Work();

            Task task = obj.getTask(taskID);
            System.out.println("int "+ taskID);
            System.out.println(task);
            datePicker.setValue(LocalDate.of(task.getDeadLine().getYear(), task.getDeadLine().getMonthValue(),
                    task.getDeadLine().getDayOfMonth()));
            taskNameTxtF.setText(task.getTaskName());

            minuteChb.setValue(task.getDeadLine().getMinute());
            ObservableList<Integer> minutes = FXCollections.observableArrayList();
            minutes.addAll(Stream.iterate(0, n -> n + 5).limit(13).toList());
            minuteChb.setItems(minutes);

            hourChb.setValue(task.getDeadLine().getHour());
            ObservableList<Integer> hours = FXCollections.observableArrayList();
            hours.addAll(Stream.iterate(0, n -> ++n).limit(23).toList());
            hourChb.setItems(hours);
        });
    }
}

