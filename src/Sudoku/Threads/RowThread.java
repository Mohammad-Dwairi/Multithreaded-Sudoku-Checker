package Sudoku.Threads;

import Sudoku.Sudoku;

public class RowThread implements Runnable{
    private final Sudoku sudoku;
    public RowThread(Sudoku sudoku) {
        this.sudoku = sudoku;
    }

    @Override
    public void run() {
        try {
            System.out.println("#### Rows Thread Is Running ####");
            sudoku.checkRows();
            System.out.println("Rows Valid: " + sudoku.isRowsValid());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
