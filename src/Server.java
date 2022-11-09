
import java.math.BigInteger;
import java.net.*;
import java.io.*;
import java.util.ArrayList;

class Server{

    public static void main(String[] args){
        try {
            int num = 75;
            int b = 401;
            BigInteger clientP, clientG, clientA, B, K;


            KeyGeneration keyGeneration = new KeyGeneration(num);
            ArrayList<BigInteger> keys = keyGeneration.getKeys();

            BigInteger bigB_pubKey = keys.get(0);
            BigInteger bigB_prvKey = keys.get(1);
            BigInteger bigB_n = keys.get(2);

            CipheredText cipheredText = new CipheredText();

            ServerSocket serv = new ServerSocket(Integer.parseInt("1234"));
            Socket socket = serv.accept();


            //Diffie–Hellman
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            clientP = (BigInteger) ois.readObject();
            clientG = (BigInteger) ois.readObject(); // to accept g
            clientA = (BigInteger) ois.readObject(); // to accept A

            B = clientG.pow(b).mod(clientP);

            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(B);
            oos.flush();

            K = clientA.pow(b).mod(clientP);
            System.out.println("Secret Diffie–hellman key =  " + K);

            //XOR
            bigB_pubKey = bigB_pubKey.xor(K);

            //RSA
            oos.writeObject(bigB_pubKey);
            oos.flush();
            oos.writeObject(bigB_n);
            oos.flush();

            BigInteger bigB_cipherVal = (BigInteger) ois.readObject();
            int mes = cipheredText.getDecryptedText(bigB_cipherVal, bigB_prvKey, bigB_n);
            System.out.println("Received message - " + mes);

            socket.close();
            serv.close();

        }
        catch(Exception e){e.printStackTrace();}
    }

}