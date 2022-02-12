package com.saptarshi.games.tris.main;

import javax.swing.SwingUtilities;

import com.saptarshi.games.tris.frontend.GameFrame;

public final class TreasureIsland {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GameFrame().setVisible(true));
    }

}
