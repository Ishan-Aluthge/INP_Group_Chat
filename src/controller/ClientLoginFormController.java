package controller;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ClientLoginFormController {
    public JFXTextField txtUserName;
    public AnchorPane LoginContext;
    public static String userName;

    public void btnLogInOnAction(ActionEvent actionEvent) throws IOException {
        if (txtUserName.getText().length()>0){
            userName =txtUserName.getText();
            setUi("../view/ClientDashBoard");
        }else {
            new Alert(Alert.AlertType.WARNING,"Please Enter User Name").show();
        }

    }

    public void setUi(String location) throws IOException {
        Stage stage = (Stage) LoginContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource(location+".fxml"))));
        stage.show();
    }
}
