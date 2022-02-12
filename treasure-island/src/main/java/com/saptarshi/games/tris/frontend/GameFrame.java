package com.saptarshi.games.tris.frontend;

import javax.swing.JFrame;

public final class GameFrame extends JFrame {

    public GameFrame() {
        this.add(new GameBoard());
        this.setTitle("Treasure Island");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setLocationRelativeTo(null);
    }

}
