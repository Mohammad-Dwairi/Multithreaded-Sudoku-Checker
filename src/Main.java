import Sudoku.GUI.Grid;
import Sudoku.Sudoku;
import Sudoku.Threads.ColumnsThread;
import Sudoku.Threads.RowsThread;
import Sudoku.Threads.SubGridsThread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Main {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        long startTime = System.currentTimeMillis();
        long endTime = 0;
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
        
        boolean rowsCheckResult = startRowsThread(sudoku);
        boolean columnsCheckResult = startColumnsThread(sudoku);
        boolean subGridsCheckResult = startSubGridsThread(sudoku);

        System.out.println("Rows Thread Result: " + rowsCheckResult);
        System.out.println("Columns Thread Result: " + columnsCheckResult);
        System.out.println("SubGrids Threads Results: " + subGridsCheckResult);
        System.out.println("Final Result: " + ((rowsCheckResult && columnsCheckResult && subGridsCheckResult) ? "Valid Sudoku" : "Invalid Sudoku"));

        endTime = System.currentTimeMillis();
        int executionTime = (int) (endTime - startTime);
        System.out.println("Execution Time: " + executionTime);
        startGUIThread(grid);
    }

    public static boolean startRowsThread(Sudoku sudoku) throws ExecutionException, InterruptedException {
        ExecutorService service = Executors.newSingleThreadExecutor();
        Future<Boolean> rowsThreadPromise = service.submit(new RowsThread(sudoku));
        service.shutdown();
        return rowsThreadPromise.get();
    }

    public static boolean startColumnsThread(Sudoku sudoku) throws ExecutionException, InterruptedException {
        ExecutorService service = Executors.newSingleThreadExecutor();
        Future<Boolean> columnsThreadPromise = service.submit(new ColumnsThread(sudoku));
        service.shutdown();
        return columnsThreadPromise.get();
    }

    public static boolean startSubGridsThread(Sudoku sudoku) throws InterruptedException, ExecutionException {
        ExecutorService pool = Executors.newFixedThreadPool(9);
        List<SubGridsThread> threads = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            threads.add(new SubGridsThread(sudoku, i));
        }
        List<Future<Boolean>> subGridsPromises = pool.invokeAll(threads);
        pool.shutdown();
        List<Boolean> results = new ArrayList<>();
        for (Future<Boolean> promise : subGridsPromises) {
            results.add(promise.get());
        }
        for (Boolean result : results) {
            if (!result) {
                return false;
            }
        }
        return true;
    }

    public static void startGUIThread(int[][] grid) {
        Grid gridGUI = new Grid(grid);
        gridGUI.showGrid();
    }

}
