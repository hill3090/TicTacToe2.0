package com.model;

import com.view.*;
import java.util.Random;

public class Model {

    private char[][] board;
    ComputerMove compMove;
    private int boarddim;
    private int movesCount;
    private int won;
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
        won = 0;

        this.board = new char[boarddim][boarddim];
        movesCount = 9;
        playerOne = new Player("Andrew", 123,true);
        computer = new Player("Tori", 345,false);
        compMove = new ComputerMove();

    }

    public boolean isMovesLeft(char board[][])
    {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (board[i][j] == '_')
                    return true;
        return false;
    }

    public void registerView(View v) {
        this.view = v;
    }

    public void updateBoard(int x, int y)   {

        // Figures out what player has a turn
        playerTurns();

        // Implements moves and updated the board, followed by the view
        if (movesCount > 0 && won == 0) {
            board[x][y] = 'X';
            movesCount--;
            view.updateView(x, y, board[x][y], "Computer's Turn");

            won = checkWin(x, y);
             if (won == -1) {
                 view.gameWon("Tie game.");
                 return;
             } else if (won != 0) {
                movesCount = 0;
                if (playerOne.isTurn()) {
                    view.gameWon(playerOne.getName() + " has won.");
                    return;
                } else {
                    view.gameWon(computer.getName() + " has won.");
                    return;
                }
            }
            compMove = findBestMove(board);
            board[compMove.row][compMove.col] = 'O';
            view.computerClick(compMove.row, compMove.col);
            movesCount--;

            // Not sure why this is here....left just in case.
            //view.updateView(x, y, board[x][y], "Player One's Turn");

            won = checkWin(x, y);

            if (won == -1) {
                view.gameWon("Tie game.");
                return;
            } else if (won != 0) {
                movesCount = 0;
                if (playerOne.isTurn())
                    view.gameWon(playerOne.getName() + " has won.");
                else
                    view.gameWon(computer.getName() + " has won.");
            }
        }
    }

    public void updateBoardComputer(int x, int y)   {
        board[compMove.row][compMove.col] = 'O';
        view.updateView(x, y, board[x][y], "Player One's Turn");
        playerTurns();

        won = checkWin(x, y);
        if (won == -1) {
            view.gameWon("Tie game.");
            return;
        } else if (won != 0) {
            movesCount = 0;
            if (playerOne.isTurn())
                view.gameWon(playerOne.getName() + " has won.");
            else
                view.gameWon(computer.getName() + " has won.");
        }
    }

    public ComputerMove findBestMove(char board[][])    {

        int bestVal = -1000;
        ComputerMove bestMove = new ComputerMove();
        bestMove.row = -1;
        bestMove.col = -1;

        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                // Check if cell is empty
                if (board[i][j] == '\0')
                {
                    // Make the move
                    board[i][j] = 'X';

                    // compute evaluation function for this
                    // move.
                    int moveVal = minimax(board, 0, false);

                    // Undo the move
                    board[i][j] = '\0';

                    // If the value of the current move is
                    // more than the best value, then update
                    // best/
                    if (moveVal > bestVal)
                    {
                        bestMove.row = i;
                        bestMove.col = j;
                        bestVal = moveVal;
                    }
                }
            }
        }

        return bestMove;
    }

    public int minimax(char board[][], int depth, boolean isMax)    {
        int score = evaluate(board);

        // If Maximizer has won the game
        // return his/her evaluated score
        if (score == 10)
            return score;

        // If Minimizer has won the game
        // return his/her evaluated score
        if (score == -10)
            return score;

        // If there are no more moves and
        // no winner then it is a tie
        if (isMovesLeft(board) == false)
            return 0;

        // If this maximizer's move
        if (isMax)
        {
            int best = -1000;

            // Traverse all cells
            for (int i = 0; i < 3; i++)
            {
                for (int j = 0; j < 3; j++)
                {
                    // Check if cell is empty
                    if (board[i][j]=='\0')
                    {
                        // Make the move
                        board[i][j] = 'X';

                        // Call minimax recursively and choose
                        // the maximum value
                        best = Math.max(best, minimax(board,
                                depth + 1, !isMax));

                        // Undo the move
                        board[i][j] = '\0';
                    }
                }
            }
            return best;
        }

        // If this minimizer's move
        else
        {
            int best = 1000;

            // Traverse all cells
            for (int i = 0; i < 3; i++)
            {
                for (int j = 0; j < 3; j++)
                {
                    // Check if cell is empty
                    if (board[i][j] == '\0')
                    {
                        // Make the move
                        board[i][j] = 'O';

                        // Call minimax recursively and choose
                        // the minimum value
                        best = Math.min(best, minimax(board,
                                depth + 1, !isMax));

                        // Undo the move
                        board[i][j] = '\0';
                    }
                }
            }
            return best;
        }
    }

    public int evaluate(char b[][])
    {
        // Checking for Rows for X or O victory.
        for (int row = 0; row < 3; row++)
        {
            if (b[row][0] == b[row][1] &&
                    b[row][1] == b[row][2])
            {
                if (b[row][0] == 'X')
                    return +10;
                else if (b[row][0] == 'O')
                    return -10;
            }
        }

        // Checking for Columns for X or O victory.
        for (int col = 0; col < 3; col++)
        {
            if (b[0][col] == b[1][col] &&
                    b[1][col] == b[2][col])
            {
                if (b[0][col] == 'X')
                    return +10;

                else if (b[0][col] == 'O')
                    return -10;
            }
        }

        // Checking for Diagonals for X or O victory.
        if (b[0][0] == b[1][1] && b[1][1] == b[2][2])
        {
            if (b[0][0] == 'X')
                return +10;
            else if (b[0][0] == 'O')
                return -10;
        }

        if (b[0][2] == b[1][1] && b[1][1] == b[2][0])
        {
            if (b[0][2] == 'X')
                return +10;
            else if (b[0][2] == 'O')
                return -10;
        }

        // Else if none of them have won then return 0
        return 0;
    }


    public int checkWin(int x, int y)   {

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
                if (symbol == 'X')
                    return 10;
                else
                    return -10;
            }
        }

        // Check Row
        for (int i = 2; i >= 0; i--) {
            if(board[x][i] != symbol)  {
                break;
            }
            if (i == 0) {
                if (symbol == 'X')
                    return 10;
                else
                    return -10;
            }
        }

        // Check Diagonal
        if (x == y) {
            for (int i = 2; i >= 0; i--) {
                if (board[i][i] != symbol) {
                    break;
                }
                if (i == 0) {
                    if (symbol == 'X')
                        return 10;
                    else
                        return -10;
                }
            }
        }

        // Check anti-diagonal
        if (x+y == boarddim - 1) {
            for (int i = 2; i >= 0; i--) {
                if (board[i][(boarddim - i) - 1] != symbol) {
                    break;
                }
                if (i == 0) {
                    if (symbol == 'X')
                        return 10;
                    else
                        return -10;
                }
            }
        }

        if(movesCount == 0){
            return -1;
        }

        return 0;
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
        won = 0;
    }
}
