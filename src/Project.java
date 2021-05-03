/**
 * @author Andrew "Gozz" Gozzard <andrew.gozzard@uwa.edu.au>
 * @author Tom Hill Almeida <00087533@uwa.edu.au>
 *
 * DO NOT MODIFY this file in the course of completing your project.
 * 
 * The interface to implement for the 2021 CITS2200 Data Structures and Algorithms Project.
 * Your implementation MUST provide a zero-argument constructor, which will be the only one used when assessing your submission.
 * 
 * All questions work with a theoretical model of a computer network described in the project specification.
 * 
 * Time complexity specification in this document use D for the number of devices in the network, L for the number of links, and N for the size of the network (D + L).
 */
public interface Project {
    /**
     * Determine if all of the devices in the network are connected to the network.
     * Devices are considered to be connected to the network if they can transmit (including via other devices) to every other device in the network.
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
    public boolean allDevicesConnected(int[][] adjlist);

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
    public int numPaths(int[][] adjlist, int src, int dst);

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
    public int[] closestInSubnet(int[][] adjlist, short[][] addrs, int src, short[][] queries);

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
    public int maxDownloadSpeed(int[][] adjlist, int[][] speeds, int src, int dst);
}
