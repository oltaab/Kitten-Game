import javax.swing.*;
/**
 * The Model class represents the game logic for the BreakThrough game.
 * It manages the game state, player moves, and win conditions.
 */
public class Model {
    private int size;
    private Player actualPlayer;
    private Player[][] table;
    private int prevRow = -1;
    private int prevColumn = -1;
    private boolean flag = true;

    public Model(int size) {
        this.size = size;
        actualPlayer = Player.PLAYER1;
        table = new Player[size][size];

        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                if (i < 2) {
                    table[i][j] = Player.PLAYER1;
                } else if (i >= size - 2) {
                    table[i][j] = Player.PLAYER2;
                } else {
                    table[i][j] = Player.NOBODY;
                }
            }
        }
    }

    public boolean getFlag() {
        return flag;
    }

    public int getPrevRow() {
        return prevRow;
    }

    public int getPrevColumn() {
        return prevColumn;
    }

    public void setPrevRow(int i) {
        prevRow = i;
    }

    public void setPrevColumn(int j) {
        prevColumn = j;
    }

    public Player getActualPlayer() {
        return actualPlayer;
    }

    public void setActualPlayer(Player a, int i, int j){
        table[i][j] = a;
        if (a == Player.PLAYER1) {
            actualPlayer = Player.PLAYER2;
        } else {
            actualPlayer = Player.PLAYER1;
        }
    }


    public void setFlag(boolean f) {
        flag = f;
    }

    /**
     * Frees the specified cell on the game board by setting it to "nobody's" control.
     *
     * @param row    The row index of the cell.
     * @param column The column index of the cell.
     */
    public void freeThis(int row, int column) {
        table[row][column] = Player.NOBODY;
    }

    /**
     * Determines whether the current player can move to the specified position (i, j)
     * from their current position (curri, currj) on the game board.
     *
     * @param i     The target row index.
     * @param j     The target column index.
     * @param curri The current row index.
     * @param currj The current column index.
     * @return true if the move is valid, false otherwise.
     */
    public boolean canIMoveWithMyCurrentPosition(int i, int j, int curri, int currj) {
        if (curri < 0 || curri >= size || currj < 0 || currj >= size || i < 0 || i >= size || j < 0 || j >= size) {
            return false;
        }
        Player currentPlayer = table[curri][currj];
        if (currentPlayer == Player.PLAYER1) {
            if (i - curri == 1 && (j == currj || Math.abs(j - currj) == 1)) {
                if (table[i][j] == Player.NOBODY) {
                    return true;
                }
            }
        } else if (currentPlayer == Player.PLAYER2) {
            if (curri - i == 1 && (j == currj || Math.abs(j - currj) == 1)) {
                if (table[i][j] == Player.NOBODY) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Moves the current player to the specified cell (row, column) on the game board.
     * Updates the game state and switches to the next player's turn.
     *
     * @param row    The target row index.
     * @param column The target column index.
     * @return The player that occupies the target cell after the move.
     */
    public Player step(int row, int column) {

        if (table[row][column] != Player.NOBODY) {
            return table[row][column];
        }
        Player winner = stepWin(row,column);

        if(winner == null){
            return actualPlayer;
        }

        table[row][column] = actualPlayer;

        if (actualPlayer == Player.PLAYER1) {
            actualPlayer = Player.PLAYER2;
        } else {
            actualPlayer = Player.PLAYER1;
        }
        return table[row][column];
    }

    public Player stepWin(int row, int column){
        if ((actualPlayer == Player.PLAYER1 && row == table.length - 1) ||
                (actualPlayer == Player.PLAYER2 && row == 0)) {
            hasPlayerWon(actualPlayer);
            System.out.println("THIS WON");
            return null;
        }
        return Player.NOBODY;
    }

    public Player step2(int row, int column) {
        findWinner();
        return table[row][column];
    }


    /**
     * Determines if a diagonal move from (previ, prevj) to (i, j) is valid.
     *
     * @param previ The previous row index.
     * @param prevj The previous column index.
     * @param i     The target row index.
     * @param j     The target column index.
     * @return true if the move is valid, false otherwise.
     */
    public boolean isDiagonal(int previ, int prevj, int i, int j) {
        if (previ < 0 || previ >= size || prevj < 0 || prevj >= size || i < 0 || i >= size || j < 0 || j >= size) {
            return false;
        }
        Player currentPlayer = table[previ][prevj];
        Player targetPlayer = table[i][j];

        int rowDifference = Math.abs(i - previ);
        int colDifference = Math.abs(j - prevj);

        return rowDifference == 1 && colDifference == 1
                && ((currentPlayer == Player.PLAYER1 && targetPlayer == Player.PLAYER2)
                || (currentPlayer == Player.PLAYER2 && targetPlayer == Player.PLAYER1));
    }

    /**
     * Checks if the current player can make a valid move from their current position to (row, column).
     *
     * @param row    The target row index.
     * @param column The target column index.
     * @return true if the move is valid, false otherwise.
     */
    public boolean isMoveable(int row, int column) {
        Player currentPlayer = table[row][column];

        if (currentPlayer == actualPlayer) {
            if (currentPlayer == Player.PLAYER1) {
                if (row < size - 1 && table[row + 1][column] == Player.NOBODY) {
                    return true;
                } else if (row < size - 1 && column > 0 && table[row + 1][column - 1] == Player.NOBODY) {
                    return true;
                } else if (row < size - 1 && column < size - 1 && table[row + 1][column + 1] == Player.NOBODY) {
                    return true;
                }
            } else if (currentPlayer == Player.PLAYER2) {
                if (row > 0 && table[row - 1][column] == Player.NOBODY) {
                    return true;
                } else if (row > 0 && column > 0 && table[row - 1][column - 1] == Player.NOBODY) {
                    return true;
                } else if (row > 0 && column < size - 1 && table[row - 1][column + 1] == Player.NOBODY) {
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * Finds the winner of the game based on the current player's position.
     *
     * @return The winning player or Player.NOBODY if there is no winner yet.
     */
    public Player findWinner() {
        System.out.println(actualPlayer);
        if (actualPlayer == Player.PLAYER1) {
            for (int col = 0; col < size; col++) {
                if (table[size - 1][col] == Player.PLAYER1) {
                    return Player.PLAYER1;
                }
            }
        } else if (actualPlayer == Player.PLAYER2) {
            for (int col = 0; col < size; col++) {
                if (table[0][col] == Player.PLAYER2) {
                    return Player.PLAYER2;
                }
            }
        }
        return Player.NOBODY;
    }

    /**
     * Determines if a player is trying to move down the game board according to the game rules.
     *
     * @param i The target row index.
     * @param j The target column index.
     * @return true if the move is valid, false otherwise.
     */
    public boolean tryingToStepDown(int i,int j) {
        if (actualPlayer == Player.PLAYER1
                && i < prevRow) {
            return false;
        } else if (actualPlayer == Player.PLAYER2
                && i > prevRow) {
            return false;
        }
        return true;
    }


    public boolean hasPlayerWon(Player player) {
        if (player == Player.PLAYER1) {
            for (int col = 0; col < size; col++) {
                if (table[size - 1][col] == Player.PLAYER1) {
                    return true; // Player 1 has won
                }
            }
        } else if (player == Player.PLAYER2) {
            for (int col = 0; col < size; col++) {
                if (table[0][col] == Player.PLAYER2) {
                    return true; // Player 2 has won
                }
            }
        }
        return false; // No win for the given player
    }
}

