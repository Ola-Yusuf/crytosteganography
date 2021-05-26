/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Crysteganography;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.swing.JOptionPane;
import java.util.Base64;


/**
 *
 * @author OLATUNDE YUSUF
 */
public class BlowfishOperation {
       
    static SecretKey Blow_key;
    static  String cipher_text, plain_text;
    long key_time_taken, encrypt_time_taken, decrypt_time_taken = 0;
    
    //KEY GENERATION FUNCTION BLOWFISH
    public static  SecretKey keygen() throws Exception {
      KeyGenerator keygen = KeyGenerator.getInstance("Blowfish");
      Blow_key = keygen.generateKey();
      return Blow_key;
    }
    
    //ENCRYPTION FUNCTION BLOWFISH
    static String doEncrypt(String pt,SecretKey key ) throws Exception
    {
      Cipher c = Cipher.getInstance("Blowfish");
      c.init(Cipher.ENCRYPT_MODE, key);
      byte[] ptBytes = pt.getBytes("utf-8");
      byte[] enc = c.doFinal(ptBytes);
      String str = Base64.getEncoder().encodeToString(enc);
      return str;
    }
    
    //DECRYPTION FUNCTION BLOWFISH
     static String doDecrypt(String ct,SecretKey key ) throws Exception {
      Cipher c = Cipher.getInstance("Blowfish");
      c.init(Cipher.DECRYPT_MODE, key);
       byte[] enc = Base64.getDecoder().decode(ct);
       byte[] ptBytes = c.doFinal(enc);
      String str = new String(ptBytes, "utf-8");
      return str;  
    }
     
    public static String Encrpt_Blow(String plain_text, String keyfile) throws Exception{
         if(isKeyPresent(keyfile)){
          ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(keyfile));
          final SecretKey key = (SecretKey) inputStream.readObject();
            try {
             cipher_text = doEncrypt(plain_text, key);
              } catch (IOException ex) {
                Logger.getLogger(BlowfishOperation.class.getName()).log(Level.SEVERE, null, ex);
            }
            }else{ KeyErrorMessage();}
         return cipher_text; 
    }
     
    public static String Decrpt_Blow( String cipher_text, SecretKey key ) throws Exception{
//       if(isKeyPresent(keyfile)){
//            ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(keyfile));
//            final SecretKey key = (SecretKey) inputStream.readObject();
            try{
               plain_text = doDecrypt(cipher_text,key);
            }catch (Exception ex) {
                Logger.getLogger(BlowfishOperation.class.getName()).log(Level.SEVERE, null, ex);
            }
//       }else{ KeyErrorMessage();}
       return plain_text; 
     }
  
    public static  String CreateKey(String fl){
        File SecretKeyFile = new File(fl);
        // Create files to store key
        if (SecretKeyFile.getParentFile() != null) 
            SecretKeyFile.getParentFile().mkdirs();
        try {
            SecretKeyFile.createNewFile();
            try (ObjectOutputStream SecretKeyOS = new ObjectOutputStream(
                    new FileOutputStream(SecretKeyFile))) {
                SecretKeyOS.writeObject(keygen());
            }
        } catch (IOException ex) {
             Logger.getLogger(BlowfishOperation.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
             Logger.getLogger(BlowfishOperation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    } 
   
    public static boolean isKeyPresent(String ffl) {
        File privateKey = new File(ffl);
        return privateKey.exists();
    }

    public static void KeyErrorMessage(){
        JOptionPane.showMessageDialog(null, "Unable to locate Blowfish Key File \n Please Generate Key and Try Again.");       
    }  
}
