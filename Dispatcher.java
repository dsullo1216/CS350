import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;

public class Dispatcher {

    private String file_path;
    private int num_threads;
    private int timeout;
    private LinkedBlockingDeque<String> queue;
    public static List<String> uncrackedHashes;
    public static List<Integer> crackedHashes;
    public static List<String> crackedCompoundHashes;

    private ArrayList<String> finalUncrackedHashes;
    private ArrayList<Integer> finalCrackedHashes;
    private ArrayList<String> finalCrackedCompoundHashes;

    public Dispatcher(String path_to_file, int n, int timeout) throws FileNotFoundException {
        file_path = path_to_file;
        num_threads = n;
        this.timeout = timeout;
        queue = new LinkedBlockingDeque<String>();
        // make uncracked hashes a synchronized list
        uncrackedHashes = Collections.synchronizedList(new ArrayList<String>());
        crackedHashes = Collections.synchronizedList(new ArrayList<Integer>());
        crackedCompoundHashes = Collections.synchronizedList(new ArrayList<String>());
        finalUncrackedHashes = new ArrayList<String>();
        finalCrackedHashes = new ArrayList<Integer>();
        finalCrackedCompoundHashes = new ArrayList<String>();

    }

    public Dispatcher(ArrayList<String> hashes, int n, int timeout) {
        num_threads = n;
        this.timeout = timeout;
        queue = new LinkedBlockingDeque<String>();
        uncrackedHashes = Collections.synchronizedList(new ArrayList<String>());
        crackedHashes = Collections.synchronizedList(new ArrayList<Integer>());
        crackedCompoundHashes = Collections.synchronizedList(new ArrayList<String>());
        finalUncrackedHashes = new ArrayList<String>();
        finalCrackedHashes = new ArrayList<Integer>();
        finalCrackedCompoundHashes = new ArrayList<String>();
        for (String hash : hashes) {
            queue.add(hash);
        }
    }

    public void fillQueue() throws FileNotFoundException {

        Scanner scanner = new Scanner(new File(file_path));
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            queue.add(line);
        }
    }

    public void dispatch() throws InterruptedException {

        ExecutorService executor = Executors.newFixedThreadPool(num_threads);

        for (int i = 0; i < num_threads; i++) {
            if (queue.isEmpty()) {
                executor.shutdown();
                Thread.sleep(2000);
                finalUncrackedHashes.addAll(uncrackedHashes);
                finalCrackedHashes.addAll(crackedHashes);
                finalCrackedCompoundHashes.addAll(crackedCompoundHashes);
                return;
            }
            executor.execute(new UnHashingThread(queue.remove(), timeout, uncrackedHashes));
            // UnHashingThread unHashingThread = new UnHashingThread(queue.remove(), timeout, uncrackedHashes);
            // unHashingThread.start();
            // try {
            //     unHashingThread.join();
            // } catch (InterruptedException e) {
            //     e.printStackTrace();
            // }
            //uncrackedHashes.addAll(unHashingThread.getUncrackedHashes());
            //crackedHashes.addAll(unHashingThread.getCrackedHashes());
        }
        executor.shutdown();

    }

    public void dispatch(List<Integer> crackedHashes) throws InterruptedException {

        ExecutorService executor = Executors.newFixedThreadPool(num_threads);
        for (int i = 0; i < num_threads; i++) {
            if (queue.isEmpty()) {
                executor.shutdown();
                Thread.sleep(2500);
                finalUncrackedHashes.addAll(uncrackedHashes);
                finalCrackedHashes.addAll(crackedHashes);
                finalCrackedCompoundHashes.addAll(crackedCompoundHashes);
                return;
            }
            executor.execute(new UnHashingThread(queue.remove(), timeout, uncrackedHashes, crackedHashes));
            // UnHashingThread unHashingThread = new UnHashingThread(queue.remove(), timeout, uncrackedHashes, crackedHashes);
            // unHashingThread.start();
            // try {
            //     unHashingThread.join();
            // } catch (InterruptedException e) {
            //     e.printStackTrace();
            // }
            //uncrackedHashes = unHashingThread.getUncrackedHashes();
            //crackedHashes = unHashingThread.getCrackedHashes();
            // crackedCompoundHashes.addAll(unHashingThread.getCrackedCompoundHashes());
        }
        executor.shutdown();

    }

    public Queue<String> getQueue() {
        return queue;
    }

    public void setQueue(LinkedBlockingDeque<String> queue) {
        this.queue = queue;
    }

    public List<String> getUncrackedHashes() {
        return uncrackedHashes;
    }

    public void setUncrackedHashes(ArrayList<String> uncrackedHashes1) {
        uncrackedHashes = uncrackedHashes1;
    }

    public List<Integer> getCrackedHashes() {
        return crackedHashes;
    }

    public void setCrackedHashes(ArrayList<Integer> crackedHashes1) {
        crackedHashes = crackedHashes1;
    }

    public List<String> getCrackedCompoundHashes() {
        return crackedCompoundHashes;
    }

    public void setCrackedCompoundHashes(ArrayList<String> crackedCompoundHashes1) {
        crackedCompoundHashes = crackedCompoundHashes1;
    }

    public ArrayList<String> getFinalUncrackedHashes() {
        return finalUncrackedHashes;
    }

    public void setFinalUncrackedHashes(ArrayList<String> finalUncrackedHashes) {
        this.finalUncrackedHashes = finalUncrackedHashes;
    }

    public ArrayList<Integer> getFinalCrackedHashes() {
        return finalCrackedHashes;
    }

    public void setFinalCrackedHashes(ArrayList<Integer> finalCrackedHashes) {
        this.finalCrackedHashes = finalCrackedHashes;
    }

    public ArrayList<String> getFinalCrackedCompoundHashes() {
        return finalCrackedCompoundHashes;
    }

    public void setFinalCrackedCompoundHashes(ArrayList<String> finalCrackedCompoundHashes) {
        this.finalCrackedCompoundHashes = finalCrackedCompoundHashes;
    }

    // public static void main(String[] args) throws FileNotFoundException, NoSuchAlgorithmException {

    //     String path_to_file = args[0];
    //     int n = Integer.parseInt(args[1]);
    //     Dispatcher dispatcher;
    //     if (args.length > 2) {
    //         int timeout = Integer.parseInt(args[2]);
    //         dispatcher = new Dispatcher(path_to_file, n, timeout);
    //     }
    //     else {
    //         dispatcher = new Dispatcher(path_to_file, n, 0);
    //     }
    //     dispatcher.fillQueue();
    //     while (!dispatcher.queue.isEmpty()) {
    //         dispatcher.dispatch();
    //     }

    // }
    
}
