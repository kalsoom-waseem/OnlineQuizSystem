package sample.Student;

import javafx.event.Event;
import javafx.scene.Scene;
import sample.LayoutsInterface;

public interface StudentInterface extends LayoutsInterface {
    void setStudentLayout(Event event, Scene scene);
}
