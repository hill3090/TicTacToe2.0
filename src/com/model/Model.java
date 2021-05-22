package com.model;

import com.view.*;

public class Model {

    private char[][] board;
    private int boarddim;
    private int movesCount;
    private boolean won;

    private String nameOne;
    private String nameTwo;

    private Player playerOne;
    private Player playerTwo;

    private View view;

    public Model()  {

        boarddim = 3;
        this.board = new char[boarddim][boarddim];
        movesCount = 9;
        playerOne = new Player("Andrew", 123,true);
        playerTwo = new Player("Tori", 345,false);

    }

    public void registerView(View v) {
        this.view = v;
    }

    public void updateBoard(int x, int y)   {

        // Figures out what player has a turn
        playerTurns();

        // Implements moves and updated the board, followed by the view
        if (movesCount > 0) {
            if (playerOne.isTurn()) {
                board[x][y] = 'X';
                movesCount--;
                view.updateView(x, y, board[x][y], "Player Two's Turn");  // Calls view and updates it
            } else {
                board[x][y] = 'O';
                movesCount--;
                view.updateView(x, y, board[x][y], "Player One's Turn");  // Calls view and updates it
            }
        }

        // Check the win conditions
        won = checkWin(x, y);
        if (won) {
            movesCount = 0;
            if (playerOne.isTurn())
                view.gameWon(playerOne.getName() + " has won.");
            else
                view.gameWon(playerTwo.getName() + " has won.");
        }

        // Change turn to next player
        playerTurns();
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
        /*int countRow = 0;
        int countCol = 0;
        int countLDiag = 0;
        int countRDiag = 0;
        char symbol;
        if(playerOne.isTurn())
            symbol = 'X';
        else
            symbol = 'O';

        for(int i=0; i<board.length;i++) {
            if(board[x][i] == symbol)
                countRow++;
            if(board[i][y] == symbol)
                countCol++;
            if(board[i][i] == symbol)
                countRDiag++;
            if(board[board.length-1-i][i] == symbol)
                countLDiag++;
        }

        if(countCol==board.length || countRow==board.length
                || countLDiag == board.length || countRDiag == board.length)
            return true;
        */

    }

    public char[][] getBoard()  {
        return board;
    }

    public void playerTurns()   {

        // Keeps track of player turns
        if (movesCount % 2 != 0) {
            playerOne.updateTurn(true);
            playerTwo.updateTurn(false);
        } else  {
            playerTwo.updateTurn(true);
            playerOne.updateTurn(false);
        }
    }


    public void reset() {
        for (int row = 0; row < 3; row++)   {
            for (int col = 0; col < 3; col++)   {
                board[row][col] = ' ';
                view.reset(row, col, board[row][col], "Tic Tac Toe");
            }
        }
        movesCount = 9;

    }
}
