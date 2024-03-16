package be.kuleuven.candycrush;

import be.kuleuven.candycrush.model.CandycrushModel;
import be.kuleuven.candycrush.view.CandycrushView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class CandycrushController {
    @FXML
    public Button btnReset;
    @FXML
    private TextField loginBox;
    @FXML
    private Label scoreText;
    @FXML
    private Label Label;
    @FXML
    private Button btnStart;
    @FXML
    private AnchorPane speelbord;
    @FXML
    private AnchorPane paneel;

    private CandycrushModel model;
    private CandycrushView view;

    private boolean gameStarted = false;

    @FXML
    void initialize() {
        model = new CandycrushModel(loginBox.getText());
        view = new CandycrushView(model);
        speelbord.getChildren().add(view);
        view.setOnMouseClicked(this::onCandyClicked);
    }

    public void update() {
        scoreText.setText(String.valueOf(model.getScore()));
        view.update();
    }

    public void onCandyClicked(MouseEvent me) {
        if (gameStarted) {
            int candyIndex = view.getIndexOfClicked(me);
            model.candyWithIndexSelected(candyIndex);
            update();
        }


    }

    public void onReset(ActionEvent actionEvent) {
        if (gameStarted) {
            loginBox.clear();
            model.resetScore();
            btnStart.setDisable(false);
            loginBox.setEditable(true);
            model.reset();
            update();
        }
    }

    public void onStart(ActionEvent actionEvent) {
        String username = loginBox.getText();
        if (!username.isEmpty()) {
            loginBox.setStyle("");
            loginBox.setEditable(false);
            gameStarted = true;
            btnStart.setDisable(true);
        } else {
            loginBox.setStyle("-fx-border-color: red; -fx-background-color: #ffeeee;");
        }
    }
}
