package application.model;

import java.io.*;
import java.time.LocalDate;
import java.util.Random;
import java.util.ArrayList;

/**@author Julian Beltz */

public class csv {
    /** csv file format: filename,date created,date modified */
    
    public static void NewNote(String filename, String user, String password) {
        LocalDate date = LocalDate.now();
        String dateString = date.toString();
        String entry = filename + "," + dateString + "," + dateString + "\n"; /** Can change to storage directory */
        try {
            String csvFile = file.Read(user, password, "csv");
            csvFile = csvFile.trim() + "\n";
            csvFile += entry;
            /** System.out.println(csvFile); */
            file.Save(user, csvFile, password, user, "csv");
        } catch (Exception e) {
            System.out.println("Error adding file to csv"); /* Fix */
        }
    }



    public static void DeleteNote(String filename, String user, String password) {
        /** loop through csv lines and look for filename in first column
         * if found, delete that line */
        try {
            /** make temp file */
            String temp_string = "temp";
            java.util.Random rand = new Random();
            while (file.detectCollision(temp_string, "csv")) { /** in case a user names themselves temp or something similar. This tries to avoid collision and be dynamic */
                temp_string += Integer.toString(rand.nextInt(10));
                if (temp_string.length() > 20) {
                    temp_string = "temp";
                }
            }
            java.io.File temp = new java.io.File(temp_string + ".csv");
            java.io.FileWriter tempWriter = new java.io.FileWriter(temp);
            String file_text = file.Read(user, password, "csv");
            /** read csv file line by line, then split the string on ','
             * check if first token is filename, if so, delete that line */
            String[] lines = file_text.split("\n");
            String new_text = user + "\n";
            for (int i = 1; i < lines.length; i++) {
                String[] tokens = lines[i].split(",");
                if (tokens[0].equals(filename)) {
                    continue;
                } else {
                    /** tempWriter.write(lines[i] + "\n"); */
                    new_text += lines[i] + "\n";
                }
            }
            /** replace csv file with temp file */
            tempWriter.write(file.encrypt(new_text, password));
            tempWriter.close();
            /** delete old csv file */
            new java.io.File(user + ".csv").delete();
            /** rename temp file to csv file */
            temp.renameTo(new java.io.File(user + ".csv"));
            /** See if temp file still exists, and if so, delete it */
            if (file.detectCollision(temp_string, "csv")) {
                new java.io.File(temp_string+".csv").delete();
            }

        } catch (Exception e) {
            System.out.println("Error deleting file"); /** Fix */
        }
    }



    public static void changeDate(String filename, String username, String password) {
        try {
            String[] csvText = file.Read(username, password, "csv").split("\n");
            String temptxt = username + "\n";
            for (int i = 1; i < csvText.length; i++) {
                if (csvText[i].split(",")[0].equals(filename)) {
                    temptxt += filename + "," + csvText[i].split(",")[1] + "," + LocalDate.now().toString() + "\n";
                } else {
                    temptxt += csvText[i] + "\n";
                }
            }
            /** make temp csv file */
            String temp_string = "temp";
            java.util.Random rand = new Random();
            while (file.detectCollision(temp_string, "csv")) { /** in case a user names themselves temp or something similar. This tries to avoid collision and be dynamic */
                temp_string += Integer.toString(rand.nextInt(10));
                if (temp_string.length() > 20) {
                    temp_string = "temp";
                }
            }
            java.io.File temp = new java.io.File(temp_string);
            java.io.FileWriter tempWriter = new java.io.FileWriter(temp);

            temptxt = file.encrypt(temptxt, password);

            /** replace csv file with temp file */
            tempWriter.write(temptxt);
            tempWriter.close();
            /** delete old csv file */
            new java.io.File(username + ".csv").delete();
            /* rename temp file to csv file */
            temp.renameTo(new java.io.File(username + ".csv"));
            /** See if temp file still exists, and if so, delete it */
            if (file.detectCollision(temp_string, "csv")) {
                new java.io.File(temp_string+".csv").delete();
            }
        } catch (Exception e) {
            System.out.println("Error changing file"); /** Fix */
        }
    }




    public static void ChangeFile(String oldFilename, String newFilename, String username, String password) {
        try {
            String[] csvText = file.Read(username, password, "csv").trim().split("\n", -1);
            String temptxt = username + "\n";
            for (int i = 1; i < csvText.length; i++) {
                if (csvText[i].split(",")[0].equals(oldFilename)) {
                    temptxt += newFilename + "," + csvText[i].split(",")[1] + "," + LocalDate.now().toString() + "\n";
                } else {
                    temptxt += csvText[i] + "\n";
                }
            }
            /** make temp csv file */
            String temp_string = "temp";
            java.util.Random rand = new Random();
            while (file.detectCollision(temp_string, "csv")) { /** in case a user names themselves temp or something similar. This tries to avoid collision and be dynamic */
                temp_string += Integer.toString(rand.nextInt(10));
                if (temp_string.length() > 20) {
                    temp_string = "temp";
                }
            }
            java.io.File temp = new java.io.File(temp_string);
            java.io.FileWriter tempWriter = new java.io.FileWriter(temp);

            temptxt = file.encrypt(temptxt, password);

            /** replace csv file with temp file */
            tempWriter.write(temptxt);
            tempWriter.close();
            /** delete old csv file */
            new java.io.File(username + ".csv").delete();
            /** rename temp file to csv file */
            temp.renameTo(new java.io.File(username + ".csv"));
            /** See if temp file still exists, and if so, delete it */
            if (file.detectCollision(temp_string, "csv")) {
                new java.io.File(temp_string+".csv").delete();
            }

            File file = new File(username + "_" + oldFilename + ".txt");
            file.renameTo(new File(username + "_" + newFilename + ".txt"));
        } catch (Exception e) {
            System.out.println("Error changing file"); /** Fix */
        }
    }




    public static String NewUser(String username, String password) {
        /** create new csv file with username and encrypted with password
         * return either "success" or "error" 8 */
        if (file.detectCollision(username, "csv")) {
            return "Error: User already exists";
        }
        try {
            File temp = new File(username + ".csv");
            temp.createNewFile();
            FileWriter tempWriter = new FileWriter(username + ".csv");
            tempWriter.write(file.encrypt(username + "\n", password));
            tempWriter.close();
            return "Success";
        } catch (Exception e) {
            return "Error creating user";
        }
    }




    public static Boolean verifyUser(String username, String password) { /** Need to work on errors to make them more security aligned (not as informative to error) */
        if (password.length() < 1) {
            return false;
        }
        if (file.detectCollision(username, "csv")) {
            String csvFile = file.Read(username, password, "csv");
            /** compare first line to username */
            if (csvFile.split("\n")[0].equals(username)) {
                return true;
            } else {
                return false;
            }
        }
        else {
            return false;
        }
    }



    public static ArrayList<String> Load(String username, String password) {
        if (file.detectCollision(username, "csv")) {
            ArrayList<String> notes = new ArrayList<String>();
            String csvFile = file.Read(username, password, "csv");
            String[] csvText = csvFile.split("\n");
            for (int i = 1; i < csvText.length; i++) {
                notes.add(csvText[i].split(",")[0]);
            }
            return notes;
        }
        else {
            ArrayList<String> error = new ArrayList<String>();
            return error;
        }
    }



    public static void DeleteUser(String username, String password) {
        /** loop through csv file and delete all files with username_name.txt
         * then delete csv file // NEVERMIND, the DeleteNote() does that automatically */
        String csvpath = username + ".csv";
        try {
            String[] csvText = file.Read(username, password, "csv").split("\n");
            for (int i = 1; i < csvText.length; i++) {
                note.DeleteNoteFromCSV(csvText[i].split(",")[0], username);
            }
            java.io.File csv = new java.io.File(csvpath);
            csv.delete();
        } catch (Exception e) {
            System.out.println("Error deleting user"); /** Fix */
        }
    }

}