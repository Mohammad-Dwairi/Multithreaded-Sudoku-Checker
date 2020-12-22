package Sudoku;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class Sudoku {
    private final int gridWidth = 9;
    private final int gridHeight = 9;

    private final List<List<Integer>> grid;
    private boolean isRowsValid;
    private boolean isColumnsValid;
    private boolean isSubGridsValid;
    public Sudoku(int[][] grid) {
        isRowsValid = true;
        isColumnsValid = true;
        isSubGridsValid = true;
        List<List<Integer>> transformedGrid = new ArrayList<>();

       for (int i=0; i < 9; i++) {
           List<Integer> row = new ArrayList<>();
           for (int j = 0; j < 9; j++) {
               row.add(grid[i][j]);
           }
           transformedGrid.add(row);
       }

        this.grid = transformedGrid;
        System.out.println("Original Grid");
        System.out.println(this.grid);
    }

    public boolean isRowsValid() {
        return isRowsValid;
    }

    public boolean isColumnsValid() {
        return isColumnsValid;
    }

    public boolean isSubGridsValid() {
        return isSubGridsValid;
    }

    public void checkRows() {
        for (List<Integer> row : grid) {
            for (int i = 1; i <= gridWidth; i++) {
                if (!row.contains(i)) {
                    isRowsValid = false;
                }
            }
        }
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
        System.out.println("Transpose");
        System.out.println(gridTranspose);
        return gridTranspose;
    }

    public void checkColumn() {
        List<List<Integer>> gridTranspose = makeTranspose();
        for (List<Integer> column : gridTranspose) {
            for (int i = 1; i <= gridWidth; i++) {
                if (!column.contains(i)) {
                   isColumnsValid = false;
                }
            }
        }
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

    public void checkSubGrid(int gridNumber) {
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
        isSubGridsValid = numbers.isEmpty();
    }
}
