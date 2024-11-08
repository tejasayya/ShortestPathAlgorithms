package Algorithms;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class TopologicalSort {
    private Map<Character, List<Character>> adjList;
    private Set<Character> visitedNodes;
    private Set<Character> activePath;
    private List<List<Character>> detectedCycles;

    public void performTopologicalSort(String filePath) {
        try {
            adjList = new HashMap<>();
            visitedNodes = new HashSet<>();
            activePath = new HashSet<>();
            detectedCycles = new ArrayList<>();

            // Read the graph from the file
            Scanner scanner = new Scanner(new File(filePath));

            int numVertices = scanner.nextInt();
            int numEdges = scanner.nextInt();
            System.out.println("Vertices Count: " + numVertices);
            System.out.println("Edges Count: " + numEdges);
            char graphType = scanner.next().charAt(0);

            scanner.nextLine(); // Skip to the next line

            // Construct the graph
            for (int i = 0; i < numEdges; i++) {
                String line = scanner.nextLine();
                char startNode = line.charAt(0);
                char endNode = line.charAt(2);

                adjList.computeIfAbsent(startNode, k -> new ArrayList<>()).add(endNode);
                if (graphType == 'U') {
                    adjList.computeIfAbsent(endNode, k -> new ArrayList<>()).add(startNode);
                }
            }
            scanner.close();

            // Check for cycles using DFS
            for (char node : adjList.keySet()) {
                if (!visitedNodes.contains(node)) {
                    detectCycles(node, new HashSet<>(), new ArrayList<>());
                }
            }

            if (detectedCycles.isEmpty()) {
                System.out.println("The graph is acyclic. Proceeding with topological sort...");
                long startTime = System.nanoTime();
                List<Character> topoOrder = getTopologicalOrder();
                long endTime = System.nanoTime();
                System.out.println("Execution Time: " + (endTime - startTime) + " nanoseconds");
                System.out.println("Topological sorting order:");
                for (char node : topoOrder) {
                    System.out.print(node + " ");
                }
                System.out.println();
            } else {
                System.out.println("The graph contains cycles.");
                System.out.println("Detected cycles with their lengths:");
                int cycleCount = 1;
                for (List<Character> cycle : detectedCycles) {
                    System.out.print("Cycle " + cycleCount + ": ");
                    for (int i = 0; i < cycle.size(); i++) {
                        System.out.print(cycle.get(i));
                        if (i < cycle.size() - 1) {
                            System.out.print(" -> ");
                        }
                    }
                    System.out.println(" (Length: " + cycle.size() + ")");
                    cycleCount++;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filePath);
            e.printStackTrace();
        }
    }

    private void detectCycles(char node, Set<Character> pathNodes, List<Character> pathTrail) {
        if (activePath.contains(node)) {
            List<Character> cycle = new ArrayList<>(pathTrail.subList(pathTrail.indexOf(node), pathTrail.size()));
            detectedCycles.add(cycle);
            return;
        }
        if (!visitedNodes.contains(node)) {
            visitedNodes.add(node);
            activePath.add(node);
            pathTrail.add(node);

            List<Character> neighbors = adjList.getOrDefault(node, new ArrayList<>());
            for (char neighbor : neighbors) {
                detectCycles(neighbor, pathNodes, pathTrail);
            }

            activePath.remove(node);
            pathTrail.remove(pathTrail.size() - 1);
        }
    }

    private List<Character> getTopologicalOrder() {
        List<Character> topoOrder = new ArrayList<>();
        Set<Character> visited = new HashSet<>();
        Set<Character> inProcess = new HashSet<>();

        for (char node : adjList.keySet()) {
            if (!visited.contains(node)) {
                dfsTopoSort(node, visited, inProcess, topoOrder);
            }
        }

        Collections.reverse(topoOrder);
        return topoOrder;
    }

    private void dfsTopoSort(char node, Set<Character> visited, Set<Character> inProcess, List<Character> topoOrder) {
        visited.add(node);
        inProcess.add(node);

        List<Character> neighbors = adjList.getOrDefault(node, new ArrayList<>());
        for (char neighbor : neighbors) {
            if (!visited.contains(neighbor)) {
                dfsTopoSort(neighbor, visited, inProcess, topoOrder);
            }
        }

        inProcess.remove(node);
        topoOrder.add(node);
    }
}
