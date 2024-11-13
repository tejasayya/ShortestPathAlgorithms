package Algorithms;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class ShortestPathAlgorithm {
    private int startNode;
    private int numVertices;
    private int numEdges;
    private Map<Integer, List<int[]>> adjacencyMap;
    private int[] minDistances;
    private int[] previousNode;  // Tracks the previous node for path reconstruction
    private boolean[] visitedNodes;
    private String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public void runAlgorithm(String filePath) {
        try {
            loadGraph(filePath);
            long startTime = System.nanoTime();
            findShortestPaths();
            long endTime = System.nanoTime();
            displayResults(startTime, endTime);
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
            e.printStackTrace();
        }
    }

    private void loadGraph(String filePath) throws FileNotFoundException {
        File file = new File(filePath);
        Scanner scanner = new Scanner(file);
        numVertices = scanner.nextInt();
        numEdges = scanner.nextInt();
        char graphType = scanner.next().charAt(0);

        adjacencyMap = new HashMap<>();
        minDistances = new int[numVertices];
        previousNode = new int[numVertices];
        visitedNodes = new boolean[numVertices];

        for (int i = 0; i < numEdges; i++) {
            String fromNode = scanner.next();
            String toNode = scanner.next();
            int weight = scanner.nextInt();

            int fromIndex = alphabet.indexOf(fromNode.charAt(0));
            int toIndex = alphabet.indexOf(toNode.charAt(0));
            adjacencyMap.computeIfAbsent(fromIndex, k -> new ArrayList<>())
                    .add(new int[]{toIndex, weight});
            if (graphType == 'U') {
                adjacencyMap.computeIfAbsent(toIndex, k -> new ArrayList<>())
                        .add(new int[]{fromIndex, weight});
            }
        }

        String startNodeStr = scanner.next();
        startNode = alphabet.indexOf(startNodeStr.charAt(0));

        Arrays.fill(minDistances, Integer.MAX_VALUE);
        Arrays.fill(previousNode, -1);
        scanner.close();
    }

    private void findShortestPaths() {
        PriorityQueue<int[]> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));
        minDistances[startNode] = 0;
        priorityQueue.offer(new int[]{startNode, 0});

        while (!priorityQueue.isEmpty()) {
            int[] current = priorityQueue.poll();
            int currentNode = current[0];
            int currentDist = current[1];
            if (visitedNodes[currentNode]) continue;

            visitedNodes[currentNode] = true;

            for (int[] neighbor : adjacencyMap.getOrDefault(currentNode, Collections.emptyList())) {
                int neighborNode = neighbor[0];
                int edgeWeight = neighbor[1];
                int newDist = currentDist + edgeWeight;

                if (newDist < minDistances[neighborNode]) {
                    minDistances[neighborNode] = newDist;
                    previousNode[neighborNode] = currentNode;
                    priorityQueue.offer(new int[]{neighborNode, newDist});
                }
            }
        }
    }

    private void displayResults(long startTime, long endTime) {
        System.out.println("Number of Vertices: " + numVertices);
        System.out.println("Number of Edges: " + numEdges);
        System.out.println("Execution Time: " + (endTime - startTime) + " nanoseconds");
        System.out.println("Shortest Path Tree from source vertex " + alphabet.charAt(startNode) + ":");
        System.out.println("Path from " + alphabet.charAt(startNode) + " to other vertices:");

        for (int i = 0; i < numVertices; i++) {
            if (i != startNode) {
                if (minDistances[i] == Integer.MAX_VALUE) {
                    System.out.println(alphabet.charAt(startNode) + " --> " + alphabet.charAt(i) + " Path cost: Unreachable");
                } else {
                    String path = buildPath(i);
                    System.out.println(path + " Path cost: " + minDistances[i]);
                }
            }
        }
    }

    private String buildPath(int destination) {
        StringBuilder path = new StringBuilder();
        for (int at = destination; at != -1; at = previousNode[at]) {
            if (at != destination) {
                path.insert(0, " --> ");
            }
            path.insert(0, alphabet.charAt(at));
        }
        return path.toString();
    }
}
