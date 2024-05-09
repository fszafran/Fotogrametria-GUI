package main.guifotogrametria;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.effect.Light;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.QuadCurve;
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
    private Pane drawPane;

    private Stage stage;
    private Scene scene;
    private Parent root;
    private int liczbaZdjec = FirstScene.flightParams.getLiczbaZdjec();
    private double nY = FirstScene.flightParams.getnY();
    private double nX = FirstScene.flightParams.getnX();
    private String czasLotu = FirstScene.flightParams.getCzasLotu();
    private double newP = FirstScene.flightParams.getNewP();
    private double newQ=FirstScene.flightParams.getNewQ();
    private boolean southNorth = FirstScene.flightParams.getnorthSouth();
    private double Dx = FirstScene.flightParams.getDx();
    private double Dy = FirstScene.flightParams.getDy();

    public void back(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("FirstScene.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene=new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    private void draw(){
        Image image = new Image("/jet.jpg");
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(50);
        imageView.setFitWidth(50);
        imageView.setPreserveRatio(true);
        if(!southNorth){
            //drawing pane 400x400
            int topRightX = 350;
            int topRightY = 20;
            int topLeftX = 50;
            int topLeftY = 20;
            int bottomY = 380;
            int rowDistance = (int) ((bottomY-topLeftY) / (this.nY-1));
            int photoDistance = (int) ((topRightX-topLeftX) / (this.nX-1));
            System.out.println("photo: "+photoDistance);
            imageView.setX(topRightX);
            imageView.setY(topLeftY);
            drawPane.getChildren().add(imageView);
            for(int i =0; i<this.nY;i++){
                //draw lines
                topRightX = 350;
                topLeftX = 50;
                Line line = new Line(topRightX,topRightY,topLeftX,topLeftY);
                int controlY = topRightY + (int)rowDistance/2;
                int controlX;
                int curveXStart;
                int curveYStart=topLeftY;
                if(i%2==0){
                    controlX = 10;
                    curveXStart = 50;
                }
                else{
                    controlX = 390;
                    curveXStart = 350;
                }
                if(i!= this.nY-1){
                    QuadCurve curve = new QuadCurve(curveXStart, curveYStart, controlX, controlY, curveXStart, curveYStart + rowDistance);
                    curve.setFill(Color.WHITE);
                    curve.setStroke(Color.BLACK);
                    curve.setStrokeWidth(1);
                    drawPane.getChildren().addAll(line, curve);
                }
                else{
                    drawPane.getChildren().add(line);
                }
                for(int j=0; j<this.nX;j++){
                    Circle circle = new Circle(topRightX-5,topLeftY,2);
                    topRightX-=photoDistance;
                    drawPane.getChildren().add(circle);
                }
                topRightY+= (int) rowDistance;
                topLeftY += (int) rowDistance;
            }

        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        szeregiLabel.setText("Liczba szeregów: "+ (int)nY);
        liczbaWszereguLabel.setText("Liczba zdjęć w szeregu: "+ (int) nX);
        zdjeciaLabel.setText("Liczba zdjęć w sumie: "+ liczbaZdjec);
        czasLabel.setText("Czas przelotu: "+ czasLotu);
        System.out.println(newP);
        System.out.println(newQ);
        draw();
        if(newP != 60){
            pLabel.setText("Zmieniono wartosc p, na : " + newP+"%");
        }
        if(newQ != 30){
            qLabel.setText("Zmieniono wartosc q, na: " + newQ+"%");
        }
    }
}
