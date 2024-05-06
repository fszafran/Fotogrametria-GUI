package main.guifotogrametria;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class SecondScene implements Initializable {
    @FXML
    private Label szeregiLabel;
    @FXML
    private Label zdjeciaLabel;
    @FXML
    private Label liczbaWszereguLabel;
    @FXML
    private Label czasLabel;
    @FXML
    private Label pLabel;
    @FXML
    private Label qLabel;
    @FXML
    private Canvas canvas;

    private Stage stage;
    private Scene scene;
    private Parent root;
    private int liczbaZdjec = FirstScene.flightParams.getLiczbaZdjec();
    private double nY = FirstScene.flightParams.getnY();
    private double nX = FirstScene.flightParams.getnX();
    private String czasLotu = FirstScene.flightParams.getCzasLotu();
    private double newP = FirstScene.flightParams.getNewP();
    private double newQ=FirstScene.flightParams.getNewQ();

    public void back(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("FirstScene.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene=new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        szeregiLabel.setText("Liczba szeregów: "+ (int)nY);
        liczbaWszereguLabel.setText("Liczba zdjęć w szeregu: "+ (int) nX);
        zdjeciaLabel.setText("Liczba zdjęć w sumie: "+ liczbaZdjec);
        czasLabel.setText("Czas przelotu: "+ czasLotu);
        System.out.println(newP);
        System.out.println(newQ);

        if(newP != 60){
            pLabel.setText("Zmieniono wartosc p, na : " + newP+"%");
        }
        if(newQ != 30){
            qLabel.setText("Zmieniono wartosc q, na: " + newQ+"%");
        }
    }
}
