package com.tictactoe;

/**
 * Created by prasanthnair on 8/27/15.
 */
public class Watcher implements Runnable {
    TicTacToeServer t3s;

    public Watcher(TicTacToeServer t3s) {
        this.t3s = t3s;
    }

    // when a player disconnects
    // when game ends

    @Override
    public void run() {
//        System.out.println(t3s.getTest());
    }
}
