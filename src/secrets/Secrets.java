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

    }

}
