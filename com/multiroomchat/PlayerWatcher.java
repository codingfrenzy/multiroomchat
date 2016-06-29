package com.multiroomchat;

import java.rmi.RemoteException;

/**
 * Created by prasanthnair on 8/27/15.
 */
public class PlayerWatcher implements Runnable {
    ITicTacToeServer t3i;

    static int PING = Config.PING;

    public PlayerWatcher(ITicTacToeServer t3i) {
        this.t3i = t3i;
    }

    @Override
    public void run() {
//        System.out.println(t3s.getTest());
        try {
            while (true) {
                // when a player disconnects
                // when game ends

                if (ifServerReset()) {
                    System.out.println("Server was reset because the other player has disconnected.");
                    return;
                }

                Thread.sleep(PING * 1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private boolean ifServerReset() {
        if()
        return false;
    }

}
