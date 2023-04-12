package algorithms.search;

import java.util.*;

public class DepthFirstSearch extends ASearchingAlgorithm {

    protected Stack<AState> openList;

    public DepthFirstSearch() {
        super();
        openList = new Stack<>();

    }
    public AState popOpenList(){
        VisitedNodes++;
        return openList.pop();
    }

    public String getName(){
        return "Depth First Search";
    }

    public Solution solve(ISearchable item){
        if(item == null)
            return null;
        // hashmap that storages all the "nodes" that we are done with
        HashMap<String,AState> Visited = new HashMap<>();
        // enters the start state to the stack
        AState start = item.getStartState();
        if(start == null)
                return null;
        // add the start position to the start
        openList.push(item.getStartState());
        // checks if I have anymore states inside the priority queue
        while (!openList.isEmpty()){
            AState curr = popOpenList();
            // checks if my current state is the goal state
            if(curr.equals(item.getGoalState())){
                return new Solution(curr);
            }
            Visited.put(curr.toString(),curr);
            // gets all the neighbors of the current state
            ArrayList<AState> Neighbors = item.getAllStates(curr);
            for (AState neighbor : Neighbors) {
                // checks if the neighbor has been checked already if not
                // set the current state as his "father" and enters him and the current state to the stack
                if (!Visited.containsKey(neighbor.toString())) {
                    neighbor.setCameFrom(curr);
                    openList.push(curr);
                    openList.push(neighbor);
                    break;
                }
            }
        }
        return null;
    }
}
