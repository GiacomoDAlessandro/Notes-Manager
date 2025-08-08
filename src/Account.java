import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class Account {


  private File userFile;

  /**
   * @param username
   * @param password Hashed Password
   */
  public static void addUser(String username, String password) {
    try {
      BufferedWriter writer = new BufferedWriter(new FileWriter("Users.txt", true));
      writer.write(username + ", " + password);
      writer.newLine();
      writer.close();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    noteFiles userFiles = new noteFiles(username);
  }

  public static HashMap<String, String> loadUsers()  {

    HashMap<String, String> users = new HashMap<>();

    try {
      BufferedReader reader = new BufferedReader(new FileReader("Users.txt"));

      String userName;
      String password;
      String[] partsString;
      String line;
      while ((line = reader.readLine()) != null) {
        partsString = line.split(",");
        userName = partsString[0];
        password = partsString[1];
        users.put(userName, password);
      }
      reader.close();

    } catch (FileNotFoundException e) {
      throw new RuntimeException("File not Found");
    } catch (IOException e) {
      throw new RuntimeException();
    }
    return users;
  }

  public static boolean usernameExists(String userName)  {
    HashMap<String, String> users = null;
    users = loadUsers();
    if (users.get(userName) != null) {
      return true;
    }
    return false;
  }
}
