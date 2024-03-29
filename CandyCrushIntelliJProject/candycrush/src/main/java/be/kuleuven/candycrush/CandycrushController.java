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
    private CandycrushModel model;
    private CandycrushView view;


    private boolean gameStarted = false;

    @FXML
    void initialize() {

        model = new CandycrushModel();
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
            loginBox.setEditable(true);
            resetScoreAndModel();
            btnStart.setDisable(false);
            update();
        }
    }



    private void resetScoreAndModel() {
        model.resetScore();
        model.reset();
    }


    public void onStart(ActionEvent actionEvent) {
        model.setSpeler(loginBox.getText());
        if (!model.getSpeler().isEmpty()) {
            startGame();
        } else {
            loginBox.setStyle("-fx-border-color: red; -fx-background-color: #ffeeee;");
        }
    }

    private void startGame() {
        loginBox.setStyle("");
        loginBox.setEditable(false);
        gameStarted = true;
        btnStart.setDisable(true);
    }




}