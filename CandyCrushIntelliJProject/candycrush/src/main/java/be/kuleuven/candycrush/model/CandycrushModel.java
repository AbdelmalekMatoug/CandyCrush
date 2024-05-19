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
            case 0, 3:
                return new Candy.Bounty();
            case 1:
                return new Candy.Snickers();
            case 2:
                return new Candy.Mars();
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
            if (speelbord.getCellAt(neighbourPosition).equals(currentCandy) && !(currentCandy instanceof Candy.Empty)) {
                sameNeighbourPositions.add(neighbourPosition);
            }
        }
        sameNeighbourPositions.add(position);// add clicked position
        return sameNeighbourPositions;
    }

    private boolean firstTwoHaveCandy(Candy candy, Stream<Position> positions) {
        List<Position> listOfPostions = positions.toList();
        int size = listOfPostions.size();
        if (size >= 2) {
            return listOfPostions.stream().limit(2).map(p -> speelbord.getCellAt(p)).allMatch(c -> c.equals(candy));
        } else {
            return false;
        }
    }

    private Stream<Position> horizontalStartingPositions() {
        return boardSize.positions().stream()
                .filter(p -> !(firstTwoHaveCandy(speelbord.getCellAt(p), p.walkLeft()) && p.column() != 0));
    }

    private Stream<Position> verticalStartingPositions() {
        return boardSize.positions().stream()
                .filter(p -> !(firstTwoHaveCandy(speelbord.getCellAt(p), p.walkUp()) && p.row() != 0));
    }

    private List<Position> longestMatchToRight(Position pos) {
        return pos.walkRight().takeWhile(p -> speelbord.getCellAt(p).equals(speelbord.getCellAt(pos))).toList();
    }

    private List<Position> longestMatchDown(Position pos) {
        return pos.walkDown().takeWhile(p -> speelbord.getCellAt(p).equals(speelbord.getCellAt(pos))).toList();
    }

    public Set<List<Position>> findAllMatches() {
        Set<List<Position>> allMatches = new HashSet<>();

        Stream<List<Position>> horizontalMatches = horizontalStartingPositions()
                .map(this::longestMatchToRight)
                .filter(match -> match.size() >= 3 && match.stream().noneMatch(pos -> speelbord.getCellAt(pos) instanceof Candy.Empty));

        Stream<List<Position>> verticalMatches = verticalStartingPositions()
                .map(this::longestMatchDown)
                .filter(match -> match.size() >= 3 && match.stream().noneMatch(pos -> speelbord.getCellAt(pos) instanceof Candy.Empty));

        allMatches = Stream.concat(horizontalMatches, verticalMatches).collect(Collectors.toSet());

        return allMatches;
    }

    void clearMatch(List<Position> matchList) {
        if (!matchList.isEmpty()) {
            Position removedPosition = matchList.remove(0);
            speelbord.replaceCellAt(removedPosition, new Candy.Empty());
            clearMatch(matchList);
        }
    }

    public void fallDownTo(Position pos) {
        if (pos.row() == 0) {
            return;
        }
        Position abovePos = new Position(boardSize, pos.row() - 1, pos.column());
        Candy currentCandy = speelbord.getCellAt(pos);
        Candy candyAbove = speelbord.getCellAt(abovePos);

        if (currentCandy instanceof Candy.Empty && !(candyAbove instanceof Candy.Empty)) {
            speelbord.replaceCellAt(pos, candyAbove);
            speelbord.replaceCellAt(abovePos, new Candy.Empty());
            fallDownTo(pos);
        } else {
            fallDownTo(abovePos);
        }
    }





    private void swap(Position pos1, Position pos2) {


        Candy candy1 = speelbord.getCellAt(pos1);
        Candy candy2 = speelbord.getCellAt(pos2);

        speelbord.replaceCellAt(pos1, candy2);
        speelbord.replaceCellAt(pos2, candy1);


    }

    public List<Position[]> maximizeScore() {
        List<Position[]> bestSequence = new ArrayList<>();
        int[] bestScore = {0};
        backtrack(new ArrayList<>(), bestSequence, bestScore);
        System.out.println("Best Score: " + bestScore[0]);
        System.out.print("Best Sequence: ");
        for (Position[] swap : bestSequence) {
            System.out.print(Arrays.toString(swap) + ", ");
        }

        return bestSequence;
    }
    private void backtrack(List<Position[]> currentSequence, List<Position[]> bestSequence, int[] bestScore) {
        // Backtracking wordt uitgevoerd om alle mogelijke zetten te verkennen en de beste sequentie van zetten te vinden

        // Voor elke positie op het bord
        for (Position pos : boardSize.positions()) {
            int row = pos.row();
            int col = pos.column();

            // Probeer te swappen met het snoepje rechts van de huidige positie
            if (col + 1 < getWidth()) {
                Position rightPos = new Position(boardSize, row, col + 1);

                if (isValidSwap(pos, rightPos)) {
                    // Voer de swap uit
                    swap(pos, rightPos);

                    // Sla de huidige score op
                    int prevScore = score;

                    // Controleer of er overeenkomsten zijn na de swap
                    if (matchAfterSwitch()) {
                        // Voeg de swap toe aan de huidige sequentie
                        currentSequence.add(new Position[]{pos, rightPos});

                        // Voer backtracking uit met de bijgewerkte sequentie
                        backtrack(currentSequence, bestSequence, bestScore);

                        // Update de beste sequentie en score als de huidige sequentie een hogere score heeft
                        if (score > bestScore[0]) {
                            bestScore[0] = score;
                            bestSequence.clear();
                            bestSequence.addAll(currentSequence);
                        }

                        // Verwijder de laatst toegevoegde swap om andere mogelijkheden te verkennen
                        currentSequence.remove(currentSequence.size() - 1);
                        score = prevScore;
                    }

                    // Herstel de swap om de staat van het bord te herstellen
                    swap(pos, rightPos);
                    updateBoard();
                }
            }

            // Probeer te swappen met het snoepje onder de huidige positie
            if (row + 1 < getHeight()) {
                Position belowPos = new Position(boardSize, row + 1, col);
                if (isValidSwap(pos, belowPos)) {
                    // Voer de swap uit
                    swap(pos, belowPos);

                    // Sla de huidige score op
                    int prevScore = score;

                    // Controleer of er overeenkomsten zijn na de swap
                    if (matchAfterSwitch()) {
                        // Voeg de swap toe aan de huidige sequentie
                        currentSequence.add(new Position[]{pos, belowPos});

                        // Voer backtracking uit met de bijgewerkte sequentie
                        backtrack(currentSequence, bestSequence, bestScore);

                        // Update de beste sequentie en score als de huidige sequentie een hogere score heeft
                        if (score > bestScore[0]) {
                            bestScore[0] = score;
                            bestSequence.clear();
                            bestSequence.addAll(currentSequence);
                        }

                        // Verwijder de laatst toegevoegde swap om andere mogelijkheden te verkennen
                        currentSequence.remove(currentSequence.size() - 1);
                        score = prevScore;

                    }

                    // Herstel de swap om de staat van het bord te herstellen
                    swap(pos, belowPos);
                    updateBoard();
                }
            }
        }

        // Als de huidige sequentie beter is dan de beste sequentie tot nu toe, update de beste sequentie en score
        if ((currentSequence.size() < bestSequence.size() || bestSequence.isEmpty()) && score > bestScore[0]) {
            bestSequence.clear();
            bestSequence.addAll(currentSequence);
            bestScore[0] = score;
        }
    }
    // Controleert of er na een swap overeenkomsten op het bord zijn.
    public boolean matchAfterSwitch() {
        return updateBoard();
    }

    // Controleert of een swap tussen twee posities geldig is.
    private boolean isValidSwap(Position pos1, Position pos2) {
        return isSwapable(pos1, pos2) &&
                !(speelbord.getCellAt(pos1) instanceof Candy.Empty) &&
                !(speelbord.getCellAt(pos2) instanceof Candy.Empty) &&(!speelbord.getCellAt(pos1).equals(speelbord.getCellAt(pos2))) && (!pos1.equals(pos2)) ;
    }

    // Controleert of twee posities kunnen worden geswapd.
    private boolean isSwapable(Position pos1, Position pos2) {
        int rowDiff = pos2.row() - pos1.row();
        int colDiff = pos2.column() - pos1.column();
        return Math.abs(rowDiff) + Math.abs(colDiff) == 1 && (rowDiff == 1 || colDiff == 1);
    }

    // Update het bord door alle overeenkomsten te verwijderen en snoepjes naar beneden te laten vallen.
    public boolean updateBoard() {
        Set<List<Position>> allMatches = findAllMatches();

        if (!allMatches.isEmpty()) {
            int totalSize = allMatches.stream().mapToInt(List::size).sum();
            score += totalSize;

            allMatches.forEach(match -> {
                clearMatch(new ArrayList<>(match));
                match.forEach(this::fallDownTo);
            });
            updateBoard();
            return  true;
        }
        return false;
    }
}
