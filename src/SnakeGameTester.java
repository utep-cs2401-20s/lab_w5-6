import static org.junit.jupiter.api.Assertions.*;

class SnakeGameTester {
    static boolean[][] board0 = {
            {false,false,true,false,false},
            {false,false,true,false,false},
            {false,false,true,true,false},
            {false,false,false,false,false}
    };
    static SnakeGame game0 = new SnakeGame(board0,0,2);
    static boolean[][] board1 = {
            {false,false,true,false},
            {false,false,true,false},
            {false,true,true,false},
            {true,true,false,false}
    };
    static SnakeGame game1 = new SnakeGame(board1,3,0);

    /* ******************** Exhaustive tests ******************** */
    @org.junit.jupiter.api.Test
    void findTailExhaustive_checks0() {
        game0.findTailExhaustive();
        assertEquals(14,game0.getExhaustiveChecks());
    }

    @org.junit.jupiter.api.Test
    void findTailExhaustive_tailAndLength0() {
        assertArrayEquals(new int[]{2,3,4},game0.findTailExhaustive());
    }

    @org.junit.jupiter.api.Test
    void findTailExhaustive_checks1() {
        game1.findTailExhaustive();
        assertEquals(3,game1.getExhaustiveChecks());
    }

    @org.junit.jupiter.api.Test
    void findTailExhaustive_tailAndLength1() {
        assertArrayEquals(new int[]{0,2,6},game1.findTailExhaustive());
    }

    /* ******************** Recursive tests ******************** */
    @org.junit.jupiter.api.Test
    void findTailRecursive_checks0() {
        game0.findTailRecursive();
        assertEquals(4,game0.getRecursiveChecks());
    }

    @org.junit.jupiter.api.Test
    void findTailRecursive_tailAndLength0() {
        assertArrayEquals(new int[]{2,3,4},game0.findTailRecursive());
    }

    @org.junit.jupiter.api.Test
    void findTailRecursive_checks1() {
        game1.findTailRecursive();
        assertEquals(6,game1.getRecursiveChecks());
    }

    @org.junit.jupiter.api.Test
    void findTailRecursive_tailAndLength1() {
        assertArrayEquals(new int[]{0,2,6},game1.findTailRecursive());
    }

}