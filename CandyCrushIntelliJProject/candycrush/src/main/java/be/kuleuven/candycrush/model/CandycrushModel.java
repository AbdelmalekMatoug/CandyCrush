package be.kuleuven.candycrush.model;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        Position pos = new Position(this.boardSize, 1, 2);
        horizontalStartingPositions().forEach(System.out:: println);

        generateRandomBoard();
        System.out.println(findAllMatches());
        //System.out.println(  longestMatchDown(pos));
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

    public Board<Candy> getBoard() {
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

    private boolean firstTwoHaveCandy(Candy candy, Stream<Position> positions) {

        List<Position> listOfPostions = positions.toList();
        int size = listOfPostions.size();
        if( size >= 2){
            return listOfPostions.stream().limit(2).map(p -> speelbord.getCellAt(p)).allMatch(c -> c.equals(candy));
        }
        else {
            return false;
        }


    }

    private Stream<Position> horizontalStartingPositions() {
        return boardSize.positions().stream()
                .filter(p -> !(firstTwoHaveCandy(speelbord.getCellAt(p), p.walkLeft()) && p.column() != 0));
    }

    private  Stream<Position> verticalStartingPositions() {
        return boardSize.positions().stream()
                .filter(p -> !(firstTwoHaveCandy(speelbord.getCellAt(p), p.walkUp()) && p.row() != 0));
    }

    private List<Position> longestMatchToRight(Position pos){
     return  pos.walkRight().takeWhile(p -> speelbord.getCellAt(p).equals(speelbord.getCellAt(pos)) ) .toList();
    }
    private   List<Position> longestMatchDown(Position pos){
        return  pos.walkDown().takeWhile(p -> speelbord.getCellAt(p).equals(speelbord.getCellAt(pos)) ) .toList();
    }
    public Set<List<Position>> findAllMatches(){
        Set<List<Position>> allMatches = new HashSet<>();
        Stream<List<Position>> horizontalMatches = horizontalStartingPositions().map(this:: longestMatchToRight).filter(p-> p.size()>=3);

       Stream<List<Position>> verticalMatches = verticalStartingPositions().map(this:: longestMatchDown).filter(p-> p.size()>=3);
        allMatches = Stream.concat(horizontalMatches,verticalMatches).collect(Collectors.toSet());
        return  allMatches;

    }
}
