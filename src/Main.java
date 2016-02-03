import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        //Read "strFilePath" value from keyboard
        System.out.println("Enter filename");
        String strFilePath = reader.readLine();

        //Read "key" value from keyboard
        System.out.println("Enter key value: ");
        String key = reader.readLine();

        try {
            //Create new RC4SteamCypher object called rc4 with "key" value as argument
            RC4SteamCypher rc4 = new RC4SteamCypher(key);

            //Read message from keyboard
            System.out.println("Enter message to encrypt to " + strFilePath + " file or press Enter to decrypt from this file using current key");
            String message = reader.readLine();

            //If message is not empty, encrypt it and save to file
            if (message.length() != 0) {
                FileOutputStream fos = new FileOutputStream(strFilePath);
                char[] result = rc4.encrypt(message.toCharArray());
                fos.write(new String(result).getBytes("UTF-16"));
                fos.close();
                System.out.println("Your encrypted message was saved to " + strFilePath);
            }

            // Else (if user just pressed enter) decrypt message from file and show it in console
            else {
                System.out.println("Your decrypted message from " + strFilePath + " file: ");
                Path path = Paths.get(strFilePath);
                byte[] data = Files.readAllBytes(path);
                System.out.println(rc4.decrypt(new String(data, "UTF-16").toCharArray()));
            }

        } catch (InvalidKeyException e) {
            System.out.println("InvalidKeyException : " + e);
        } catch (FileNotFoundException ex) {
            System.out.println("FileNotFoundException : " + ex);
        } catch (IOException ioe) {
            System.out.println("IOException : " + ioe);
        }

    }
}
