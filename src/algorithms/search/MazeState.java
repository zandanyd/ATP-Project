package algorithms.search;

import algorithms.mazeGenerators.Position;

public class MazeState extends AState{


    public MazeState(Position state) {
       super(state);
    }
    public MazeState(Position state, double cost){
        super(state,cost);
    }

    @Override
    public Object getState() {
        return this.State;
    }
}
