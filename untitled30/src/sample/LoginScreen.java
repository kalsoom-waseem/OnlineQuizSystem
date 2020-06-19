package sample;

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
import org.w3c.dom.Text;
import sample.Student.StudentMainScreen;
import sample.Teacher.TeacherMainScreen;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class LoginScreen implements MainInterface{

    @FXML
    TextField username,password;
    private String type;
    @FXML
    Label login_display,error;
    ArrayList<User> users = new ArrayList<>();


    public void Login(ActionEvent event) {
        String usernam = username.getText();
        String pass = password.getText();
        if (type.equals("Admin")){
            if (usernam.equals("admin")&&pass.equals("admin")){
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Admin/AdminMainScreen.fxml"));
                Parent root = null;
                try {
                    root = loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                setMainLayout(event,new Scene(root));
            }
            else{
                error.setText("Wrong Username or password");
            }
        return;
        }

        for (int i = 0; i < users.size(); i++){
            if (users.get(i).getUsername().equals(usernam)&&users.get(i).getPassword().equals(pass)){
                if (type.equals("Teacher")){
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("Teacher/TeacherMainScreen.fxml"));
                    Parent root = null;
                    try {
                        root = loader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    TeacherMainScreen controller = loader.getController();
                    controller.setSubject(((teacher)users.get(i)).getSubject());
                    setMainLayout(event,new Scene(root));
                }
                else{
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("Student/StudentMainScreen.fxml"));
                    Parent root = null;
                    try {
                        root = loader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    StudentMainScreen controller = loader.getController();
                    controller.setSemester(((student)users.get(i)).getSemester());
                    setMainLayout(event,new Scene(root));
                }
            }
            else {
            error.setText("Wrong Username or password");
            }
        }
    }

    private void fetch(String type) {
        if (type.equals("Admin")) return;
        try {
            File folder = new File("Users/" + type);
            String[] files = folder.list();

            for (int i =0; i<files.length;i++){
                FileInputStream fileIn = new FileInputStream("Users/" + type+"/"+files[i]);
                ObjectInputStream objectIn = new ObjectInputStream(fileIn);
                User user = (User) objectIn.readObject();
                objectIn.close();
                users.add(user);
            }

        }
        catch (Exception e){}
    }



    public void setType(String type) {
        this.type = type;
        login_display.setText(type);
        fetch(type);
    }

    @Override
    public void back(ActionEvent event)  {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../sample/MainScreen.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        setMainLayout(event,new Scene(root));
        //setScene(event,main_screen_scene);
    }

    @Override
    public void setMainLayout(Event event, Scene scene) {
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
    }
}
