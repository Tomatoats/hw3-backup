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
                boolean[][] connectionGraph = new boolean[totalComps+2][totalComps+2];
                HashMap<Integer, Boolean> ConnectionList = new HashMap<>();
                long ans = 0;
                long totalConnections = 0;
                for (int i = 1; i < connections+1; i++){
                    int connection1 = in.nextInt();
                    int connection2 = in.nextInt();
                    connectionGraph[connection1][connection2] = true;
                    //connectionGraph[connection2][connection1] = true;
                    ConnectionList.put(i,connectionGraph[connection1][connection2]);
                    mySet.union(connection1, connection2);
                    System.out.printf("%d and %d are put away!\n", connection1, connection2);
                }
                long[] connectionNumbers = new long[connections+2];
                for (int i = 0; i < connectionsDestroyed+1; i++){
                    for (int j = 1; j < connections+1 ;j++) {
                        totalConnections =  countConnections(connectionGraph, connections, j, 1, 0, j);
                        connectionNumbers[j] = totalConnections * totalConnections;
                        ans = ans + totalConnections;
                        System.out.printf("Total connections to computer %d: %d\n", j, totalConnections);
                    }
                    long compsMissing = totalComps-ans;
                    long trueAnswer = compsMissing;
                    for (int j = 1; j < connections +1; j++){
                        trueAnswer =  trueAnswer + connectionNumbers[j];
                    }
                    System.out.printf("Connection destroyed! %d\n",trueAnswer);
                    int connectionEdgeToDestroy = in.nextInt();
                    ConnectionList.replace( connectionEdgeToDestroy, false);

                }
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
    public static long countConnections(boolean[][] connectionGraph, int connections,int i, int j, long ans, int orig){

        //for (int p = i; p < connections +1; p++) {
        long[] connectionNumbers = new long[connections+2];
            for (int k = j; k < connections + 2; k++) {
                if (connectionGraph[i][k] == false) {
                    //System.out.printf("%d does not connect with %d\n",i, k);
                    continue;
                } else {
                    ans++;
                    System.out.printf("%d and %d connect! Does %d connect to anything else?\n",i, k, k);
                    connectionGraph[orig][k] = true;
                    connectionGraph[i][k] = false;
                    if (k == 9){}
                    else {
                        countConnections(connectionGraph, connections, k, 1, ans, orig);
                    }
                }
            }

            if (ans != 0){
                ans++;
                connectionNumbers[orig] = ans;
                ans = ans * ans;
            }
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
        if (parents[ root1].getHeight() > parents[ root2].getHeight()) {
            parents[ root2].setID( root1);
        }

        // Attach tree 1 to tree 2
        else if (parents[root2].getHeight() > parents[root1].getHeight() ) {
            parents[root1].setID(root2);
        }

        // Same height case - just attach tree 2 to tree 1, adjust height.
        else {
            parents[root2].setID(root1);
            parents[root1].incHeight();
        }

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
