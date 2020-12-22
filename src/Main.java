import Sudoku.Sudoku;
import Sudoku.Threads.ColumnThread;
import Sudoku.Threads.RowThread;
import Sudoku.Threads.SubGridThread;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        // initializing 9 X 9 Sudoku.Sudoku grid
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
        Sudoku sudoku = new Sudoku(grid);
        Thread rowsThread = new Thread(new RowThread(sudoku));
        rowsThread.start();

        Thread columnsThread = new Thread(new ColumnThread(sudoku));
        columnsThread.start();

        List<Thread> gridsThreads = new ArrayList<>();

        for (int i = 0; i < 9; i++) {
            gridsThreads.add(new Thread(new SubGridThread(sudoku, i)));
        }

        for (Thread thread : gridsThreads) {
            thread.start();
        }

        try {
            rowsThread.join();
            columnsThread.join();
            for (Thread thread : gridsThreads) {
                thread.join();
            }
            System.out.println("OVERALL: " + (sudoku.isRowsValid() && sudoku.isColumnsValid() && sudoku.isSubGridsValid()));

        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        long endTime = System.currentTimeMillis();
        System.out.println("Execution Time: " + (endTime - startTime));
    }
}
