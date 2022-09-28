public class LoadGenerator {

    private String type;
    private double lambda;

    static Timeline timeline = new Timeline();

    public LoadGenerator(String type, double lambda) {
        this.type = type;
        this.lambda = lambda;
    }
 
    public void releaseRequest(Event evt) {
        
        String prevIDNum = evt.getID().substring(1);
        String newID = evt.getType() + String.valueOf(Integer.parseInt(prevIDNum) + 1);
        double newTime = evt.getTimestamp() + Exp.getExp(lambda);

        Event nextEvt = new Event(evt.getType(), newTime, newID);

        timeline.addToTimeline(nextEvt);

    }

    public static void main(String[] args) {
        Event aEvent = new Event("A", 0, "A0");
        Event bEvent = new Event("B", 0, "B0");
        timeline.addToTimeline(aEvent);
        timeline.addToTimeline(bEvent);
        double aLambda = Double.parseDouble(args[0]);
        double bLambda = Double.parseDouble(args[1]);
        double TLength = Double.parseDouble(args[2]);
        LoadGenerator aLoadGenerator = new LoadGenerator("A", aLambda);
        LoadGenerator bLoadGenerator = new LoadGenerator("B", bLambda);

        Event curEvent = timeline.popNext();
        while (curEvent.getTimestamp() < TLength) {
            if (curEvent.getType().equals("A")) {
                aLoadGenerator.releaseRequest(curEvent);
            }
            else {
                bLoadGenerator.releaseRequest(curEvent);
            }
            System.out.println(curEvent);
            curEvent = timeline.popNext();
        }
    }

}