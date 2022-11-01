public class Request implements Comparable<Request> {

    private int id;
    private double arrivalTime;
    private double serviceTime;
    private double departureTime;

    public Request(int id, double arrivalTime, double serviceTime) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.serviceTime = serviceTime;
    }

    @Override
    public String toString() {
        return "" + id;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getArrivalTime() {
        return this.arrivalTime;
    }

    public void setArrivalTime(double arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public double getServiceTime() {
        return this.serviceTime;
    }

    public void setServiceTime(double serviceTime) {
        this.serviceTime = serviceTime;
    }

    public double getDepartureTime() {
        return this.departureTime;
    }

    public void setDepartureTime(double departureTime) {
        this.departureTime = departureTime;
    }

    @Override
    public int compareTo(Request o) {
        return o.departureTime < this.departureTime ? 1 : -1;
    }
    
    
}
