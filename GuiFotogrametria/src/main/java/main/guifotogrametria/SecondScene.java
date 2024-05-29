package main.guifotogrametria;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.QuadCurve;
import javafx.scene.shape.Rectangle;
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

    private final int liczbaZdjec = FirstScene.flightParams.getLiczbaZdjec();
    private final double nY = FirstScene.flightParams.getnY();
    private final double nX = FirstScene.flightParams.getnX();
    private final String czasLotu = FirstScene.flightParams.getCzasLotu();
    private final double newP = FirstScene.flightParams.getNewP();
    private final double newQ=FirstScene.flightParams.getNewQ();
    private final boolean northSouth = FirstScene.flightParams.getnorthSouth();
    private final boolean qChanged = FirstScene.flightParams.isqChanged();
    private final boolean pChanged = FirstScene.flightParams.ispChanged();


    public void back(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("FirstScene.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    private void draw(){
        Image image = new Image("/jet.jpg");
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(25);
        imageView.setFitWidth(25);
        imageView.setPreserveRatio(true);
        if(!northSouth){
            int topRightX = 550;
            int topRightY = 150;
            int topLeftX = 50;
            int topLeftY = 150;
            int bottomY = 450;
            int rowDistance = (int) ((bottomY-topLeftY) / (this.nY-1));
            int photoDistance = (int) ((topRightX-topLeftX) / (this.nX-1));
            imageView.setX(topRightX+ (double) photoDistance /2 +2);
            imageView.setY(topLeftY-12.5);
            imageView.setRotate(270);
            drawPane.getChildren().add(imageView);
            for(int i =0; i<this.nY;i++){
                //draw lines
                topRightX = 550;
                Line line = new Line(topRightX,topRightY,topLeftX,topLeftY);
                line.setStrokeWidth(2);
                int controlY = topRightY + rowDistance/2;
                int controlX;
                int curveXStart;
                int curveYStart=topLeftY;
                if(i%2==0){
                    controlX = 10;
                    curveXStart = 50;
                }
                else{
                    controlX = 590;
                    curveXStart = 550;
                }
                if(i!= this.nY-1){
                    QuadCurve curve = new QuadCurve(curveXStart, curveYStart, controlX, controlY, curveXStart, curveYStart + rowDistance);
                    curve.setFill(Color.TRANSPARENT);
                    curve.setStroke(Color.BLACK);
                    curve.setStrokeWidth(2);
                    drawPane.getChildren().addAll(line,curve);

                }
                else{
                    drawPane.getChildren().add(line);
                }
                for(int j=0; j<this.nX;j++){
                    circles(topLeftY, topRightX, photoDistance, rowDistance, j);
                    topRightX -= photoDistance;
                }
                topRightY+= rowDistance;
                topLeftY += rowDistance;
            }

        }
        else {
            int rightX = 500;
            int leftX = 100;
            int leftBottomY = 550;
            int leftBottomX = 100;
            int leftTopY = 50;
            int leftTopX = 100;
            int colDistance = (int) ((rightX-leftX) / (this.nY-1));
            int photoDistance = (int) ((leftBottomY-leftTopY) / (this.nX-1));
            imageView.setX(leftTopX-12.5);
            imageView.setY(leftTopY-(photoDistance)-12.5);
            imageView.setRotate(180);
            drawPane.getChildren().add(imageView);
            for(int i =0; i<this.nY;i++){
                leftTopY = 50;
                Line line = new Line(leftBottomX,leftBottomY,leftTopX,leftTopY);
                line.setStrokeWidth(2);
                int controlX = leftTopX + colDistance /2;
                int controlY;
                int curveXStart = leftTopX;
                int curveYStart;
                if(i%2!=0){
                    controlY = 10;
                    curveYStart = 50;
                }
                else{
                    controlY = 590;
                    curveYStart = 550;
                }
                if(i!=this.nY-1){
                    QuadCurve curve = new QuadCurve(curveXStart,curveYStart,controlX,controlY,curveXStart+colDistance,curveYStart);
                    curve.setFill(Color.TRANSPARENT);
                    curve.setStroke(Color.BLACK);
                    curve.setStrokeWidth(2);
                    drawPane.getChildren().addAll(line, curve);
                }
                else{
                    drawPane.getChildren().add(line);
                }
                for(int j=0; j<this.nX;j++){
                    circles(leftTopY, leftTopX, colDistance, photoDistance, j);
                    leftTopY+=photoDistance;
                }
                leftTopX += colDistance;
                leftBottomX += colDistance;
            }

        }
    }

    private void circles(int leftTopY, int leftTopX, int colDistance, int photoDistance, int j) {
        Circle circle = new Circle(leftTopX,leftTopY,3);
        if(j>1 && j <this.nX - 2) {
            Rectangle rectangle = new Rectangle(leftTopX - ((double) colDistance / 2), leftTopY - ((double) photoDistance / 2), colDistance, photoDistance);
            rectangle.setFill(Color.TRANSPARENT);
            rectangle.setStroke(Color.LIGHTBLUE);
            rectangle.setStrokeWidth(2);
            drawPane.getChildren().addFirst(rectangle);
        }
        drawPane.getChildren().add(circle);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        szeregiLabel.setText("Liczba szeregów: "+ (int)nY);
        liczbaWszereguLabel.setText("Liczba zdjęć w szeregu: "+ (int) nX);
        zdjeciaLabel.setText("Liczba zdjęć w sumie: "+ liczbaZdjec);
        czasLabel.setText("Czas przelotu: "+ czasLotu);
        draw();
        if(pChanged){
            pLabel.setText("Zmieniono wartosc p, na : " + newP+"%");
        }
        if(qChanged){
            qLabel.setText("Zmieniono wartosc q, na: " + newQ+"%");
        }
    }
}
