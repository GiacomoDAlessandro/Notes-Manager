import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.control.Button;
import javafx.scene.text.Font;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.geometry.Insets;
import javafx.util.Duration;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

/**
 *
 */
public class NotesGUI extends  Application {

  /**
   *
   */
  private Stage primaryWindow;

  /**
   *
   */
  private Scene homeScreen;

  /**
   *
   */
  private Scene createAccount;

  /**
   *
   */
  private Scene signIn;

  private final Font timesNewRoman = new Font("Times New Roman", 18);

  private final Font Title = new Font("Times New Roman", 24);

  private HashMap<String, String> users = new HashMap<>();

  public void setUpWindow() {
    setUpHome();
    setUpSignIn();
    setUpCreateAccount();
    this.primaryWindow.initStyle(StageStyle.UNIFIED);
    this.primaryWindow.setScene(homeScreen);
    this.primaryWindow.show();
  }

  public void setUpHome() {
    VBox verticalBox = new VBox(16);
    BorderPane layout = new BorderPane();

    Label homeLabel = new Label("Notes Manager");
    homeLabel.setFont(Title);

    Button signInButton = new Button("Sign In");
    signInButton.setFont(timesNewRoman);

    //When sign in is clicked sign-in scene pops up

    signInButton.setOnAction(actionEvent -> {
      this.primaryWindow.setScene(this.signIn);
    });

    Button createAccountButton = new Button("Create Account");

    createAccountButton.setOnAction(actionEvent -> {
      this.primaryWindow.setScene(this.createAccount);
    });
    createAccountButton.setFont(timesNewRoman);

    //When create account button is clicked create-account scene pops up

    verticalBox.getChildren().addAll(homeLabel, signInButton, createAccountButton);
    verticalBox.setAlignment(Pos.TOP_CENTER);
    verticalBox.setPadding(new Insets(250,0,0,0));

    layout.setCenter(verticalBox);

    this.homeScreen = new Scene(layout, 700, 700);
  }

  public void setUpCreateAccount() {

    Account account = new Account();

    BorderPane layout = new BorderPane();
    VBox verticalLayout = new VBox(20);

    Label createAccountLabel = new Label("Create Account");
    createAccountLabel.setFont(Title);

    TextField usernameField = new TextField();
    usernameField.setPromptText("Username: ");
    usernameField.setMaxWidth(250);

    TextField passwordField = new TextField();
    passwordField.setPromptText("Password: ");
    passwordField.setMaxWidth(250);

    Button createAccountButton = new Button("Create Account");

    Alert noUserName = new Alert(Alert.AlertType.ERROR);
    noUserName.setTitle("Username Error");
    noUserName.setContentText("Please Enter A Valid Username");

    Alert noPassword = new Alert(Alert.AlertType.ERROR);
    noPassword.setTitle("Password Error");
    noPassword.setContentText("Please Enter A Valid Password");

    Alert userNameNotAvailable = new Alert(Alert.AlertType.ERROR);
    userNameNotAvailable.setTitle("Username Unavailable");
    userNameNotAvailable.setContentText("Username Already In Use");

    verticalLayout.getChildren().addAll(createAccountLabel, usernameField, passwordField,
        createAccountButton);
    verticalLayout.setAlignment(Pos.TOP_CENTER);
    verticalLayout.setPadding(new Insets(200,0,0,0));


    Button backButton = new Button("Back");

    backButton.setOnAction(actionEvent -> {
      this.primaryWindow.setScene(homeScreen);
    });

    HBox horizontalLayout = new HBox();
    horizontalLayout.getChildren().add(backButton);
    horizontalLayout.setPadding(new Insets(10));
    backButton.setAlignment(Pos.TOP_LEFT);

    layout.setCenter(verticalLayout);
    layout.setTop(horizontalLayout);
    this.createAccount = new Scene(layout, 700, 700);

    createAccountButton.setOnAction(actionEvent -> {

      //Checks that user wrote a username in username textbox
      if (usernameField.getText().isEmpty()) {
        noUserName.showAndWait();

        //Checks that user wrote a password in password textbox
      } else if (passwordField.getText().isEmpty()) {
        noPassword.showAndWait();

        //Checks if username is already in use
      } else if (Account.usernameExists(usernameField.getText())) {
        userNameNotAvailable.showAndWait();

      } else {
        PasswordHashing hasher = new PasswordHashing(usernameField.getText(), passwordField.getText());
        Account.addUser(usernameField.getText(), hasher.getPasswordHash());
        this.users = Account.loadUsers();
        this.primaryWindow.setScene(notesScreen(usernameField.getText()));
      }
      usernameField.clear();
      passwordField.clear();
    });
  }

  public void setUpSignIn() {

    VBox verticalLayout = new VBox(15);

    Label signInLabel = new Label("Sign In");
    signInLabel.setFont(timesNewRoman);

    BorderPane layout = new BorderPane();


    TextField userNameField = new TextField();
    userNameField.setMaxWidth(250);
    userNameField.setPromptText("Username: ");
    TextField PasswordField = new TextField();
    PasswordField.setMaxWidth(250);
    PasswordField.setPromptText("Password: ");

    Button signInButton = new Button("Sign In");

    Alert userNameError = new Alert(Alert.AlertType.ERROR);
    userNameError.setTitle("Username Error");
    userNameError.setContentText("Please Enter A Valid Username");

    Alert passwordError = new Alert(Alert.AlertType.ERROR);
    passwordError.setTitle("Password Error");
    passwordError.setContentText("Please Enter A Valid Password");

    verticalLayout.getChildren().addAll(signInLabel, userNameField, PasswordField, signInButton);
    verticalLayout.setAlignment(Pos.TOP_CENTER);
    verticalLayout.setPadding(new Insets(200,0,0,0));


    Button backButton = new Button("Back");

    backButton.setOnAction(actionEvent -> {
      this.primaryWindow.setScene(homeScreen);
    });

    HBox horizontalLayout = new HBox();
    horizontalLayout.getChildren().add(backButton);
    horizontalLayout.setPadding(new Insets(10));
    backButton.setAlignment(Pos.TOP_LEFT);

    layout.setCenter(verticalLayout);
    layout.setTop(horizontalLayout);


    this.signIn = new Scene(layout, 700, 700);


    Alert userNameDoesNotExist = new Alert(Alert.AlertType.ERROR);
    userNameDoesNotExist.setTitle("Username Error");
    userNameDoesNotExist.setContentText("Username does not exist, please enter a valid username");

    Alert wrongPassword = new Alert(Alert.AlertType.ERROR);
    wrongPassword.setTitle("Password Error");
    wrongPassword.setContentText("Password is Incorrect, please try again");

    signInButton.setOnAction(actionEvent -> {

      String userName = userNameField.getText();
      String password = PasswordField.getText();

      if (userName == null) {
        userNameError.showAndWait();
      } else if (password == null) {
        passwordError.showAndWait();
      } else if (!Account.usernameExists(userName)) {
        userNameDoesNotExist.showAndWait();
        userNameField.clear();
      } else {
        if (signInCorrect(userName, password)) {
          this.primaryWindow.setScene(notesScreen(userName));
          userNameField.clear();
        } else {
          wrongPassword.showAndWait();
        }
      }

      PasswordField.clear();



    });

  }


  public boolean signInCorrect(String username, String password) {
    PasswordHashing attempt = new PasswordHashing(username, password);

    this.users = Account.loadUsers();

    String attemptHash = attempt.getPasswordHash().trim();
    String expected = this.users.get(username).trim();

    return attemptHash.equals(expected);
  }


  //TODO: Setup Notes Screen where users can actually write notes, make sure it gets saved
  //TODO: This gets called with the password and whatever from the sign In Button
  //TODO: Use the Getfile method that I made
  public Scene notesScreen(String username) {
    BorderPane layout = new BorderPane();

    Button backButton = new Button("Sign Out");
    backButton.setOnAction(actionEvent -> {
      this.primaryWindow.setScene(signIn);
    });

    TextArea noteArea = new TextArea();
    noteArea.setPromptText("Ex. Pick up school supplies...");

    try {
      String prevContent = noteFiles.getContent(username);
      noteArea.setText(prevContent);
    } catch (IOException ignore) {}


    noteArea.setPadding(new Insets(25));

    backButton.setAlignment(Pos.TOP_LEFT);

    layout.setTop(backButton);

    layout.setCenter(noteArea);

    Button save = new Button("Save");
    HBox horizontal = new HBox(save);
    save.setAlignment(Pos.CENTER);
    layout.setBottom(horizontal);

    PauseTransition pause = new PauseTransition(Duration.seconds(2));

    save.setOnAction(actionEvent -> {
      try {
        noteFiles.saveNotes(noteArea.getText(), username);
        noteArea.setStyle("-fx-border-color: green;");
        pause.setOnFinished(event -> noteArea.setStyle(""));
        pause.play();
      } catch (IOException e) {
        noteArea.setStyle("-fx-border-color: red;");
        pause.setOnFinished(event -> noteArea.setStyle(""));
        pause.play();
      }

    });



    return new Scene(layout, 700, 700);
  }

  @Override
  public void start(Stage primaryWindow) {
    this.primaryWindow = primaryWindow;
    setUpWindow();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
