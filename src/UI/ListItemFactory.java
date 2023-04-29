package UI;

import Bussiness.Task;
import UI.ListItemCont;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;


public class ListItemFactory implements Callback<ListView<Task>, ListCell<Task>> {

    @Override
    public ListCell<Task> call(ListView<Task> param) {
        return new ListItemCont();
    }
}
