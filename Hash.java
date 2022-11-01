import java.math.BigInteger;
import java.security.*;

public class Hash {

    public String hash(int to_hash) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(Integer.toString(to_hash).getBytes());
        byte[] digest = md.digest();
        BigInteger bigInt = new BigInteger(1, digest);
        String hashtext = bigInt.toString(16);
        while (hashtext.length() < 32) {
            hashtext = "0" + hashtext;
        }
        return hashtext;
    }

    // public static void main(String[] args) throws NoSuchAlgorithmException {
    //     System.out.println(hash(12345));
    // }

}