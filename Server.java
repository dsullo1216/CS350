import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class Server {

    private Queue<Request> serverQueue;
    private double lambda;
    private Request currRequest;
    private String policy;

    public Server(double lambda, String policy) {

        if (policy.equals("SJN")) {
            this.serverQueue = new PriorityQueue<Request>();
        }
        else {
            this.serverQueue = new LinkedList<Request>();
        }

        this.lambda = lambda;
        this.policy = policy;

    }

    public void addRequest(Request request) {
        serverQueue.add(request);
    }

    public Request removeRequest() {

        serverQueue.remove(currRequest);
        Request currStored = currRequest;
        currRequest = null;
        return currStored;

    }

    public Request serveRequest() {
        if (this.policy.equals("SJN")) {
            return currRequest = serverQueue.poll();
        }
        else {
            return currRequest = ((LinkedList<Request>) serverQueue).getFirst();
        }

    }

    public Queue<Request> getServerQueue() {
        return this.serverQueue;
    }

    public void setServerQueue(Queue<Request> serverQueue) {
        this.serverQueue = serverQueue;
    }

    public double getLambda() {
        return this.lambda;
    }

    public void setLambda(double lambda) {
        this.lambda = lambda;
    }

    public Request getCurrRequest() {
        return this.currRequest;
    }

    public void setCurrRequest(Request currRequest) {
        this.currRequest = currRequest;
    }

    public String getPolicy() {
        return this.policy;
    }

    public void setPolicy(String policy) {
        this.policy = policy;
    }
    
}