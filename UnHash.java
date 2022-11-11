import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UnHash {

    public int unhash(String to_unhash) throws NoSuchAlgorithmException {
        int curInt = 1;
        Hash hash = new Hash();
        while (true) {
            if (hash.hash(String.valueOf(curInt)).equals(to_unhash)) {
                return curInt;
            }
            curInt++;
        }
    }

    public String unhash(String to_unhash, int timeout) throws NoSuchAlgorithmException {
        int curInt = 1;
        Hash hash = new Hash();
        long startTime = System.currentTimeMillis();
        //long timeDiff = Long.MIN_VALUE;
        while (true) {
            long timeDiff = System.currentTimeMillis() - startTime;
            if (timeout != 0 && timeDiff > timeout) {
                return String.valueOf(-1);
            }
            if (hash.hash(String.valueOf(curInt)).equals(to_unhash)) {
                return String.valueOf(curInt);
            }
            curInt++;
        }
    }

    public String unhash(String to_unhash, int timeout, List<Integer> crackedHashes) throws NoSuchAlgorithmException {
        Hash hash = new Hash();
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < crackedHashes.size(); i++) {
            if (crackedHashes.get(i) == -1) {
                continue;
            }
            for (int j = i + 1; j < crackedHashes.size(); j++) {
                if (crackedHashes.get(j) == -1) {
                    continue;
                }
                for (int k = crackedHashes.get(i) + 1; k < crackedHashes.get(j); k++) {
                    //long timeDiff = System.currentTimeMillis() - startTime;
                    long timeDiff = Long.MIN_VALUE;
                    if (timeout != 0 && timeDiff > timeout) {
                        return String.valueOf(-1);
                    }
                    String hint = String.valueOf(crackedHashes.get(i)) + ";" + String.valueOf(k) + ";" + String.valueOf(crackedHashes.get(j));
                    if (hash.hash(hint).equals(to_unhash)) {
                        crackedHashes.set(i, -1);
                        crackedHashes.set(j, -1);
                        // crackedHashes.remove(i);
                        // crackedHashes.remove(j-1);
                        return hint;
                    }

                }
            }
        }
        return String.valueOf(-1);
    }

    // public static void main(String[] args) throws NoSuchAlgorithmException {
    //     String to_unhash = args[0];
    //     System.out.println(new UnHash().unhash(to_unhash));
    // }

}
