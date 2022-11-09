import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;
import java.math.BigInteger;
class KeyGeneration {

    private int input;


    KeyGeneration(int input) {
        this.input = input;
    }

    public ArrayList<BigInteger> getKeys() throws Exception {
        ArrayList<BigInteger> keys = new ArrayList();
        Random rand1 = new Random(System.currentTimeMillis());
        Random rand2 = new Random(System.currentTimeMillis() * 10);

        BigInteger bigB_p = BigInteger.probablePrime(32, rand1);
        BigInteger bigB_q = BigInteger.probablePrime(32, rand2);

        BigInteger bigB_n = bigB_p.multiply(bigB_q);
        BigInteger bigB_p_1 = bigB_p.subtract(new BigInteger("1")); //p-1
        BigInteger bigB_q_1 = bigB_q.subtract(new BigInteger("1")); //q-1
        BigInteger bigB_p_1_q_1 = bigB_p_1.multiply(bigB_q_1); // (p-1)*(q-1)
        // generating the correct public key
        while (true) {
            BigInteger BigB_GCD = bigB_p_1_q_1.gcd(new BigInteger("" + input));
            if (BigB_GCD.equals(BigInteger.ONE)) {
                break;
            }
            input++;
        }
        BigInteger bigB_pubKey = new BigInteger("" + input);
        BigInteger bigB_prvKey = bigB_pubKey.modInverse(bigB_p_1_q_1);

        keys.add(bigB_pubKey);
        keys.add(bigB_prvKey);
        keys.add(bigB_n);

        System.out.println("");
        System.out.println(" public key: " + bigB_pubKey + " , " + bigB_n);
        System.out.println(" private key: " + bigB_prvKey + " , " + bigB_n);

        return keys;
    }
}
