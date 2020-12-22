package Sudoku.Threads;

import Sudoku.Sudoku;

public class ColumnThread implements Runnable {
    private final Sudoku sudoku;

    public ColumnThread(Sudoku sudoku) {
        this.sudoku = sudoku;
    }

    @Override
    public void run() {
        try {
            System.out.println("#### Columns Thread Is Running");
            sudoku.checkColumn();
            System.out.println("Columns Valid: " + sudoku.isColumnsValid());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
