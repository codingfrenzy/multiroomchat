package com.multiroomchat;

import java.rmi.*;

/**
 * Created by prasanthnair on 8/20/15.
 */
public interface ITicTacToeServer extends Remote {

    // time left should be implemented in the client side;
//    public int timeLeft() throws RemoteException;

//    public void setTest(int test) throws RemoteException;
//    public int getTest() throws RemoteException;

    public int connectPlayer() throws RemoteException;

    public int playersConnected() throws RemoteException;

    public boolean makeMove(int row, int col, int playerID) throws RemoteException;

    public int[][] gameStatus(int playerID) throws RemoteException;

    public boolean readyToPlay(int playerID) throws RemoteException;

    public int checkWinner() throws RemoteException;

    public boolean ifServerReset() throws RemoteException;

}
