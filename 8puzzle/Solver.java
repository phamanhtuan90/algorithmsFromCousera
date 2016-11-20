/**
 * Created by tuanpa on 11/19/16.
 */
package puzzle;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.MinPQ;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class Solver {
    private List<Board> road;
    private HashSet<String> history;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        MinPQ<NodeBoard> queue = new MinPQ<NodeBoard>();
        NodeBoard lastest = null;
        history = new HashSet<String>();

        queue.insert(new NodeBoard(null, initial));
        queue.insert(new NodeBoard(null, initial.twin()));
        while (!queue.isEmpty()) {
            NodeBoard minNode = queue.delMin();
            if (minNode.getBoard().isGoal()) {
                lastest = minNode;
                break;
            }
            history.add(minNode.board.toString());
            if (minNode.equals(lastest))
                continue;
            lastest = minNode;
            for (Board b : minNode.board.neighbors()) {
                if (!history.contains(b.toString()))
                    queue.insert(new NodeBoard(minNode, b));
            }
        }


        LinkedList<Board> listResult = new LinkedList<Board>();
        while (lastest != null) {
            listResult.addFirst(lastest.getBoard());
            lastest = lastest.getParent();
        }
        Board first = listResult.getFirst();
        if (first.equals(initial)){
            road = listResult;
        }
    }

    // is the initial board solvable?
    public boolean isSolvable() {
        return road != null;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (isSolvable()) {
            return road.size() - 1;
        }
        return -1;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        return road;
    }

    private class NodeBoard implements Comparable<Solver.NodeBoard> {
        public NodeBoard parent;
        public Board board;
        private int depth;

        public NodeBoard(NodeBoard parent, Board node) {
            this.board = node;
            this.parent = parent;
            if (parent == null) {
                this.depth = 0;
            } else {
                this.depth = parent.getDepth() + 1;
            }
        }

        public Board getBoard() {
            return this.board;
        }

        public int getDepth() {
            return this.depth;
        }

        public NodeBoard getParent() {
            return this.parent;
        }

        public int compareTo(NodeBoard that) {
            return (depth + board.manhattan()) - (that.getDepth() + that.getBoard().manhattan());
        }
    }

    // solve a slider puzzle (given below)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);
        //StdOut.println(solver.isSolvable());
        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
