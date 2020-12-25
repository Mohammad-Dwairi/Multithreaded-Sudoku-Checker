package Sudoku.Threads;

import Sudoku.GUI.Grid;

import javax.swing.JButton;

public class GUIThread implements Runnable{
    private final int[][] grid;
    JButton button;
    public GUIThread(int[][] grid, JButton button) {
        this.grid = grid;
        this.button = button;
    }
    @Override
    public void run() {
        Grid gridGUI = null;
        try {
            gridGUI = new Grid(grid, button);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assert gridGUI != null;
        gridGUI.showGrid();
    }
}
