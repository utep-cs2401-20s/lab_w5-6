import static org.junit.jupiter.api.Assertions.*;

class SnakeGameTest {
    static boolean[][] board = {
            {false,false,true,false,false},
            {false,false,true,false,false},
            {false,false,true,true,false},
            {false,false,false,false,false}
    };
    static SnakeGame game = new SnakeGame(board,0,2);

    /* ******************** Exhaustive tests ******************** */
    @org.junit.jupiter.api.Test
    void findTailExhaustive_checks() {
        game.findTailExhaustive();
        assertEquals(14,game.getExhaustiveChecks());
    }

    @org.junit.jupiter.api.Test
    void findTailExhaustive_tailAndLength() {
        assertArrayEquals(new int[]{2,3,4},game.findTailExhaustive());
    }

    /* ******************** Recursive tests ******************** */
    @org.junit.jupiter.api.Test
    void findTailRecursive_checks() {
        game.findTailRecursive();
        assertEquals(4,game.getRecursiveChecks());
    }

    @org.junit.jupiter.api.Test
    void findTailRecursive_tailAndLength() {
        assertArrayEquals(new int[]{2,3,4},game.findTailRecursive());
    }
}