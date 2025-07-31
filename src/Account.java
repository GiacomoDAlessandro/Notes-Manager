import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class Account {

  /**
   *
   * @param username
   * @param password Hashed Password
   */
  public void addUser(String username, String password) {
    try {
      BufferedWriter writer = new BufferedWriter(new FileWriter("Users.txt", true));
      writer.write(username + ", " + password);
      writer.newLine();
      writer.close();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public HashMap<String, String> loadUsers() throws FileNotFoundException {

    HashMap<String, String> users = new HashMap<>();

    try {
      BufferedReader reader = new BufferedReader(new FileReader("src/Users,txt"));

      String userName;
      String password;
      String[] partsString;
      String line;
      while (reader.readLine() != null) {
        line = reader.readLine();
        partsString = line.split(",");
        userName = partsString[0];
        password = partsString[1];
        users.put(userName, password);
      }

    } catch (FileNotFoundException e) {
      throw new FileNotFoundException("File not Found");
    } catch (IOException e) {
      throw new RuntimeException();
    }
    return users;
  }
}
