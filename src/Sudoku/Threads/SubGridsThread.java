package Sudoku.Threads;

import Sudoku.Sudoku;

import java.util.concurrent.Callable;

public class SubGridsThread implements Callable<Boolean> {
    private int gridNumber;
    private Sudoku sudoku;

    public SubGridsThread(Sudoku sudoku, int gridNumber) {
        this.sudoku = sudoku;
        this.gridNumber = gridNumber;
    }

    @Override
    public Boolean call() throws Exception {
        return sudoku.checkSubGrid(gridNumber);
    }
}
