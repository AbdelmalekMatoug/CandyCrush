package be.kuleuven.candycrush.model;

import java.util.ArrayList;
import java.util.*;
import java.util.Set;
import java.util.function.Function;

public class Board<T> {
    private BoardSize boardSize;
    private int rows;
    private int columns;
    private HashMap<Position, T> boardMap;
    private ArrayList<T> cells;

    private HashMap<T, Set<Position>> reversedMap;
    public Board(BoardSize boardSize) {
        this.boardMap = new HashMap<>();
        this.boardSize = boardSize;
        this.cells = new ArrayList<>();
        this.reversedMap = new HashMap<>();
    }
    private void updateReversedMap(T cells) {
        Set<Position> samePositions = new HashSet<>();
        for (Map.Entry<Position, T> entry : boardMap.entrySet()) {
            Position position = entry.getKey();
            if (entry.getValue().equals(cells) && !samePositions.contains(position)) {
                samePositions.add(position);
            }
        }
        reversedMap.put(cells, samePositions);
    }


    public T getCellAt(Position position) {
        return cells.get(position.toIndex());
    }

    public T replaceCellAt(Position position, T newCell) {
        T replacedCell = getCellAt(position);
        cells.set(position.toIndex(), newCell);
        boardMap.put(position,newCell);
        updateReversedMap(newCell);
        return replacedCell;
    }

    public void fill(Function<Position, T> cellCreator) {
        for (int index = 0; index < boardSize.rows()* boardSize.columns(); index++) {
            Position position = Position.fromIndex(index,boardSize);

            T newCell = cellCreator.apply(position);
            cells.add(index,newCell);
            boardMap.put(position,newCell);
            updateReversedMap(newCell);

        }
    }

    public void copyTo(Board<T> toCopyBoard) {

        if (toCopyBoard.boardSize.rows() != boardSize.rows() || toCopyBoard.boardSize.columns() != boardSize.columns()) {
            throw new IllegalArgumentException("Boards have different dimensions");
        } else {
            for (int i = 0; i < boardSize.columns() * boardSize.rows(); i++) {
                toCopyBoard.cells.add(cells.get(i));
            }
        }
    }

    public Set<Position> getPositionOfElement(T cell) {
        Set<Position> positionElements = new HashSet<>();
        for (Map.Entry<T, Set<Position>> entry : reversedMap.entrySet()) {
            if (entry.getKey().equals(cell)) {
                positionElements.addAll(entry.getValue());
            }
        }
        return Collections.unmodifiableSet(positionElements);
    }
    public void removeCellAt(Position position) {
        T removedCell = getCellAt(position);
        cells.set(position.toIndex(), null);
        boardMap.remove(position);
        updateReversedMap(removedCell);
    }

}

