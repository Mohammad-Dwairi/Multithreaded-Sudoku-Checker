package Sudoku.Threads;

import Sudoku.Sudoku;

import java.util.concurrent.Callable;

public class RowsThread implements Callable<Boolean> {
    private final Sudoku sudoku;

    public RowsThread(Sudoku sudoku) {
        this.sudoku = sudoku;
    }

    @Override
    public Boolean call() throws Exception {
        return sudoku.checkRows();
    }
}
