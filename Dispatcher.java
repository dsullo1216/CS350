import java.io.File;
import java.io.FileNotFoundException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class Dispatcher {

    private String file_path;
    private int num_threads;
    private int timeout;
    private Queue<String> queue;

    public Dispatcher(String path_to_file, int n, int timeout) throws FileNotFoundException {
        file_path = path_to_file;
        num_threads = n;
        this.timeout = timeout;
        queue = new LinkedList<String>();

    }

    public void fillQueue() throws FileNotFoundException {

        Scanner scanner = new Scanner(new File(file_path));
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            queue.add(line);
        }
    }

    public void dispatch() {

        for (int i = 0; i < num_threads; i++) {
            if (queue.isEmpty()) {
                return;
            }
            UnHashingThread unHashingThread = new UnHashingThread(queue.remove(), timeout);
            unHashingThread.start();
        }

    }

    public static void main(String[] args) throws FileNotFoundException, NoSuchAlgorithmException {

        String path_to_file = args[0];
        int n = Integer.parseInt(args[1]);
        int timeout = Integer.parseInt(args[2]);
        Dispatcher dispatcher = new Dispatcher(path_to_file, n, timeout);
        dispatcher.fillQueue();
        while (!dispatcher.queue.isEmpty()) {
            dispatcher.dispatch();
        }

    }
    
}
