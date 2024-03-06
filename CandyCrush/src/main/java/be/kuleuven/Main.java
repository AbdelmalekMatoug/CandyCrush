package be.kuleuven;

import java.util.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Iterable<Integer> constantArrayList = new ArrayList<>(Arrays.asList(
                0, 0, 1,
                1, 1, 0,
                2, 1,0,
                0, 1, 1
        ));
        CheckNeighboursInGrid test= new CheckNeighboursInGrid();
        CheckNeighboursInGrid.getSameNeighboursIds(constantArrayList, 3,4,5);

    }
}