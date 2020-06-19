package sample.Teacher;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import sample.Mcq;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.Calendar;
import java.util.ResourceBundle;

public class AddMcqScreen implements Initializable, TeacherInterface {
    @FXML
    private TextField AdminMcqBody, AdminMcqOp1, AdminMcqOp2, AdminMcqOp3, AdminMcqOp4;

    @FXML
    private Label AdminAddMcqError;

    @FXML
    private ToggleGroup TeacherToggle;
    private String subject;

    public void write(ActionEvent event) {


        if (TeacherToggle.getSelectedToggle() == null){
            AdminAddMcqError.setText("plz select the correct answer");
            return;
        }

        if (AdminMcqBody.getText().equals("") || AdminMcqOp1.getText().equals("") || AdminMcqOp2.getText().equals("") || AdminMcqOp3.getText().equals("") || AdminMcqOp4.getText().equals("")) {
            AdminAddMcqError.setText("Some portion is empty");
        } else {
            Mcq mcq = new Mcq();
            String McqBody = AdminMcqBody.getText();
            String McqOp1 = AdminMcqOp1.getText();
            String McqOp2 = AdminMcqOp2.getText();
            String McqOp3 = AdminMcqOp3.getText();
            String McqOp4 = AdminMcqOp4.getText();
            String selectedOp = ((RadioButton) TeacherToggle.getSelectedToggle()).getText();

            int cop_int = 0;
            if (selectedOp.equals("a.")){
                cop_int = 1;
            }
            if (selectedOp.equals("b.")){
                cop_int = 2;
            }
            if (selectedOp.equals("c.")){
                cop_int = 3;
            }
            if (selectedOp.equals("d.")){
                cop_int = 4;
            }

            TeacherToggle.getSelectedToggle().setSelected(false);

            Long id = Calendar.getInstance().getTimeInMillis();
            mcq.setId(id);
            mcq.setBody(McqBody);
            mcq.setOp1(McqOp1);
            mcq.setOp2(McqOp2);
            mcq.setOp3(McqOp3);
            mcq.setOp4(McqOp4);
            mcq.setCop(cop_int);
            try (FileOutputStream fos = new FileOutputStream(new File("data").getAbsolutePath().concat("/" + subject) + "/" + id + ".txt");
                 ObjectOutputStream oos = new ObjectOutputStream(fos)) {
                 oos.writeObject(mcq);
                 AdminMcqBody.clear();
                 AdminMcqOp1.clear();
                 AdminMcqOp2.clear();
                 AdminMcqOp3.clear();
                 AdminMcqOp4.clear();
                 AdminAddMcqError.setText("Mcq Added succesfully!!");
            } catch (IOException e) {
                e.printStackTrace();
                AdminAddMcqError.setText("permisson not granted!!");
            }

        }
    }

    @Override
    public void back(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../Teacher/SubjectDetailsScreen.fxml"));
            Parent root = loader.load();
            setTeacherLayout(event,new Scene(root));
            SubjectDetailsScreen controller = loader.getController();
            controller.setSubject(subject);

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setSubject(String subjectTitle) {
        subject = subjectTitle;
    }


    @Override
    public void setTeacherLayout(Event event, Scene scene) {

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
    }
}
