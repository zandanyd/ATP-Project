package algorithms.search;


import java.io.Serializable;
import java.util.Collection;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

public abstract class ASearchingAlgorithm implements ISearchingAlgorithm, Serializable {

    protected Collection<AState> openList;
    protected int VisitedNodes;

    public ASearchingAlgorithm(){
        VisitedNodes = 0;
    }

    public abstract AState popOpenList();

    @Override
    public int getNumberOfNodesEvaluated() {
        return this.VisitedNodes;
    }

    public abstract Solution solve(ISearchable iSearchable);






}
