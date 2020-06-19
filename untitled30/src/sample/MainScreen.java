package sample;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.*;

import javafx.event.ActionEvent;
import javafx.stage.*;

import java.io.IOException;


public class MainScreen implements MainInterface {


    @FXML
    public void teacherClick(ActionEvent event) throws Exception {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginScreen.fxml"));
        Parent root = loader.load();
        setMainLayout(event,new Scene(root));
        LoginScreen controller = loader.getController();
        controller.setType("Teacher");
    }

    @FXML
    public void studentClick(ActionEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginScreen.fxml"));
        Parent root = loader.load();
        setMainLayout(event,new Scene(root));
        LoginScreen controller = loader.getController();
        controller.setType("Student");
    }

    @FXML
    public void adminClick(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginScreen.fxml"));
        Parent root = loader.load();
        setMainLayout(event,new Scene(root));
        LoginScreen controller = loader.getController();
        controller.setType("Admin");
    }


    @Override
    public void setMainLayout(Event event, Scene scene) {
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
    }

    @Override
    public void back(ActionEvent event) {

    }
}


