

public class Answer {
    private Request request;
    private double departTime;
    private int assigned_CU;
    private int layers_in_EdgeDC;
    private Path path;
    private boolean[] assigned_slots;
    private int bw;
    private double cu_metric;
    private double bw_metric;
    private double transmission_delay;
    private double calculate_delay;

    public Answer(Request request, double departTime, int assigned_CU, int layers_in_EdgeDC, Path path, boolean[] assigned_slots, int bw) {
        this.request = request;
        this.departTime = departTime;
        this.assigned_CU = assigned_CU;
        this.layers_in_EdgeDC = layers_in_EdgeDC;
        this.assigned_slots = assigned_slots;
        this.path = path;
        this.bw = bw;
    }

    public Request getRequest() {
        return request;
    }

    public double getDepartTime() {
        return departTime;
    }

    public int getAssigned_CU() {
        return assigned_CU;
    }

    public int getLayers_in_EdgeDC() {
        return layers_in_EdgeDC;
    }

    public Path getPath() {
        return path;
    }

    public boolean[] getAssigned_slots() {
        return assigned_slots;
    }

    public int getBw() {
        return bw;
    }

    public void setCu_metric(double cu_metric) {
        this.cu_metric = cu_metric;
    }

    public void setBw_metric(double bw_metric) {
        this.bw_metric = bw_metric;
    }

    public double getCu_metric() {
        return cu_metric;
    }

    public double getBw_metric() {
        return bw_metric;
    }

    public double getTransmission_delay() {
        return transmission_delay;
    }

    public double getCalculate_delay() {
        return calculate_delay;
    }

    public void setTransmission_delay(double transmission_delay) {
        this.transmission_delay = transmission_delay;
    }

    public void setCalculate_delay(double calculate_delay) {
        this.calculate_delay = calculate_delay;
    }
}
