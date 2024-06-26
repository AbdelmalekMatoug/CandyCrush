package be.kuleuven.candycrush;


import be.kuleuven.candycrush.model.BoardSize;
import be.kuleuven.candycrush.model.Candy;
import be.kuleuven.candycrush.model.CandycrushModel;
import be.kuleuven.candycrush.model.Position;
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

        model = model1;
        view = new CandycrushView(model);
        speelbord.getChildren().add(view);
        view.setOnMouseClicked(this::onCandyClicked);
    }

    public void update() {

        scoreText.setText(String.valueOf(model.getScore()));
        view.update();
    }


    public void onCandyClicked(MouseEvent me) {
        update();
        if (gameStarted) {

            int candyIndex = view.getIndexOfClicked(me);
           model.fallDownTo(Position.fromIndex(candyIndex,model.getBoardSize()));
          model.candyWithIndexSelected(Position.fromIndex(candyIndex,model.getBoardSize()));

        }
    }

    public void onReset(ActionEvent actionEvent) {

            loginBox.clear();
            loginBox.setEditable(true);

            resetScoreAndModel();
            btnStart.setDisable(false);
            update();

    }



    private void resetScoreAndModel() {
        model.resetScore();
        model.reset();
    }


    public void onStart(ActionEvent actionEvent) {


        model.setSpeler(loginBox.getText());
        if (!model.getSpeler().isEmpty()) {


            model.maximizeScore();
            update();
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

    CandycrushModel model1 = createBoardFromString("""
   @@o#
   o*#o
   @@**
   *#@@""");

    CandycrushModel model2 = createBoardFromString("""
   #oo##
   #@o@@
   *##o@
   @@*@o
   **#*o""");

       CandycrushModel model3 = createBoardFromString("""
   #@#oo@
   @**@**
   o##@#o
   @#oo#@
   @*@**@
   *#@##*""");


    public static CandycrushModel createBoardFromString(String configuration) {
        var lines = configuration.toLowerCase().lines().toList();
        BoardSize size = new BoardSize(lines.size(), lines.getFirst().length());
        CandycrushModel model = new CandycrushModel("speler",size); // deze moet je zelf voorzien
        for (int row = 0; row < lines.size(); row++) {
            var line = lines.get(row);
            for (int col = 0; col < line.length(); col++) {
                Candy candy = characterToCandy(line.charAt(col));
                Position pos = new Position(size,row,col);
                model.getBoard().replaceCellAt(pos, candy);
            }
        }
        return model;
    }

    private static Candy characterToCandy(char c) {
        return switch(c) {
            case '.' -> null;
            case 'o' -> new Candy.NormalCandy(0);
            case '*' -> new Candy.NormalCandy(1);
            case '#' -> new Candy.NormalCandy(2);
            case '@' -> new Candy.NormalCandy(3);
            default -> throw new IllegalArgumentException("Unexpected value: " + c);
        };
    }
    public static CandycrushModel createModel3() {
        return new CandycrushModel("speler", new BoardSize(8,8));
    }


}