
public class Main {

    public static void main(String[] args) {

        // initializing 9 X 9 Sudoku grid
        int[][] grid = {
                {6, 2, 4, 5, 3, 9, 1, 8, 7},
                {5, 1, 9, 7, 2, 8, 6, 3, 4},
                {8, 3, 7, 6, 1, 4, 2, 9, 5},
                {1, 4, 3, 8, 6, 5, 7, 2, 9},
                {9, 5, 8, 2, 4, 7, 3, 6, 1},
                {7, 6, 2, 3, 9, 1, 4, 5, 8},
                {3, 7, 1, 9, 5, 6, 8, 4, 2},
                {4, 9, 6, 1, 8, 2, 5, 7, 3},
                {2, 8, 5, 4, 7, 3, 9, 1, 6}
        };
        try {
            // Sudoku object takes sudoku grid as parameter for construction.
            Sudoku sudoku = new Sudoku(grid);
            System.out.println(sudoku.checkRow(1));
            System.out.println(sudoku.checkColumn(1));
            System.out.println(sudoku.checkSubGrid(8));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
