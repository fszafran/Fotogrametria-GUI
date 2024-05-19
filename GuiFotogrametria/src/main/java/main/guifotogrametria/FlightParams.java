package main.guifotogrametria;

public class FlightParams {
    private int liczbaZdjec;
    private int nY;
    private int nX;
    private String czasLotu;
    private double newP;
    private boolean pChanged;
    private boolean qChanged;
    private double newQ;
    private boolean northSouth;
    private double Dx;
    private double Dy;
    private double Lx;
    private double Ly;
    FlightParams (int liczbaZdjec, double nY, double nX, String czasLotu, double newP, double newQ, boolean northSouth,double Dx, double Dy, double Lx, double Ly,boolean pChanged, boolean qChanged){
        this.czasLotu = czasLotu;
        this.liczbaZdjec = liczbaZdjec;
        this.nY = (int)nY;
        this.nX = (int)nX;
        System.out.println("Lesgoo");
        this.newP = newP;
        this.newQ=newQ;
        this.northSouth = northSouth;
        this.Dx = Dx;
        this.Dy = Dy;
        this.Lx = Lx;
        this.Ly = Ly;
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

    public double getDx() {
        return Dx;
    }

    public double getDy() {
        return Dy;
    }

    public double getLx() {
        return Lx;
    }

    public double getLy() {
        return Ly;
    }

    public boolean ispChanged() {
        return pChanged;
    }

    public boolean isqChanged() {
        return qChanged;
    }
}
