package algorithms.search;

import algorithms.mazeGenerators.IMazeGenerator;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BestFirstSearchTest {
    private BestFirstSearch bestFirstSearch = new BestFirstSearch();

    @Test
    void popOpenList() {
        assertEquals(0,bestFirstSearch.getNumberOfNodesEvaluated());
        bestFirstSearch.popOpenList();
        assertEquals(1,bestFirstSearch.getNumberOfNodesEvaluated());
    }

    @Test
    void solve() {
        IMazeGenerator mg = new MyMazeGenerator();
        Maze maze = mg.generate(0, 0);
        SearchableMaze searchableMaze = new SearchableMaze(maze);
        assertNull(bestFirstSearch.solve(searchableMaze));
        Maze maze1 = mg.generate(5, 1);
        SearchableMaze searchableMaze1 = new SearchableMaze(maze1);
        assertEquals(Solution.class, bestFirstSearch.solve(searchableMaze1).getClass());
        Maze maze2 = mg.generate(1, 24);
        SearchableMaze searchableMaze2 = new SearchableMaze(maze2);
        assertEquals(Solution.class, bestFirstSearch.solve(searchableMaze2).getClass());
        Maze maze3 = mg.generate(1, 1);
        SearchableMaze searchableMaze3 = new SearchableMaze(maze3);
        assertEquals(Solution.class, bestFirstSearch.solve(searchableMaze3).getClass());
    }
}