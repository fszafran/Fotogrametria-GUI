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

    private Stage stage;
    private Scene scene;
    private Parent root;
    private int liczbaZdjec = FirstScene.flightParams.getLiczbaZdjec();
    private double nY = FirstScene.flightParams.getnY();
    private double nX = FirstScene.flightParams.getnX();
    private String czasLotu = FirstScene.flightParams.getCzasLotu();
    private double newP = FirstScene.flightParams.getNewP();
    private double newQ=FirstScene.flightParams.getNewQ();
    private boolean northSouth = FirstScene.flightParams.getnorthSouth();
    private double Dx = FirstScene.flightParams.getDx();
    private double Dy = FirstScene.flightParams.getDy();
    private double Lx = FirstScene.flightParams.getLx();
    private double Ly = FirstScene.flightParams.getLy();
    private boolean qChanged = FirstScene.flightParams.isqChanged();
    private boolean pChanged = FirstScene.flightParams.ispChanged();


    public void back(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("FirstScene.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene=new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    int[] rectangleCoords(double Dx, double Dy, double Lx, double Ly, boolean southNorth){
        int xDistance;
        int yDistance;
        if(!southNorth){
            xDistance = (int)((500 * Lx)/Dx);
            System.out.println("x: "+ xDistance);
            yDistance = (int)((560 * Ly)/Dy);
            System.out.println("y: "+ yDistance);
        }
        else{
            xDistance = (int) (500 * Ly);
            yDistance = (int) (560 * Lx);
        }
        return new int []{xDistance,yDistance};
    }
    private void draw(){
        Image image = new Image("/jet.jpg");
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(25);
        imageView.setFitWidth(25);
        imageView.setPreserveRatio(true);
        System.out.println("draw bool: "+ this.northSouth);
        System.out.println("Lx: "+ Lx + " Ly: "+Ly);
        if(!northSouth){
            int topRightX = 550;
            int topRightY = 100;
            int topLeftX = 50;
            int topLeftY = 100;
            int bottomY = 500;
            int rowDistance = (int) ((bottomY-topLeftY) / (this.nY-1));
            int photoDistance = (int) ((topRightX-topLeftX) / (this.nX-1));
            System.out.println("photo: "+photoDistance);
            imageView.setX(topRightX+(int)(photoDistance/2)+2);
            imageView.setY(topLeftY-12.5);
            imageView.setRotate(270);
            drawPane.getChildren().add(imageView);
            for(int i =0; i<this.nY;i++){
                //draw lines
                topRightX = 550;
                Line line = new Line(topRightX,topRightY,topLeftX,topLeftY);
                line.setStrokeWidth(2);
                int controlY = topRightY + (int)rowDistance/2;
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
                    Circle circle = new Circle(topRightX,topLeftY,3);
                    Rectangle rectangle = new Rectangle(topRightX-(int)(photoDistance/2),topLeftY-(int)(rowDistance/2),photoDistance,rowDistance);
                    rectangle.setFill(Color.TRANSPARENT);
                    rectangle.setStroke(Color.LIGHTBLUE);
                    rectangle.setStrokeWidth(1);
                    topRightX-=photoDistance;
                    drawPane.getChildren().addFirst(rectangle);
                    drawPane.getChildren().add(circle);
                }
                topRightY+= (int) rowDistance;
                topLeftY += (int) rowDistance;
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
                int controlX = leftTopX + (int)colDistance/2;
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
                    curve.setStrokeWidth(1);
                    drawPane.getChildren().addAll(line, curve);
                }
                else{
                    drawPane.getChildren().add(line);
                }
                for(int j=0; j<this.nX;j++){
                    Circle circle = new Circle(leftTopX,leftTopY,2);
                    Rectangle rectangle = new Rectangle(leftTopX-(int)(colDistance/2),leftTopY-(int)(photoDistance/2),colDistance,photoDistance);
                    rectangle.setFill(Color.TRANSPARENT);
                    rectangle.setStroke(Color.LIGHTBLUE);
                    rectangle.setStrokeWidth(1);
                    leftTopY+=photoDistance;
                    drawPane.getChildren().addFirst(rectangle);
                    drawPane.getChildren().add(circle);
                }
                leftTopX += (int) colDistance;
                leftBottomX += (int) colDistance;
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
        if(pChanged){
            pLabel.setText("Zmieniono wartosc p, na : " + newP+"%");
        }
        if(qChanged){
            qLabel.setText("Zmieniono wartosc q, na: " + newQ+"%");
        }
    }
}
