import java.security.NoSuchAlgorithmException;
import java.security.MessageDigest;

public class PasswordHashing {

  private String userName;

  private String password;

  PasswordHashing(String username, String Password) {
    this.userName = username;

    this.password = bytesToString(hashPassword(Password));
  }


  public static byte[] hashPassword(String password) {
    byte[] bytes = password.getBytes();

    try {
      MessageDigest md = MessageDigest.getInstance("SHA-256");
      bytes = md.digest(bytes);
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException(e);
    }

    return bytes;
  }

  private static String bytesToString(byte[] bytes) {
    StringBuilder builder = new StringBuilder();

    for (byte b: bytes) {
      builder.append(String.format("%02x", b));
    }

    return builder.toString();
  }

  public String getUsername() {
    return this.userName;
  }

  public String getPasswordHash() {
    return this.password;
  }

}
