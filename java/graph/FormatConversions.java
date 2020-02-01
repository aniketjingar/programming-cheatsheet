package graph;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Sometimes graphs are given in one form and we need it in some other format for our algorithm.
 * This class aims to cover common formats and their conversions.
 */
public class FormatConversions {
  public static void main(String[] args) {
    // Sample Unweighted DAGs (Directed Acyclic Graph)
    String[][] graph = {
            {"A", "B"},
            {"A", "C"},
            {"B", "K"},
            {"C", "K"},
            {"E", "L"},
            {"F", "G"},
            {"J", "M"},
            {"E", "F"},
            {"G", "H"},
            {"G", "I"}
    };
    Map<String, List<String>> adjacencyList = getAdjacencyListFromEdgeList(graph);

    System.out.println(adjacencyList);
    /*
       Output:
            {A=[B, C], B=[K], C=[K], E=[L, F], F=[G], G=[H, I], H=[], I=[], J=[M], K=[], L=[], M=[]}
     */
  }

  /**
   * One simple way to represent a graph is just a list, or array, of |E| edges, which we call an
   * edge list. To represent an edge, we just have an array of two vertex numbers, or an array of
   * objects containing the vertex # of the vertices that the edges are incident on.
   * <p>
   * This method is for unweighted graphs. Edge list for weighted graphs usually have 3rd dimension
   * of weight.
   *
   * @param edgePairs pairs of edges
   * @return adjacency list
   */
  private static Map<String, List<String>> getAdjacencyListFromEdgeList(String[][] edgePairs) {
    Map<String, List<String>> graph = new HashMap<>();

    for (String[] pair : edgePairs) {
      List<String> adjacent = graph.getOrDefault(pair[0], new LinkedList<>());
      adjacent.add(pair[1]);
      graph.put(pair[0], adjacent);

      if (!graph.containsKey(pair[1])) { // do this block only if you need entry for sinks, too.
        // like A->[]
        graph.put(pair[1], new LinkedList<>());
      }
    }
    return graph;
  }
}
