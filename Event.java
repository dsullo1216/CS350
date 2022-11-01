public class Event implements Comparable<Event>{

    private String type;
    private double timestamp;
    private int id;
    private String letter;

    public Event(String type, double timestamp, int id, String letter) {
        this.type = type;
        this.timestamp = timestamp;
        this.id = id;
        this.letter = letter;
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

    public int getID() {
        return this.id;
    }

    public int setID(int newID) {
        this.id = newID;
        return this.id;
    }

    public String getLetter() {
        return this.letter;
    }

    public String setLetter(String newLetter) {
        this.letter = newLetter;
        return this.letter;
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