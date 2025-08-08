import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class noteFiles {

  private File userFile;

  public noteFiles(String username) {
    this.userFile = new File("Notes/" + username + ".txt");
  }

  public static String getContent(String username) throws IOException{
    return Files.readString(Path.of("Notes/" + username + ".txt"));
  }

  public static void saveNotes(String content, String username) throws IOException {
      FileWriter writer = new FileWriter("Notes/" + username + ".txt");
      writer.write(content);
      writer.close();

  }


}
