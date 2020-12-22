package Sudoku.Threads;

import Sudoku.Sudoku;

public class SubGridThread implements Runnable{
    private int gridNumber;
    private Sudoku sudoku;

    public SubGridThread(Sudoku sudoku, int gridNumber) {
        this.sudoku = sudoku;
        this.gridNumber = gridNumber;
    }

    @Override
    public void run() {
        sudoku.checkSubGrid(gridNumber);
        System.out.println("SubGrid Thread" + gridNumber + " Valid: " + sudoku.isSubGridsValid());
    }
}
