package be.kuleuven.candycrush.model;

import java.util.ArrayList;

public record Position(BoardSize board, int row, int column) {

    public Position {

        if (row < 0 || row > board.rows() || column < 0 || column > board.columns()) {

            throw new IllegalArgumentException("Aantal rijen en kolommen zijn incorrect");        }
    }
    public int toIndex(){
        return this.board.columns() * row + column;
    }

    public static Position fromIndex(int index, BoardSize size) {
        if (index < 0 || index >= size.rows() * size.columns()) {
            throw new IllegalArgumentException("Invalid index");
        }
        int row = index / size.columns();
        int column = index % size.columns();
        return new Position(size, row, column);
    }
    public Iterable<Position> neighborPositions() {
        ArrayList<Position> neighbors = new ArrayList<>();

        for (int otherRow = 0; otherRow < board.rows(); otherRow++) {
            for (int otherColumn = 0; otherColumn < board.columns(); otherColumn++) {
                // Calculate the distance between the current position and the target position
                double distance = Math.sqrt(Math.pow(row - otherRow, 2) + Math.pow(column - otherColumn, 2));

                if (distance > 0 && distance <= Math.sqrt(2)) {
                    neighbors.add(new Position(board, otherRow, otherColumn));
                }
            }
        }

        return neighbors;
    }

    public boolean isLastColumn() {
        System.out.println("Huidige column: "+ board.columns());
        return column == board.columns();
    }
}

