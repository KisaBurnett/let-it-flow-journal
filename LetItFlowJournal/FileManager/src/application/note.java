/* import java.time.LocalDate; */

public class note {

    public static void NewNote(String filename, String user, String password) {
        if (file.detectCollision(user + "_" + filename, "txt")) { /* CAN CHANGE THIS LINE TO POINT TO STORAGE DIRECTORY */
            System.out.println("File already exists"); /* Fix */
        } else {
            try {
                csv.NewNote(filename, user, password);
                java.io.FileWriter txtFile = new java.io.FileWriter(user + "_" + filename + ".txt");
                    txtFile.close();
            } catch (Exception e) {
                System.out.println("Error creating file"); /* Fix */
            }
        }
    }



    public static void DeleteNoteFromCSV(String filename, String user) {
        if (file.detectCollision(user + "_" + filename, "txt")) {
            java.io.File file = new java.io.File(user + "_" + filename + ".txt");
            file.delete();
        } else {
            System.out.println("File does not exist"); /* Fix */
        }
    }


    public static void DeleteNote(String filename, String user, String password) {
        if (file.detectCollision(user + "_" + filename, "txt")) {
            java.io.File file = new java.io.File(user + "_" + filename + ".txt");
            file.delete();
            csv.DeleteNote(filename, user, password);
        } else {
            System.out.println("File does not exist"); /* Fix */
        }
    }



    

}
