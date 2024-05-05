package main.guifotogrametria;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class SecondScene implements Initializable {
    @FXML
    private Label szeregiLabel;
    @FXML
    private Label zdjeciaLabel;
    @FXML
    private Label kLabel;
    @FXML
    private Label czasLabel;

    private int liczbaZdjec = FirstScene.flightParams.getLiczbaZdjec();
    private double nY = FirstScene.flightParams.getnY();
    private String wspolczynnikEmpiryczny = FirstScene.flightParams.getWspolczynnikEmpiryczny();
    private String czasLotu = FirstScene.flightParams.getCzasLotu();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        szeregiLabel.setText("Liczba szeregów: "+ nY);
        zdjeciaLabel.setText("Liczba zdjęć: "+ liczbaZdjec);
        kLabel.setText("Współczynnik K: "+ wspolczynnikEmpiryczny);
        czasLabel.setText("Czas przelotu: "+ czasLotu);
    }
}
