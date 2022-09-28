public class Request {

    private LoadGenerator generator;
    private String id;
    private double arrivalTime;
    private double serviceTime;
    private double departureTime;

    public Request(LoadGenerator generator, String id) {
        this.generator = generator;
        this.id = id;
    }

    @Override
    public String toString() {
        return id;
    }

    public LoadGenerator getGenerator() {
        return this.generator;
    }

    public void setGenerator(LoadGenerator generator) {
        this.generator = generator;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
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
    
    
}
