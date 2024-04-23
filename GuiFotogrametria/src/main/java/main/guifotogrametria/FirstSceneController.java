package main.guifotogrametria;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.*;

public class FirstSceneController implements Initializable {
    @FXML
    private Button submitButton;
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
    final private double kmToMs = (double) 1000 / 3600;
    private int GSD;
    private double Dx;
    private double Dy;
    private float f;
    private int lx;
    private int ly;
    private int p;
    private int q;
    private int Hmax;
    private int Hmin;
    private double nX;
    private double nY;
    private double deltaTime;
    private float px;
    private float cyklPracy;
    private double maxPlaneLevel;
    private double absoluteHeight;
    final private Map<String, double[]> cameraMap = new HashMap<>();
    final private Map<String, double[]> planeMap = new HashMap<>();

    double[] getDParams(double Xp, double Yp, double Xl, double Yl){
        double Dx = Xp - Xl;
        double Dy = Yl - Yp; // może na odwrót
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
        double Nx = Dx /Bx +4;
        double deltaTime = Bx/v;
        return new double[]{Nx,Ny,deltaTime};
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
        alert.setContentText("Proponowane modele: " + result.toString());
        alert.showAndWait();
    }
    public void submit(){
        try{
            this.GSD = Integer.parseInt(GSDText.getText());
            double Xp = Double.parseDouble(XPText.getText());
            double Yp = Double.parseDouble(YPText.getText());
            double Xl = Double.parseDouble(XLText.getText());
            double Yl = Double.parseDouble(YLText.getText());
            double[] dParams = getDParams(Xp, Yp, Xl, Yl);
            this.Dx = dParams[0];
            this.Dy = dParams[1];
            double hMax = Double.parseDouble(hMaxText.getText());
            double hMin = Double.parseDouble(hMinText.getText());

            //this.absoluteHeight = getAbsoluteHeight(this.GSD, this.f, this.px, hMax, hMin);

            double[] chosenCamera = cameraMap.get(cameraComboBox.getValue());
            double[] chosenPlane = planeMap.get(planeComboBox.getValue());
            this.maxPlaneLevel = chosenPlane[2];
            System.out.println(this.maxPlaneLevel);
            System.out.println(this.absoluteHeight);
            if(this.absoluteHeight>this.maxPlaneLevel){
                overLevelAlert();
            }
            double v = chosenPlane[1];
            double lx = chosenCamera[0];
            double ly = chosenCamera[1];
            double p = 0.0;
            double q = 0.0;
            double[] nParams = getNParams(this.GSD, lx, ly, p, q, this.Dx, this.Dy, v);
            this.nX = nParams[0];
            this.nY = nParams[1];
            this.deltaTime = nParams[2];
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
        planeMap.put("Cessna 402", new double[]{132* kmToMs, 428* kmToMs, 8200, 5});
        planeMap.put("T206H NAV III", new double[]{100* kmToMs,280* kmToMs,4785,5});
        planeMap.put("Vulcan Air P68 Observer 2", new double[]{135* kmToMs,275* kmToMs,6100,6});
        planeMap.put("Tencam MMA", new double[]{120* kmToMs,267* kmToMs,4572,6});
        String[] planes = planeMap.keySet().toArray(new String[0]);
        String[] cameras = cameraMap.keySet().toArray(new String[0]);
        cameraComboBox.getItems().addAll(cameras);
        planeComboBox.getItems().addAll(planes);
        this.absoluteHeight = 5000;
    }
}
