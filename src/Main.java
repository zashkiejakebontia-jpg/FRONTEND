import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;
import javafx.scene.text.Text;


public class Main extends Application {

    private ProgressBar funBar = new ProgressBar(0.0);
    private ProgressBar hungerBar = new ProgressBar(0.0);
    private ProgressBar cleanBar = new ProgressBar(0.0);
    private ProgressBar sleepBar = new ProgressBar(0.0);

    private VBox allBarsBox;
    private MediaPlayer mediaPlayer;
    private CircularProgress levelCircle = new CircularProgress(80);

    @Override
    public void start(Stage stage) {
        Label title = new Label("");
        title.getStyleClass().add("title-label");

        Label userLabel = new Label("Username:");
        userLabel.getStyleClass().add("form-label");

        TextField usernameField = new TextField();
        usernameField.setPromptText("Enter username");
        usernameField.setMaxWidth(220);
        usernameField.getStyleClass().add("form-field");

        VBox userBox = new VBox(5, userLabel, usernameField);
        userBox.setAlignment(Pos.CENTER_LEFT);
        userBox.setMaxWidth(220);

        Label passLabel = new Label("Password:");
        passLabel.getStyleClass().add("form-label");

        Label error = new Label();
        error.getStyleClass().add("error-label");
        error.setVisible(true);

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter password");
        passwordField.setMaxWidth(220);
        passwordField.getStyleClass().add("form-field");

        VBox passBox = new VBox(5, passLabel, passwordField);
        passBox.setAlignment(Pos.CENTER_LEFT);
        passBox.setMaxWidth(220);

        Button loginButton = new Button("🦆 Sign In");
        loginButton.getStyleClass().add("duck-button");
        loginButton.setOnAction(e -> {
            String user = usernameField.getText();
            String pass = passwordField.getText();
            if (user.equals("Zashkie") && pass.equals("1234")) {
                DuckHouse(stage, user);
            } else {
                error.setText("Invalid Username or Password");
                error.setVisible(true);
            }
        });

        VBox vbox = new VBox(15, title, userBox, passBox, loginButton, error);
        vbox.setAlignment(Pos.CENTER);

        VBox.setMargin(userBox, new Insets(60, 0, 0, 0));

        Image bgImage = new Image(getClass().getResource("temporary.png").toExternalForm());
        ImageView bgView = new ImageView(bgImage);
        bgView.setPreserveRatio(false);

        StackPane root = new StackPane(bgView, vbox);
        Scene scene = new Scene(root, 400, 500);

        bgView.fitWidthProperty().bind(scene.widthProperty());
        bgView.fitHeightProperty().bind(scene.heightProperty());

        scene.getStylesheets().add(getClass().getResource("DuckStyle.css").toExternalForm());

        stage.setTitle("QuackMate - Login");
        Image icon = new Image(getClass().getResource("/QuackMate.png").toExternalForm());
        stage.getIcons().add(icon);

        stage.setResizable(false);
        stage.setScene(scene);
        stage.setMaximized(false);
        stage.show();

        String musicFile = getClass().getResource("bgmusic.mp3").toExternalForm();
        Media sound = new Media(musicFile);
        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.play();
    }

    private void DuckHouse(Stage stage, String username) {
        sceneTemplate(stage, "balay.png", username);
    }

    private void kitchen(Stage stage, String username) {
        sceneTemplate(stage, "eat.png", username);
    }

    private void bathRoom(Stage stage, String username) {
        sceneTemplate(stage, "cr.png", username);
    }

    private void bedRoom(Stage stage, String username) {
        sceneTemplate(stage, "room.png", username);
    }

    private BorderPane sceneTemplate(Stage stage, String bgFile, String username) {
        Image backGround = new Image(getClass().getResource(bgFile).toExternalForm());
        ImageView bg = new ImageView(backGround);
        bg.setPreserveRatio(false);

        Image character = new Image(getClass().getResource("").toExternalForm());
        ImageView charac = new ImageView(character);
        charac.setFitWidth(150);
        charac.setPreserveRatio(true);

        Button statsBtn = new Button("📊");
        statsBtn.setId("statsBtn");
        statsBtn.getStyleClass().add("duck-button");

        Button muteBtn = new Button("🔇");
        muteBtn.getStyleClass().add("duck-button");
        muteBtn.setOnAction(e -> {
            if (mediaPlayer != null) {
                if (mediaPlayer.isMute()) {
                    mediaPlayer.setMute(false);
                    muteBtn.setText("🔇");
                } else {
                    mediaPlayer.setMute(true);
                    muteBtn.setText("🔊");
                }
            }
        });

        Button logoutBtn = new Button("🚪");
        logoutBtn.getStyleClass().add("duck-button");
        logoutBtn.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle(null);
            alert.setHeaderText(null);
            alert.setContentText("🦆 Are you sure you want to sign off?");
            ButtonType yesBtn = new ButtonType("Yes");
            ButtonType noBtn = new ButtonType("No");
            alert.getButtonTypes().setAll(yesBtn, noBtn);
            Stage dialogStage = (Stage) alert.getDialogPane().getScene().getWindow();
            dialogStage.initStyle(javafx.stage.StageStyle.UNDECORATED);
            DialogPane dp = alert.getDialogPane();
            dp.getStylesheets().add(getClass().getResource("DuckStyle.css").toExternalForm());
            dp.getStyleClass().add("confirm-part");
            dp.setPrefSize(300, 150);
            alert.showAndWait().ifPresent(response -> {
                if (response == yesBtn) {
                    mediaPlayer.stop();
                    start(stage);
                }
            });
        });

        VBox subMenu = new VBox(5, statsBtn, muteBtn, logoutBtn);
        subMenu.setAlignment(Pos.TOP_CENTER);
        subMenu.setVisible(false);

        Button duckMenuBtn = new Button("🦆");
        duckMenuBtn.getStyleClass().add("duck-button");
        duckMenuBtn.setOnAction(e -> subMenu.setVisible(!subMenu.isVisible()));

        VBox duckMenuWrapper = new VBox(5, duckMenuBtn, subMenu);
        duckMenuWrapper.setAlignment(Pos.TOP_CENTER);

        Button btn1 = new Button("🏠");
        Button btn2 = new Button("\uD83C\uDF73");
        Button btn3 = new Button("🛁");
        Button btn4 = new Button("🛏");

        btn1.getStyleClass().addAll("duck-button", "house-button");
        btn2.getStyleClass().addAll("duck-button", "house-button");
        btn3.getStyleClass().addAll("duck-button", "house-button");
        btn4.getStyleClass().addAll("duck-button", "house-button");

        btn1.setOnAction(e -> DuckHouse(stage, username));
        btn2.setOnAction(e -> kitchen(stage, username));
        btn3.setOnAction(e -> bathRoom(stage, username));
        btn4.setOnAction(e -> bedRoom(stage, username));

        double btnSize = 50;
        btn1.setPrefSize(btnSize, btnSize);
        btn2.setPrefSize(btnSize, btnSize);
        btn3.setPrefSize(btnSize, btnSize);
        btn4.setPrefSize(btnSize, btnSize);
        duckMenuBtn.setPrefSize(btnSize, btnSize);
        statsBtn.setPrefSize(btnSize, btnSize);
        muteBtn.setPrefSize(btnSize, btnSize);
        logoutBtn.setPrefSize(btnSize, btnSize);

        HBox topRow = new HBox(15, btn1, btn2, btn3, btn4, duckMenuWrapper);
        topRow.setAlignment(Pos.TOP_CENTER);
        topRow.setPadding(new Insets(10));

        if (allBarsBox == null) {
            allBarsBox = new VBox(10,
                    createProgressBox("Fun", funBar),
                    createProgressBox("Hunger", hungerBar),
                    createProgressBox("Cleanliness", cleanBar),
                    createProgressBox("Sleep", sleepBar)
            );
            allBarsBox.setAlignment(Pos.CENTER_LEFT);
            allBarsBox.setVisible(false);
        }

        statsBtn.setOnAction(e -> allBarsBox.setVisible(!allBarsBox.isVisible()));

        Button playBtn = new Button("\uD83C\uDFAE");
        playBtn.getStyleClass().add("duck-button");
        playBtn.setOnAction(e -> GamesFrame(stage, username));

        levelCircle.setProgress(0.0);
        levelCircle.setLevel(1);

        HBox bottomBox = new HBox(20, playBtn, allBarsBox, levelCircle);
        bottomBox.setAlignment(Pos.BOTTOM_LEFT);
        bottomBox.setPadding(new Insets(10));
        BorderPane.setAlignment(levelCircle, Pos.BOTTOM_RIGHT);

        BorderPane layout = new BorderPane();
        layout.setTop(topRow);
        layout.setCenter(charac);
        layout.setBottom(bottomBox);

        StackPane root = new StackPane(bg, layout);
        Scene scene = new Scene(root, 400, 500, Color.BLACK);
        scene.getStylesheets().add(getClass().getResource("DuckStyle.css").toExternalForm());

        bg.fitWidthProperty().bind(scene.widthProperty());
        bg.fitHeightProperty().bind(scene.heightProperty());

        stage.setScene(scene);
        stage.setResizable(false);
        stage.setMaximized(false);
        stage.show();

        return layout;
    }

    private void GamesFrame(Stage stage, String username) {
        Image bg = new Image(getClass().getResource("/game.png").toExternalForm());
        ImageView backGround = new ImageView(bg);
        backGround.setPreserveRatio(false);

        Image playIcon = new Image(getClass().getResource("/game2.png").toExternalForm());
        ImageView playView = new ImageView(playIcon);
        playView.setFitWidth(200);
        playView.setFitHeight(200);
        playView.setPreserveRatio(true);

        Button picBtn = new Button();
        picBtn.setGraphic(playView);
        picBtn.getStyleClass().add("duck-button");
        picBtn.setStyle("-fx-background-radius: 0; -fx-padding: 0;");
        picBtn.setOnAction(e -> System.out.println("Picture button 1 clicked!"));
        picBtn.setOnMousePressed(e -> { picBtn.setScaleX(0.9); picBtn.setScaleY(0.9); });
        picBtn.setOnMouseReleased(e -> { picBtn.setScaleX(1.0); picBtn.setScaleY(1.0); });

        Image playIcon2 = new Image(getClass().getResource("/duckierun.png").toExternalForm());
        ImageView playView2 = new ImageView(playIcon2);
        playView2.setFitWidth(200);
        playView2.setFitHeight(200);
        playView2.setPreserveRatio(true);

        Button picBtn2 = new Button();
        picBtn2.setGraphic(playView2);
        picBtn2.getStyleClass().add("duck-button");
        picBtn2.setStyle("-fx-background-radius: 0; -fx-padding: 0;");
        picBtn2.setOnAction(e -> System.out.println("Picture button 2 clicked!"));
        picBtn2.setOnMousePressed(e -> { picBtn2.setScaleX(0.9); picBtn2.setScaleY(0.9); });
        picBtn2.setOnMouseReleased(e -> { picBtn2.setScaleX(1.0); picBtn2.setScaleY(1.0); });

        VBox buttonBox = new VBox(20, picBtn, picBtn2);
        buttonBox.setAlignment(Pos.CENTER);

        Button btn1 = new Button("⬅");
        btn1.getStyleClass().addAll("duck-button", "house-button");
        btn1.setPrefSize(50, 50);
        btn1.setOnAction(e -> DuckHouse(stage, username));

        BorderPane layout = new BorderPane();
        layout.setTop(btn1);
        BorderPane.setAlignment(btn1, Pos.TOP_LEFT);
        layout.setCenter(buttonBox);

        StackPane root = new StackPane(backGround, layout);

        Scene scene = new Scene(root, 400, 500, Color.BLACK);
        scene.getStylesheets().add(getClass().getResource("/DuckStyle.css").toExternalForm());

        backGround.fitWidthProperty().bind(scene.widthProperty());
        backGround.fitHeightProperty().bind(scene.heightProperty());

        stage.setScene(scene);
        stage.setResizable(false);
        stage.setMaximized(false);
        stage.show();
    }

    private VBox createProgressBox(String labelText, ProgressBar bar) {
        Label label = new Label(labelText + ":");
        label.getStyleClass().add("progress-label");
        bar.setPrefWidth(200);
        return new VBox(5, label, bar);
    }

    private void increaseProgress(ProgressBar bar) {
    }

    public static void main(String[] args) {
        launch(args);
    }
}

class CircularProgress extends javafx.scene.canvas.Canvas {
    private double progress = 0.0;
    private int level = 1;
    private Color bgColor = Color.web("#fff9d6");
    private Color progressColor = Color.web("#ffcc00");
    private Color innerColor = Color.WHITE;
    private Color textColor = Color.web("#4A2C00");
    private String fontFamily = "Comic Sans MS";
    private double fontSize = 24;

    public CircularProgress(double size) {
        super(size, size);
        getStyleClass().add("level-bar");
        widthProperty().addListener(e -> draw());
        heightProperty().addListener(e -> draw());
        draw();
    }
    public void setProgress(double value) {
        this.progress = Math.max(0, Math.min(1, value));
        draw();
    }
    public void setLevel(int level) {
        this.level = level;
        draw();
    }

    public void setColors(Color bg, Color prog, Color inner, Color text) {
        this.bgColor = bg;
        this.progressColor = prog;
        this.innerColor = inner;
        this.textColor = text;
        draw();
    }

    public void setFont(String family, double size) {
        this.fontFamily = family;
        this.fontSize = size;
        draw();
    }

    private void draw() {
        double size = getWidth();
        var gc = getGraphicsContext2D();
        gc.clearRect(0, 0, size, size);

        gc.setFill(bgColor);
        gc.fillOval(0, 0, size, size);

        gc.setFill(progressColor);
        gc.fillArc(0, 0, size, size, 90, -progress * 360, javafx.scene.shape.ArcType.ROUND);

        gc.setFill(innerColor);
        gc.fillOval(size * 0.15, size * 0.15, size * 0.7, size * 0.7);

        gc.setFill(textColor);
        gc.setFont(Font.font(fontFamily, fontSize));
        String text = String.valueOf(level);
        Text helper = new Text(text);
        helper.setFont(gc.getFont());
        double textWidth = helper.getLayoutBounds().getWidth();
        gc.fillText(text, (size - textWidth) / 2, size * 0.6);
    }
}

