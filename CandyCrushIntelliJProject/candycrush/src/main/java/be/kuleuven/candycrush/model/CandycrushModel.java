package be.kuleuven.candycrush.model;

import be.kuleuven.CheckNeighboursInGrid;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CandycrushModel {
    private String speler;
    private ArrayList<Integer> speelbord;
    private int width;
    private int height;
    private int score;

    public CandycrushModel(String speler) {
        this.speler = speler;
        this.speelbord = new ArrayList<>();
        this.width = 8;
        this.height = 8;
        generateRandomBoard();
    }
    public CandycrushModel() {
        this("initPlayer");
    }
    private void generateRandomBoard() {
        speelbord.clear();
        for (int i = 0; i < width * height; i++) {
            Random random = new Random();
            int randomGetal = random.nextInt(5) + 1;
            speelbord.add(randomGetal);
        }
    }

    public void reset() {
        generateRandomBoard();
        score = 0;
    }
    public void setSpeler(String spelerNaam) {
        this.speler = spelerNaam;
    }


    public String getSpeler() {
        return speler;
    }

    public List<Integer> getSpeelbord() {
        return speelbord;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getScore() {
        return score;
    }

    public void resetScore() {
        score = 0;
    }


    public void candyWithIndexSelected(int index) {
        List<Integer> sameNeighborsList = findSameNeighbors(index);
        int neighboursAmount = sameNeighborsList.size() - 1;

        if (index != -1 && neighboursAmount >= 3) {
            addScore(neighboursAmount + 1);
            replaceCandies(sameNeighborsList);
        } else {
            //System.out.println("model:candyWithIndexSelected:indexWasMinusOne");
        }
    }



    private List<Integer> findSameNeighbors(int index) {
        CheckNeighboursInGrid grid = new CheckNeighboursInGrid();
        Iterable<Integer> sameNeighbors = grid.getSameNeighboursIds(this.speelbord, this.width, this.height, index);
        List<Integer> sameNeighborsList = new ArrayList<>();

        for (int neighbor : sameNeighbors) {
            sameNeighborsList.add(neighbor);
        }
        sameNeighborsList.add(index);
        BoardSize boardSize = new BoardSize(8,8);
        Position position = new Position(boardSize, 1, 8);

        System.out.println("These are the positon: "+ position.column());
        System.out.println("This are the neighbours: " + position.isLastColumn() );

        return sameNeighborsList;
    }

    private void replaceCandies(List<Integer> sameNeighborsList) {
        Random random = new Random();
        for (int element : sameNeighborsList) {
            int randomGetal = random.nextInt(5) + 1;
            speelbord.set(element, randomGetal);

        }
    }

    public int getIndexFromRowColumn(int row, int column) {
        return column + row * width;
    }



    public void addScore(int points) {
        if(points>=0){
            this.score += points;
        }

    }
}
