/**
 * @author mahdafr
 * Holds a single state of the board in the Snake Game to check for its length:
 *  - exhaustively, and
 *  - recursively.
 */
public class SnakeGame {
    private boolean[][] game;
    private int[] headPosition;
    private static int exhaustiveChecks;
    private static int recursiveChecks;

    /* ****************************** Constructors ****************************** */
    public SnakeGame() {
        //initialize the board and headPosition to a 1x1 cell
        game = new boolean[1][1];
        headPosition = new int[]{0,0};
    }

    public SnakeGame(boolean[][] g, int x, int y) {
        //initialize the board and headPosition
        game = new boolean[g.length][g[0].length];
        headPosition = new int[]{x,y};
        //copy from g into game
        for ( int i=0 ; i<g.length ; i++ )
            for ( int j=0 ; j<g[i].length ; j++ )
                game[i][j] = g[i][j];
    }

    /* ****************************** Getters/Setters ****************************** */
    public static int getExhaustiveChecks() {
        return exhaustiveChecks;
    }

    public static int getRecursiveChecks() {
        return recursiveChecks;
    }

    public static void resetCounters() {
        exhaustiveChecks = 0;
        recursiveChecks = 0;
    }

    /* ****************************** Operations ****************************** */
    /**
     * Finds the tail exhaustively, starting from cell (0,0) in the board.
     * @return the X tail position, Y tail position, and length of the snake
     */
    public int[] findTailExhaustive() {
        resetCounters();
        //store tail's x, tail's y, length of snake
        int[] ret = new int[]{-1,-1,0};

        //checks each cell in the game to find the tail
        //and counts the length of the snake
        for ( int i=0 ; i<game.length ; i++ )
            for ( int j=0 ; j<game[i].length ; j++ ) {
                //if we have not found the tail, then we increment until we do
                if ( !foundTail(ret) )
                    exhaustiveChecks++;
                //does this cell have a piece of the snake in it?
                if ( hasSnake(i,j) ) {
                    //have we found the tail?
                    if ( neighbors(i,j)==1 && !isHead(i,j) ) {
                        //save the tail's position
                        ret[0] = i;
                        ret[1] = j;
                    }
                    ret[2]++; //length increments
                }
            }
		//length of snake is 1, so the tail is the head
		if ( ret[2]==1 ) {
			ret[0] = headPosition[0];
			ret[1] = headPosition[1];
		}
        return ret;
    }

    /**
     * Finds the tail recursively, starting from the head of the snake.
     * @return the X tail position, Y tail position, and length of the snake
     */
    public int[] findTailRecursive() {
        resetCounters();
        return findTailRecursive(headPosition,headPosition);
    }

    /**
     * Iterates through the snake to find the tail recursively.
     * @param currentPosition the current cell (X,Y) to look at
     * @param previousPosition the cell (X,Y) we previously looked at
     * @return the X tail position, Y tail position, and length of the snake
     */
    private int[] findTailRecursive(int[] currentPosition, int[] previousPosition) {
        recursiveChecks++; //looked at this cell to find the tail
        //base case: found the tail!
        if ( currentPosition!=headPosition && neighbors(currentPosition[0],currentPosition[1])==1 )
            return new int[]{currentPosition[0],currentPosition[1],getLength(headPosition,headPosition)};
        //recursive case: move through the snake through the next neighbor
        return findTailRecursive(findNextNeighbor(currentPosition,previousPosition),currentPosition);
    }

    /* ****************************** Helper methods ****************************** */
    /**
     * Checks how many neighbors around cell (r,c) have a snake in it.
     * @param r the row of the cell to check
     * @param c the column of the cell to check
     * @return the number of neighbors surrounding cell (r,c) that have a snake
     */
    private int neighbors(int r, int c) {
        int n = 0;
        //row before (above)
        n += isValidCell(r-1,c) && hasSnake(r-1,c) ? 1 : 0;
        //row after (below)
        n += isValidCell(r+1,c) && hasSnake(r+1,c) ? 1 : 0;
        //col before (left)
        n += isValidCell(r,c-1) && hasSnake(r,c-1) ? 1 : 0;
        //col after (right)
        n += isValidCell(r,c+1) && hasSnake(r,c+1) ? 1 : 0;
        return n;
    }

    /**
     * Checks if the cell (r,c) is a cell within the board.
     * @param r the row of the cell to check
     * @param c the column of the cell to check
     * @return whether the cell is out of bounds (false) or not (true)
     */
    private boolean isValidCell(int r, int c) {
        return (r>=0 && r<game.length) && (c>=0 && c<game[r].length);
    }

    /**
     * Checks if the cell (r,c) has a snake in it.
     * @param r the row of the cell to check
     * @param c the column of the cell to check
     * @return whether the game has a snake (true) in this cell or not (false)
     */
    private boolean hasSnake(int r, int c) {
        return game[r][c];
    }

    /**
     * Counts the number of cells in the board which have a snake.
     * @param curr the current cell (X,Y) to look at
     * @param prev the cell (X,Y) we previously looked at
     * @return the total length of the snake (number of cells in which a snake is found)
     */
    private int getLength(int[] curr, int[] prev) {
        //base case: found the tail, stop counting!
        if ( neighbors(curr[0],curr[1])==1 && curr!=headPosition )
            return 1;
        //recursive case: move to next neighbor
        return 1 + getLength(findNextNeighbor(curr,prev),curr);
    }

    /**
     * Finds the neighboring cell that is a snake but has not been visited.
     * @param curr the current cell (X,Y) to look at
     * @param prev the cell (X,Y) we previously looked at
     * @return the cell (X,Y) of the next cell to look at with a snake
     */
    private int[] findNextNeighbor(int[] curr, int[] prev) {
        int r = curr[0], c = curr[1];
        //row before (above)
        if ( isValidCell(r-1,c) && hasSnake(r-1,c) && !isSameCell(r-1,c,prev) )
            return new int[]{r-1,c};
        //row after (below)
        if ( isValidCell(r+1,c) && hasSnake(r+1,c) && !isSameCell(r+1,c,prev) )
            return new int[]{r+1,c};
        //col before (left)
        if ( isValidCell(r,c-1) && hasSnake(r,c-1) && !isSameCell(r,c-1,prev) )
            return new int[]{r,c-1};
        //col after (right)
        if ( isValidCell(r,c+1) && hasSnake(r,c+1) && !isSameCell(r,c+1,prev) )
            return new int[]{r,c+1};
        return null;
    }

    /**
     * Checks whether the cell (r,c) is the same as cell.
     * @param r the row of the cell (r,c) to look at
     * @param c the column of the cell (r,c) to look at
     * @param cell the cell (X,Y) to compare to
     * @return whether cell (r,c) is the same as cell (true) or not (false)
     */
    private boolean isSameCell(int r, int c, int[] cell) {
        return ( r==cell[0] && c==cell[1] );
    }

    /**
     * Checks whether we have fond the tail.
     * @param arr the array to check for the tail
     * @return whether the tail has been found (true) or not (false)
     */
    private boolean foundTail(int[] arr) {
        return arr[0]!=-1;
    }

    /**
     * Checks whether the cell (r,c) is the head.
     * @param r the row of the cell (r,c) to look at
     * @param c the column of the cell (r,c) to look at
     * @return true if cell (r,c) is the head, false otherwise
     */
    private boolean isHead(int r, int c) {
        return headPosition[0]==r && headPosition[1]==c;
    }
}
