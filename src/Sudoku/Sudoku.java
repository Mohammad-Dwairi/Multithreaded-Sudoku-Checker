package Sudoku;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class Sudoku {
    private final int gridWidth = 9;
    private final int gridHeight = 9;
    private final List<List<Integer>> grid;

    public Sudoku(int[][] grid) {
        List<List<Integer>> transformedGrid = new ArrayList<>();
       for (int i=0; i < 9; i++) {
           List<Integer> row = new ArrayList<>();
           for (int j = 0; j < 9; j++) {
               row.add(grid[i][j]);
           }
           transformedGrid.add(row);
       }
       this.grid = transformedGrid;
    }

    public boolean checkRows() {
        for (List<Integer> row : grid) {
            for (int i = 1; i <= gridWidth; i++) {
                if (!row.contains(i)) {
                    return false;
                }
            }
        }
        return true;
    }

    private List<List<Integer>> makeTranspose() {
        List<List<Integer>> gridTranspose = new ArrayList<>();
        for (int i = 0; i < gridWidth; i++) {
            List<Integer> column = new ArrayList<>();
            for (List<Integer> row : grid) {
                column.add(row.get(i));
            }
            gridTranspose.add(column);
        }
        return gridTranspose;
    }

    public boolean checkColumns() {
        List<List<Integer>> gridTranspose = makeTranspose();
        for (List<Integer> column : gridTranspose) {
            for (int i = 1; i <= gridWidth; i++) {
                if (!column.contains(i)) {
                   return false;
                }
            }
        }
        return true;
    }

    private List<List<Integer>> createSubGrid(int gridNumber) {
        List<List<Integer>> subGrid = new ArrayList<>();

        int Y_InitialCoordinate = 0;
        int Y_FinalCoordinate = 3;

        int X_InitialCoordinate = 0;
        int X_FinalCoordinate = 3;

        if (gridNumber >= 0 && gridNumber <= 2) {
            Y_InitialCoordinate = gridNumber * 3;
            Y_FinalCoordinate = Y_InitialCoordinate + 3;
        }
        else if (gridNumber > 2 && gridNumber < 6) {
            Y_InitialCoordinate = (gridNumber % 3) * 3;
            Y_FinalCoordinate = Y_InitialCoordinate + 3;

            X_InitialCoordinate = 3;
            X_FinalCoordinate = X_InitialCoordinate + 3;
        }
        else if (gridNumber >= 6 && gridNumber < 9) {
            Y_InitialCoordinate = (gridNumber % 3) * 3;
            Y_FinalCoordinate = Y_InitialCoordinate + 3;

            X_InitialCoordinate = 6;
            X_FinalCoordinate = X_InitialCoordinate + 3;
        }

        for (int rowNumber = Y_InitialCoordinate; rowNumber < Y_FinalCoordinate; rowNumber++) {
            subGrid.add(grid.get(rowNumber).subList(X_InitialCoordinate, X_FinalCoordinate));
        }
        return subGrid;
    }

    public boolean checkSubGrid(int gridNumber) {
        List<List<Integer>> subGrid = this.createSubGrid(gridNumber);
        List<Integer> numbers = new ArrayList<>();
        for (int i = 1; i <= 9; i++) {
            numbers.add(i);
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++ ) {
                numbers.remove(subGrid.get(i).get(j));
            }
        }
        return numbers.isEmpty();
    }
}
