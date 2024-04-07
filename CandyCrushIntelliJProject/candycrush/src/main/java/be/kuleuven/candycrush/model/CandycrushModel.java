package be.kuleuven.candycrush.model;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CandycrushModel {
    private String speler;
    private ArrayList<Candy> speelbord;

    private int score;
    private BoardSize boardSize;
    private Position position;

    public CandycrushModel(String speler, BoardSize boardSize) {
        this.speler = speler;
        this.speelbord = new ArrayList<>();
        this.boardSize = boardSize;
        boardSize.positions();
        generateRandomBoard();

    }


    private void generateRandomBoard() {
        speelbord.clear();
        for (int i = 0; i < boardSize.columns() * boardSize.rows(); i++) {
            speelbord.add(generateRandomCandy());
        }
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

    public List<Candy> getSpeelbord() {
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


    public void candyWithIndexSelected(Position position) {
        int index = position.toIndex();
        List<Position> sameNeighborsList = (List<Position>) getSameNeighbourPositions(position);
        int neighboursAmount = sameNeighborsList.size();

        if (position.toIndex() != -1 && neighboursAmount >= 3) {
            addScore(neighboursAmount + 1);
            replaceCandies(sameNeighborsList);
        } else {
            //System.out.println("model:candyWithIndexSelected:indexWasMinusOne");
        }

    }


   private void replaceCandies(List<Position> sameNeighborsList) {

        for (Position element : sameNeighborsList) {
            Candy randCandy = generateRandomCandy();
            speelbord.set(element.toIndex(), randCandy);

       }
   }


    public void addScore(int points) {
        if (points >= 0) {
            this.score += points;
        }
    }

    public Candy generateRandomCandy() {
        Random random = new Random();
        switch (random.nextInt(5)) { // Generate a random number between 0 and 3
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

        Candy huidigeCandy = speelbord.get(position.toIndex());
        for (Position neighbourPosition : position.neighborPositions()) {
            if (speelbord.get(neighbourPosition.toIndex()).equals(huidigeCandy)) {
                sameNeighbourPositions.add(neighbourPosition);
            }
        }
        return sameNeighbourPositions;
    }

}

