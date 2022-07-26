import java.io.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        FileManager fm = new FileManager("test", "test");
        while (true) {
            // l - login
            // ue - user exists
            // du - delete user
            // ct - change title
            // ld - load
            // dfe - does file exist
            // rn - read note
            // sn - save note
            // dn - delete note
            // nn - new note
            // rcsv - read csv
            // q - quit

            Scanner in = new Scanner(System.in);

            System.out.println("Enter command: ");
            String command = in.nextLine();
            if (command.equals("l")) {
                System.out.println(FileManager.Login(in.nextLine(), in.nextLine()));
            }
            if (command.equals("ue")) {
                System.out.println(fm.UserExists(in.nextLine()));
            }
            if (command.equals("du")) {
                fm.DeleteUser();
            }
            if (command.equals("ct")) {
                fm.ChangeTitle(in.nextLine(), in.nextLine());
            }
            if (command.equals("ld")) {
                System.out.println(fm.Load());
            }
            if (command.equals("dfe")) {
                System.out.println(fm.DoesFileExist(in.nextLine(), in.nextLine()));
            }
            if (command.equals("rn")) {
                System.out.println(fm.ReadNote(in.nextLine()));
            }
            if (command.equals("sn")) {
                fm.SaveNote(in.nextLine(), in.nextLine());
            }
            if (command.equals("dn")) {
                fm.DeleteNote(in.nextLine());
            }
            if (command.equals("nn")) {
                fm.NewNote(in.nextLine());
            }
            if (command.equals("q")) {
                in.close();
                break;
            }
            if (command.equals("rcsv")) {
                System.out.println(fm.ReadCSV());
            }
        }

    }
}
