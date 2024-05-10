public class Board {
    public enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    public Square[][] board;

    int grids = 4;
    int posValue[] = new int[18];
    int scoreValue = 0;

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

    public Square[][] getBoard() {
        return board;
    }

    public int getScore() {
        return score;
    }

    public void spawn() {
        boolean empty = true;
        while (empty) {
            int row = (int) (Math.random() * 4);
            int col = (int) (Math.random() * 4);
            double x = Math.random();
            if (board[row][col].getValue() == 0) {
                if (x < 0.3) {
                    board[row][col] = new Square(4);
                    empty = false;
                } else {
                    board[row][col] = new Square(2);
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
                    int newI = i;
                    int newJ = j;
                    boolean canMove = true;

                    while (canMove) {
                        int nextI = newI + dI;
                        int nextJ = newJ + dJ;

                        // Check if the next position is within the grid bounds
                        if (nextI >= 0 && nextI < grids && nextJ >= 0 && nextJ < grids) {
                            Square nextTile = board[nextI][nextJ];

                            // If the next position is empty, move the current tile to that position
                            if (nextTile.getValue() == 0) {
                                board[nextI][nextJ] = currentTile;
                                board[newI][newJ] = new Square(); // Clear the original cell
                                newI = nextI;
                                newJ = nextJ;
                                moved = true;
                            }
                            // If the next position contains a tile with the same value and hasn't been merged before, merge them
                            else if (nextTile.getValue() == currentTile.getValue() && !nextTile.isMerged()) {
                                nextTile.setValue(currentTile.getValue() * 2);
                                nextTile.setMerged(true);
                                board[newI][newJ] = new Square(); // Clear the original cell
                                moved = true;
                                canMove = false; // Exit the loop to avoid further merging
                            }
                            // If the next position contains a tile with a different value or has already been merged, stop moving
                            else {
                                canMove = false;
                            }
                        } else {
                            // If the next position is out of bounds, stop moving
                            canMove = false;
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

    /*public void up()
    {
        for ( int i = 0; i < grids; i++ )
        {
            border = 0;
            for ( int j = 0; j < grids; j++ )
            {
                if ( board[j][i].getValue() != 0 )
                {
                    if ( border <= j )
                    {
                        verticalMove( j, i, "up" );
                    }
                }
            }
        }
    }


    /**
     *
     * This method is called when a 's' or down arrow is pressed - goes through
     * the entire board and calls verticalMove with a "down" parameter for each
     * tile
     */
    /*
    public void down()
    {
        for ( int i = 0; i < grids; i++ )
        {
            border = ( grids - 1 );
            for ( int j = grids - 1; j >= 0; j-- )
            {
                if ( board[j][i].getValue() != 0 )
                {
                    if ( border >= j )
                    {
                        verticalMove( j, i, "down" );
                    }
                }
            }
        }
    }


    /**
     *
     * Compares two tile's values together and if they are the same or if one is
     * equal to 0 (plain tile) - their values are added (provided that the tiles
     * we are comparing are two different tiles and they are moving towards the
     * appropriate direction) - Uses recursion to go through the entire column
     *
     * @param row
     *            row that the compare tile is currently on
     * @param col
     *            column that the compare tile is currently on
     * @param direction
     *            direction (up or down) that the tile is moving in
     */
    /*
    private void verticalMove( int row, int col, String direction )
    {
        Square initial = board[border][col];
        Square compare = board[row][col];
        if ( initial.getValue() == 0 || initial.getValue() == compare.getValue() )
        {
            if ( row > border || ( direction.equals( "down" ) && ( row < border ) ) )
            {
                int addScore = initial.getValue() + compare.getValue();
                if ( initial.getValue() != 0 )
                {
                    score += addScore;
                }
                initial.setValue( addScore );
                compare.setValue( 0 );
            }
        }
        else
        {
            if ( direction.equals( "down" ) )
            {
                border--;
            }
            else
            {
                border++;
            }
            verticalMove( row, col, direction );
        }
    }


    /**
     *
     * This method is called when an 'a' or left arrow is pressed - goes through
     * the entire board and calls horizontalMove with a "left" parameter for
     * each tile
     */
    /*
    public void left()
    {
        for ( int i = 0; i < grids; i++ )
        {
            border = 0;
            for ( int j = 0; j < grids; j++ )
            {
                if ( board[i][j].getValue() != 0 )
                {
                    if ( border <= j )
                    {
                        horizontalMove( i, j, "left" );
                    }
                }
            }
        }
    }


    /**
     *
     * This method is called when a 'd' or right arrow is pressed - goes through
     * the entire board and calls horizontalMove with a "right" parameter for
     * each tile
     */
    /*
    public void right()
    {
        for ( int i = 0; i < grids; i++ )
        {
            border = ( grids - 1 );
            for ( int j = ( grids - 1 ); j >= 0; j-- )
            {
                if ( board[i][j].getValue() != 0 )
                {
                    if ( border >= j )
                    {
                        horizontalMove( i, j, "right" );
                    }
                }
            }
        }
    }


    /**
     *
     * Compares two tile's values together and if they are the same or if one is
     * equal to 0 (plain tile) - their values are added (provided that the tiles
     * we are comparing are two different tiles and they are moving towards the
     * appropriate direction) - Uses recursion to go through the entire row
     *
     * @param row
     *            row that the compare tile is currently on
     * @param col
     *            column that the compare tile is currently on
     * @param direction
     *            direction (left or right) that the tile is moving in
     */
    /*
    private void horizontalMove( int row, int col, String direction )
    {
        Square initial = board[row][border];
        Square compare = board[row][col];
        if ( initial.getValue() == 0 || initial.getValue() == compare.getValue() )
        {
            if ( col > border || ( direction.equals( "right" ) && ( col < border ) ) )
            {
                int addScore = initial.getValue() + compare.getValue();
                if ( initial.getValue() != 0 )
                {
                    score += addScore;
                }
                initial.setValue( addScore );
                compare.setValue( 0 );
            }
        }
        else
        {
            if ( direction.equals( "right" ) )
            {
                border--;
            }
            else
            {
                border++;
            }
            horizontalMove( row, col, direction );
        }
    }
    */
    public void up() {
        for (int col = 0; col < grids; col++) {
            for (int row = 1; row < grids; row++) {
                moveTile(row, col, -1, 0);
            }
        }
    }

    public void down() {
        for (int col = 0; col < grids; col++) {
            for (int row = grids - 2; row >= 0; row--) {
                moveTile(row, col, 1, 0);
            }
        }
    }

    public void left() {
        for (int row = 0; row < grids; row++) {
            for (int col = 1; col < grids; col++) {
                moveTile(row, col, 0, -1);
            }
        }
    }

    public void right() {
        for (int row = 0; row < grids; row++) {
            for (int col = grids - 2; col >= 0; col--) {
                moveTile(row, col, 0, 1);
            }
        }
    }

    private void moveTile(int row, int col, int dRow, int dCol) {
        int currentRow = row;
        int currentCol = col;
        Square currentTile = board[row][col];

        while (currentTile.getValue() != 0 && isValid(currentRow + dRow, currentCol + dCol)) {
            int nextRow = currentRow + dRow;
            int nextCol = currentCol + dCol;
            Square nextTile = board[nextRow][nextCol];

            if (nextTile.getValue() == 0 || nextTile.getValue() == currentTile.getValue()) {
                int sum = currentTile.getValue() + nextTile.getValue();
                if (nextTile.getValue() != 0) {
                    score += sum;
                }
                nextTile.setValue(sum);
                currentTile.setValue(0);
            } else {
                break;
            }
            currentRow = nextRow;
            currentCol = nextCol;
            currentTile = board[currentRow][currentCol];
        }
    }

    private boolean isValid(int row, int col) {
        return row >= 0 && row < grids && col >= 0 && col < grids;
    }


}



