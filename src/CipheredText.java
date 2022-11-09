import java.util.*;
import java.math.BigInteger;
class CipheredText{

    CipheredText() {
    }

    public BigInteger getCipheredText(int message, BigInteger bigB_pubKey, BigInteger bigB_n) throws Exception {

        BigInteger bigB_val = new BigInteger(""+message);
        BigInteger bigB_cipherVal = bigB_val.modPow(bigB_pubKey, bigB_n);

        return bigB_cipherVal;
    }

    public int getDecryptedText(BigInteger bigB_cipherVal, BigInteger bigB_prvKey, BigInteger bigB_n) throws Exception {

        BigInteger bigB_plainVal = bigB_cipherVal.modPow(bigB_prvKey, bigB_n);
        int plainVal = bigB_plainVal.intValue();

        return plainVal;
    }

}
