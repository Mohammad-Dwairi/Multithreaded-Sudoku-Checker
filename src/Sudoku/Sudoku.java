package Sudoku;

import java.util.ArrayList;
import java.util.List;

public class Sudoku {

    private final List<List<Integer>> grid;

    public Sudoku(int[][] grid) throws Exception {
        if (grid.length != 9 || grid[0].length != 9) {
            throw new Exception("The grid dimensions must be 9 X 9");
        }

        // Transforming two dimensional array into two dimensional list for functionality purposes.
        List<List<Integer>> transformedGrid = new ArrayList<>();
        for (int i=0; i < 9; i++) {
            List<Integer> row = new ArrayList<>();
            for (int j = 0; j < 9; j++) {
                if (grid[i][j] <= 0 || grid[i][j] > 9) {
                    throw new Exception("Value (" + grid[i][j] + ") is not valid in Sudoku");
                }
                row.add(grid[i][j]);
            }
            transformedGrid.add(row);
        }
        this.grid = transformedGrid;
    }

    public boolean checkRows() {
        // Returns whether all rows in the grid are valid. (contains numbers from 1 to 9).
        for (List<Integer> row : grid) {
            for (int i = 1; i <= 9; i++) {
                if (!row.contains(i)) {
                    return false;
                }
            }
        }
        return true;
    }

    private List<List<Integer>> makeTranspose() {
        // Transpose matrix operation, to check columns as rows-check operation. see checkColumns()
        List<List<Integer>> gridTranspose = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            List<Integer> column = new ArrayList<>();
            for (List<Integer> row : grid) {
                column.add(row.get(i));
            }
            gridTranspose.add(column);
        }
        return gridTranspose;
    }

    public boolean checkColumns() {
        List<List<Integer>> gridTranspose = makeTranspose(); // get the transpose of the grid.
        for (List<Integer> column : gridTranspose) {
            for (int i = 1; i <= 9; i++) {
                if (!column.contains(i)) {
                   return false;
                }
            }
        }
        return true;
    }

    private List<List<Integer>> createSubGrid(int gridNumber) {
        /*
        * Creates 3 X 3 Sub-Grids based on provided Grid number.
        * grid number ranges from 0 to 8 (9 Sub-Grids).
        * grid numbers begin at the top left 3 X 3 grid, increasing by going down.
        *
        *  Sub-Grids numbers:
        * | 0     3     6 |
        * | 1     4     7 |
        * | 2     5     8 |
        *
        * NOTE: Y_Coordinates is 0 at the top of the grid, increasing by going down,
        *       each square (value) in the original Grid is considered 1 step on y-axis.
        *       X_Coordinates is 0 at the left most of the grid, increasing by going right,
        *       each square (value) in the original Grid is considered 1 step on x-axis.
        * */
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
        /*
        * Checks if the Sub-Grid corresponding to the gridNumber has the numbers from 1 -> 9.
        * */
        List<List<Integer>> subGrid = this.createSubGrid(gridNumber);

        /*
        * Initialize numbers list from 1 -> 9.
        * */
        List<Integer> numbers = new ArrayList<>();
        for (int i = 1; i <= 9; i++) {
            numbers.add(i);
        }

        /*
        * Iterate over the 3 X 3 Sub-Grid, then remove the current number from the previous numbers list.
        * If the Sub-Grid has all values from 1 -> 9, then it will be empty at the end of the loop below.
        * */
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++ ) {
                numbers.remove(subGrid.get(i).get(j));
            }
        }
        return numbers.isEmpty(); // return if the numbers list is empty.
    }
}
