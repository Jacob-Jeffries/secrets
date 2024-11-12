package secrets;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.FileInputStream;

public class SecretsTest {
    private byte key = 0x1F;
    private byte[] plainText = { 0x5A, 0x4D, (byte) 0x9C };
    private byte[] cipherText = { 0x1F, 0x45, 0x52, (byte) 0x83 };

    @Test
    public void testEncrypt() {
        try {
            InputStream input = new ByteArrayInputStream(plainText);
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            Secrets.encrypt(key, input, output);
            byte[] cipher = output.toByteArray();
            assertEquals(cipherText.length, cipher.length);
            for(int i = 0; i < cipherText.length; ++i){
                assertEquals(cipherText[i], cipher[i]);
            }
        } catch (IOException ex) {
            fail("Caught an unexpected IOException.");
        }
    }

    @Test
    public void testDecrpyt(){
        try {
            InputStream input = new ByteArrayInputStream(cipherText);
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            Secrets.decrypt(input, output);
            byte[] plain = output.toByteArray();
            assertEquals(plainText.length, plain.length);
            for(int i = 0; i < plainText.length; ++i){
                assertEquals(plainText[i], plain[i]);
            }
        } catch (IOException ex){
            fail("Caught an unexpected IOException.");
        }
    }

    // This test alone green lighting does not mean you are done.
    // There must be a human readable file called story.txt that is legible
    // to a literate human. Check it out and make sure that is true.
    @Test
    public void testCryptFile(){
        try{
          byte key = 73;
          Secrets.decryptFile("cipher", "story.txt");
          Secrets.encryptFile(key, "story.txt", "coded");
          assertTrue(compareFiles("cipher", "cipher"));
        } catch(IOException ex){
            fail("Caught an unexpected IOException.");
        }
    }

    public boolean compareFiles(String filename1, String filename2) throws IOException {
        // open both files as file input streams
        InputStream input1 = null;
        InputStream input2 = null;
        try{
            input1 = new FileInputStream(filename1);
            input2 = new FileInputStream(filename2);
            int nextByte1;
            int nextByte2;
            while((nextByte1 = input1.read()) != -1){
                nextByte2 = input2.read();
                if(nextByte1 != nextByte2){
                    return false;
                }
            }
            return (nextByte2 = input2.read()) == -1;
        } finally {
            if(input1 != null){
                input1.close();
            }
            if(input2 != null){
                input2.close();
            }
        }
    }
}
