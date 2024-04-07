package be.kuleuven.candycrush.model;

import java.util.ArrayList;
import java.util.function.Function;

public class Board<T> {
    private BoardSize boardSize;
    private int rows;
    private int columns;

    private ArrayList<T> cells;

    public Board(BoardSize boardSize) {
        this.boardSize = boardSize;
        this.cells = new ArrayList<>();
    }

    public T getCellAt(Position position) {

        return cells.get(position.toIndex());
    }

    public T replaceCellAt(Position position, T newCell) {
        T replacedCell = getCellAt(position);
        cells.set(position.toIndex(), newCell);
        return replacedCell;
    }

    public void fill(Function<Position, T> cellCreator) {
        for (int index = 0; index < boardSize.rows()* boardSize.columns()-1; index++) {
            Position position = Position.fromIndex(index,boardSize);
            T newCell = cellCreator.apply(position);
            cells.add(index,newCell);
        }
    }

    public void copyTo(Board<T> toCopyBoard) {

        if (toCopyBoard.boardSize.rows() != boardSize.rows() || toCopyBoard.boardSize.columns() != boardSize.columns()) {
            throw new IllegalArgumentException("Boards have different dimensions");
        } else {
            for (int i = 0; i < boardSize.columns() * boardSize.rows()-1; i++) {
                Position currentPosition = Position.fromIndex(i, boardSize);
                T cellValue = cells.get(i);
                toCopyBoard.replaceCellAt(currentPosition, cellValue);
            }
        }
    }
}

