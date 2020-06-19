package sample.Student;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class ResultScreen implements StudentInterface {


    @FXML
    private Label TotalMarks,ObtainedMarks,Percentage;
    public void setData(int correct_answers, int totalMcqs) {

        TotalMarks.setText(totalMcqs+"");
        ObtainedMarks.setText(correct_answers+"");
        Percentage.setText((int)(((correct_answers+0.0)/totalMcqs)*100)+"");
    }


    @Override
    public void setStudentLayout(Event event, Scene scene) {
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
    }

    @Override
    public void back(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Student/StudentMainScreen.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        setStudentLayout(event, new Scene(root));
    }
}

