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
    // GetLength(String), returns the number of lines in the txt file
    // GetNLine(String, int), returns the nth line of the txt file
    // DetectCollision(String), returns true if the filename already exists in the csv file
    // GetDateMade(String), returns the date the file was made
    // Load() returns an arraylist of the filenames in the csv file
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

    public static String Read(String filename) {
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
            return text;
        } else {
            System.out.println("File does not exist"); // FIX
            return "";
        }
    }

    public static void Save(String filename, String text) {
        if (detectCollision(filename)) {
            String txtpath = filename + ".txt";
            try {
                java.io.FileWriter txtFile = new java.io.FileWriter(txtpath);
                txtFile.write(text);
                txtFile.close();
                changeDate(filename);
            } catch (Exception e) {
                System.out.println("Error saving file"); // FIX
            }
        } else {
            System.out.println("File does not exist"); // FIX
        }
    }

    public static void Change(String oldFilename, String newFilename) {
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
                changeDate(newFilename); // a bit inefficient since we can do it in the while loop above, but abstraction, ig
            } catch (Exception e) {
                System.out.println("Error changing file"); // FIX
            }
        } else {
            System.out.println("File does not exist"); // FIX
        }
    }

    public static int getLength(String filename) {
        if (detectCollision(filename)) {
            String txtpath = filename + ".txt";
            int length = 0;
            try {
                java.io.FileReader txt = new java.io.FileReader(txtpath);
                java.io.BufferedReader reader = new java.io.BufferedReader(txt);
                String line;
                while ((line = reader.readLine()) != null) {
                    length++;
                }
                reader.close();
            } catch (Exception e) {
                System.out.println("Error getting length"); // FIX
            }
            return length;
        } else {
            System.out.println("File does not exist"); // FIX
            return 0;
        }
    }

    public static String getNLine(String filename, int n) {
        if (detectCollision(filename)) {
            String txtpath = filename + ".txt";
            String text = "";
            try {
                java.io.FileReader txt = new java.io.FileReader(txtpath);
                java.io.BufferedReader reader = new java.io.BufferedReader(txt);
                String line;
                int i = 1;
                while ((line = reader.readLine()) != null) {
                    if (i == n) {
                        text = line;
                    }
                    i++;
                }
                reader.close();
            } catch (Exception e) {
                System.out.println("Error getting nth line"); // FIX
            }
            return text;
        } else {
            System.out.println("File does not exist"); // FIX
            return "";
        }
    }
    
    public static Boolean detectCollision(String filename) {
        return new java.io.File(filename + ".txt").exists();
    }

    private static void changeDate(String filename) {
        String csvpath = "notes.csv";
        try {
            java.io.FileReader csv = new java.io.FileReader(csvpath);
            java.io.BufferedReader reader = new java.io.BufferedReader(csv);
            // make temp csv file
            java.io.File temp = new java.io.File("temp.csv");
            java.io.FileWriter tempWriter = new java.io.FileWriter(temp);

            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains(filename)) {
                    tempWriter.write(line.split(",")[0] + "," + line.split(",")[1] + "," + LocalDate.now().toString() + "\n");
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
        } catch (Exception e) {
            System.out.println("Error changing file"); // FIX
        }
    }

    public static ArrayList<String> Load() {
        ArrayList<String> notes = new ArrayList<String>();
        String csvpath = "notes.csv";
        try {
            java.io.FileReader csv = new java.io.FileReader(csvpath);
            java.io.BufferedReader reader = new java.io.BufferedReader(csv);
            String line;
            while ((line = reader.readLine()) != null) {
                notes.add(line);
            }
            reader.close();
        } catch (Exception e) {
            System.out.println("Error loading file"); // FIX
        }
        return notes;
    }
}