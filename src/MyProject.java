import java.util.HashSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Queue;
import java.util.Set;
import java.util.LinkedList;
import java.util.List;

/**
 * 
 * CITS2200 Project Interface. 
 * 
 * Designed to complete tasks as set out in our project outline given as part of the CITS2200 coursework Semester 1, 2021.
 * 
 * @author Kyron MIlton - 22363788 
 * @author David Norris - 22680264
 */

public class MyProject implements Project {
	// a hash set to store if a vertex id has been seen by the traversal.
	Set<Integer> seen;
	
	// queue of unsettled nodes for depth algorithms vertex ordering.
	Queue<Node> unsettled;

	// a private sub class to be created for each node and hold it's data.
	private class Node {
		// vertex reference in node.
		private int id;
		// data for weights in bfs.
		private int data;

		public Node(int id, int data) {
			// intialize node to input data.
			this.id = id;
			this.data = data;
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
		// mark the current node as visited.
		seen[v] = true;
		// for each vertex adjacent to v.
		for(int y = 0; y < list[v].length; y++){  
			// create local copy of the vertex to examine next.
			int n = list[v][y];
			// check to see whether n vertex has already been visited.
			if (!seen[n]){
				// recursively call DFS until all vertices are seen. 
				dfsADC(n, seen, list);}
		}
	}

	/**
	 * 
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
	 * 
	 */

	public int numPaths(int[][] adjlist, int src, int dst) {
		// check to see if the source is the same as the destination.
		if (src == dst) { return 1; }
		// create new queue of nodes represented as a linked list.
		unsettled = new LinkedList<Node>();
		// stores information on whether the vertex has been visited or not.
		seen = new HashSet<>();
		// create a new node to start bfs with id = vertex.
		Node start = new Node(src, 0);
		// add vertex to seen so it isnt used again.
		seen.add(src);
		// create a local variable to store the amounts of paths seen.
		int paths = 0;
		// push the start node onto queue to begin BFS.
		unsettled.add(start);
		// while queue is not empty.
		while (!unsettled.isEmpty()) {
			// create local variable of node removed from queue. 
			Node current = unsettled.remove();
			// for all i that are adjacent to id of current node.
			for (int i : adjlist[current.id]) {
				// check to see if vertex has been seen.
				if (!seen.contains(i)) {
					// check if current vertex is equal to destination.
					if (i == dst) { 
						// increase count of paths.
						paths++;
					// vertex wasnt equal to destination.
					} else {
						// create a node of the next vertex to be traversed.
						Node tmp = new Node(i, current.data + 1);
						// add next node to seen.
						seen.add(i);
						// add next node to the queue to be processed.
						unsettled.add(tmp);
					}
				}
			}

		}
		// return the number of paths found.
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
		// used to store the return values for each query.
		int[] shortestPaths = new int[queries.length];
		// set all values in shortesPath array to Integer.MAX_VALUE so if node cant be reach it has the correct output. 
		for (int i=1 ; i<shortestPaths.length ; i++) {shortestPaths[i] = Integer.MAX_VALUE;}
		// create a queue of vertices to be scanned as per BFS LinkedList.
		unsettled = new LinkedList<Node>();
		// stores information on whether the vertex has been visited or not.
		seen = new HashSet<>();
		// create a new node to start bfs with id = vertex.
		Node start = new Node(src, 0);
		// push the start node onto queue to begin BFS.
		unsettled.add(start);
		// add vertex to seen so it isnt used again.
		seen.add(src);
		// stores the queries to be tested, modified by inSubnet().
		HashMap<String, List<Integer>> qmap = new HashMap<String, List<Integer>>();
		// add each query as a string.
		for (int i=0 ; i<queries.length ; i++) {
			// create a key of the ith part of the query and convert it to a string.
			String key = Arrays.toString(queries[i]);
			// check to see if key is already apart of map.
			List<Integer> q;
			// check to see if map contains key.
			if (qmap.containsKey(key)) {
				// get array at position key.
				q = qmap.get(key);
				// store new data in q. 
				q.add(i);
			} else {
				// create a new lniked list to be stored in map. 
				q = new LinkedList<Integer>();
				// store data i in q.
				q.add(i);
			}
			// create a new location in map.
			qmap.put(key, q);
		}
		// while unsettled queue is not empty and map size greater than 0.
		while ((!unsettled.isEmpty()) && (0<qmap.size()))  {
			// create local variable of node removed from queue.
			Node current = unsettled.remove();
			// for all integers in the range of adjlist[y] length.
			for (int y=0 ; y<adjlist[current.id].length ; y++) {
				// create a local copy of the vertex at y.
				int id = adjlist[current.id][y];
				// check to see if the vertex is already seen.
				if (!seen.contains(id)) {
					// create a node of the next vertex to be traversed.
					Node tmp = new Node(id, current.data + 1);
					// add next node to seen.
					seen.add(id);
					// add next node to the queue to be processed.
					unsettled.add(tmp);
				}
			}
			// used at every node to determine is it was part of the queries and add to shortestPath. 
			testSubnet(addrs, qmap, current, shortestPaths);
		}
		// returns an array of int that represents the shortest paths taken by each query.
		return shortestPaths;
	}

	/**
	 * 
	 * Test subnet
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
	
	public void testSubnet(short[][] addrs, HashMap<String, List<Integer>> qmap, Node current, int[] shortestPaths) {
		// for 1 to 4 (min to max possible length of query) do the following.
		for (int i=1 ; i<5 ; i++) {
			// create local copy of the vertices address with length i. 
			short[] subquery = Arrays.copyOfRange(addrs[current.id], 0, i);
			// convert subquery into a string.
			String test = Arrays.toString(subquery);
			// check if the map contains the string value of subquery.
			if (qmap.containsKey(test)) {
				// assign a local copy of the linked list in the map.
				List<Integer> dupes = qmap.get(test);
				// for each int in dupes.
				for (int d : dupes) {
					// assign the shortestPath at dupes to data of the current node. 
					shortestPaths[d] = current.data;
				}
				// remove test key from map.
				qmap.remove(test);
			}
		}
	}

    /**
     * Compute the maximum possible download speed from a transmitting device to a receiving device.
     * The download may travel through more than one path simultaneously, and you can assume that there is no other traffic in the network.
     * If the transmitting and receiving devices are the same, then you should return -1.
     *
     * Each link in the network has a related speed at the same index in the speeds array (e.g. the link described at adjlist[0][1] has its related speed at speeds[0][1]).
     * Speeds may be asymmetric (that is - the speed in one direction of a link may be different to the speed in the other direction of a link).
     *
     * Marks (14 total):
     * - Correctness: +5 marks
     * - Complexity:
     *   - O(D L^2): +3 marks
     *   - O(D^2 L): +1 mark
     *   - O(D^3): +1 mark
     * - Quality: +4 marks
     * 
     * @param adjlist The adjacency list describing the connections between devices
     * @param speeds The list of query row segments
     * @param src The source (transmitting) device
     * @param dst The destination (receiving) device
     * @return The maximum download speed possible from the source to the destination, or -1 if they are the same
     */
	
	public int maxDownloadSpeed(int[][] adjlist, int[][] speeds, int src, int dst) {
		// check to see if the source is the same as the destination.
		if (src == dst) { return -1; }
		// creates a list to store the sum of speeds seen in traversal per path.
		List<Integer> pathweights = new ArrayList<Integer>();
		// stores information on whether the vertex has been visited or not.
		seen = new HashSet<>();
		// create new queue of nodes represented as a linked list.
		unsettled = new LinkedList<Node>();
		// create a new node to start bfs with id = vertex.
		Node start = new Node(src, 0);
		// add vertex to seen so it isnt used again.
		seen.add(src);
		// push the start node onto queue to begin BFS.
		unsettled.add(start);
		// while queue is not empty.
		while (!unsettled.isEmpty()) {
			// create local variable of node removed from queue.
			Node current = unsettled.remove();
			// for all integers in the range of adjlist[y] length.
			for (int y=0 ; y<adjlist[current.id].length ; y++) {
				// create a local copy of the vertex at y.
				int id=adjlist[current.id][y];
				// check to see if the vertex is already seen. 
				if (!seen.contains(id)) {
					// check if current vertex is equal to destination.
					if (id == dst) {
						// add total download speed.
						pathweights.add(current.data + speeds[current.id][y]);
					} else {
						// create a node of the next vertex to be traversed.
						Node tmp = new Node(id, current.data + speeds[current.id][y]);
						// add next node to seen.
						seen.add(id);
						// add next node to the queue to be processed.
						unsettled.add(tmp);
					}
				}
			}

		}
		// use a switch statement to determine the length of pathweights and the required outcome.
		switch (pathweights.size()) {
		// no possible connections.
		case 0:
			return 0;

		// one possible connection.
		case 1:
			return (int) pathweights.remove(0);

		// multiple possible connections.
		default:
			// create a local variable to stores sum of values calculated from path weights.
			double calc = 0;
			// for all weights in pathweights.
			for (int weight : pathweights) { 
				// create local copy of the current weight in pathweights.
				double tmp = weight;
				// preform resistance calculation on weight and sum to calc.
				calc = calc + (1/tmp); 
			}
			// preform last iteration of resistance calculation.
			calc = 1/calc;
			// return calc as an integer from double. 
			return (int) calc;
		}
	}
}
