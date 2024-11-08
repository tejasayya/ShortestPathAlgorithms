package Algorithms;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ShortestPathAlgorithm dijkstra = new ShortestPathAlgorithm();
        MinimumSpanningTree kruskal = new MinimumSpanningTree();
        TopologicalSort topoSort = new TopologicalSort();

        Scanner scanner = new Scanner(System.in);
        int selectedFile;

        System.out.println("Choose an Algorithm:\n" +
                "1. Dijkstra's Algorithm (Shortest Path)\n" +
                "2. Kruskal's Algorithm (Minimum Spanning Tree)\n" +
                "3. Topological Sorting (Cycle detection and topological order)");

        int choice = Integer.parseInt(scanner.nextLine());
        switch (choice) {
            case 1:
                System.out.println("Select an Input File:\n" +
                        "1. Undirected Graph - Input1\n" +
                        "2. Undirected Graph - Input2\n" +
                        "3. Undirected Graph - Input3\n" +
                        "4. Undirected Graph - Input4\n" +
                        "5. Directed Graph - Input1\n" +
                        "6. Directed Graph - Input2\n" +
                        "7. Directed Graph - Input3\n" +
                        "8. Directed Graph - Input4");
                selectedFile = Integer.parseInt(scanner.nextLine());
                String dijkstraFilePath = getFilePath(selectedFile);
                System.out.println("========== Dijkstra's Algorithm ===========");
                dijkstra.runAlgorithm(dijkstraFilePath);
                break;
            case 2:
                System.out.println("Select an Input File:\n" +
                        "1. Undirected Graph - Input1\n" +
                        "2. Undirected Graph - Input2\n" +
                        "3. Undirected Graph - Input3\n" +
                        "4. Undirected Graph - Input4");
                selectedFile = Integer.parseInt(scanner.nextLine());
                String kruskalFilePath = getFilePath(selectedFile);
                System.out.println("========== Kruskal's Algorithm ==========");
                kruskal.executeMST(kruskalFilePath);
                break;
            case 3:
                System.out.println("Select an Input File:\n" +
                        "5. DAG - Input1\n" +
                        "6. DAG - Input2\n" +
                        "7. Cyclic Graph - Input3\n" +
                        "8. Cyclic Graph - Input4");
                selectedFile = Integer.parseInt(scanner.nextLine());
                String topoFilePath = getFilePath(selectedFile);
                System.out.println("========== Topological Sorting ==========");
                topoSort.performTopologicalSort(topoFilePath);
                break;
            default:
                System.out.println("Invalid input. Please choose a valid option.");
                break;
        }
        scanner.close();
    }

    static String getFilePath(int selectedFile) {
        String filePath;
        switch (selectedFile) {
            case 1:
                filePath = "./inputfiles/UndirectedGraph_Input1.txt";
                break;
            case 2:
                filePath = "./inputfiles/UndirectedGraph_Input2.txt";
                break;
            case 3:
                filePath = "./inputfiles/UndirectedGraph_Input3.txt";
                break;
            case 4:
                filePath = "./inputfiles/UndirectedGraph_Input4.txt";
                break;
            case 5:
                filePath = "./inputfiles/DirectedGraph_Input1.txt";
                break;
            case 6:
                filePath = "./inputfiles/DirectedGraph_Input2.txt";
                break;
            case 7:
                filePath = "./inputfiles/DirectedGraph_Input3.txt";
                break;
            case 8:
                filePath = "./inputfiles/DirectedGraph_Input4.txt";
                break;
            default:
                System.out.println("Invalid Input. Defaulting to Undirected Graph - Input1.");
                filePath = "./inputfiles/UndirectedGraph_Input1.txt";
                break;
        }
        return filePath;
    }
}
