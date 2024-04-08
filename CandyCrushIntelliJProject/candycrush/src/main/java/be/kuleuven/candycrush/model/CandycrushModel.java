package be.kuleuven.candycrush.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

public class CandycrushModel {
    private String speler;
    private Board<Candy> speelbord;
    private int score;
    private BoardSize boardSize;

    public CandycrushModel(String speler, BoardSize boardSize) {
        this.speler = speler;
        this.boardSize = boardSize;
        this.speelbord = new Board<>(this.boardSize);
        generateRandomBoard();
    }

    private void generateRandomBoard() {
        Function<Position, Candy> cellCreator = position -> generateRandomCandy();
        speelbord.fill(cellCreator);
    }

    public void reset() {
        generateRandomBoard();
        score = 0;
    }

    public void setSpeler(String spelerNaam) {
        this.speler = spelerNaam;
    }

    public BoardSize getBoardSize() {
        return boardSize;
    }

    public String getSpeler() {
        return speler;
    }

    public Board<Candy> getSpeelbord() {
        return speelbord;
    }

    public int getWidth() {
        return boardSize.columns();
    }


    public int getHeight() {
        return boardSize.rows();
    }

    public int getScore() {
        return score;
    }

    public void resetScore() {


        score = 0;
    }
public Board<Candy> getBoard(){
        return speelbord;
}
    public void candyWithIndexSelected(Position position) {
        List<Position> sameNeighborsList = (List<Position>) getSameNeighbourPositions(position);
        int neighboursAmount = sameNeighborsList.size();

        if (position.toIndex() != -1 && neighboursAmount >= 3) {
            addScore(neighboursAmount + 1);
            replaceCandies(sameNeighborsList);
        }
    }

    private void replaceCandies(List<Position> sameNeighborsList) {
        for (Position element : sameNeighborsList) {
            Candy randCandy = generateRandomCandy();
            speelbord.replaceCellAt(element, randCandy);
        }
    }

    public void addScore(int points) {
        if (points >= 0) {
            this.score += points;
        }
    }

    public Candy generateRandomCandy() {
        Random random = new Random();
        switch (random.nextInt(5)) {
            case 0:
                return new Candy.NormalCandy(random.nextInt(4));
            case 1:
                return new Candy.Snickers();
            case 2:
                return new Candy.Mars();
            case 3:
                return new Candy.Bounty();
            case 4:
                return new Candy.Twix();
            default:
                throw new IllegalArgumentException("Candy isn't generated");
        }
    }

    public Iterable<Position> getSameNeighbourPositions(Position position) {
        List<Position> sameNeighbourPositions = new ArrayList<>();
        Candy currentCandy = speelbord.getCellAt(position);

        for (Position neighbourPosition : position.neighborPositions()) {
            if (speelbord.getCellAt(neighbourPosition).equals(currentCandy)) {
                sameNeighbourPositions.add(neighbourPosition);
            }
        }
        sameNeighbourPositions.add(position);// add clicked position
        return sameNeighbourPositions;
    }
}
