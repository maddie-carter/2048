import java.awt.event.ActionEvent;

public class Board {
    public enum Direction {
        UP, DOWN, LEFT, RIGHT
    }
    public Square[][] board;

    int grids = 4;

    int border = 0;

    public int score = 0;
    private int animationStep = 0;

    public void resetAnimationStep() {
        animationStep = 0;
    }

    public void incrementAnimationStep() {
        animationStep++;
    }

    public int getAnimationStep() {
        return animationStep;
    }




    public Board() {
        board = new Square[4][4];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = new Square();
            }
        }
    }
    public Square[][] getBoard()
    {
        return board;
    }
    public int getScore()
    {
        return score;
    }
    public void spawn()
    {
        boolean empty = true;
        while ( empty )
        {
            int row = (int)( Math.random() * 4 );
            int col = (int)( Math.random() * 4 );
            double x = Math.random();
            if ( board[row][col].getValue() == 0 )
            {
                if ( x < 0.3 )
                {
                    board[row][col] = new Square( 4 );
                    empty = false;
                }
                else
                {
                    board[row][col] = new Square( 2 );
                    empty = false;
                }
            }

        }

    }

    public void moveTiles(Direction direction) {
        boolean moved = false;


        resetMergedFlags();


        int dI = 0, dJ = 0;
        switch (direction) {
            case UP:
                dI = -1;
                break;
            case DOWN:
                dI = 1;
                break;
            case LEFT:
                dJ = -1;
                break;
            case RIGHT:
                dJ = 1;
                break;
        }


        for (int i = 0; i < grids; i++) {
            for (int j = 0; j < grids; j++) {
                Square currentTile = board[i][j];
                if (currentTile.getValue() != 0) {
                    int newI = i + dI;
                    int newJ = j + dJ;


                    while (newI >= 0 && newI < grids && newJ >= 0 && newJ < grids) {
                        Square nextTile = board[newI][newJ];
                        if (nextTile.getValue() == 0) {
                            // Move to an empty cell
                            board[newI][newJ] = currentTile;
                            board[i][j] = new Square(); // Clear the original cell
                            i = newI; // Update i and j for the next iteration
                            j = newJ;
                            newI += dI;
                            newJ += dJ;
                            moved = true;
                        } else if (nextTile.getValue() == currentTile.getValue() && !nextTile.isMerged()) {
                            // Merge with the next tile if values match and it hasn't been merged before
                            nextTile.setValue(currentTile.getValue() * 2);
                            nextTile.setMerged(true);
                            board[i][j] = new Square(); // Clear the original cell
                            //score += nextTile.getValue(); // Increase score
                            moved = true;
                            break; // Exit the loop to avoid further merging
                        } else {
                            // Stop if an obstacle is encountered
                            break;
                        }
                    }
                }
            }
        }

        // Reset merged flags after the move
        resetMergedFlags();


    }

    // Helper method to reset merged flags on all tiles
    private void resetMergedFlags() {
        for (int i = 0; i < grids; i++) {
            for (int j = 0; j < grids; j++) {
                if (board[i][j].getValue() != 0) {
                    board[i][j].setMerged(false);
                }
            }
        }
    }
          }


