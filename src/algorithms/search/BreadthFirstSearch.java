package algorithms.search;

import java.util.*;


public class BreadthFirstSearch extends ASearchingAlgorithm {
    private LinkedList<AState> openList;

    public BreadthFirstSearch(){
        super();
        this.openList = new LinkedList<>();

    }

    public AState popOpenList(){
        VisitedNodes++;
        return openList.remove();
    }

    public String getName(){
        return "Breadth First Search";
    }


    @Override
    public Solution solve(ISearchable item) {
            if(item == null)
                return null;
            AState Curr = null;
            // hashmap that storages all the "nodes" that we are done with
            HashMap<String,AState> Close = new HashMap<>();
            // set the start position and checks if it is even existed
            AState Start = item.getStartState();
            if(Start == null)
                return null;
            // add the start position to the queue
            openList.add(Start);
            // checks if I have anymore states inside the queue
            while(!openList.isEmpty()){
                Curr = popOpenList();
                // checks if my current state is the goal state
                if(Curr.equals(item.getGoalState())){
                    return new Solution(Curr);
                }
                else{
                    // gets all the neighbors of the current state
                    ArrayList<AState> Neighbors = item.getAllStates(Curr);
                    for(AState state : Neighbors){
                        // checks if the neighbor has been checked already if not set the price of the arrival to him,
                        // set the current state as his "father" and enters him to the close hashmap and to the queue
                        if(!Close.containsKey(state.toString()) && !openList.contains(state)){
                            state.setCameFrom(Curr);
                            Close.put(state.toString(),state);
                            openList.add(state);
                        }
                    }
                    // enters the current state to the close hashmap
                    Close.put(Curr.toString(),Curr);
                }
            }
            return null ;
        }
    }

