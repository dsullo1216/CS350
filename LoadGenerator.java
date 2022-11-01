public class LoadGenerator {

    private double lambda;
    private double Ts;

    private Timeline timeline;

    public LoadGenerator(double lambda, double Ts, Timeline timeline) {
        this.lambda = lambda;
        this.Ts = Ts;
        this.timeline = timeline;
    }
 
    public void releaseRequest(Event evt, Server server) {
        
        int newID = evt.getID() + 1;
        double newTime;

        if (evt.getType().equals("ARR")) {

            double serviceTime = Exp.getExp(1 / Ts);

            System.out.println(evt.getLetter() + evt.getID() + " ARR: " + evt.getTimestamp() + " LEN: " + serviceTime);

            Request newRequest = new Request(evt.getID(), evt.getTimestamp(), serviceTime);
            server.addRequest(newRequest);


            newTime = evt.getTimestamp() + Exp.getExp(lambda);
            Event newEvent = new Event(evt.getType(), newTime, newID, evt.getLetter());
            timeline.addToTimeline(newEvent);

            if (server.getServerQueue().size() == 1) {

                Request request = server.serveRequest();
                request.setDepartureTime(evt.getTimestamp());
                Event death = new Event("DONE", evt.getTimestamp() + request.getServiceTime(), newID, evt.getLetter());
                timeline.addToTimeline(death);

                System.out.println(evt.getLetter() + request.getId() + " START: " + request.getDepartureTime());
            }

        }

        else if (evt.getType().equals("DONE")) {
            Request request = server.removeRequest();
            server.removeRequest();

            Simulator.timeBusy += request.getServiceTime();
            Simulator.numServices++;

            if (evt.getLetter().equals("X")) {
                Simulator.busyOnX += request.getServiceTime();
                Simulator.numRequestsX++;
                Simulator.responseTimeX += (request.getServiceTime() + (request.getDepartureTime() - request.getArrivalTime()));
            }
            else {
                Simulator.busyOnY += request.getServiceTime();
                Simulator.numRequestsY++;
                Simulator.responseTimeY += (request.getServiceTime() + (request.getDepartureTime() - request.getArrivalTime()));
            }

            double prob = Math.random();

            if (prob < Simulator.pOut) {
                System.out.println(evt.getLetter() + request.getId() + " DONE: " + evt.getTimestamp());
            }
            else {
                System.out.println(evt.getLetter() + request.getId() + " LOOP: " + evt.getTimestamp());
                Request loopedRequest = new Request(request.getId(), request.getArrivalTime(), Exp.getExp(lambda));
                server.addRequest(loopedRequest);
                Simulator.numServices++;
            }

            if (server.getServerQueue().size() > 0) {

                Request newRequest = server.serveRequest();
                newRequest.setDepartureTime(request.getDepartureTime() + request.getServiceTime());

                Event death = new Event("DONE", evt.getTimestamp() + newRequest.getServiceTime(), newID, evt.getLetter());
                timeline.addToTimeline(death);

                System.out.println(evt.getLetter() + newRequest.getId() + " START: " + evt.getTimestamp());
            }

        }

        else if (evt.getType().equals("MON")) {

            Simulator.numMonEvents++;
            Simulator.queueSize += server.getServerQueue().size();
            Simulator.waitingQueueSize += Math.max(server.getServerQueue().size() - 1, 0);


            Event monitor = new Event("MON", evt.getTimestamp() + Exp.getExp(lambda), newID, "M");
            timeline.addToTimeline(monitor);

        }

    }

    public double getLambda() {
        return this.lambda;
    }

    public void setLambda(double lambda) {
        this.lambda = lambda;
    }

    public double getTs() {
        return this.Ts;
    }

    public void setTs(double Ts) {
        this.Ts = Ts;
    }


}