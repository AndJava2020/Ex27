package org.example;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class PrimaryController {
    @FXML
    public Button primaryButton;
    @FXML
    public Label answer;
    @FXML
    public TextField field;


    @FXML
    public void initialize(){
        primaryButton.setOnAction(actionEvent -> {

            Rpn rpn=new Rpn();

            answer.setText("answer: " +  rpn.rpnRes(   field.getText()));


        });


    }

}
