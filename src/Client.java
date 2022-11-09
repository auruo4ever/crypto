import java.math.BigInteger;
import java.net.*;
import java.io.*;
import java.util.*;
class Client{
    public static void main(String[] args){
        try {
            BigInteger p = new BigInteger("" + 149);
            BigInteger g = new BigInteger("" + 2);
            int a = 307;
            BigInteger K, serverB;

            int message = 1234;

            System.out.println("Our secret message is: " + message);

            InetAddress serverHost = InetAddress.getByName("localhost");
            int serverPortNum = Integer.parseInt("1234");
            Socket clientSocket = new Socket(serverHost, serverPortNum);

            ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());
            oos.writeObject(p);
            oos.flush();
            oos.writeObject(g);
            oos.flush();
            BigInteger A = g.pow(a).mod(p);
            oos.writeObject(A);
            oos.flush();


            // Accepts the data
            ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());
            serverB = (BigInteger) ois.readObject();
            K = serverB.pow(a).mod(p);

            System.out.println("Secret Diffie–hellman key = " + K);

            BigInteger bigB_pubKey = (BigInteger) ois.readObject();
            System.out.println("Received e: " + bigB_pubKey);
            bigB_pubKey = bigB_pubKey.xor(K);
            System.out.println("e xor: " + bigB_pubKey);
            BigInteger bigB_n = (BigInteger) ois.readObject();

            CipheredText cipheredText = new CipheredText();

            BigInteger ciph = cipheredText.getCipheredText(message, bigB_pubKey, bigB_n);

            oos.writeObject(ciph);
            oos.flush();

            clientSocket.close();
        }
        catch(Exception e){e.printStackTrace();}
    }
}