public class Event implements Comparable<Event>{

    private String type;
    private double timestamp;
    private String id;

    public Event(String type, double timestamp, String id) {
        this.type = type;
        this.timestamp = timestamp;
        this.id = id;
    }

    public String getType() {
        return this.type;
    }

    public String setType(String newType) {
        this.type = newType;
        return this.type;
    }

    public double getTimestamp() {
        return this.timestamp;
    }

    public double setTimestamp(double newTimestamp) {
        this.timestamp = newTimestamp;
        return this.timestamp;
    }

    public String getID() {
        return this.id;
    }

    public String setID(String newID) {
        this.id = newID;
        return this.id;
    }

    @Override
    public int compareTo(Event o) {
        return o.timestamp < this.timestamp ? 1 : -1;
    }

    @Override
    public String toString() {
        return this.id + ": " + Double.toString(this.timestamp);
    }
}