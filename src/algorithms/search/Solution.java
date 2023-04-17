package algorithms.search;

import algorithms.mazeGenerators.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Solution {

    private AState state;

    Solution(AState state){
        this.state = state;
    }


    // this method checks recursively from the goal state the "father" the state until we got the start state, the method
    // returns a path of solution as a list
    public ArrayList<AState> getSolutionPath(){
        Stack<AState> stack = new Stack<>();
        ArrayList<AState> list = new ArrayList<>();
        if(this.state == null){
            return null;
        }
        stack.push(state);
        while (stack.peek().cameFrom != null){
            stack.push(stack.peek().getCameFrom());
        }
        while (!stack.isEmpty()){
            list.add(stack.pop());
        }
        return list;
    }
}
