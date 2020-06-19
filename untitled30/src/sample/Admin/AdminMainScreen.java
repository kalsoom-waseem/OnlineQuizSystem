package sample.Admin;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import sample.Teacher.SubjectDetailsScreen;
import sample.User;
import sample.student;
import sample.teacher;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;
import java.util.ResourceBundle;

public class AdminMainScreen implements Initializable,AdminInterface{
    @FXML
    private TableView<User> studentsTable, teachersTable;
    private ObservableList<User> studentsData;
    private ObservableList<User> teachersData;

    public void display(){
        getData("Student");
        getData("Teacher");
        setTable("Student");
        setTable("Teacher");
    }

    private void getData(String type) {
        ArrayList<User> user_objects = new ArrayList<>();
        try {

            File folder = new File("Users/" + type);
            String[] user_files = folder.list();

            for (int i = 0; i < user_files.length; i++) {
                FileInputStream fileIn = new FileInputStream("Users/" + type  + "/" + user_files[i]);
                ObjectInputStream objectIn = new ObjectInputStream(fileIn);
                User userToAdd = (User) objectIn.readObject();
                objectIn.close();
                user_objects.add(userToAdd);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        if (type.equals("Student")) {
            studentsData = FXCollections.observableArrayList(user_objects);
        }
        else{
            teachersData = FXCollections.observableArrayList(user_objects);
        }

    }

    private void setTable(String type) {
        TableColumn nameCol = new TableColumn("Name");
        nameCol.setMinWidth(120);
        nameCol.setCellValueFactory(
                new PropertyValueFactory<User, String>("name"));
        nameCol.setCellFactory(TextFieldTableCell.forTableColumn());


        TableColumn userameCol = new TableColumn("Username");
        userameCol.setMinWidth(150);
        userameCol.setCellValueFactory(
                new PropertyValueFactory<User, String>("username"));
        userameCol.setCellFactory(TextFieldTableCell.forTableColumn());


        TableColumn passCol = new TableColumn("Password");
        passCol.setMinWidth(120);
        passCol.setCellValueFactory(
                new PropertyValueFactory<User, String>("password"));
        passCol.setCellFactory(TextFieldTableCell.forTableColumn());



        if (type.equals("Student")) {
            studentsTable.getColumns().addAll(nameCol, userameCol, passCol);
            studentsTable.setItems(studentsData);
        }
        else{
            teachersTable.getColumns().addAll(nameCol, userameCol, passCol);
            teachersTable.setItems(teachersData);
        }

    }


        @FXML
        private TextField Admin_name;
        public String typeSelected;
        @FXML
        private MenuButton dropdown;
        @FXML
        Label password,username;
    @FXML
    TextField Admin_extra;
        public void initialize(URL location, ResourceBundle resources) {

            display();
            MenuItem tr = new MenuItem("Teacher");
            MenuItem st = new MenuItem("Student");
            dropdown.getItems().add(tr);
            dropdown.getItems().add(st);

            tr.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    dropdown.setText("Teacher");
                    typeSelected = "Teacher";
                    Admin_extra.setPromptText("Subject");
                }
            });

            st.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    dropdown.setText("Student");
                    typeSelected = "Student";
                    Admin_extra.setPromptText("Semester");
                }
            }); }

        @FXML
        private Label Admin_name_error;

        public void AddNewUser(ActionEvent event) throws IOException {

            if (Admin_name.getText().equals("")){
                Admin_name_error.setText("Name empty");
                return;
            }

            if (typeSelected==null) {
                Admin_name_error.setText("Plz select type");
                return;
            }
            User userToAdd;
            if (typeSelected.equals("Student")){
                userToAdd = new student(Admin_extra.getText());
            }
            else{
                userToAdd = new teacher(Admin_extra.getText());
            }
                        String name = Admin_name.getText();
            String username =name+"@"+typeSelected+".com";
            Random rand = new Random();
            int rand_int1 = rand.nextInt(1000);

            String password = rand_int1+"";
            if(name.length()>=3) {
                password = new StringBuilder(name).reverse().toString().substring(0,3) + rand_int1;
            }
            else{
                password = new StringBuilder(name).reverse().toString() + rand_int1;
            }

            Long id = Calendar.getInstance().getTimeInMillis();
                userToAdd.setId(id);
                userToAdd.setName(name);
                userToAdd.setPassword(password);
                userToAdd.setUsername(username);

                try {
                    File maindir = new File("Users");
                    String filePath = maindir.getAbsolutePath();
                    if (!maindir.exists()) {
                        if (maindir.mkdirs()) {
                            System.out.println("users dir created");
                        }
                    }
                }
                catch (Exception e){}



                try {
                    File maindir = new File("Users/"+typeSelected);
                    String filePath = maindir.getAbsolutePath();
                    if (!maindir.exists()) {
                        if (maindir.mkdirs()) {
                            System.out.println("type dir created");
                        }
                    }
                }
                catch (Exception e){}


                FileOutputStream fos = new FileOutputStream(new File("Users").getAbsolutePath().concat("/" + typeSelected) + "/" + id + ".txt");
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(userToAdd);
                //display();
                Admin_name_error.setText("User Added succesfully!!");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminMainScreen.fxml"));
            Parent root = loader.load();
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(new Scene(root));
        }

    @Override
    public void setAdminLayout(Event event, Scene scene) {
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
        setAdminLayout(event,new Scene(root));
        //setScene(event,main_screen_scene);
    }

    public void delete(ActionEvent event) {

        if (studentsTable.getSelectionModel().getSelectedItem() != null) {
            User selectedItem = studentsTable.getSelectionModel().getSelectedItem();
            String file_name = String.valueOf(selectedItem.getId());
            File file = new File("Users/Student" + "/" + file_name + ".txt");


            if (file.delete()) {
                System.out.println("File deleted successfully");
                FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminMainScreen.fxml"));
                Parent root = null;
                try {
                    root = loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(new Scene(root));
            } else {
                System.out.println("Failed to delete the file");
            }
        }

        if (teachersTable.getSelectionModel().getSelectedItem() !=null) {
            User selectedItemm = teachersTable.getSelectionModel().getSelectedItem();
            String file_namee = String.valueOf(selectedItemm.getId());
            File filee = new File("Users/Teacher" + "/" + file_namee + ".txt");


            if (filee.delete()) {
                System.out.println("File deleted successfully");
                FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminMainScreen.fxml"));
                Parent root = null;
                try {
                    root = loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(new Scene(root));
            } else {
                System.out.println("Failed to delete the file");
            }
        }
    }

}
