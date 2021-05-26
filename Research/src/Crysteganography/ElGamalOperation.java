/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Crysteganography;

import static Crysteganography.BlowfishOperation.KeyErrorMessage;
import static Crysteganography.BlowfishOperation.isKeyPresent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.math.BigInteger;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author Olat-Yusuf-O
 */
public class ElGamalOperation {
    
     /////////////  AES variables //////////////////////////
   static SecretKey key;
   String secretKey, encryptedSecretKey, decryptedSecretKey;
   
    public static BigInteger generatePublicKey(int private_key)// pk is random element
    {
        // start public key generation
        BigInteger p = new BigInteger("3347"); //large prime number
        BigInteger g = new BigInteger("1574");   // primitive element
        return (g.pow(private_key)).mod(p);  // return the public key.
    }
   
   public static BigInteger elgamalEncrypt(int message,int SenderPrivateKey,int receiverPublicKey)
    {
        BigInteger R_Public_key = new BigInteger(String.valueOf(receiverPublicKey));
        BigInteger K = (R_Public_key.pow(SenderPrivateKey));
        BigInteger M = new BigInteger(String.valueOf(message));
        return (K.multiply(M)).mod(new BigInteger("3347"));
     
    }

    public static BigInteger elgamalDecrypt(int cipherText,int ReceiverPrivateKey,int SenderPublicKey)
    {
         BigInteger Sender_publicKey = new BigInteger(String.valueOf(SenderPublicKey));
         BigInteger Z = (Sender_publicKey.pow(ReceiverPrivateKey)).mod(new BigInteger("3347"));
         Z = Z.modInverse(new BigInteger("3347"));
         BigInteger J = (Z.multiply(new BigInteger(""+cipherText))).mod(new BigInteger("3347"));
         return J;
    }
    
    public String encryptionOperation(String blowfishKeyFile, int receiverPublicKey, int senderPrivateKey) throws FileNotFoundException, IOException, ClassNotFoundException{
        
        if(isKeyPresent(blowfishKeyFile)){
            ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(blowfishKeyFile));
            key = (SecretKey) inputStream.readObject();
            secretKey = Base64.getEncoder().encodeToString(key.getEncoded());//convert blowfish key to string
            encryptedSecretKey = "";
            try{
                //encrypt the blowfish secret key
               int l = secretKey.length();
               for(int i = 0; i<l; i++){
                int a = secretKey.charAt(i);
                char c = (char)Integer.parseInt(elgamalEncrypt(a, senderPrivateKey, receiverPublicKey).toString());
                encryptedSecretKey += c;
                }
            }catch (NumberFormatException ex) {
                Logger.getLogger(BlowfishOperation.class.getName()).log(Level.SEVERE, null, ex);
            }
       }else{ KeyErrorMessage();}
       return encryptedSecretKey;
    }
    
    public SecretKey decryptionOperation(String encryptedSecretKey, int receiverPrivateKey, int senderPublicKey){
        
            decryptedSecretKey = "";
            try{
                //encrypt the blowfish secret key
               int l = encryptedSecretKey.length();
               for(int i = 0; i<l; i++){
                    int a = encryptedSecretKey.charAt(i);
                    char c = (char)Integer.parseInt(elgamalEncrypt(a, receiverPrivateKey, senderPublicKey).toString());
                    decryptedSecretKey += c;
                }
               
               /////
               byte[] decryptedSecretKeyBytes = Base64.getEncoder().encode(decryptedSecretKey.getBytes());
               
             // decode the base64 encoded string
             byte[] decodedKey = Base64.getDecoder().decode(decryptedSecretKeyBytes); // Logger.getLogger(Blowfish.class.getName()).log(Level.SEVERE, null, ex);
            // rebuild key using SecretKeySpec
            key = new SecretKeySpec(decodedKey, 0, decodedKey.length, "Blowfish");
               
            }catch (NumberFormatException ex) {
                Logger.getLogger(BlowfishOperation.class.getName()).log(Level.SEVERE, null, ex);
            }
       return key;
    }
    
}
