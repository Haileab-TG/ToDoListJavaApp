package UI.Utilites;

import UI.Utilites.Callers;
import UI.Utilites.Loadable;
import javafx.stage.Stage;

public class ModalWindow {
    protected Callers caller;
    protected Stage stage;
    protected Loadable loadable;

    public void setCallerInfo(Callers caller, Stage stage, Loadable loadable){
        this.caller = caller;
        this.stage = stage;
        this.loadable = loadable;
    }
}
