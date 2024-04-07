package be.kuleuven.candycrush.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class CandycrushModelTests {
    //Positions tests
    @Test
    public void testToIndex() {
        BoardSize boardSize = new BoardSize(3, 3);
        Position position = new Position(boardSize, 1, 2);
        assert position.toIndex() == 5;
    }

    @Test
    public void testFromIndex() {
        BoardSize boardSize = new BoardSize(4, 4);
        Position position = Position.fromIndex(9, boardSize);
        assert position.row() == 2;
        assert position.column() == 1;
    }

    @Test
    public void testNeighborPositions() {
        BoardSize boardSize = new BoardSize(3, 3);
        Position position = new Position(boardSize, 1, 1);
        Iterable<Position> neighbors = position.neighborPositions();
        int count = 0;
        for (Position neighbor : neighbors) {
            assert neighbor.row() >= 0 && neighbor.row() < 3;//Grenzen checken
            assert neighbor.column() >= 0 && neighbor.column() < 3;//Grenzen checken
            assert Math.abs(neighbor.row() - position.row()) <= 1;//Kijken of het verschil van het aantal rijen gelijk is aan 1
            assert Math.abs(neighbor.column() - position.column()) <= 1; // kijken of het verschil in kolommen, gelijk is aan 1
            count++; // Aantal buren
        }

        assert count == 8;
    }

    @Test
    public void testIsLastColumnTrue() {
        BoardSize boardSize = new BoardSize(4, 4);
        Position position = new Position(boardSize, 2, 4);
        assert position.isLastColumn();
    }

    @Test
    public void testIsLastColumnFalse() {
        BoardSize boardSize = new BoardSize(4, 4);
        Position position = new Position(boardSize, 2, 2);
        assert !position.isLastColumn();

    }
    //Boardsize tests//
    @Test
    public void testConstructorValidArguments() {
        BoardSize boardSize = new BoardSize(5, 5);
        assert boardSize.rows() == 5;
        assert boardSize.columns() == 5;
    }

    @Test
    public void testConstructorInvalidArguments() {
        assertThrows(IllegalArgumentException.class, () -> {
            new BoardSize(0, 5); // 0 rows
        });

        assertThrows(IllegalArgumentException.class, () -> {
            new BoardSize(5, -1); // Negatieve columns
        });

        assertThrows(IllegalArgumentException.class, () -> {
            new BoardSize(-1, -1); // Negatieve rows and columns
        });
    }

    @Test
    public void testPositions() {
        BoardSize boardSize = new BoardSize(3, 3);
        Iterable<Position> positions = boardSize.positions();


        int count = 0;
        for (Position position : positions) {
            count++;
        }
        assert count == 9;


        int expectedRow = 0, expectedColumn = 0;
        for (Position position : positions) {
            assert position.row() == expectedRow;
            assert position.column() == expectedColumn;
            expectedColumn++;
            if (expectedColumn == 3) {
                expectedColumn = 0;
                expectedRow++;
            }
        }
    }


    //Board tests//
    @Test
    void testGetCellAt() {
        BoardSize boardSize = new BoardSize(3, 3);
        Board<Integer> board = new Board<>(boardSize);
        board.fill(position -> position.row() * boardSize.columns() + position.column());

        assert board.getCellAt(new Position(boardSize, 0, 0)).equals(0);
        assert board.getCellAt(new Position(boardSize, 1, 1)).equals(4);
        assert board.getCellAt(new Position(boardSize, 2, 2)).equals(8);
    }

    @Test
    void testReplaceCellAt() {
        BoardSize boardSize = new BoardSize(3, 3);
        Board<Integer> board = new Board<>(boardSize);
        board.fill(position -> 0);

        assert board.replaceCellAt(new Position(boardSize, 1, 1), 5).equals(0);
        assert board.getCellAt(new Position(boardSize, 1, 1)).equals(5);
    }

    @Test
    void testFillInteger() {
        BoardSize boardSize = new BoardSize(2, 2);
        Board<Integer> board = new Board<>(boardSize);
        board.fill(position -> position.row() * boardSize.columns() + position.column());

        assert board.getCellAt(new Position(boardSize, 0, 0)).equals(0);
        assert board.getCellAt(new Position(boardSize, 0, 1)).equals(1);
        assert board.getCellAt(new Position(boardSize, 1, 0)).equals(2);
        assert board.getCellAt(new Position(boardSize, 1, 1)).equals(3);
    }
    @Test
    void testFillIntegerCandy() {
        BoardSize boardSize = new BoardSize(2, 2);
        Board<Candy> board = new Board<>(boardSize);
        board.fill(position -> new Candy.Twix());

        assert board.getCellAt(new Position(boardSize, 0, 0)).getClass() == Candy.Twix.class;
        assert board.getCellAt(new Position(boardSize, 0, 1)).getClass() == Candy.Twix.class;
        assert board.getCellAt(new Position(boardSize, 1, 0)).getClass() == Candy.Twix.class;
        assert board.getCellAt(new Position(boardSize, 1, 1)).getClass() == Candy.Twix.class;
    }

    @Test
    void testCopyTo() {
        BoardSize boardSize = new BoardSize(2, 2);
        Board<Integer> board1 = new Board<>(boardSize);
        Board<Integer> board2 = new Board<>(boardSize);

        board1.fill(position -> position.row() * boardSize.columns() + position.column());
        board1.copyTo(board2);
        int size = boardSize.columns()*boardSize.rows();
        for (int i = 0; i < size; i++) {
            Position cellPosition = Position.fromIndex(i, boardSize);
            assert board1.getCellAt(cellPosition).equals(board2.getCellAt(cellPosition));
        }

    }

}
