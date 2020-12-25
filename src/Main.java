import Sudoku.Sudoku;
import Sudoku.Threads.ColumnsThread;
import Sudoku.Threads.GUIThread;
import Sudoku.Threads.RowsThread;
import Sudoku.Threads.SubGridsThread;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Main {
    public static long rowsThreadResponseTime;
    public static long columnsThreadResponseTime;
    public static long subGridsThreadsResponseTime;

    public static void main(String[] args) throws Exception {

        // initializing a valid 9 X 9 Sudoku.
        // Change values in the grid to see different results.
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

        int[][] grid2 = {
                {1, 2, 3, 4, 5, 6, 7, 8, 9},
                {2, 3, 4, 5, 6, 7, 8, 9, 1},
                {3, 4, 5, 6, 7, 8, 9, 1, 2},
                {4, 5, 6, 7, 8, 9, 1, 2, 3},
                {5, 6, 7, 8, 9, 1, 2, 3, 4},
                {6, 7, 8, 9, 1, 2, 3, 4, 5},
                {7, 8, 9, 1, 2, 3, 4, 5, 6},
                {8, 9, 1, 2, 3, 4, 5, 6, 7},
                {9, 1, 2, 3, 4, 5, 6, 7, 8}
        };
        // initializing a new instance of Sudoku class with 9 X 9 grid.
        Sudoku sudoku = new Sudoku(grid2);

        // Declaring ( Java Swing ) JButton with an event listener.
        // Whenever the user clicks the button; the lambda function will execute to invoke Threads.
        JButton startThreadsBtn = new JButton("Check Sudoku");
        startThreadsBtn.addActionListener(e -> {
            try {
                // Store current time in ms when the button is clicked.
                long startTime = System.currentTimeMillis();

                // Starting (rowThread) that checks whether all (9 rows) are valid;
                boolean rowsThreadResult = startRowsThread(sudoku);

                // Starting (columnsThread) that checks whether all (9 columns) are valid;
                boolean columnsThreadResult = startColumnsThread(sudoku);

                // Starting (SubGridsThreads 9 Threads), each thread holds the number (id) of the subGrid to be checked.
                boolean subGridsThreadsResult = startSubGridsThreads(sudoku);

                boolean finalResult = rowsThreadResult && columnsThreadResult && subGridsThreadsResult;

                /*
                    We Guarantee that all threads are terminated at this point because of the 'Blocking nature' of get() method.
                    See the return statement of each thread executor below.
                */

                // Store current time in ms after the termination of the threads.
                long endTime = System.currentTimeMillis();
                long executionTime = endTime - startTime;

                // Some String variables to dynamically display results depending on the returning results of the threads.
                String timeResult = "<html>Execution Time: " + executionTime + " ms<br><br>";
                String rowsResult = rowsThreadResult ? "Rows: Valid <br><br>" : "One or more of the rows are Invalid!<br><br>";
                String columnsResult = columnsThreadResult ? "Columns: Valid<br><br>" : "One or more of the columns are Invalid!<br><br>";
                String subGridsResult = subGridsThreadsResult ? "SubGrids: Valid<br><br>" : "One or more of the (3 X 3) SubGrids are Invalid!<br><br>";
                String finalResultString = finalResult ? "Final Result: Valid Sudoku" : "Final Result: Invalid Sudoku </html>";

                // Showing the final results as JOptionPane message.
                JOptionPane.showMessageDialog(
                        null,
                        timeResult
                        + "Rows Thread Response Time: " + rowsThreadResponseTime + " ms <br><br>"
                        + "Columns Thread ResponseTime: " + columnsThreadResponseTime + " ms <br><br>"
                        + "Sub-Grids Threads Response Time: " + subGridsThreadsResponseTime + " ms <br><br><hr>"
                        + rowsResult + columnsResult + subGridsResult + finalResultString,
                        "Results",
                        JOptionPane.INFORMATION_MESSAGE
                );
            } catch (ExecutionException | InterruptedException executionException) {
                executionException.printStackTrace();
            }
        });

        // Starting GUI on separate Thread.
        startGUIThread(grid2, startThreadsBtn);
    }

    public static boolean startRowsThread(Sudoku sudoku) throws ExecutionException, InterruptedException {
        /*
        * startRowsThread method registers Thread executor, then submits rows checking task to a new thread
        * to be executed.
        *
        * The submission of the task returns Future Object.
        * Future Object can be imagined as (promise) that will hold the value returned
        * from this thread once it is finished or (returned) (Async).
        */
        ExecutorService service = Executors.newSingleThreadExecutor();
        long start = System.currentTimeMillis();
        Future<Boolean> rowsThreadPromise = service.submit(new RowsThread(sudoku));
        rowsThreadResponseTime = (System.currentTimeMillis() - start);

        // shutting down the service once the task is done and to prevent any new tasks submissions.
        service.shutdown();

        /*
         *  Getting the value returned from this thread. get() method is a blocking method,
         *  which means it blocks the execution until the value is returned from this thread.
        */
        return rowsThreadPromise.get();
    }

    public static boolean startColumnsThread(Sudoku sudoku) throws ExecutionException, InterruptedException {
        /* See startRowsThread comments */
        ExecutorService service = Executors.newSingleThreadExecutor();

        long start = System.currentTimeMillis();
        Future<Boolean> columnsThreadPromise = service.submit(new ColumnsThread(sudoku));
        columnsThreadResponseTime = System.currentTimeMillis() - start;
        service.shutdown();
        return columnsThreadPromise.get();
    }

    public static boolean startSubGridsThreads(Sudoku sudoku) throws InterruptedException, ExecutionException {
        /*
        * startSubGridsThreads creates a thread pool of 9 threads, these threads are ready
        * to handle 9 tasks (1 task / thread).
        * then submitting 9 threads, each with the grid number it will operate on.
        * */
        ExecutorService pool = Executors.newFixedThreadPool(9);
        List<SubGridsThread> threads = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            threads.add(new SubGridsThread(sudoku, i));
        }
        long start = System.currentTimeMillis();
        List<Future<Boolean>> subGridsPromises = pool.invokeAll(threads); // returns list of Future Objects.
        subGridsThreadsResponseTime = (System.currentTimeMillis() - start);

        pool.shutdown(); // Shutting down the service after invoking all threads.
        List<Boolean> results = new ArrayList<>();
        for (Future<Boolean> promise : subGridsPromises) {
            results.add(promise.get()); // getting the results returned from threads.
        }
        for (Boolean result : results) {
            if (!result) {
                // exit if any of the returned results is false (which means that the subgrid is invalid)
                return false;
            }
        }
        return true;
    }

    public static void startGUIThread(int[][] grid, JButton btn) {
        /* Starts a new thread to run GUI on */
        Thread thread = new Thread(new GUIThread(grid, btn));
        thread.start();
    }

}
