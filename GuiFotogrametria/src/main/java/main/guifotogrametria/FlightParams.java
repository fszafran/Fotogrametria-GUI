package main.guifotogrametria;

public class FlightParams {
    private final int liczbaZdjec;
    private final int nY;
    private final int nX;
    private final String czasLotu;
    private final double newP;
    private final boolean pChanged;
    private final boolean qChanged;
    private final double newQ;
    private final boolean northSouth;

    FlightParams (int liczbaZdjec, double nY, double nX, String czasLotu, double newP, double newQ, boolean northSouth,boolean pChanged, boolean qChanged){
        this.czasLotu = czasLotu;
        this.liczbaZdjec = liczbaZdjec;
        this.nY = (int)nY;
        this.nX = (int)nX;
        System.out.println("Lesgoo");
        this.newP = newP;
        this.newQ=newQ;
        this.northSouth = northSouth;
        this.qChanged=qChanged;
        this.pChanged=pChanged;
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
    public boolean getnorthSouth(){
        return northSouth;
    }

    public boolean ispChanged() {
        return pChanged;
    }

    public boolean isqChanged() {
        return qChanged;
    }
}
