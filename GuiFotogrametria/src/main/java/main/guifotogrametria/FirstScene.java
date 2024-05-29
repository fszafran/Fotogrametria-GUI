package main.guifotogrametria;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class FirstScene implements Initializable {

    @FXML
    private ComboBox<String> planeComboBox;
    @FXML
    private ComboBox<String> cameraComboBox;
    @FXML
    private TextField hMinText;
    @FXML
    private TextField hMaxText;
    @FXML
    private TextField XPText;
    @FXML
    private TextField YPText;
    @FXML
    private TextField XLText;
    @FXML
    private TextField YLText;
    @FXML
    private TextField GSDText;
    @FXML
    private TextField pText;
    @FXML
    private TextField qText;
    private double p;
    private double q;
    private double absoluteHeight;
    final private Map<String, double[]> cameraMap = new HashMap<>();
    final private Map<String, double[]> planeMap = new HashMap<>();
    private boolean northSouth=false;
    private boolean pChanged=false;
    private boolean qChanged=false;
    public static FlightParams flightParams;

    String convertSeconds(double totalSecs){
        int hours = (int) (totalSecs / 3600);
        int minutes = (int) ((totalSecs % 3600) / 60);
        return String.format("%02d h : %02d m", hours, minutes);
    }
    double[] getDParams(double Xp, double Yp, double Xl, double Yl){
        double Dx = Math.abs(Xp - Xl);
        double Dy = Math.abs(Yl - Yp);
        this.northSouth = !(Dx > Dy);
        return new double[]{Dx,Dy};
    }

    double getAbsoluteHeight(double GSD, double f, double px, double hMax, double hMin){
        double height = (GSD *f)/px;
        double averageHeight = (hMax+hMin)/2;
        return height+averageHeight;
    }
    double[] getNParams(double GSD, double lx, double ly, double p, double q, double Dx, double Dy, double v){
        double Lx = lx *GSD;
        double Ly = ly *GSD;
        double Bx = Lx * (100-p)/100;
        double By = Ly * (100-q)/100;
        double Ny = Dy /By;
        double Nx = Dx /Bx + 4;
        double roofNy = Math.ceil(Ny);
        double roofNx = Math.ceil(Nx);
        double calculatedQ = -((Dy*100)/(roofNy*Ly)-100);
        if(Math.abs(calculatedQ-q)>= 1e-3){
            this.qChanged = true;
        }
        double calculatedP = -((Dx*100)/((roofNx-4)*Lx)-100);
        if(Math.abs(calculatedP-p)>= 1e-3){
            this.pChanged = true;
        }
        calculatedQ = Math.floor(calculatedQ * 10) / 10.0;
        calculatedP = Math.floor(calculatedP * 10) / 10.0;
        this.q = calculatedQ;
        this.p = calculatedP;
        double deltaTime = Bx/v;
        return new double[]{roofNx,roofNy,deltaTime,Bx,By};
    }
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
    private void overLevelAlert(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Przekroczono pułap samolotu");
        alert.setHeaderText("Propozycja: Zmień model samolotu");
        List<String> propositions = new ArrayList<>();
        StringBuilder result = new StringBuilder();
        for(Map.Entry<String, double[]> plane : planeMap.entrySet()){
            double planeMaxLevel = plane.getValue()[2];
            if(this.absoluteHeight<=planeMaxLevel){
                propositions.add(plane.getKey());
            }
        }
        int i=0;
        for (String proposition : propositions){
            if(i<1){
                result.append(proposition);
            }
            else{
                result.append(", ").append(proposition);
            }
            i++;
        }
        alert.setContentText("Proponowane modele: " + result);
        alert.showAndWait();
    }
    public void submit(ActionEvent event) throws IOException {
        try{
            double GSD = Double.parseDouble(GSDText.getText()) / 100;
            double Xp = Double.parseDouble(XPText.getText());
            double Yp = Double.parseDouble(YPText.getText());
            double Xl = Double.parseDouble(XLText.getText());
            double Yl = Double.parseDouble(YLText.getText());
            double[] dParams = getDParams(Xp, Yp, Xl, Yl);
            double dx = dParams[0];
            double dy = dParams[1];
            double hMax = Double.parseDouble(hMaxText.getText());
            double hMin = Double.parseDouble(hMinText.getText());
            double[] chosenCamera = cameraMap.get(cameraComboBox.getValue());
            double[] chosenPlane = planeMap.get(planeComboBox.getValue());
            double maxPlaneLevel = chosenPlane[2];
            double f = chosenCamera[3];
            double cyklPracy = chosenCamera[4];
            double px = chosenCamera[2];
            double planeMaxSpeed = chosenPlane[1];
            double planeMinSpeed = chosenPlane[0];
            double averagePlaneSpeed = (planeMaxSpeed + planeMinSpeed)/2;
            double lx = chosenCamera[0];
            double ly = chosenCamera[1];
            this.absoluteHeight = getAbsoluteHeight(GSD, f, px, hMax, hMin);
            if(this.absoluteHeight> maxPlaneLevel){
                overLevelAlert();
            }
            this.p = Double.parseDouble(pText.getText());
            this.q = Double.parseDouble(qText.getText());
            double[] nParams = getNParams(GSD, ly, lx, this.p, this.q, dx, dy, planeMaxSpeed);
            double nX = nParams[0];
            double nY = nParams[1];
            if(cyklPracy >nParams[2]){
                showAlert("Przekroczono cykl pracy", "Delta t jest mniejsza od cyklu pracy");
            }
            int liczbaZdjec = (int) (nX * nY);
            String formattedTime;
            if(!this.northSouth){
                double totalDistance = dx * nY;
                double totalTime = totalDistance/averagePlaneSpeed + (nY -1)*((double) 140 /60);
                formattedTime = convertSeconds(totalTime);
            }
            else{
                double totalDistance = dy * nY;
                double totalTime = totalDistance/averagePlaneSpeed + (nY -1)*((double) 140 /60);
                formattedTime = convertSeconds(totalTime);
            }
            flightParams = new FlightParams(liczbaZdjec, nY, nX, formattedTime, this.p,this.q, this.northSouth,this.pChanged,this.qChanged);
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("SecondScene.fxml")));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        catch (NullPointerException e){
            showAlert("Puste parametry","Uzupełnij wszystkie pola");
        }
        catch (NumberFormatException e) {
            showAlert("Niepoprawny format wejściowy", "Wprowadź poprawne wartości liczbowe");
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cameraMap.put("Z/I DMC IIe 230", new double[]{15552,14144,5.6e-6, 92e-3, 1.8});
        cameraMap.put("Leica DMC III", new double[]{25728,14592,3.9e-6, 92e-3, 1.9});
        cameraMap.put("UltraCam Falcon", new double[]{17310,11310,6.0e-6, 70e-3, 1.35});
        cameraMap.put("UltraCam Hawk", new double[]{23010,14790,4.6e-6, 80e-3, 1.65});
        double kmToMs = (double) 1000 / 3600;
        planeMap.put("Cessna 402", new double[]{132* kmToMs, 428* kmToMs, 8200, 5});
        planeMap.put("T206H NAV III", new double[]{100* kmToMs,280* kmToMs,4785,5});
        planeMap.put("Vulcan Air P68 Observer 2", new double[]{135* kmToMs,275* kmToMs,6100,6});
        planeMap.put("Tencam MMA", new double[]{120* kmToMs,267* kmToMs,4572,6});
        String[] planes = planeMap.keySet().toArray(new String[0]);
        String[] cameras = cameraMap.keySet().toArray(new String[0]);
        cameraComboBox.getItems().addAll(cameras);
        planeComboBox.getItems().addAll(planes);
    }
}
