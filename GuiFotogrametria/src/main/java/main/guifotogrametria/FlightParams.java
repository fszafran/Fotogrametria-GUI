package main.guifotogrametria;

public class FlightParams {
    private int liczbaZdjec;
    private int nY;
    private int nX;
    private String czasLotu;
    private double newP;
    private double newQ;
    FlightParams (int liczbaZdjec, double nY, double nX, String czasLotu, double newP, double newQ){
        this.czasLotu = czasLotu;
        this.liczbaZdjec = liczbaZdjec;
        this.nY = (int)nY;
        this.nX = (int)nX;
        System.out.println("Lesgoo");
        this.newP = newP;
        this.newQ=newQ;

    }

    public int getLiczbaZdjec() {
        return liczbaZdjec;
    }

    public double getnY() {
        return nY;
    }

    public String getCzasLotu() {
        return czasLotu;
    }
    public double getNewP(){
        return newP;
    }

    public double getNewQ() {
        return newQ;
    }

    public int getnX() {
        return nX;
    }
}
