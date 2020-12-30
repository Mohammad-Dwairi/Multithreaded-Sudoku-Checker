# Introduction
Sudoku board is a 9x9 grid in which a valid sudoku board should contain digits from 1 to
9, such that:
* Each row contains unique values from 1 to 9.
* Each column contains unique values from 1 to 9.
* Each 3x3 sub-grid contains unique values from 1 to 9.
To check whether each row, column and sub-grid is valid or not, and to obtain better CPU
utilization; multithreading is used such that:
* A thread to check if all rows are valid.
* A thread to check if all columns are valid.
* Nine threads (one thread per sub-grid) each checking if its sub-grid is valid.
# Code Overview
1. Initializing Sudoku grid as a 2D array.
2. Create an instance of Sudoku class.
  1. The constructor takes 2D array as a parameter.
  2. Transforms the 2D array into a two-dimensional List “List<List<Integer>>” for functionality purposes.
3. Sudoku class contains methods that are responsible for validation operations:
  * checkRows()
  * checkColumns()
  * checkSubGrid(int gridNumber)
  * createSubGrid(int gridNumber)
  * makeTranspose()

4. makeTranspose Transposes Sudoku matrix. This gives the ability to treat columns as rows to perform checkRows() on them.
5. createSubGrid(int gridNumbe) returns 3x3 grid depending on the gridNumber parameter. The following figure illustrates how sub-grids are organized and numbered.
  * ![Alt Text](https://i.imgur.com/lw8w3Vt.png)
# Threads
Four Thread-Classes to handle four different tasks:
* RowsThread
  * Handles rows validation.
* ColumnsThread
  * Handles columns validation.
* SubGridsThread
  * Handles sub-grids validation.
* GUIThread
  * Handles the GUI.
## The following flowchart shows how threads are executed:
![Alt Text](https://i.imgur.com/XEzFb7v.png)
