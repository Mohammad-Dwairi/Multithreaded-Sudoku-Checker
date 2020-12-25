package Sudoku.Threads;

import Sudoku.Sudoku;

import java.util.concurrent.Callable;

public class ColumnsThread implements Callable<Boolean> {
    private final Sudoku sudoku;
    public ColumnsThread(Sudoku sudoku) {
        this.sudoku = sudoku;
    }

    @Override
    public Boolean call() {
        return sudoku.checkColumns();
    }
}
