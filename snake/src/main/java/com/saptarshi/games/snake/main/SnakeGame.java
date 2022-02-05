package com.saptarshi.games.snake.main;

import com.saptarshi.games.snake.frontend.GameFrame;

import javax.swing.SwingUtilities;

public class SnakeGame {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new GameFrame().setVisible(true);
            }
        });
    }

}
