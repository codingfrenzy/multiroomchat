package com.tictactoe;

import java.net.MalformedURLException;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by prasanthnair on 8/20/15.
 */
public class TicTacToePlayer implements Runnable {

    static ITicTacToeServer it;

    int playerID;

    int[][] game = new int[3][3];

    public static void connectToServer(String ip) {

        System.out.println("Connecting to IP: " + ip);
        try {
            Registry registry = LocateRegistry.getRegistry(ip);
            it = (ITicTacToeServer) Naming.lookup(String.format(
                    "//%s:%s/TicTacToeServer", ip, 15213));

        } catch (RemoteException e) {
            e.printStackTrace();
            //throw new RemoteException("Failed to connect to the server (" + ip + ":" + port + ")");
        } catch (NotBoundException e) {
            e.printStackTrace();
//            throw new NotBoundException("Failed to connect to the server (" + ip + ":" + port + ")");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        if (it == null) {
            throw new RuntimeException("Failed to bind with the server. Please check if the server is up and running");
        }
    }

    private void displayGame() throws RemoteException {
        game = it.gameStatus(playerID);
        System.out.println("\0\t0\t1\t2");
        for (int i = 0; i < game.length; i++) {
            System.out.print(i);
            System.out.print('\t');
            for (int j = 0; j < game.length; j++) {
                char x;
                switch (game[i][j]) {
                    case 1:
                        x = 'X';
                        break;
                    case 2:
                        x = 'O';
                        break;
                    case 0:
                    default:
                        x = '-';
                }
                System.out.format("%c\t", x);
            }
            System.out.print("\n");
        }
    }

    private void playGame() throws RemoteException {
        displayGame();
        boolean validMove = false;
        while (!validMove) {
            System.out.println("Enter row and column to mark your move (like 00): ");
            Scanner reader = new Scanner(System.in);
            int input = reader.nextInt();
            validMove = it.makeMove((int) input / 10, input % 10, playerID);
            displayGame();
        }

    }

    @Override
    public void run() {
        try {
            playerID = it.connectPlayer();
            System.out.println("Player " + playerID + " connected.");

            while (it.playersConnected() < 2) {
                System.out.println("Waiting for other player..");
                Thread.sleep(5 * 1000);
            }

            System.out.println("Game Started!");
            char c = (playerID == 1) ? 'X' : '0';
            System.out.println("Your character is: " + c);

            if (playerID == 1) {
                playGame();
            }

            while (it.checkWinner() == 0) {
                System.out.println("Waiting for other player to make move.");
                while (!it.readyToPlay(playerID)) {
                    Thread.sleep(3 * 1000);
                }
                if (it.checkWinner() != 0)
                    break;
                playGame();
            }

            System.out.println("Game Over!");
            displayGame();
            System.out.println("Winner is: " + it.checkWinner());
            System.out.println("GGHF!");

        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        String ip =
//                "localhost";
                "52.21.170.190";
//                "50.248.80.131";

//        Scanner reader = new Scanner(System.in);
//        String input = reader.nextLine();
        if (args.length == 1) {
            ip = args[0];
        }
        connectToServer(ip);
        TicTacToePlayer tp = new TicTacToePlayer();
        Thread t1 = new Thread(tp);
        t1.start();
    }
}
