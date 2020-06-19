package sample.Teacher;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static sample.Main.*;

public class TeacherMainScreen implements Initializable, TeacherInterface {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        loadSubjects();
    }
    @FXML
    private ListView<String> AdminSubjectListView;
    public String[] subject_directories ;

    public void loadSubjects(){
        try {
            File folder = new File("data");
            subject_directories = folder.list();
            AdminSubjectListView.getItems().addAll(subject_directories);//subject_directories);
            AdminSubjectListView.notifyAll();
        }
        catch(Exception e){}
    }

    public void subjectClick(javafx.scene.input.MouseEvent event) {
        try {
            if (AdminSubjectListView.getSelectionModel().getSelectedItem() == null){
                return;
            }
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../Teacher/SubjectDetailsScreen.fxml"));
            Parent root = loader.load();
            setTeacherLayout(event,new Scene(root));
            String selectedItem = AdminSubjectListView.getSelectionModel().getSelectedItem();
            SubjectDetailsScreen controller = loader.getController();
            controller.setSubject(selectedItem);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void addSubjectScr(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Teacher/AddSubjectScreen.fxml"));
        Parent root = loader.load();
        setTeacherLayout(event,new Scene(root));
        //setScene(event,admin_addsubscrn_scene);
    }


    @Override
    public void setTeacherLayout(Event event, Scene scene) {
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
    }

    @Override
    public void back(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../MainScreen.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        setTeacherLayout(event,new Scene(root));
        //setScene(event,main_screen_scene);
    }

    public void setSubject(String subject) {
    }
}
