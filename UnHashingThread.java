import java.security.NoSuchAlgorithmException;

public class UnHashingThread extends Thread {

    private String to_unhash;
    private int timeout;

    public UnHashingThread(String to_unhash, int timeout) {
        this.to_unhash = to_unhash;
        this.timeout = timeout;
    }

    @Override
    public void run() {
        try {
            int result = new UnHash().unhash(to_unhash, timeout);
            if (result != -1) {
                System.out.println(result);
            }
            else {
                System.out.println(to_unhash);
            }
            // System.out.println(new UnHash().unhash(to_unhash, timeout));
            // Add code to handle timing out on impossible hashes
            

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();

        }
    }

}
