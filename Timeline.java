import java.util.PriorityQueue;
import java.util.Queue;

public class Timeline {

    private Queue<Event> eventTimeline;

    public Timeline() {
        this.eventTimeline = new PriorityQueue<>();
    }

    public void addToTimeline(Event evtToAdd) {
        eventTimeline.add(evtToAdd);
    }

    public Event popNext() {
        return eventTimeline.poll();
    }

}