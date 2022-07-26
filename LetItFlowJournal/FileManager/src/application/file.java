import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;


public class file {


    public static Boolean detectCollision(String filename, String ext) {
        return new java.io.File(filename + "." + ext).exists();
    }



    public static String encrypt(String text, String key) {
        /* custom encrypt function so we don't need to worry about imports and dependencies
        Plus we aren't worrying about serious cybersecurity, but rather privacy from most people */

        /* xor each character in the file with the key
        then return the encrypted string */
        String encrypted = "";
        for (int i = 0; i < text.length(); i++) {
            encrypted += (char) (text.charAt(i) ^ key.charAt(i % key.length()));
        }
        return encrypted;
    }



    public static String decrypt(String text, String key) {
        /* custom decrypt function so we don't need to worry about imports and dependencies
        Plus we aren't worrying about serious cybersecurity, but rather privacy from most people */

        /* xor each character in the file with the key
        then return the decrypted string */
        String decrypted = "";
        for (int i = 0; i < text.length(); i++) {
            decrypted += (char) (text.charAt(i) ^ key.charAt(i % key.length()));
        }
        return decrypted;
    }


    public static String Read(String filename, String password, String ext) {
        if (file.detectCollision(filename, ext)) { /* Fix */
            String txtpath = filename + "." + ext;
            String fileContent = "";
            try {
                byte[] bytes = Files.readAllBytes(Paths.get(txtpath));
                fileContent = new String(bytes);
            } catch (Exception e) {
                System.out.println("Error loading file"); /* Fix */
            }
            String decryptedText = decrypt(fileContent, password);
            return decryptedText;
        } else {
            System.out.println("File does not exist"); /* Fix */
            return "";
        }
    }



    public static void ChangeFilename(String oldFilename, String newFilename, String username, String password) {
        if (detectCollision(username + "_" + oldFilename, "txt")) {
            if (detectCollision(username + "_" + newFilename, "txt")) {
                System.out.println("File already exists with that name"); /* Fix */
            } else {
                File oldFile = new File(username + "_" + oldFilename + ".txt");
                File newFile = new File(username + "_" + newFilename + ".txt");
                if (oldFile.renameTo(newFile)) {
                    csv.ChangeFile(oldFilename, newFilename, username, password);
                } else {
                    System.out.println("Error renaming file"); /* Fix */
                }
            }
        } else {
            System.out.println("File does not exist"); /* Fix */
        }
    }



    public static void Save(String filename, String text, String password, String username, String ext) {
        if (detectCollision(filename, ext)) {
            String txtpath = filename + "." + ext;
            String encrypted = encrypt(text.trim(), password);
            try {
                java.io.FileWriter txtFile = new java.io.FileWriter(txtpath);
                txtFile.write(encrypted);
                txtFile.close();
                if (!ext.equals("csv")) {
                    csv.changeDate(filename, username, password);
                }
            } catch (Exception e) {
                System.out.println("Error saving file"); /* Fix */
            }
        } else {
            System.out.println("File does not exist"); /* Fix */
        }
    }

}
