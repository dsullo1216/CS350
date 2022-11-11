import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class UnHashingThread extends Thread {

    private String to_unhash;
    private int timeout;
    private List<String> uncrackedHashes;
    private List<Integer> crackedHashes;
    private List<String> crackedCompoundHashes;


    public UnHashingThread(String to_unhash, int timeout, List<String> results) {
        this.to_unhash = to_unhash;
        this.timeout = timeout;
        this.uncrackedHashes = results;
        this.crackedHashes = new ArrayList<Integer>();
        this.crackedCompoundHashes = new ArrayList<String>();
    }

    public UnHashingThread(String to_unhash, int timeout, List<String> results, List<Integer> crackedHashes) {
        this.to_unhash = to_unhash;
        this.timeout = timeout;
        this.uncrackedHashes = results;
        this.crackedHashes = crackedHashes;
        this.crackedCompoundHashes = new ArrayList<String>();
    }

    public List<String> getUncrackedHashes() {
        return uncrackedHashes;
    }

    public void setUncrackedHashes(ArrayList<String> uncrackedHashes) {
        this.uncrackedHashes = uncrackedHashes;
    }

    public List<Integer> getCrackedHashes() {
        return crackedHashes;
    }

    public void setCrackedHashes(ArrayList<Integer> crackedHashes) {
        this.crackedHashes = crackedHashes;
    }

    public List<String> getCrackedCompoundHashes() {
        return crackedCompoundHashes;
    }

    public void setCrackedCompoundHashes(ArrayList<String> crackedCompoundHashes) {
        this.crackedCompoundHashes = crackedCompoundHashes;
    }


    @Override
    public void run() {
        try {
            String result;
            boolean isCrackedHashes = (crackedHashes.size() == 0);
            result = isCrackedHashes ? new UnHash().unhash(this.to_unhash, this.timeout) : new UnHash().unhash(to_unhash, timeout, crackedHashes);
            // if (this.crackedHashes == null) {
            //     result = new UnHash().unhash(to_unhash, timeout);
            // }
            // else {
            //     result = new UnHash().unhash(to_unhash, timeout, crackedHashes);
            // }
            if (!result.equals("-1")) {
                if (result.contains(";")) {
                    Dispatcher.crackedCompoundHashes.add(result);
                }
                else {
                    Dispatcher.crackedHashes.add(Integer.parseInt(result));
                }
            }
            else {
                Dispatcher.uncrackedHashes.add(to_unhash);
            }
            // System.out.println(new UnHash().unhash(to_unhash, timeout));
            // Add code to handle timing out on impossible hashes
            

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();

        }
    }

}
