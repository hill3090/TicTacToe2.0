package com.model;

import com.view.*;
import java.util.Random;

public class Model {

    private char[][] board;
    private int boarddim;
    private int movesCount;
    private boolean won;
    private Random rand;
    private int random;
    private int randomTwo;

    private String nameOne;
    private String nameTwo;

    private Player playerOne;
    private Player computer;

    private View view;

    public Model()  {

        boarddim = 3;
        rand = new Random();
        won = false;

        this.board = new char[boarddim][boarddim];
        movesCount = 9;
        playerOne = new Player("Andrew", 123,true);
        computer = new Player("Tori", 345,false);

    }

    public void registerView(View v) {
        this.view = v;
    }

    public void updateBoard(int x, int y)   {

        // Figures out what player has a turn
        playerTurns();

        // Implements moves and updated the board, followed by the view
        if (movesCount > 0 && won == false) {
            board[x][y] = 'X';
            movesCount--;
            view.updateView(x, y, board[x][y], "Computer's Turn");

            won = checkWin(x, y);
            if (won) {
                movesCount = 0;
                if (playerOne.isTurn()) {
                    view.gameWon(playerOne.getName() + " has won.");
                    return;
                } else {
                    view.gameWon(computer.getName() + " has won.");
                    return;
                }
            }
            random = rand.nextInt(boarddim);
            randomTwo = rand.nextInt(boarddim);
            while (board[random][randomTwo] != '\0')    {
                random = rand.nextInt(boarddim);
                randomTwo = rand.nextInt(boarddim);
            }
            board[random][randomTwo] = 'O';
            view.computerClick(random, randomTwo);
            movesCount--;
            view.updateView(x, y, board[x][y], "Player One's Turn");

            won = checkWin(x, y);
            if (won) {
                movesCount = 0;
                if (playerOne.isTurn())
                    view.gameWon(playerOne.getName() + " has won.");
                else
                    view.gameWon(computer.getName() + " has won.");
            }
        }
    }

    public void updateBoardComputer(int x, int y)   {
        board[random][randomTwo] = 'O';
        view.updateView(x, y, board[x][y], "Player One's Turn");

        won = checkWin(x, y);
        if (won) {
            movesCount = 0;
            if (playerOne.isTurn())
                view.gameWon(playerOne.getName() + " has won.");
            else
                view.gameWon(computer.getName() + " has won.");
        }

    }

    public boolean checkWin(int x, int y)   {

        char symbol;

        if (playerOne.isTurn()) {
            symbol = 'X';
        } else  {
            symbol = 'O';
        }

        // Check Column
        for (int i = 2; i >= 0; i--) {
            if(board[i][y] != symbol)  {
                break;
            }
            if (i == 0) {
                return true;
            }
        }

        // Check Row
        for (int i = 2; i >= 0; i--) {
            if(board[x][i] != symbol)  {
                break;
            }
            if (i == 0) {
                return true;
            }
        }

        // Check Diagonal
        if (x == y) {
            for (int i = 2; i >= 0; i--)    {
                if (board[i][i] != symbol)  {
                    break;
                }
                if (i == 0) {
                    return true;
                }
            }
        }

        // Check anti-diagonal
        if (x+y == boarddim - 1)   {
            for (int i = 2; i >= 0; i--) {
                if(board[i][(boarddim-i) - 1] != symbol)    {
                    break;
                }
                if (i == 0) {
                    return true;
                }
            }
        }

        return false;

    }

    public char[][] getBoard()  {
        return board;
    }

    public void playerTurns()   {

        // Keeps track of player turns
        if (movesCount % 2 != 0) {
            playerOne.updateTurn(true);
            computer.updateTurn(false);
        } else  {
            computer.updateTurn(true);
            playerOne.updateTurn(false);
        }
    }


    public void reset() {
        for (int row = 0; row < 3; row++)   {
            for (int col = 0; col < 3; col++)   {
                board[row][col] = '\0';
                view.reset(row, col, board[row][col], "Tic Tac Toe");
            }
        }
        movesCount = 9;
        won = false;
    }
}
