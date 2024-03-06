package be.kuleuven;

import java.util.ArrayList;
import java.util.Iterator;

public class CheckNeighboursInGrid {

    public static Iterable<Integer> getSameNeighboursIds(Iterable<Integer> grid, int width, int height, int indexToCheck) {
        ArrayList<Integer> list = new ArrayList<>();
        int indexToCheckColumn = indexToCheck % width;
        int indexToCheckRow = indexToCheck / width;
        int index = 0;
        int indexToCheckValue = 0;

        for (int num : grid) {
            if (index == indexToCheck) {
                indexToCheckValue = num;
                break;
            }
            index++;
        }
        index = 0;
        for (int num : grid) {
            int indexRow = index / width;
            int indexColumn = index % width;

            if (index != indexToCheck) {
                double distance = Math.sqrt(Math.pow((indexToCheckColumn - indexColumn), 2) + Math.pow(indexToCheckRow - indexRow, 2));

                if (distance <= Math.sqrt(2) && num == indexToCheckValue) {
                    list.add(index);
                }
            }

            index++;
        }
        System.out.print(list);
        return list; // Return the size of the list containing neighboring indices
    }
}
