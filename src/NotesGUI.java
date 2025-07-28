import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.control.Button;
import javafx.scene.text.Font;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.control.TextField;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.layout.StackPane;

import java.awt.*;

import static javafx.application.Application.launch;

public class NotesGUI extends  Application {

  private Stage primaryWindow;

  private Scene homeScreen;

  private Scene createAccount;

  private Scene signIn;

  private final Font timesNewRoman = new Font("Times New Roman", 18);

  private final Font Title = new Font("Times New Roman", 24);

  public void setUpWindow() {
    setUpHome();
    setUpSignIn();
    setUpCreateAccount();
    this.primaryWindow.initStyle(StageStyle.UNIFIED);
    this.primaryWindow.setScene(homeScreen);
    this.primaryWindow.show();
  }

  public void setUpHome() {
    VBox verticalBox = new VBox(25);
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

    BorderPane layout = new BorderPane();
    VBox verticalLayout = new VBox(25);

    Label createAccountLabel = new Label("Create Account");
    createAccountLabel.setFont(Title);

    TextField Username = new TextField("Create Username");
    TextField Password = new TextField("Create Password");

    Button createAccountButton = new Button("Create Account");


    Alert noUserName = new Alert(Alert.AlertType.ERROR);
    noUserName.setTitle("Username Error");
    noUserName.setContentText("Please Enter A Valid Username");

    Alert noPassword = new Alert(Alert.AlertType.ERROR);
    noPassword.setTitle("Password Error");
    noPassword.setContentText("Please Enter A Valid Password");

    verticalLayout.getChildren().addAll(createAccountLabel, Username, Password,
        createAccountButton);
    verticalLayout.setAlignment(Pos.TOP_CENTER);
    verticalLayout.setPadding(new Insets(250,0,0,0));

    layout.setCenter(verticalLayout);
    this.createAccount = new Scene(layout, 700, 700);

    createAccountButton.setOnAction(actionEvent -> {
      if (Username.getText().isEmpty()) {
        noUserName.showAndWait();
      } else if (Password.getText().isEmpty()) {
        noPassword.showAndWait();
      }

    });
  }

  public void setUpSignIn() {

    VBox verticalLayout = new VBox(15);

    Label signInLabel = new Label("Sign In");
    signInLabel.setFont(timesNewRoman);

    BorderPane layout = new BorderPane();


    TextField userNameField = new TextField();
    userNameField.setMaxWidth(250);
    userNameField.setText("Username: ");
    TextField PasswordField = new TextField();
    PasswordField.setMaxWidth(250);
    PasswordField.setText("Password: ");

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

    HBox horizontalBox = new HBox();
    horizontalBox.setPadding(new Insets(10));

    horizontalBox.getChildren().add(backButton);
    backButton.setAlignment(Pos.TOP_LEFT);

    layout.setCenter(verticalLayout);
    layout.setTop(horizontalBox);


    this.signIn = new Scene(layout, 700, 700);

    signInButton.setOnAction(actionEvent -> {
      if (userNameField.getText().isEmpty()) {
        userNameError.showAndWait();
      } else if (PasswordField.getText().isEmpty()) {
        passwordError.showAndWait();
      }

    });

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
