package sample.Student;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class StudentMainScreen implements Initializable ,StudentInterface{

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showSubjects();
    }


    @FXML
     private ListView<String> StudentSubjectListView;

    public String[] subject_directories ;

    public void showSubjects(){
        try {
            File folder = new File("data");
            subject_directories = folder.list();
            StudentSubjectListView.getItems().addAll(subject_directories);//subject_directories);
            StudentSubjectListView.notifyAll();
        }
        catch(Exception e){}
    }

    public void StudentsubjectClick(MouseEvent event) {
        try {
            if (StudentSubjectListView.getSelectionModel().getSelectedItem() == null){
                return;
            }
            String selectedItem = StudentSubjectListView.getSelectionModel().getSelectedItem();

            File folder = new File("data/" + selectedItem);
            if (folder.list().length == 0){
                //no mcqs
                return;
            }
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../Student/StudentMcqsScreen.fxml"));
            Parent root = loader.load();
            setScene(event,new Scene(root));
            StudentMcqsScreen controller = loader.getController();
            controller.setSubject(selectedItem);

        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public void setScene(Event event, Scene scene) {
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
}
    @Override
    public void setStudentLayout(Event event, Scene scene) {
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
        setStudentLayout(event,new Scene(root));
        //setScene(event,main_screen_scene);
    }

    @FXML
    Label Semester_Label;
    public void setSemester(String semester) {
       Semester_Label.setText("Semester:"+semester);
    }
}
