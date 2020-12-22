package Sudoku.Threads;

import Sudoku.Sudoku;

public class LinearThread implements Runnable{
    private final int[][] grid;
    public LinearThread(int[][] grid) {
        this.grid = grid;
    }
    @Override
    public void run() {
        try {
            // Sudoku.Sudoku object takes sudoku grid as parameter for construction.
            Sudoku sudoku = new Sudoku(grid);
            System.out.println(sudoku.checkRow(1));
            System.out.println(sudoku.checkColumn(1));
            System.out.println(sudoku.checkSubGrid(0));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
