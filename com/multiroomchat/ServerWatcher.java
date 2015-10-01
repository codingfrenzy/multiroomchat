package com.multiroomchat;

import java.rmi.RemoteException;

/**
 * Created by prasanthnair on 8/27/15.
 */
public class ServerWatcher implements Runnable {
    TicTacToeServer t3s;

    static int PING = Config.PING;

    public ServerWatcher(TicTacToeServer t3s) {
        this.t3s = t3s;
    }

    @Override
    public void run() {
//        System.out.println(t3s.getTest());
        try {
            while (true) {
                // when a player disconnects
                // when game ends

                if (playerDisconnected() || gameEnded()) {
                    resetServer();
                }

                Thread.sleep(PING * 1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void resetServer() {
        System.out.println("Resetting server");

    }

    private boolean gameEnded() throws RemoteException {
        if (t3s.checkWinner() == 0)
            return false;



        return false;
    }

    private boolean playerDisconnected() throws RemoteException {
        if (t3s.playersConnected() == 0)
            return false;

        // put the pings received from clients into an atmoic HMap, and then iterate through them and if one is above the ping threshold, then reset


        return false;
    }
}
