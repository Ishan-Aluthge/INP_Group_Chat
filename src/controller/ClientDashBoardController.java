package controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class ClientDashBoardController extends Thread {
    public TextField txtField;
    public FileChooser chooser;
    public File path;
    public ImageView imgSendImages;
    public Label lblName;
    public Pane emojiPane;
    public VBox vBox;


    Socket socket;
    BufferedReader bufferedReader;
    PrintWriter printWriter;

    /*public class FX_firstController implements Initializable{
        @FXML
        private ImageView minimize_button;

        @Override
        public void initialize(URL location, ResourceBundle resources) {

        }
    }*/


    public void initialize(){
        emojiPane.setVisible(false);
        String userName = ClientLoginFormController.userName;
        lblName.setText(userName);

        try {
            socket=new Socket("localhost",4000);
            bufferedReader= new BufferedReader(new InputStreamReader(socket.getInputStream()));
            printWriter=new PrintWriter(socket.getOutputStream(),true);
            this.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void btnSendOnAction(ActionEvent actionEvent) throws IOException {
        String message=txtField.getText();
        printWriter.println(lblName.getText()+" : " + message);
        printWriter.flush();
        txtField.clear();
        emojiPane.setVisible(false);
        if (message.equalsIgnoreCase("bye")) {
            Stage stage = (Stage) txtField.getScene().getWindow();
            stage.close();
        }

    }

    public void run() {
        try {
            while (true) {
                String message = bufferedReader.readLine();
                String[] tokens = message.split(" ");
                String command = tokens[0];

                StringBuilder clientMessage = new StringBuilder();
                for (int i = 1; i < tokens.length; i++) {
                    clientMessage.append(tokens[i] + " ");
                }

                String[] messageAr = message.split(" ");
                String string = "";
                for (int i = 0; i < messageAr.length - 1; i++) {
                    string += messageAr[i + 1] + " ";
                }

                Text text = new Text(string);
                String fChar = "";

                if (string.length() > 3) {
                    fChar = string.substring(0, 3);
                }

                if (fChar.equalsIgnoreCase("img")) {
                    string = string.substring(3, string.length() - 1);

                    File file = new File(string);
                    Image image = new Image(file.toURI().toString());

                    ImageView imageView = new ImageView(image);

                    imageView.setFitWidth(150);
                    imageView.setFitHeight(150);

                    HBox hBox = new HBox(10);
                    hBox.setAlignment(Pos.BOTTOM_RIGHT);

                    vBox.setAlignment(Pos.TOP_LEFT);
                    hBox.setAlignment(Pos.CENTER_LEFT);

                    Text text1 = new Text(command + " :");
                    hBox.getChildren().add(text1);
                    hBox.getChildren().add(imageView);

                    Platform.runLater(() -> vBox.getChildren().addAll(hBox));

                } else {
                    TextFlow tempTextFlow = new TextFlow();

                    if (!command.equalsIgnoreCase(lblName.getText() + ":")) {
                        Text name = new Text(command + " ");
                        name.getStyleClass().add("name");
                        tempTextFlow.getChildren().add(name);
                    }

                    tempTextFlow.getChildren().add(text);
                    tempTextFlow.setMaxWidth(500);

                    TextFlow textFlow = new TextFlow(tempTextFlow);
                    HBox hBox = new HBox(12);

                    vBox.setAlignment(Pos.TOP_LEFT);
                    hBox.setAlignment(Pos.CENTER_LEFT);
                    hBox.getChildren().add(textFlow);

                    Platform.runLater(() -> vBox.getChildren().addAll(hBox));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void photoOnMouseClickedOnAction(MouseEvent mouseEvent) {

        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        chooser = new FileChooser();
        chooser.setTitle("Open Image");
        this.path = chooser.showOpenDialog(stage);
        printWriter.println(lblName.getText() + " " + "img" + path.getPath());
        printWriter.flush();
    }


    public void emojiOnMouseClickedOnAction(MouseEvent mouseEvent) {
        if (!emojiPane.isVisible()){
            emojiPane.setVisible(true);
        }else {
            emojiPane.setVisible(false);
        }
    }

    public void smileOnMouseClickedOnAction(MouseEvent mouseEvent) {
        txtField.appendText("\uD83D\uDE03");
    }

    public void cryingOnMouseClickedOnAction(MouseEvent mouseEvent) {
        txtField.appendText("\uD83D\uDE02");
    }

    public void smile2photoOnMouseClickedOnAction(MouseEvent mouseEvent) {
        txtField.appendText("\uD83D\uDE09");
    }

    public void smile3photoOnMouseClickedOnAction(MouseEvent mouseEvent) {
        txtField.appendText("\uD83D\uDE06");
    }

    public void smile4OnMouseClickedOnAction(MouseEvent mouseEvent) {
        txtField.appendText("\uD83D\uDE07");
    }

    public void hateOnMouseClickedOnAction(MouseEvent mouseEvent) {
        txtField.appendText("\uD83D\uDE08");
    }

    public void neutralOnMouseClickedOnAction(MouseEvent mouseEvent) {
        txtField.appendText("\uD83D\uDE10");
    }

    public void smile5OnMouseClickedOnAction(MouseEvent mouseEvent) {
        txtField.appendText("\uD83D\uDE12");
    }

    public void smile6OnMouseClickedOnAction(MouseEvent mouseEvent) {
        txtField.appendText("\uD83D\uDE13");
    }

    public void sadOnMouseClickedOnAction(MouseEvent mouseEvent) {
        txtField.appendText("\uD83D\uDE14");
    }

    public void kissOnMouseClickedOnAction(MouseEvent mouseEvent) {
        txtField.appendText("\uD83D\uDE18");
    }

    public void AddClientOnAction(MouseEvent mouseEvent) throws IOException {

        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/ClientLoginForm.fxml"))));
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.show();
    }

    public void minimizeOnAction(MouseEvent mouseEvent) {
        /*Stage stage = (Stage) minim.getScene().getWindow();
        stage.setIconified(true);
*/
    }
}
