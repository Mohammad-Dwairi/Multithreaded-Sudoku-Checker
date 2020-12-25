package Sudoku.Threads;

import Sudoku.Sudoku;

import java.util.concurrent.Callable;

public class SubGridsThread implements Callable<Boolean> {
    private final int gridNumber;
    private final Sudoku sudoku;

    public SubGridsThread(Sudoku sudoku, int gridNumber) {
        this.sudoku = sudoku;
        this.gridNumber = gridNumber;
    }

    @Override
    public Boolean call() {
        return sudoku.checkSubGrid(gridNumber);
    }
}
