package algorithms.search;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;


public class BestFirstSearch extends ASearchingAlgorithm {

    class  AStateComparator implements Comparator<AState> {


        @Override
        public int compare(AState o1, AState o2) {
            if(o1.cost > o2.cost){
                return 1;
            }
            if (o1.cost < o2.cost){
                return -1;
            }
            return 0;
        }
    }
    protected PriorityQueue<AState> openList;

    public BestFirstSearch() {
        super();
        openList = new PriorityQueue<>(( new AStateComparator()));

    }

    public String getName(){
        return "Best First Search";
    }

    public AState popOpenList(){
        VisitedNodes++;
        return openList.poll();
    }
    public Solution solve(ISearchable item){
        if(item == null)
            return null;
        AState Curr = null;
        // hashmap that storages all the "nodes" that we are done with
        HashMap<String,AState> Close = new HashMap<>();
        // set the start position and checks if it is even existed
        AState Start = item.getStartState();
        if(Start == null)
            return null;
        // add the start position to the priority queue
        openList.add(Start);
        // checks if I have anymore states inside the priority queue
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
                    // set the current state as his "father" and enters him to the close hashmap and to the priority queue
                        if(!Close.containsKey(state.toString()) && !openList.contains(state)){
                            state.setCost(state.getCost() + Curr.getCost());
                            state.setCameFrom(Curr);
                            Close.put(state.toString(),state);
                            openList.add(state);
                        }
                }
                // enters the current state to the close hashmap
            Close.put(Curr.toString(), Curr);
            }
        }
        return null ;
    }
}
