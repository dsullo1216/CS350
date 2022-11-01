import java.util.*;

public class Simulator {

    public static double timeBusy;
    public static double queueSize;
    public static double waitingQueueSize;
    public static double busyOnX;
    public static double busyOnY;
    public static double responseTimeX;
    public static double responseTimeY;
    public static double numRequestsX;
    public static double numRequestsY;
    public static double numMonEvents;

    public static double pOut;
    public static double numServices;

    private double duration;
    private double lambdaX;
    private double lambdaY;
    private double TsX;
    private double TsY;
    private String queuePolicy;
    private Timeline timeline;
    private Server server;
    private LoadGenerator xGenerator;
    private LoadGenerator yGenerator;


    public Simulator(double lambdaX, double lambdaY, double TsX, double TsY, String queuePolicy) {

        this.lambdaX = lambdaX;
        this.lambdaY = lambdaY;
        this.TsX = TsX;
        this.TsY = TsY;
        this.queuePolicy = queuePolicy;

        this.timeline = new Timeline();
        this.server = new Server(lambdaX, queuePolicy);
        xGenerator = new LoadGenerator(lambdaX, TsX, timeline);
        yGenerator = new LoadGenerator(lambdaY, TsY, timeline);

        Event x1 = new Event("ARR", 0, 0, "X");
        Event y1 = new Event("ARR", 0, 0, "Y");
        Event m1 = new Event("MON", 0, 0, "M");

        timeline.addToTimeline(x1);
        timeline.addToTimeline(y1);
        timeline.addToTimeline(m1);

    }

    public void simulate(double time) {

        double timeElapsed = 0;

        while (timeElapsed < time) {

            Event event = timeline.popNext();
            timeElapsed = event.getTimestamp();

            switch (event.getLetter()) {
                case "X":
                    xGenerator.releaseRequest(event, server);
                    break;
                case "Y":
                    yGenerator.releaseRequest(event, server);
                    break;
                case "M":
                    if (lambdaX > lambdaY) {
                        xGenerator.releaseRequest(event, server);
                    }
                    else {
                        yGenerator.releaseRequest(event, server);
                    }
                    break;
                default:
                    break;
            }

        }

        System.out.println("UTIL: " + timeBusy / timeElapsed);
        System.out.println("QAVG: " + queueSize / numMonEvents);
        System.out.println("WAVG: " + waitingQueueSize / numMonEvents);
        System.out.println("TRESP X: " + responseTimeX / numRequestsX);
        System.out.println("TWAIT X:" + (responseTimeX - busyOnX) / numRequestsX);
        System.out.println("TRESP Y: " + responseTimeY / numRequestsY);
        System.out.println("TWAIT Y:" + (responseTimeY - busyOnY) / numRequestsY);
        System.out.println("RUNS: " + (numServices) / (numRequestsX + numRequestsY));

    }

    
    public static void main(String[] args) {
        double duration = Double.valueOf(args[0]);
        double lambdaX = Double.valueOf(args[1]);
        double lambdaY = Double.valueOf(args[2]);
        double TsX = Double.valueOf(args[3]);
        double TsY = Double.valueOf(args[4]);
        String queuePolicy = args[5];
        pOut = Double.valueOf(args[6]);

        // double duration = 20000;
        // double lambdaX = 2;
        // double lambdaY = .01;
        // double TsX = .45;
        // double TsY = .01;
        // String queuePolicy = "FIFO";
        // pOut = 1;


        Simulator simulator = new Simulator(lambdaX, lambdaY, TsX, TsY, queuePolicy);
        simulator.simulate(duration);


    }
}