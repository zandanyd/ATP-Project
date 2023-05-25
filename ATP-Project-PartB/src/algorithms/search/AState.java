package algorithms.search;

import algorithms.mazeGenerators.Position;

import java.io.Serializable;

public abstract class AState implements Serializable {
    protected Object State;
    protected double cost;
    protected AState cameFrom;

    public AState(Object state){
        this.State =state;
        this.cost = 0;
        this.cameFrom = null;
    }
    public AState(Object state, double cost){
        this.State = state;
        this.cost = cost;
    }

    @Override
    public boolean equals(Object obj) {
        return this.State.equals(((AState) obj).State);
    }

    public void setCameFrom(AState cameFrom) {
        this.cameFrom = cameFrom;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public double getCost() {
        return cost;
    }

    public abstract Object getState();

    public AState getCameFrom() {
        return cameFrom;
    }

    @Override
    public String toString() {
        return this.State.toString();
    }

}
