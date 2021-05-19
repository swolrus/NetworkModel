import java.util.HashMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Queue;
import java.util.LinkedList;
import java.util.BitSet;
import java.util.Stack;
import java.util.Iterator;

// Full Name (StudentNum)

public class MyProject implements Project {

    // how the graph is stored.
    // each item in the list is another list.
    // each stored list stores the destination of the edges that leave the vertex
    // represented by the address in adjacencyList
    public ArrayList<ArrayList<Integer>> adjacencyL;
    // a transpose of the adjcaency list
    public ArrayList<ArrayList<Integer>> transposeAJ;
    // arraylist to store the URL of each vertex
    public ArrayList<ArrayList<Short>> address;
    // an index that stores the lates vertex added to the list
    public Integer vert;
    // an array to store is a vertice has been visted by traversal
    public Boolean seen[];
    

	
	public MyProject() {
        vert = 0;
	}
	
    public boolean allDevicesConnected(int[][] adjlist) {
        seen = new Boolean[adjlist.length];
        dfsADC(vert, seen, adjlist);
        return Arrays.asList(seen).contains(false);
    }
    
    private void dfsADC(int v, Boolean[] seen, int[][] list) {
        // Mark the current node as visited
        seen[v] = true;
        for(int y = 0; y < list[v].length; y++){   
            int n = list[v][y];
            if (!seen[n]){
                dfsADC(n, seen, list);}
        }
    }

    public int numPaths(int[][] adjlist, int src, int dst) {
        // TODO
        return 0;
    }

    public int[] closestInSubnet(int[][] adjlist, short[][] addrs, int src, short[][] queries) {
        // TODO
        return null;
    }

    public int maxDownloadSpeed(int[][] adjlist, int[][] speeds, int src, int dst) {
        // TODO
        return 0;
    }
}
