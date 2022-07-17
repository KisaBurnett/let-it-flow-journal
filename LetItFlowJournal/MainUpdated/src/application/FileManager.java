package application;

import java.time.LocalDate;
import java.util.ArrayList;

public class FileManager {
    // Constants:
        // notes.csv => name,date_made,date_modified
    // Usages:
        // When passing in a filename, you only use the title, ie the name of the file, but
        // without the .txt
    // New(String), appends filename/date to the csv and creates the txt file
    // Delete(String), deletes txt file and entry from csv file
    // Read(String), reads txt file and returns string of the entire file
    // Save(String, String), overrides the txt file with the string passed in
    // Change(String, String), changes the old filename to the new filename, updates csv entry and the filename
    // DetectCollision(String), returns true if the filename already exists in the csv file
    // changeDate(String), returns the date the file was made
    // Load() returns an arraylist of the filenames in the csv file
    // encrypt() encrypts a string
    // decrypt() decrypts a string
        // The encrypt/decrypt can either be used by the Note class to let the user try to decrypt the private note while it's open, and we won't tell
        // them if they got it or not, or we can have the Homescreen handle that when the user tries to open a note. We can save a boolean to the
        // csv, and if the file is encrypted, you open up a prompt for the password. We can also keep track of how many attempts they've made,
        // or just let them make unlimited attempts, which opens it up to a brute force attack.
    public static void New(String filename) {
        if (detectCollision(filename)) {
            System.out.println("File already exists"); // FIX
        } else {
            LocalDate date = LocalDate.now();
            String dateString = date.toString();
            String entry = filename + "," + dateString + "," + dateString + "\n";
            String csvpath = "notes.csv";
            try {
                java.io.FileWriter csv = new java.io.FileWriter(csvpath, true);
                    csv.write(entry + "\n");
                    csv.close();
                java.io.FileWriter txtFile = new java.io.FileWriter(filename + ".txt");
                    txtFile.close();
            } catch (Exception e) {
                System.out.println("Error creating file"); // FIX
            }
        }
    }

    public static void Delete(String filename) {
        if (detectCollision(filename)) {
            java.io.File file = new java.io.File(filename + ".txt");
            file.delete();
            String csvpath = "notes.csv";
            // loop through csv lines and look for filename in first column
            // if found, delete that line
            try {
                // make temp file
                java.io.File temp = new java.io.File("temp.csv");
                java.io.FileWriter tempWriter = new java.io.FileWriter(temp);
                java.io.FileReader csv = new java.io.FileReader(csvpath);
                java.io.BufferedReader reader = new java.io.BufferedReader(csv);
                // read csv file line by line, then split the string on ','
                // check if first token is filename, if so, delete that line
                String line = reader.readLine();
                while (line != null) {
                    String[] tokens = line.split(",");
                    // System.out.println(tokens[0]);
                    if (tokens[0].equals(filename)) {
                        line = reader.readLine();
                        continue;
                    }
                    line = reader.readLine();
                    tempWriter.write(line + "\n");
                }
                // replace csv file with temp file
                temp.renameTo(new java.io.File(csvpath));
                tempWriter.close();
                csv.close();
                reader.close();

            } catch (Exception e) {
                System.out.println("Error deleting file"); // FIX
            }
        } else {
            System.out.println("File does not exist"); // FIX
        }
    }

    public static String Read(String filename, String password) {
        if (detectCollision(filename)) {
            String txtpath = filename + ".txt";
            String text = "";
            try {
                java.io.FileReader txt = new java.io.FileReader(txtpath);
                java.io.BufferedReader reader = new java.io.BufferedReader(txt);
                String line;
                while ((line = reader.readLine()) != null) {
                    text += line + "\n"; // will add a newline at the end of the file
                }
                reader.close();
            } catch (Exception e) {
                System.out.println("Error loading file"); // FIX
            }
            String decryptedText = decrypt(text, password);
            return decryptedText;
        } else {
            System.out.println("File does not exist"); // FIX
            return "";
        }
    }

    public static void Save(String filename, String text, String password, String username) {
        if (detectCollision(filename)) {
            String txtpath = filename + ".txt";
            String encrypted = encrypt(text, password);
            try {
                java.io.FileWriter txtFile = new java.io.FileWriter(txtpath);
                txtFile.write(encrypted);
                txtFile.close();
                changeDate(filename, username, password);
            } catch (Exception e) {
                System.out.println("Error saving file"); // FIX
            }
        } else {
            System.out.println("File does not exist"); // FIX
        }
    }

    public static void Change(String oldFilename, String newFilename, String username, String password) {
        if (detectCollision(oldFilename)) {
            java.io.File file = new java.io.File(oldFilename + ".txt");
            file.renameTo(new java.io.File(newFilename + ".txt"));
            String csvpath = "notes.csv";
            // loop through csv lines and look for oldFilename in first column
            // if found, replace that line with newFilename
            try {
                java.io.FileReader csv = new java.io.FileReader(csvpath);
                java.io.BufferedReader reader = new java.io.BufferedReader(csv);
                // make temp csv file
                java.io.File temp = new java.io.File("temp.csv");
                java.io.FileWriter tempWriter = new java.io.FileWriter(temp);

                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.contains(oldFilename)) {
                        tempWriter.write(newFilename + "," + line.split(",")[1] + "," + line.split(",")[2] + "\n");
                    }
                    else {
                        // temp.write(line + "\n");
                        tempWriter.write(line + "\n");
                    }
                }
                // replace csv file with temp file
                temp.renameTo(new java.io.File(csvpath));
                tempWriter.close();
                csv.close();
                reader.close();
                changeDate(newFilename, username, password); // a bit inefficient since we can do it in the while loop above, but abstraction, ig
            } catch (Exception e) {
                System.out.println("Error changing file"); // FIX
            }
        } else {
            System.out.println("File does not exist"); // FIX
        }
    }

    
    
    public static Boolean detectCollision(String filename) {
        return new java.io.File(filename + ".txt").exists();
    }

    private static void changeDate(String filename, String username, String password) {
        String csvpath = username + ".csv";
        try {
            String[] csvText = Read(csvpath, password).split("\n");
            String temptxt = "";
            for (int i = 1; i < csvText.length; i++) {
                if (csvText[i].split(",")[0].equals(filename)) {
                    temptxt += filename + "," + csvText[i].split(",")[1] + "," + LocalDate.now().toString() + "\n";
                } else {
                    temptxt += csvText[i] + "\n";
                }
            }
            // java.io.FileReader csv = new java.io.FileReader(csvpath);
            // java.io.BufferedReader reader = new java.io.BufferedReader(csv);
            // // make temp csv file
            java.io.File temp = new java.io.File("temp.csv");
            java.io.FileWriter tempWriter = new java.io.FileWriter(temp);

            // String line;
            // while ((line = reader.readLine()) != null) {
            //     if (line.contains(filename)) {
            //         tempWriter.write(line.split(",")[0] + "," + line.split(",")[1] + "," + LocalDate.now().toString() + "\n");
            //     }
            //     else {
            //         // temp.write(line + "\n");
            //         tempWriter.write(line + "\n");
            //     }
            // }
            // replace csv file with temp file
            tempWriter.write(temptxt);
            temp.renameTo(new java.io.File(csvpath));
            tempWriter.close();
            // csv.close();
            // reader.close();
        } catch (Exception e) {
            System.out.println("Error changing file"); // FIX
        }
    }

    public static ArrayList<String> Load(String username, String password) {
        ArrayList<String> notes = new ArrayList<String>();
        String csvpath = username + ".csv";
        String text = "";
        try {
            java.io.FileReader csv = new java.io.FileReader(csvpath);
            java.io.BufferedReader reader = new java.io.BufferedReader(csv);
            String line;
            while ((line = reader.readLine()) != null) {
                // notes.add(line);
                text += line + "\n"; // will add a newline at the end of the file
            }

            reader.close();
        } catch (Exception e) {
            System.out.println("Error loading file"); // FIX
        }
        String decryptedText = decrypt(text, password);
        // loop through lines in decryptedText and add to notes arraylist
        // check first line of csv file is the same as the username
        if (decryptedText.split("\n")[0].equals(username)) {
            for (int i = 1; i < decryptedText.split("\n").length; i++) {
                notes.add(decryptedText.split("\n")[i]);
            }
            return notes;
        } else {
            ArrayList<String> error = new ArrayList<String>();
            return error;
        }
    }

    public static String encrypt(String text, String key) {
        // custom encrypt function so we don't need to worry about imports and dependencies
        // Plus we aren't worrying about serious cybersecurity, but rather privacy from most people

        // xor each character in the file with the key
        // then return the encrypted string
        String encrypted = "";
        for (int i = 0; i < text.length(); i++) {
            encrypted += (char) (text.charAt(i) ^ key.charAt(i % key.length()));
        }
        return encrypted;
    }

    public static String decrypt(String text, String key) {
        // custom decrypt function so we don't need to worry about imports and dependencies
        // Plus we aren't worrying about serious cybersecurity, but rather privacy from most people

        // xor each character in the file with the key
        // then return the decrypted string
        String decrypted = "";
        for (int i = 0; i < text.length(); i++) {
            decrypted += (char) (text.charAt(i) ^ key.charAt(i % key.length()));
        }
        return decrypted;
    }
}