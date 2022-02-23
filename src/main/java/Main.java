import java.util.Scanner;
import java.nio.file.Paths;
import java.util.NoSuchElementException;
import java.io.IOException;
import java.util.*;
/* COP 3503C Assignment 3
This program is written by: Alexys Veloz */
public class Main {


    public static void main(String[] args) {
        try (Scanner in = new Scanner(Paths.get("in.txt"))) {
            while (in.hasNext()) {
                int totalComps = in.nextInt();
                int connections = in.nextInt();
                int connectionsDestroyed = in.nextInt();
                Main mySet = new Main(totalComps+1);
                long[] arrayAnswers = new long[connectionsDestroyed+2];
                boolean[][] staticConnectionGraph = new boolean[totalComps+2][totalComps+2];
                boolean[][] dynamicConnectionGraph = new boolean[totalComps+2][totalComps+2];
                int[] connectionsLineDestroyed = new int[connectionsDestroyed+1];
                int[] lines1 =  new int[totalComps+2];
                int[] lines2 = new int[totalComps+2];
                //HashMap<Integer, int[][]> staticConnectionList = new HashMap<>();
                //HashMap<Integer, Boolean[][]> dynamicConnectionList = new HashMap<>();
                long ans = 0;
                long totalConnections = 0;
                int connection1= 0;
                int connection2= 0;
                for (int i = 1; i < connections+1; i++){
                    connection1 = in.nextInt();
                    connection2 = in.nextInt();
                    lines1[i] = connection1;
                    lines2[i] = connection2;
                    staticConnectionGraph[connection1][connection2] = true;
                    dynamicConnectionGraph[connection1][connection2] = true;

                    //staticConnectionGraph[connection2][connection1] = true;

                    //staticConnectionList.put(i,staticConnectionGraph[connection1][connection2]);
                    //dynamicConnectionList.put(i,dynamicConnectionGraph[connection1][connection2]);
                    //mySet.union(connection1, connection2);
                    System.out.printf("%d and %d are put away! this is #%d in map\n", connection1, connection2, i);
                }
                for (int i = 1; i < connectionsDestroyed +1; i++){
                    int ploy = in.nextInt();
                    connectionsLineDestroyed[i] = ploy;
                    //System.out.printf("connection %d = ");
                    dynamicConnectionGraph[lines1[ploy]][lines2[ploy]] = false;
                    //dynamicConnectionList.replace(connectionsLineDestroyed[i], false);
                    //System.out.printf("connection %d is terminated. Proof? %b\n",ploy,dynamicConnectionList.get(staticConnectionList.get(ploy)));
                }
                System.out.printf("the real test. dynamicgraph[2][6] = %b, dynamicgraph[6][7] = %b\n", dynamicConnectionGraph[2][6],dynamicConnectionGraph[6][7]);
                unionization(mySet,connections,dynamicConnectionGraph);
                //adding them back
                for (int i = 1; i < connectionsDestroyed +1; i++){
                    int temp = connectionsLineDestroyed[i];
                    dynamicConnectionGraph[lines1[temp]][lines2[temp]] = true;
                    mySet.union(lines1[temp],lines2[temp]);
                    if (mySet.union(lines1[temp],lines2[temp]) == false){
                        System.out.printf("Hey it worked! connnection[6][7] = %b, connection[2][6] = %b\n", dynamicConnectionGraph[6][7], dynamicConnectionGraph[2][6]);
                    }
                }
                //counting the numbers up:


                /*
                long[] connectionNumbers = new long[connections+2];

                for (int i = 0; i < connectionsDestroyed+1; i++){
                    boolean[][] cloneList =  staticConnectionGraph.clone();
                    for (int j = 1; j < connections+1 ;j++) {
                        totalConnections =  countConnections(staticConnectionGraph, connections, j, 1, 0, j);
                        connectionNumbers[j] = totalConnections * totalConnections;
                        ans = ans + totalConnections;
                        //System.out.printf("Total connections to computer %d: %d\n", j, totalConnections);
                    }
                    long compsMissing = totalComps-ans;
                    long trueAnswer = compsMissing;
                    for (int j = 1; j < connections +1; j++){
                        trueAnswer =  trueAnswer + connectionNumbers[j];
                    }
                    System.out.printf("%d\n\n",trueAnswer);
                    int connectionEdgeToDestroy = in.nextInt();
                    staticConnectionList.remove(connectionEdgeToDestroy);
                    for (int j = 1; j < connections+1; j++){
                        staticConnectionList.replace(j, true);
                        staticConnectionGraph = staticConnectionGraph;
                        //System.out.printf("%b\nJust for testing, staticConnectionGraph[2][9] for section 8 = %b\n", staticConnectionList.get(j), staticConnectionGraph[2][9]);
                    }
                    for (int a = 1; a < connections+2; a++){
                        for(int b = 1; b < connections +2; b++){
                            if (staticConnectionGraph[a][b] == true){
                                //System.out.printf("[%d], [%d] = true\n",a,b);
                            }

                        }
                    }
                }*/
            }
        } catch (IOException | NoSuchElementException | IllegalStateException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }
    private  pair[] parents;

    public Main(int n) {

        // All nodes start as leaf nodes.
        parents = new pair[n];
        for (int i=0; i<n; i++)
            parents[i] = new pair(i, 0); //0 is height 0. parent[i]'s parent is i now
    }
    public static void unionization(Main mySet,  int connections, boolean[][] connectionGraph) {
        //connect shit together
        for (int i = 1; i < connections+2 ; i++)
        {
            for (int j = 1; j < connections+2; j++)
            {
                //System.out.printf("i is %d, j is %d, connectionGraph[i][j] = %b\n", i, j, connectionGraph[i][j]);
                if (connectionGraph[i][j])
                {
                    System.out.printf("%d and %d are connected\n", i, j);
                    mySet.union(i, j);

                }

            }
        }
    }
    public static long countConnections(boolean[][] cloneList, int connections,int i, int j, long ans, int orig){
        long[] connectionNumbers = new long[connections+2];
        /*//for (int p = i; p < connections +1; p++) {

        for (int k = j; k < connections + 2; k++) {
            //System.out.printf("in the function: graph[%d][%d] =  %b\n", i,k, cloneList[i][k]);
            if (cloneList[i][k] == false) {
                    //System.out.printf("%d does not connect with %d\n",i, k);
                    continue;
                } else {
                    ans++;
                    //System.out.printf("%d and %d connect! Does %d connect to anything else?\n",i, k, k);
                    cloneList[i][k] = false;
                    cloneList[orig][k] = true;
                    //connectionGraph[orig][k]=true;
                if (k == 9){}
                    else {
                        countConnections(cloneList, connections, k, 1, ans, orig);
                    }
                }
            }

            if (ans != 0){
                ans++;
                connectionNumbers[orig] = ans;
                ans = ans * ans;
            }*/
        return connectionNumbers[orig];
    }

    // Returns the root node of the tree storing id.
    public int find(int id) {

        // Go up tree until there's no parent.
        while (id != parents[id].getID())
            id = (int) parents[id].getID();

        return id;
    }

    public boolean union(int id1, int id2) {

        // Find the parents of both nodes.
        int root1 =  find(id1);
        int root2 =  find(id2);

        // No union needed.
        if (root1 == root2)
            return false;

        // Attach tree 2 to tree 1
        if (root1 < root2) {
            parents[ root2].setID( root1);
        }

        // Attach tree 1 to tree 2
        else if (root2 < root1) {
            parents[root1].setID(root2);
        }

        // Same height case - just attach tree 2 to tree 1, adjust height.


        // We successfully did a union.
        return true;
    }

    // Just represents this object as a list of each node's parent.
    public String toString() {

        String ans = "";
        for (int i=0; i<parents.length; i++)
            ans = ans + "(" + i + ", " + parents[i].getID() + ") ";
        return ans;
    }
}

class pair {

    private int ID;
    private int height;

    public pair(int myNum, int myHeight) {
        ID = myNum;
        height = myHeight;
    }

    public int getHeight() {
        return height;
    }

    public int getID() {
        return ID;
    }

    public void incHeight() {
        height++;
    }

    public void setID(int newID) {
        ID = newID;
    }
}
