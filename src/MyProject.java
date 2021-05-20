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
    
    /**
     * Zero argument constructor used to create an instance of MyProject for marking. 
     */
    
	public MyProject() {
        vert = 0;
        adjacencyL = new ArrayList<>();
	}
    /**
     * Determine if all of the devices in the network are connected to the network.
     * Devices are considered to be connected to the network if they can transmit (including via other devices) to every other device in the network.
     *
     * Create a boolean arrray of "seen" and DFS once from any node in the adjlist until completion. Once complete check the overall parity of the seen array and if true 
     * all nodes where visted and must be connected. 
     *
     * Marks (6 total):
     * - Correctness: +3 marks
     * - Complexity:
     *   - O(N): +1 mark
     * - Quality: +2 marks
     * 
     * @param adjlist The adjacency list describing the links between devices
     * @return Whether all the devices are connected or not
     */
	
    public boolean allDevicesConnected(int[][] adjlist) {
        // create new boolean array 
    	seen = new Boolean[adjlist.length];
    	// mark all values of seen as false (true as defualt)
        for(int i = 0; i < adjlist.length; i++){seen[i] = false;}
    	// begin DFS until complete 
        dfsADC(0, seen, adjlist);
        // return the overall parity for the Boolean array
        return !Arrays.asList(seen).contains(false);
    }
    
    /**
     * 
     * DFS allDevicesConnected
     * 
     * From any random node take all possible routes to any other node if not visted through recursion. All available nodes are determined in the array 
     * as the parent array, all childs of these parents are the other nodes that are connected to that parent. 
     * 
     * @param v The vertex give in this instance of DFS recursion.
     * @param seen The Boolean array to determine if a vertex has been visted or  not. 
     * @param list The adjlist itself used to transverse the network.
     * 
     */
    
    private void dfsADC(int v, Boolean[] seen, int[][] list) {
        // Mark the current node as visited
        seen[v] = true;
        for(int y = 0; y < list[v].length; y++){   
            int n = list[v][y];
            if (!seen[n]){
                dfsADC(n, seen, list);}
        }
    }

    /**
     * Determine the number of different paths a packet can take in the network to get from a transmitting device to a receiving device.
     * A device will only transmit a packet to a device that is closer to the destination, where the distance to the destination is the minimum number of hops between a device and the destination.
     *
     * Marks (10 total):
     * - Correctness: +4 marks
     * - Complexity:
     *   - O(N^2): +1 mark
     *   - O(N): +2 marks
     * - Quality: +3 marks
     * 
     * @param adjlist The adjacency list describing the links between devices
     * @param src The source (transmitting) device
     * @param dst The destination (receiving) device
     * @return The total number of possible paths between the two devices
     */
    
    public int numPaths(int[][] adjlist, int src, int dst) {
        // TODO
        return 0;
    }

    /**
     * Compute the minimum number of hops required to reach a device in each subnet query.
     * Each device has an associated IP address.
     * An IP address is here represented as an array of exactly four integers between 0 and 255 inclusive (for example, {192, 168, 1, 1}).
     * Each query specifies a subnet address.
     * A subnet address is specified as an array of up to four integers between 0 and 255.
     * An IP address is considered to be in a subnet if the subnet address is a prefix of the IP address (for example, {192, 168, 1, 1} is in subnet {192, 168} but not in {192, 168, 2}).
     * For each query, compute the minimum number of hops required to reach some device in the specified subnet.
     * If no device in that subnet is reachable, return Integer.MAX_VALUE.
     *
     * Marks (10 total):
     * - Correctness: +4 marks
     * - Complexity: (where Q is the number of queries)
     *   - O((N + Q) lg N): +2 marks
     *   - O(N + Q): +1 mark
     * - Quality: +3 marks
     * 
     * @param adjlist The adjacency list describing the links between devices
     * @param addrs The list of IP addresses for each device
     * @param src The source device
     * @param queries The queries to respond to for each subnet
     * @return An array with the distance to the closest device for each query, or Integer.MAX_VALUE if none are reachable
     */
    
    public int[] closestInSubnet(int[][] adjlist, short[][] addrs, int src, short[][] queries) {
        // TODO
        return null;
    }

    public int maxDownloadSpeed(int[][] adjlist, int[][] speeds, int src, int dst) {
        // TODO
        return 0;
    }
}
