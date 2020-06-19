package sample.Student;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import sample.Mcq;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Arrays;

public class StudentMcqsScreen implements StudentInterface {
    private String Title;
    private int McqCounter;
    private int TotalMcqs;
    private int[] Record;


    @FXML
    private Label McqOp1, McqOp2, McqOp3, McqOp4, McqBody;
    private String[] McqFileNames;
    private ArrayList<Mcq> McqsArrayList = new ArrayList<>();

    private void ReadData() {
        try {
            File folder = new File("data/" + Title);
            McqFileNames = folder.list();
            TotalMcqs=McqFileNames.length;
            Record = new int[TotalMcqs];
            Arrays.fill(Record,0);

            for (int i =0; i<McqFileNames.length;i++){
                FileInputStream fileIn = new FileInputStream("data/" + Title+"/"+McqFileNames[i]);
                ObjectInputStream objectIn = new ObjectInputStream(fileIn);
                Mcq mcq = (Mcq) objectIn.readObject();
                objectIn.close();
                McqsArrayList.add(mcq);
                System.out.print(mcq.toString()+"\n");
            }
        }

        catch (Exception e){}

}


    @FXML
    Button NextBtn;
    @FXML
    ToggleGroup MyGroup;
    @FXML
    Label McqRadioError;
    public void Next(Event event) {

        if (MyGroup.getSelectedToggle() == null){
            McqRadioError.setText("plz select");
            return;
        }

        String selectedOp = ((RadioButton) MyGroup.getSelectedToggle()).getText();

        if (selectedOp.equals("a.")){
            McqRadioError.setText("a selected");
            Record[McqCounter] =1;
        }
        if (selectedOp.equals("b.")){
            McqRadioError.setText("b selected");
            Record[McqCounter] =2;
        }
        if (selectedOp.equals("c.")){
            McqRadioError.setText("c selected");
            Record[McqCounter] =3;
        }
        if (selectedOp.equals("d.")){
            McqRadioError.setText("d selected");
            Record[McqCounter] =4;
        }
        if (NextBtn.getText().equals("Submit")){
            calculate(event);
            return;
        }
        MyGroup.getSelectedToggle().setSelected(false);
            McqCounter++;

            if (McqCounter<TotalMcqs) {

                McqOp1.setText(McqsArrayList.get(McqCounter).getOp1());
                McqOp2.setText(McqsArrayList.get(McqCounter).getOp2());
                McqOp3.setText(McqsArrayList.get(McqCounter).getOp3());
                McqOp4.setText(McqsArrayList.get(McqCounter).getOp4());
                McqBody.setText((McqCounter+1)+". "+ McqsArrayList.get(McqCounter).getBody());
            }
        if (McqCounter==TotalMcqs-1) {
                NextBtn.setText("Submit");
            }
    }

    private void calculate(Event event) {
        int correct_answers = 0;
        for (int i  = 0; i <TotalMcqs;i++){
            if (Record[i] == McqsArrayList.get(i).getCop()){
                correct_answers++;
            }
        }
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Student/ResultScreen.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(new Scene(root));
        ResultScreen controller = loader.getController();
        controller.setData(correct_answers,TotalMcqs);
    }

    @FXML
    Label SubjectLabel;

    public void setSubject(String selectedItem) {
        Title = selectedItem;
        SubjectLabel.setText(Title);
        ReadData();
        firstMcq();
    }

    private void firstMcq() {
        if (McqsArrayList.size()>0) {
            McqCounter=0;
            McqOp1.setText(McqsArrayList.get(0).getOp1());
            McqOp2.setText(McqsArrayList.get(0).getOp2());
            McqOp3.setText(McqsArrayList.get(0).getOp3());
            McqOp4.setText(McqsArrayList.get(0).getOp4());
            McqBody.setText((McqCounter+1)+". "+McqsArrayList.get(0).getBody());
        }
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