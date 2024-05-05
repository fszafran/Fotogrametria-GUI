package main.guifotogrametria;

public class FlightParams {
    private int liczbaZdjec;
    private int nY;
    private String wspolczynnikEmpiryczny;
    private String czasLotu;
    FlightParams (int liczbaZdjec, double nY, double wspolczynnikEmpiryczny, String czasLotu){
        this.czasLotu = czasLotu;
        this.liczbaZdjec = liczbaZdjec;
        this.nY = (int)nY;
        this.wspolczynnikEmpiryczny = String.format("%.2f", wspolczynnikEmpiryczny);
        System.out.println("Lesgoo");
    }

    public String getWspolczynnikEmpiryczny() {
        return wspolczynnikEmpiryczny;
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
}
