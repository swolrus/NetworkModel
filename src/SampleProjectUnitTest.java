import java.util.Arrays;

/**
 * @author Andrew "Gozz" Gozzard <andrew.gozzard@uwa.edu.au>
 * @author Tom Hill Almeida <00087533@uwa.edu.au>
 *
 * A sample unit tester for the 2021 CITS2200 Project.
 *
 * DO NOT modify this file in the course of completing your project.
 * You are, however, encouraged to copy it and use it to construct your own tests.
 */
public class SampleProjectUnitTest {
    /**
     * Total number of tests run
     */
    private static int numTest = 0;

    /**
     * Total number of tests passed
     */
    private static int numPass = 0;
    
    private static String userdef=" (usrdef)";

    /**
     * Helper function to print out a test result nicely and update counters
     * @param name The name of the test to display as output
     * @param pass Whether or note the test passed
     */
    private static void test(String name, boolean pass) {
        numTest++;
        if (pass) {
            numPass++;
            System.out.print("Pass:");
        } else {
            System.out.print("FAIL:");
        }
        System.out.print("\t");
        System.out.println(name);
    }

    private static void testInt(String name, int actual, int expected) {
        boolean pass = actual == expected;
        test(name, pass);
        if (!pass) {
            System.out.println("\tExpected " + expected + " got " + actual);
        }
    }

    private static void testInts(String name, int[] actual, int[] expected) {
        boolean pass = Arrays.equals(actual, expected);
        test(name, pass);
        if (!pass) {
            System.out.println("\tExpected " + Arrays.toString(expected) + " got " + Arrays.toString(actual));
        }
    }

    private static void testBoolean(String name, boolean actual, boolean expected) {
        boolean pass = actual == expected;
        test(name, pass);
        if (!pass) {
            System.out.println("\tExpected " + expected + " got " + actual);
        }
    }

    public static void allDevicesConnectedTest() {
        Project proj = new MyProject();

        String pref = "allDevicesConnected: ";

        int[][] adjlist;

        adjlist = new int[][] {
            { 1 },
            { 0 },
        };

        testBoolean(pref + "trivial connected",
            proj.allDevicesConnected(adjlist), true);

        adjlist = new int[][] {
            { },
            { },
        };

        testBoolean(pref + "trivial disconnected",
            proj.allDevicesConnected(adjlist), false);

        adjlist = new int[][] {
            { 1 },
            { 2 },
            { 0 },
        };

        testBoolean(pref + "circular",
            proj.allDevicesConnected(adjlist), true);

        adjlist = new int[][] {
            { 1 },
            { 1 },
            { 0 },
        };

        testBoolean(pref + "one unreachable",
            proj.allDevicesConnected(adjlist), false);

        adjlist = new int[][] {
            { 1 },
            { 0, 2, 3 },
            { 1 },
            { 1 },
        };

        testBoolean(pref + "central node",
            proj.allDevicesConnected(adjlist), true);
    }

    public static void numPathsTest() {
        Project proj = new MyProject();

        String pref = "numPaths: ";

        int[][] adjlist = new int[][] {
            { 1 },
            { 0 },
        };

        testInt(pref + "trivial connected", 
            proj.numPaths(adjlist, 0, 1), 1);

        adjlist = new int[][] {
            { 1, 2 },
            { 0, 3 },
            { 0, 3 },
            { 1, 2 },
        };
        testInt(pref + "simple two-path connected",
            proj.numPaths(adjlist, 0, 3), 2);

        adjlist = new int[][] {
            { 1, 2 },
            { 0, 2 },
            { },
            { 1, 2 },
        };

        testInt(pref + "disconnected",
            proj.numPaths(adjlist, 0, 3), 0);

        adjlist = new int[][] {
            { 1, 2 },
            { 0, 3 },
            { 0, 3 },
            { 1, 2 },
        };

        testInt(pref + "same src/dst",
            proj.numPaths(adjlist, 0, 0), 1);

        adjlist = new int[][] {
            { 0 },
            { 0, 3 },
            { 0, 3 },
            { 1, 2 },
        };

        testInt(pref + "no path",
            proj.numPaths(adjlist, 0, 3), 0);
        
        adjlist = new int[][] {
            { 1, 2, 3 },
            { 0, 4 },
            { 0, 4 },
            { 0, 4 },
            { 3, 4 },
        };
        testInt(pref + "3 paths and outlier" + userdef,
            proj.numPaths(adjlist, 0, 4), 3);
    }

    public static void closestInSubnetTest() {
        Project proj = new MyProject();

        String pref = "closestInSubnet: ";

        int[][] adjlist = new int[][] {
            { 1 },
            { 0 },
        };

        short[][] addrs = new short[][] {
            { 10, 1, 1, 1 },
            { 10, 1, 2, 1 },
        };

        short[][] queries = new short[][] {
            { 10, 1, 1 },
            { 10, 1, 2 },
            { 10, 1 },
            { 10, 2 },
        };

        int[] expected = new int[] { 0, 1, 0, Integer.MAX_VALUE };

        testInts(pref + "trivial connected",
            proj.closestInSubnet(adjlist, addrs, 0, queries), expected);

        adjlist = new int[][] {
            { 1 },
            { 2 },
            { 3, 4 },
            { 5, 6, 7},
            { 5, 6, 7},
            { 8 },
            { 8 },
            { 8 },
            { },
        };

        addrs = new short[][] {
            { 10, 1, 1, 1 },
            { 192, 168, 1, 1 },
            { 130, 1, 1, 1 },
            { 130, 95, 1, 1 },
            { 130, 95, 1, 2 },
            { 130, 95, 2, 1 },
            { 130, 95, 2, 2 },
            { 130, 95, 2, 3 },
            { 130, 95, 3, 1 },
        };

        queries = new short[][] {
            { },
            { 10 },
            { 192 },
            { 192, 168 },
            { 130 },
            { 130, 1 },
            { 130, 95 },
            { 130, 95, 1 },
            { 130, 95, 2 },
            { 130, 95, 3 },
            { 130, 95, 2, 2 },
            { 130, 95, 3, 1 },
            { 8, 8, 8, 8 },
        };

        expected = new int[] { 0, 0, 1, 1, 2, 2, 3, 3, 4, 5, 4, 5, Integer.MAX_VALUE };

        testInts(pref + "simple dag",
            proj.closestInSubnet(adjlist, addrs, 0, queries), expected);

        adjlist = new int[][] {
            { 1 },
            { 2 },
            { 0 },
        };

        addrs = new short[][] {
            { 10, 1, 1, 1 },
            { 10, 1, 2, 2 },
            { 10, 1, 2, 3 },
        };

        queries = new short[][] {
            { },
            { 10 },
            { 10, 1 },
            { 10, 1, 1 },
            { 10, 1, 2 },
            { 10, 1, 2, 3 },
        };

        expected = new int[] { 0, 0, 0, 0, 1, 2 };

        testInts(pref + "simple cycle",
            proj.closestInSubnet(adjlist, addrs, 0, queries), expected);
    }

    public static void maxDownloadSpeedTest() {
        Project proj = new MyProject();

        String pref = "maxDownloadSpeed: ";

        int[][] adjlist = new int[][] {
            { 1 },
            { 0 },
        };

        int[][] speeds = new int[][] {
            { 3 },
            { 3 },
        };

        testInt(pref + "trivial connected",
            proj.maxDownloadSpeed(adjlist, speeds, 0, 1), 3);

        //   /->1--->3
        //  /   ^-\ / \
        // 0       X   ->5
        //  \   v-/ \ /
        //   \->2--->4
        adjlist = new int[][] {
            { 1, 2 },
            { 3 },
            { 4 },
            { 4, 5 },
            { 3, 5 },
            {}
        };
        speeds = new int[][] { { 15, 4 }, { 12 }, { 10 }, { 3, 7 }, { 5, 10 }, {} };

        testInt(pref + "non-trivial connected", proj.maxDownloadSpeed(adjlist, speeds, 0, 5), 14);

        //   /->1--->3
        //  /   ^-\ /
        // 0       X   5
        //  \   v-/ \
        //   \->2--->4
        adjlist = new int[][] {
            { 1, 2 },
            { 3 },
            { 4 },
            { 4 },
            { 3 },
            {}
        };
        speeds = new int[][] { { 15, 4 }, { 12 }, { 10 }, { 3 }, { 5 }, {} };

        testInt(pref + "disconnected target", proj.maxDownloadSpeed(adjlist, speeds, 0, 5), 0);

        adjlist = new int[][] {
            { 0, 1 },
            { 0, 1 },
        };
        speeds = new int[][] { { 5, 10 }, { 5, 10 } };

        testInt(pref + "circles 1", proj.maxDownloadSpeed(adjlist, speeds, 0, 1), 10);

        adjlist = new int[][] {
            { 0, 1 },
            { 0, 1 },
        };
        speeds = new int[][] { { 5, 10 }, { 5, 10 } };

        testInt(pref + "speed to self", proj.maxDownloadSpeed(adjlist, speeds, 0, 0), -1);
    }

    public static void printSummary() {
        System.out.println();
        System.out.println(
            "Passed " +
            Integer.toString(numPass) +
            "/" +
            Integer.toString(numTest) +
            " tests");
        if (numPass == numTest) {
            System.out.println("All tests passed");
        } else {
            System.out.println(Integer.toString(numTest-numPass) + " TESTS FAILED");
        }
    }

    public static void main(String[] args) {
        allDevicesConnectedTest();
        numPathsTest();
        closestInSubnetTest();
        maxDownloadSpeedTest();

        printSummary();
    }
}
