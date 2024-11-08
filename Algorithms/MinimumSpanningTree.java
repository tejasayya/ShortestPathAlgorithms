package Algorithms;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class MinimumSpanningTree {
    private int numVertices, numEdges;
    private int mstEdgeCount;
    private Edge[] edgeList, mstEdgeList;
    private String nodeLabels = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    class Subset {
        int parent, rank;
    }

    class Edge implements Comparable<Edge> {
        int src, dest, cost;

        public int compareTo(Edge otherEdge) {
            return this.cost - otherEdge.cost;
        }
    }

    public void executeMST(String filePath) {
        try {
            readGraph(filePath);
            long startTime = System.nanoTime();
            applyKruskalMST();
            long endTime = System.nanoTime();
            printMSTResult(startTime, endTime);
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
            e.printStackTrace();
        }
    }

    private void readGraph(String filePath) throws FileNotFoundException {
        File file = new File(filePath);
        Scanner scanner = new Scanner(file);
        numVertices = scanner.nextInt();
        numEdges = scanner.nextInt();
        System.out.println("Number of Vertices: " + numVertices);
        System.out.println("Number of Edges: " + numEdges);
        scanner.next().charAt(0);
        
        edgeList = new Edge[numEdges];
        for (int i = 0; i < numEdges; ++i) {
            edgeList[i] = new Edge();
        }
        
        for (int i = 0; i < numEdges; i++) {
            int source = nodeLabels.indexOf(scanner.next().charAt(0));
            int destination = nodeLabels.indexOf(scanner.next().charAt(0));
            int weight = scanner.nextInt();
            edgeList[i].src = source;
            edgeList[i].dest = destination;
            edgeList[i].cost = weight;
        }
        scanner.close();
    }

    void applyKruskalMST() {
        mstEdgeList = new Edge[numVertices];
        mstEdgeCount = 0;
        int i;
        
        for (i = 0; i < numVertices; ++i) {
            mstEdgeList[i] = new Edge();
        }
        
        Arrays.sort(edgeList);
        Subset[] subsets = new Subset[numVertices];
        
        for (i = 0; i < numVertices; ++i) {
            subsets[i] = new Subset();
        }
        
        for (int v = 0; v < numVertices; ++v) {
            subsets[v].parent = v;
            subsets[v].rank = 0;
        }
        
        i = 0;
        
        while (mstEdgeCount < numVertices - 1) {
            Edge nextEdge = edgeList[i++];
            int rootSrc = find(subsets, nextEdge.src);
            int rootDest = find(subsets, nextEdge.dest);
            
            if (rootSrc != rootDest) {
                mstEdgeList[mstEdgeCount++] = nextEdge;
                union(subsets, rootSrc, rootDest);
            }
        }
    }

    void union(Subset subsets[], int root1, int root2) {
        int root1Parent = find(subsets, root1);
        int root2Parent = find(subsets, root2);
        
        if (subsets[root1Parent].rank > subsets[root2Parent].rank) {
            subsets[root2Parent].parent = root1Parent;
        } else if (subsets[root1Parent].rank < subsets[root2Parent].rank) {
            subsets[root1Parent].parent = root2Parent;
        } else {
            subsets[root2Parent].parent = root1Parent;
            subsets[root1Parent].rank++;
        }
    }

    int find(Subset subsets[], int i) {
        if (subsets[i].parent != i) {
            subsets[i].parent = find(subsets, subsets[i].parent);
        }
        return subsets[i].parent;
    }

    public void printMSTResult(long startTime, long endTime) {
        System.out.println("Execution Time: " + (endTime - startTime) + " nanoseconds");
        int totalMSTCost = 0;
        System.out.println("\nMinimum Spanning Tree:\n");
        
        for (int i = 0; i < mstEdgeCount; ++i) {
            System.out.println(nodeLabels.charAt(mstEdgeList[i].src) + " --> " +
                    nodeLabels.charAt(mstEdgeList[i].dest) + " Cost: " + mstEdgeList[i].cost);
            totalMSTCost += mstEdgeList[i].cost;
        }
        
        System.out.println("\nTotal Cost of MST: " + totalMSTCost);
    }
}
