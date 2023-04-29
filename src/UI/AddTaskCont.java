package UI;

import Bussiness.*;
import UI.Utilites.Callers;
import UI.Utilites.ModalWindow;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;
import java.util.stream.Stream;

public class AddTaskCont extends ModalWindow implements Initializable {

    @FXML
    private Button addBtn;

    @FXML
    private DatePicker datePicker;

    @FXML
    private ChoiceBox<Integer> hourChb;

    @FXML
    private ChoiceBox<Integer> minuteChb;

    @FXML
    private TextField taskNameTxtF;

    public void add(){
        String taskN = taskNameTxtF.getText();
        LocalDateTime date = LocalDateTime.of(datePicker.getValue(), LocalTime.of(hourChb.getValue(), minuteChb.getValue()));
        TaskGroup obj;
        if(caller == Callers.PERSONAL) obj = new Personal();
        else if(caller == Callers.WISHLIST) obj = new Wishlist();
        else obj = new Work();
        obj.add(new Task(taskN, date, Status.PENDING));
        stage.close();
        loadable.loadData();
    }

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        datePicker.setValue(LocalDate.now());
        taskNameTxtF.setText("Sample");

        minuteChb.setValue(LocalTime.now().getMinute());
        ObservableList<Integer> minutes = FXCollections.observableArrayList();
        minutes.addAll(Stream.iterate(0, n -> n + 5).limit(13).toList());
        minuteChb.setItems(minutes);

        hourChb.setValue(LocalTime.now().getHour());
        ObservableList<Integer> hours = FXCollections.observableArrayList();
        hours.addAll(Stream.iterate(0, n -> ++n).limit(23).toList());
        hourChb.setItems(hours);
    }

}
