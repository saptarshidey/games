package com.saptarshi.games.snake.frontend;

import javax.swing.JFrame;
import java.awt.Dimension;
public class GameFrame extends JFrame {

    public GameFrame() {
        this.add(new GameBoard(new Dimension(600, 600), 30, 4, 200));
        this.setTitle("Old School Snake");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setLocationRelativeTo(null);
    }

}
