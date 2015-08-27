package com.tictactoe;

import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.ServerRef;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

/**
 * Created by prasanthnair on 8/20/15.
 */
public class TicTacToeServer implements ITicTacToeServer {

    static ITicTacToeServer t;

    int playersConnected = 0;

    int winner = 0;

    int[][] gameStatus = {
            {0, 0, 0},
            {0, 0, 0},
            {0, 0, 0}
    };

    int lastPlayed = 0;

    TicTacToeServer() throws RemoteException {
        super();
    }

    public static void makeStub() {
        try {
            String name = "TicTacToeServer";
            t = new TicTacToeServer();

//            UnicastRemoteObject.unexportObject(t, true);
            ITicTacToeServer stub = (ITicTacToeServer) UnicastRemoteObject.exportObject(t, 0);

            Registry registry = LocateRegistry.createRegistry(15213);
            registry.rebind(name, stub);
            System.out.println("TicTacToeServer bound");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int connectPlayer() throws RemoteException {
        playersConnected++;
        return playersConnected;
    }

    @Override
    public int playersConnected() throws RemoteException {
        return playersConnected;
    }

    @Override
    public boolean makeMove(int row, int col, int playerID) throws RemoteException {
        if (gameStatus[row][col] == 0) {
            gameStatus[row][col] = playerID;
            lastPlayed = playerID;
            return true;
        } else
            return false;
    }

    @Override
    public int[][] gameStatus(int playerID) throws RemoteException {
        return gameStatus;
    }

    @Override
    public boolean readyToPlay(int playerID) throws RemoteException {
        if (lastPlayed == playerID || lastPlayed == 0)
            return false;
        else
            return true;
    }

    private boolean valueEquals(int a, int b, int c) {
        return a == b && b == c && c != 0;
    }

    @Override
    public int checkWinner() throws RemoteException {
        if (winner != 0)
            return winner;

        if (
                valueEquals(gameStatus[0][0], gameStatus[1][1], gameStatus[2][2]) ||
                        valueEquals(gameStatus[0][2], gameStatus[1][1], gameStatus[2][0]) ||
                        valueEquals(gameStatus[0][0], gameStatus[0][1], gameStatus[0][2]) ||
                        valueEquals(gameStatus[1][0], gameStatus[1][1], gameStatus[1][2]) ||
                        valueEquals(gameStatus[2][0], gameStatus[2][1], gameStatus[2][2]) ||
                        valueEquals(gameStatus[0][0], gameStatus[1][0], gameStatus[2][0]) ||
                        valueEquals(gameStatus[0][1], gameStatus[1][1], gameStatus[2][1]) ||
                        valueEquals(gameStatus[0][2], gameStatus[1][2], gameStatus[2][2])
                )
            return lastPlayed;
        else
            return 0;
    }

    public static void main(String[] args) {

//        System.setProperty("java.net.preferIPv4Stack", "true");

        String ip =
//                "localhost";
                "52.21.170.190";
//                "50.248.80.131";

//        System.out.println("Enter IP (default: 52.4.225.209): ");
//        Scanner reader = new Scanner(System.in);
//        String input = reader.nextLine();
        if (args.length == 1) {
            ip = args[0];
        }

        System.setProperty("java.rmi.server.hostname", ip);
        makeStub();
    }
}
