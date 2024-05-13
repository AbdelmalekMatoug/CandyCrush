package be.kuleuven.candycrush.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

// Definieer het record BoardSize
public record BoardSize(int rows, int columns) {

    public BoardSize {
        if (rows <= 0 || columns <= 0) {
            throw new IllegalArgumentException("Aantal rijen en kolommen moeten groter zijn dan 0.");
        }
    }


    public Collection<Position> positions() {
        List<Position> positions = new ArrayList<>();
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                Position pos = new Position(this,row,column);
                positions.add(pos);
            }
        }
        //System.out.println(positions);
        return positions;
    }

}