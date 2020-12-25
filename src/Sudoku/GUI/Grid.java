package Sudoku.GUI;

import javax.swing.*;
import java.awt.*;

public class Grid {

    private final JFrame appFrame;
    private final int[][] grid;
    public Grid(int[][] grid) {
        this.grid = grid;
        appFrame = new JFrame("Sudoku");
        appFrame.setSize(500, 500);
        appFrame.setLocation(600, 250);
        appFrame.setLayout(new GridLayout(9, 9, 5, 5));
        appFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fillGrid();
    }

    private void fillGrid() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                JButton button = new JButton(Integer.toString(grid[i][j]));
                button.setEnabled(false);
                button.setBackground(Color.cyan);
                appFrame.add(button);
            }
        }
    }

    public void showGrid() {
        appFrame.setVisible(true);
    }
}
