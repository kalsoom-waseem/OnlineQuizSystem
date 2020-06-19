package sample.Teacher;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import sample.Mcq;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class SubjectDetailsScreen implements TeacherInterface {


    private String subjectTitle;

    public void AddMcqScr(ActionEvent event) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("../Teacher/AddMcqScreen.fxml"));
            Parent root = loader.load();
            setTeacherLayout(event,new Scene(root));
            AddMcqScreen controller = loader.getController();
            controller.setSubject(subjectTitle);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void back(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("TeacherMainScreen.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        setTeacherLayout(event,new Scene(root));
    }

    public void setSubject(String title){
        subjectTitle = title;
        loadMcqs();
    }

    @FXML
    private ListView<String> AdminMcqListView;
    private String[] mcq_files;
    private String[] mcq_bodies;

    @FXML
    private Label subject_details_label;
    private void loadMcqs() {
        subject_details_label.setText(subjectTitle);
        try {

            File folder = new File("data/" + subjectTitle);
            mcq_files = folder.list();
            mcq_bodies = folder.list();

            for (int i =0; i<mcq_files.length;i++){
                FileInputStream fileIn = new FileInputStream("data/" + subjectTitle+"/"+mcq_files[i]);
                ObjectInputStream objectIn = new ObjectInputStream(fileIn);
                Mcq mcq = (Mcq) objectIn.readObject();
                objectIn.close();
                mcq_bodies[i]= (i+1)+". "+mcq.getBody();
            }

            AdminMcqListView.getItems().addAll(mcq_bodies);
        }
        catch (Exception e){}
    }


       @FXML
      private Label Error;
    public void delete(ActionEvent event) {
        if (AdminMcqListView.getSelectionModel().getSelectedItem()==null){
            Error.setText("plz select");
            return; //display selection error
        }
        String selectedItem = AdminMcqListView.getSelectionModel().getSelectedItem();
        String file_name = "";
        for ( int i = 0; i <mcq_bodies.length; i++){
            if (selectedItem.equals(mcq_bodies[i])){
                file_name = mcq_files[i];
            }
        }

        File file = new File("data/" + subjectTitle+"/"+file_name);

        if(file.delete())
        {
            System.out.println("File deleted successfully");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../Teacher/SubjectDetailsScreen.fxml"));
            Parent root = null;
            try {
                root = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            setTeacherLayout(event,new Scene(root));
            SubjectDetailsScreen controller = loader.getController();
            controller.setSubject(subjectTitle);
        }
        else
        {
            System.out.println("Failed to delete the file");
        }
    }

    @Override
    public void setTeacherLayout(Event event, Scene scene) {
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
    }
}
