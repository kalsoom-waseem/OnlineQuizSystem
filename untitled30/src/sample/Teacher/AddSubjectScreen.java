package sample.Teacher;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.File;

public class AddSubjectScreen implements TeacherInterface{
    @FXML
    private TextField title;
    @FXML
    private Label TeacherAddSubError;

    @FXML
    public void addSubject() throws Exception {

        if (title.getText().equals("")) {
            TeacherAddSubError.setText("Empty");
        } else {
            try {
                File maindir = new File("data");
                String filePath = maindir.getAbsolutePath();
                if (!maindir.exists()) {
                    if (maindir.mkdirs()) {
                        System.out.println("data dir created");
                    }
                }

                File dir = new File(filePath.concat("/" + title.getText()));
                if (!dir.exists()) {
                    if (dir.mkdirs()) {
                        System.out.println("subject created");
                        TeacherAddSubError.setText("Subject Added!!");
                    }
                } else {
                    System.out.println("subject already exists");
                    TeacherAddSubError.setText("subject already exists!!");
                }
            } catch (Exception e) {
                System.out.println("Failed to add subject");
                TeacherAddSubError.setText("Failed to add subject!!");
            }
        }
    }

    @Override
    public void back(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("TeacherMainScreen.fxml"));
            Parent root = loader.load();
            setTeacherLayout(event,new Scene(root));
            TeacherAddSubError.setText("");
            title.setText("");
            //loader.<AdminMainScreen>getController().loadSubjects();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void setTeacherLayout(Event event, Scene scene) {
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
    }
}
