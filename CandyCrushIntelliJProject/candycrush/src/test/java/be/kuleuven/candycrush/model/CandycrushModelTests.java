package be.kuleuven.candycrush.model;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Objects;

public class CandycrushModelTests {

    @Test
    public void test_score_reset(){
        CandycrushModel model = new CandycrushModel("Arne");
        int score = model.getScore();
        assert(score == 0);
    }
    @Test
    public void test_getIndexFromRowColumn(){
        CandycrushModel model = new CandycrushModel();
        int index = model.getIndexFromRowColumn(1,1);
        assert(index ==  9);
    }
    @Test
    public void test_score_Add_positive_input(){
        CandycrushModel model = new CandycrushModel();
        model.addScore(1);
        int score = model.getScore();
        assert(score == 1);
    }
    @Test
    public void test_score_Add_negative_input(){
        CandycrushModel model = new CandycrushModel();
        model.addScore(-1);
        int score = model.getScore();
        assert(score == 0);
    }
    @Test
    void test_CandyWithIndexSelected_negativeIndex() {
        CandycrushModel model = new CandycrushModel();
        // Set up
        int index = -1;
        model.addScore(5);
        int initialScore = model.getScore();
        // Execute
        model.candyWithIndexSelected(index);

        // Verify
        assert(initialScore == model.getScore());
    }

    @Test
    void test_CandyWithIndexSelected_positiveIndex() {
        CandycrushModel model = new CandycrushModel();
        // Set up
        int index = 5;
        model.addScore(5);
        int initialScore = model.getScore();
        // Execute
        model.candyWithIndexSelected(index);

        // Verify
        assert(initialScore == model.getScore());
    }
    @Test
    void test_Speler_set() {
        CandycrushModel model = new CandycrushModel();
        String initiatedPlayer = "testPlayer";
        model.setSpeler(initiatedPlayer);
        String currentPlayer = model.getSpeler();
        assert(Objects.equals(currentPlayer, initiatedPlayer));
    }
    @Test
    void test_getWidth() {
        CandycrushModel model = new CandycrushModel();
        int width = 8;
        int actualWidth = model.getWidth();
        assert(width == actualWidth);
    }
    @Test
    void test_getHeight() {
        CandycrushModel model = new CandycrushModel();
        int height = 8;
        int actualHeight = model.getHeight();
        assert(height == actualHeight);
    }
    @Test
    void test_getSpeelbord() {
        CandycrushModel model = new CandycrushModel();
        List<Integer> speelbord = model.getSpeelbord();
        int expectedSize = model.getWidth() * model.getHeight();
        int actualSpeelbordSize = speelbord.size();
        assert(expectedSize == actualSpeelbordSize);
    }
    //TODO: Delete previous test and test your own code

}
