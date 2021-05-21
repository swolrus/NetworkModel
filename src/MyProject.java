import java.util.HashSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Queue;
import java.util.Set;
import java.util.LinkedList;
import java.util.List;

// Full Name (StudentNum)

public class MyProject implements Project {

	// how the graph is stored.
	// each item in the list is another list.
	// each stored list stores the destination of the edges that leave the vertex
	// represented by the address in adjacencyList
	public ArrayList<ArrayList<Integer>> adjacencyL;
	// a transpose of the adjacency list
	public ArrayList<ArrayList<Integer>> transposeAJ;
	// array list to store the URL of each vertex
	public ArrayList<ArrayList<Short>> address;
	// an index that stores the latest vertex added to the list
	public Integer vert;
	// a hash set to store if a vertex has been seen by the traversal
	Set<Integer> seen;

	Queue<Node> unsettled;

	private class Node {
		private int id;
		private int data;
		private List<Query> queries;

		public Node(int id, int data) {
			queries = new LinkedList<Query>();
			this.id = id;
			this.data = data;
		}
	}
	
	private class Query {
		private List<Integer> data;
		
		private Query(int id) {
			data = new LinkedList<Integer>();
			data.add(id);
		}
		
	}
	/**
	 * Zero argument constructor used to create an instance of MyProject for marking. 
	 */

	public MyProject() {}

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
	 *   - O(N): +1 mark (ACHIEVED)
	 * - Quality: +2 marks
	 * 
	 * @param adjlist The adjacency list describing the links between devices
	 * @return Whether all the devices are connected or not
	 */

	public boolean allDevicesConnected(int[][] adjlist) {
		Set<int[]> sub = new HashSet<>();
		int[] test = { 8, 8, 8 };
		int[] add = {8,8};
		sub.add(add);
		System.out.println(sub.contains(test));

		// check to see if there are entries if not return false.
		if(adjlist.length == 0) {return false;}
		// create new boolean array .
		Boolean[] seen = new Boolean[adjlist.length];
		// mark all values of seen as false (true as defualt).
		for(int i = 0; i < adjlist.length; i++){seen[i] = false;}
		// begin DFS until complete 
		dfsADC(0, seen, adjlist);
		// return the overall parity for the Boolean array.
		return !Arrays.asList(seen).contains(false);
	}

	/**
	 * 
	 * DFS allDevicesConnected
	 * 
	 * From any random node take all possible routes to any other node if not visted through recursion. All available nodes are determined in the array 
	 * as the parent array, all childs of these parents are the other nodes that are connected to that parent. 
	 * 
	 * Complexity - 0(N)
	 * 
	 * @param v The vertex give in this instance of DFS recursion.
	 * @param seen The Boolean array to determine if a vertex has been visted or  not. 
	 * @param list The adjlist itself used to transverse the network.
	 * 
	 */

	private void dfsADC(int v, Boolean[] seen, int[][] list) {
		// mark the current node as visited
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

		if (src == dst) { return 1; }

		int paths = 0;

		unsettled = new LinkedList<Node>();

		// stores information on whether the vertex has been visited or not.
		seen = new HashSet<>();

		Node start = new Node(src, 0);
		seen.add(src);

		unsettled.add(start);

		while (!unsettled.isEmpty()) {

			Node current = unsettled.remove();

			for (int i : adjlist[current.id]) {
				if (!seen.contains(i)) {
					if (i == dst) { 
						paths++;
					} else {
						Node tmp = new Node(i, current.data + 1);
						seen.add(i);
						unsettled.add(tmp);
					}
				}
			}

		}
		return paths;
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
		// store the address of the source device for reference later in algorithm. 
		short[] srcAddrs = addrs[src];
		// used to store the return values for each query
		int[] shortestPaths = new int[queries.length];
		for (int i=1 ; i<shortestPaths.length ; i++) {
			shortestPaths[i] = Integer.MAX_VALUE;
		}

		// create a queue of vertices to be scanned as per BFS LinkedList.
		unsettled = new LinkedList<Node>();
		
		seen = new HashSet<>();

		Node start = new Node(src, 0);
		unsettled.add(start);
		seen.add(src);
		// stores the queries to be tested, modified by inSubnet()
		HashMap<String, Query> qmap = new HashMap<String, Query>();

		// add each query as a string
		for (int i=0 ; i<queries.length ; i++) {
			if (queries[i] == null) {
				shortestPaths[i] = 0;
				i++;
			}
			String key = Arrays.toString(queries[i]);
			/*if (qmap.containsKey(key)) {
				
			}*/
			Query q;
			if (qmap.containsKey(key)) {
				q = qmap.get(key);
				q.data.add(i);
			} else {
				q = new Query(i);
			}
			qmap.put(key, q);
		}
		
		while ((!unsettled.isEmpty()) && (0<qmap.size()))  {
			Node current = unsettled.remove();

			for (int y=0 ; y<adjlist[current.id].length ; y++) {
				int id = adjlist[current.id][y];
				
				if (!seen.contains(id)) {
					Node tmp = new Node(id, current.data + 1);
					seen.add(id);
					unsettled.add(tmp);
				}
				for (int i=1 ; i<4 ; i++) {
					short[] subquery = Arrays.copyOfRange(addrs[current.id], 0, i);
					String test = Arrays.toString(subquery);
					if (qmap.containsKey(test)) {
						List<Integer> dupes = qmap.get(test).data;
						for (int d : dupes) {
							shortestPaths[d] = current.data;
						}
						qmap.remove(test);
					}
				}
			}
			
		}


		return shortestPaths;
	}

	/**
	 * 
	 * In sub net
	 * 
	 * Tests IP given as address against all queries in a list
	 * Divides given IP into 3 possible segments to test against the queries
	 * Returns the count of matches
	 * 
	 * Complexity - O(N)
	 * 
	 * @param address	The address to be tested
	 * @param qlist		A list of queries to be tested
	 * 
	 * @return int the number of matched queries, or -1 if no matches
	 * 
	 */
	public int inSubnet(short[] address, Set<String> qlist) {
		int[] success = -1;
		
		return success;
	}

	/**
	 * 
	 * BFS closestInSubnet
	 * 
	 * Description of BFS 
	 * 
	 * Complexity - O(V+G)
	 * 
	 * @param start 
	 * @param vertices 
	 * @param dist 
	 * @param adjlist 
	 * 
	 */

	private void bfsCIS(int src, int vertices, int[] dist, int[][] adjlist){
		// create a queue of vertices to be scanned as per BFS LinkedList.
		unsettled = new LinkedList<Node>();
		// stores information on whether the vertex has been visited or not.
		seen = new HashSet<>();
		// now source is first to be visited 
		// distance from source is set to 0.
		Node start = new Node(src, 0);
		seen.add(src);

		unsettled.add(start);
		// BFS Algorithm itself.
		while(!unsettled.isEmpty()){
			Node current = unsettled.remove();
			for(int i : adjlist[current.id]) {
				if(!seen.contains(i)) {
					dist[i] = dist[current.id] + 1;
					Node tmp = new Node(i, current.data + 1);
					seen.add(i);
					unsettled.add(tmp);
				}
			}
		}
	}


	public int maxDownloadSpeed(int[][] adjlist, int[][] speeds, int src, int dst) {
		if (src == dst) { return -1; }

		List<Integer> pathweights = new ArrayList<Integer>();

		seen = new HashSet<>();

		unsettled = new LinkedList<Node>();

		Node start = new Node(src, 0);
		seen.add(src);

		unsettled.add(start);

		while (!unsettled.isEmpty()) {
			Node current = unsettled.remove();

			for (int y=0 ; y<adjlist[current.id].length ; y++) {
				int id=adjlist[current.id][y];

				if (!seen.contains(id)) {
					if (id == dst) {
						// add total download speed
						pathweights.add(current.data + speeds[current.id][y]);
					} else {
						Node tmp = new Node(id, current.data + speeds[current.id][y]);
						seen.add(id);
						unsettled.add(tmp);
					}
				}
			}

		}

		switch (pathweights.size()) {
		// no possible connections
		case 0:
			return 0;

			// one possible connection
		case 1:
			return (int) pathweights.remove(0);

			// multiple possible connections
		default:
			double calc = 0;
			for (int weight : pathweights) { 
				double tmp = weight;
				calc = calc + (1/tmp); 
			}
			calc = 1/calc;
			return (int) calc;
		}
	}
}
