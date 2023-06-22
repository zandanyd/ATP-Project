package View;

import javafx.event.ActionEvent;
import javafx.event.Event;

public interface IView {

    public void Start(ActionEvent actionEvent);
    public void solve(ActionEvent actionEvent);
    public void newOption(ActionEvent e) throws Exception;
    public void saveOption(ActionEvent event);
    public void loadOption(ActionEvent event);
    public void propertiesOption(ActionEvent event);
    public void exitAppMenuOption(ActionEvent event);
    public void helpAppMenuOption(Event event);
    public void aboutAppMenuOption(ActionEvent event);









    }
