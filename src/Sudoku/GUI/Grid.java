package Sudoku.GUI;

import javax.swing.*;
import java.awt.*;

public class Grid {

    private final JFrame appFrame;
    private final JPanel GridPanel;
    private final int[][] grid;
    
    public Grid(int[][] grid, JButton startThreadsButton) throws Exception {
        if (grid.length != 9 || grid[0].length != 9) {
            throw new Exception("The dimension of the grid must be 9 X 9");
        }
        this.grid = grid;

        appFrame = new JFrame("Sudoku");
        GridPanel = new JPanel();
        JPanel sidePanel = new JPanel();

        GridPanel.setLayout(new GridLayout(9, 9, 5, 5));
        GridPanel.setSize(500, 500);
        GridPanel.setBackground(Color.CYAN);
        GridPanel.setLocation(0, 0);

        sidePanel.setLayout(new GridBagLayout());
        sidePanel.setSize(200, 500);
        sidePanel.setLocation(500, 0);
        sidePanel.add(startThreadsButton);

        appFrame.setSize(700, 540);
        appFrame.setLocation(600, 250);
        appFrame.setLayout(null);
        appFrame.setResizable(false);
        appFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        appFrame.add(GridPanel);
        appFrame.add(sidePanel);

        fillGrid();
    }

    private void fillGrid() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                JButton button = new JButton(Integer.toString(grid[i][j]));
                button.setBackground(Color.cyan);
                GridPanel.add(button);
            }
        }
    }

    public void showGrid() {
        appFrame.setVisible(true);
    }
}
