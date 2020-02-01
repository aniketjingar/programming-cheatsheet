package graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Matrix is often interpreted as graphs with various limitations and rules. Here we will take a
 * binary matrix where nodes with value '0' are possible to visit and 1s are inaccessible. Basically
 * 0s are path and 1s are walls if you will.
 */
public class MatrixTraversals {
  public static void main(String[] args) {
    int[][] input = {
            {0, 0, 0, 1, 1, 1, 1},//0
            {0, 1, 0, 0, 0, 0, 0},//1
            {0, 1, 0, 1, 0, 1, 0},//2
            {0, 1, 1, 1, 1, 0, 0},//3
            {0, 1, 0, 0, 1, 0, 0},//4
            {0, 1, 0, 0, 1, 1, 0},//5
            {0, 1, 0, 0, 1, 1, 0},//6
            {0, 0, 0, 0, 0, 0, 9} //7
            // 0  1  2  3  4  5  6
    };

    bfs(input, 9);
  }

  /**
   * A method to do Breadth First Traversal from the index [0][0] till the target value is
   * encountered. We can travel in 4 directions: North, West, East, South. Some problems allow
   * traversals only west and south. It can be tweaked as needed.
   * <p>
   * As this is BFS, we will also get always the shortest path to the target value.
   * <p>
   * At the end we will also trace that shortest path and print all the nodes we get on the way to
   * that path.
   *
   * @param input       Input matrix to traverse
   * @param targetValue Value to reach
   */
  private static void bfs(int[][] input, int targetValue) {
    int[] dr = {+1, -1, 0, 0}; // direction vector for row
    int[] dc = {0, 0, +1, -1}; // direction vector for col. Better than writing i+1,j-1 combinations

    int[][] visited = new int[input.length][input[0].length];

    // more cleaner than putting both coordinates in the same queue. This is also easily scalable
    // to higher dimensions.
    Queue<Integer> qr = new LinkedList<>();
    Queue<Integer> qc = new LinkedList<>();

    // only required if you need the whole path after finding the node
    Integer[][][] prev = new Integer[input.length][input[0].length][];

    qr.offer(0); // adding to the queue. Change if any custom starting point
    qc.offer(0);
    visited[0][0] = 1;

    // end coordinates
    int endR = 0;
    int endC = 0;
    int level = 0; // only required if you need to keep track of the current level
    outer:
    while (qr.size() > 0) {
      int qSize = qr.size();
      while (qSize-- != 0) { // only required if you need to keep track of the current level
        int r = qr.poll(); // current node
        int c = qc.poll();
        if (input[r][c] == targetValue) {
          endC = c;
          endR = r;
          break outer; // break free if you found your purpose in this journey of life
        }
        //find neighbors of the current node
        for (int i = 0; i < 4; i++) { // traversing all 4 directions
          int rr = r + dr[i];
          int cc = c + dc[i];

          if (rr < 0 || cc < 0) { // invalid. duh
            continue;
          }
          if (rr >= input.length || cc >= input[0].length) { // invalid. duh
            continue;
          }
          if (input[rr][cc] != 1 && visited[rr][cc] != 1) { // don't hit a wall and don't revisit
            visited[rr][cc] = 1;
            qc.offer(cc);
            qr.offer(rr);
            prev[rr][cc] = new Integer[]{r, c}; // for tracing path. you store current to the next.
          }
        }

      }
      level++;
      // do something here if there is anything to do at the end of each level.
    }

    /* You found the value hopefully. Return or go ahead if you want to trace back your path */
    System.out.println("found @ " + endR + "," + endC);
    // TODO checks for cases when node is not found

    // time to trace the path
    List<List<Integer>> path = new ArrayList<>();
    int r = endR;
    int c = endC;
    int count = 0; // only if you need number of steps
    while (prev[r][c] != null) {
      path.add(List.of(r, c));
      int tempR = r;
      r = prev[r][c][0];
      c = prev[tempR][c][1];
      count++;
    }

    path.add(List.of(0, 0)); // never forget your roots
    Collections.reverse(path);
    System.out.println("path = " + path);
    System.out.println("steps count = " + (++count));

    /* Output:
        found @ 7,6
        path = [[0, 0], [1, 0], [2, 0], [3, 0], [4, 0], [5, 0], [6, 0], [7, 0], [7, 1], [7, 2],
        [7, 3], [7, 4], [7, 5], [7, 6]]
        steps count = 14
     */
  }
}
