package secrets;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Secrets {

  public static void encrypt(byte key, InputStream input, OutputStream output) throws IOException{
    output.write(key);
    int nextByte;
    while((nextByte = input.read()) != -1 ){
      output.write(key ^ nextByte);
    }
  }

  public static void decrypt(InputStream input, OutputStream output) throws IOException {
    byte key = (byte)input.read();
    int nextByte;
    while ((nextByte = input.read()) != -1){
      output.write((byte)(nextByte ^ key));
    }
  }

  public static void decryptFile(String encryptedFile, String outFileName) throws IOException{
    InputStream input = null;
    OutputStream output = null;
    try {
      input = new FileInputStream(encryptedFile);
      output = new FileOutputStream(outFileName);
      decrypt(input, output);
    } catch (IOException e) {
      System.err.println(e.getMessage());
    } finally {
      if(input != null){
        input.close();
      }
      if(output != null){
        output.close();
      }
    }
  }

  public static void encryptFile(byte key, String plainFile, String outFileName) throws IOException{
    InputStream input = null;
    OutputStream output = null;
    try {
      input = new FileInputStream(plainFile);
      output = new FileOutputStream(outFileName);
      encrypt(key, input, output);
    } catch (Exception e) {
      System.err.println(e.getMessage());
    } finally {
      if(input != null){
        input.close();
      }
      if(output != null){
        output.close();
      }
    }
  }

}
