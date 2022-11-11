import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Pirate {

    private String file_path;
    private int num_threads;
    private int timeout;

    public Pirate(String path_to_file, int n, int timeout) {
        file_path = path_to_file;
        num_threads = n;
        this.timeout = timeout;
    }

    public ArrayList<String> findTreasure() throws FileNotFoundException, InterruptedException {
        // Try to crack the hashes in the file without hints
        ArrayList<String> result = new ArrayList<String>();
        Dispatcher dispatcher = new Dispatcher(file_path, num_threads, timeout);
        dispatcher.fillQueue();
        while (!dispatcher.getQueue().isEmpty()) {
            dispatcher.dispatch();
        }
        
        ArrayList<String> uncrackedHashes = dispatcher.getFinalUncrackedHashes();
        List<Integer> crackedHashesD1 = dispatcher.getFinalCrackedHashes();
        Collections.sort(crackedHashesD1);
        crackedHashesD1 = Collections.synchronizedList(crackedHashesD1);
        for (int i = 0; i < crackedHashesD1.size(); i++) {
            result.add(String.valueOf(crackedHashesD1.get(i)));
        }

        // Try to crack the hashes in the file with hints
        Dispatcher dispatcher2 = new Dispatcher(uncrackedHashes, num_threads, timeout);
        while (!dispatcher2.getQueue().isEmpty()) {
            dispatcher2.dispatch(crackedHashesD1);
        }

        result.addAll(dispatcher2.getFinalCrackedCompoundHashes());
        result.addAll(dispatcher2.getFinalUncrackedHashes());

        return result;

    }



    public static void main(String[] args) throws FileNotFoundException, InterruptedException {
        String file_path = args[0];
        int num_threads = Integer.parseInt(args[1]);
        int timeout = Integer.parseInt(args[2]);
        // String file_path = "hashes.txt";
        // int num_threads = 4;
        // int timeout = 2000;
        Pirate pirate = new Pirate(file_path, num_threads, timeout);
        ArrayList<String> result = pirate.findTreasure();

        for (int i = 0; i < result.size(); i++) {
            System.out.println(result.get(i));
        }
    }
    
}
