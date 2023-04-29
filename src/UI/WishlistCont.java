package UI;

import Bussiness.Personal;
import Bussiness.Task;
import Bussiness.Wishlist;
import UI.Utilites.Callers;
import UI.Utilites.Loadable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class WishlistCont implements Initializable, Loadable {

    @FXML
    private Button addTaskBtn;

    @FXML
    private ListView<Task> listView;

    @FXML
    private Button personalBtn;

    @FXML
    private Button workBtn;

    @FXML
    void openAddTaskPop() {
        try {
            FXMLLoader root= new FXMLLoader(
                    getClass().getResource("addTask.fxml")
            );
            Scene scene = new Scene(root.load());
            Stage primaryStage = new Stage();
            primaryStage.setTitle("Add Task");
            primaryStage.setScene(scene);
            primaryStage.initModality(Modality.WINDOW_MODAL);
            primaryStage.initOwner(addTaskBtn.getScene().getWindow());
            AddTaskCont addTaskController = root.getController();
            addTaskController.setCallerInfo(Callers.WISHLIST, primaryStage, this);
            primaryStage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void showPersonal(ActionEvent event) throws IOException {
        Parent parentParent = FXMLLoader.load(getClass().getResource("personal.fxml"));
        Scene parentScene = new Scene(parentParent);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(parentScene);
        stage.show();

    }

    @FXML
    void showWork(ActionEvent event) throws IOException {
        Parent workParent = FXMLLoader.load(getClass().getResource("work.fxml"));
        Scene workScene = new Scene(workParent);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(workScene);
        stage.show();
    }

    @Override
    public void loadData(){
        ObservableList<Task> data = FXCollections.observableArrayList();
        data.addAll(new Wishlist().getAll());
        listView.setItems(data);

        listView.setCellFactory(new ListItemFactory());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadData();
        listView.getSelectionModel().selectedItemProperty().addListener(event -> {
            if(!(listView.getSelectionModel().getSelectedItems().isEmpty())){
                try {
                    FXMLLoader root= new FXMLLoader(
                            getClass().getResource("updateTask.fxml")
                    );
                    Scene scene = new Scene(root.load());
                    Stage updateTaskStage = new Stage();
                    updateTaskStage.setTitle("Update Task");
                    updateTaskStage.setScene(scene);
                    updateTaskStage.initModality(Modality.WINDOW_MODAL);
                    updateTaskStage.initOwner(listView.getScene().getWindow());
                    UpdateTaskCont updateTaskController = root.getController();
                    updateTaskController.setCallerInfo(Callers.WISHLIST, updateTaskStage, this);
                    updateTaskController.setTaskID(listView.getSelectionModel().getSelectedItems().get(0).getId());

                    updateTaskStage.show();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }


        });

    }

}
