package application.model;


import java.util.ArrayList;

public class FileManager {

    private String user;
    private String password;
    
    
    public static Boolean Login(String username, String password) {return csv.verifyUser(username, password);} // NEED TO CALL FileManager INIT AFTER SUCCESS
    public static Boolean UserExists(String user) {return new java.io.File(user + ".csv").exists();}


    private String NewUser() {return csv.NewUser(user, password);}
    
    public FileManager(String user, String password) { 
        this.user = user;
        this.password = password;
        if (!UserExists(user)) {
            NewUser();
        }
    }

    
    public void DeleteUser()                                        {csv.DeleteUser(user, password);}
    public void ChangeTitle(String oldTitle, String newTitle)       {csv.ChangeFile(oldTitle, newTitle, user, password);}
    public ArrayList<String> Load()                                 {return csv.Load(user, password);}
    public Boolean DoesFileExist(String filename, String extension) {return file.detectCollision(user + "_" + filename, extension);}
    public String ReadNote(String filename)                         {return file.Read(user + "_" + filename, password, "txt");}
    public void SaveNote(String filename, String text)              {file.Save(user + "_" + filename, text, password, user, "txt");}
    public void DeleteNote(String filename)                         {note.DeleteNote(filename, user, password);}
    public void NewNote(String filename)                            {note.NewNote(filename, user, password);}

    //public String ReadCSV() {return file.Read(user, password, "csv");}
    // public static String logout(String username, String password) { // NEEDS TESTING
    //     return "LOGOUT";
    // }
    
}