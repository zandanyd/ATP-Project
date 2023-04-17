package algorithms.search;

public interface ISearchingAlgorithm {
    public Solution solve(ISearchable item);
    public AState popOpenList();
    public int getNumberOfNodesEvaluated();
    public String getName();
}
