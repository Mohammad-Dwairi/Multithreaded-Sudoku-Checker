package Sudoku.Threads;

import Sudoku.GUI.Grid;

import javax.swing.*;

public class GUIThread implements Runnable{
    private int[][] grid;
    private int executionTime;

    public GUIThread(int[][] grid, int executionTime) {
        this.grid = grid;
        this.executionTime = executionTime;
    }
    @Override
    public void run() {
        Grid gridGUI = new Grid(grid);
        gridGUI.showGrid();
    }
}
