package be.kuleuven.candycrush.model;

import be.kuleuven.CheckNeighboursInGrid;

import java.util.ArrayList;
import java.util.List;

record Position(BoardSize board, int row, int column) {

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
        List<Position> neighbors = new ArrayList<>();

        // Get the grid of neighboring positions
        Iterable<Integer> neighborIndices = CheckNeighboursInGrid.getSameNeighboursIds(List.of(), board.columns(), board.rows(), toIndex());
        // Convert indices to positions
        for (int index : neighborIndices) {
            int row = index / board.columns();
            int column = index % board.columns();
            neighbors.add(new Position(board, row, column));
        }

        return neighbors;
    }
    public boolean isLastColumn() {
        System.out.println("Huidige column: "+ board.columns());
        return column == board.columns();
    }
}

