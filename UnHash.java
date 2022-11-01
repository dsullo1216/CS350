import java.security.NoSuchAlgorithmException;

public class UnHash {

    public int unhash(String to_unhash) throws NoSuchAlgorithmException {
        int curInt = 1;
        Hash hash = new Hash();
        while (true) {
            if (hash.hash(curInt).equals(to_unhash)) {
                return curInt;
            }
            curInt++;
        }
    }

    public int unhash(String to_unhash, int timeout) throws NoSuchAlgorithmException {
        int curInt = 1;
        Hash hash = new Hash();
        while (true) {
            if (hash.hash(curInt).equals(to_unhash)) {
                return curInt;
            }
            curInt++;
        }
    }

    public static void main(String[] args) throws NoSuchAlgorithmException {
        String to_unhash = args[0];
        System.out.println(new UnHash().unhash(to_unhash));
    }
    
}
