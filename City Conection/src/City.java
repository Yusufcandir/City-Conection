import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class City {

    private final Map<String, Integer> cityIndices;
    private final ArrayList<ArrayList<Edge>> adjacencyList;

    public City() {
        cityIndices = new Map<>();
        adjacencyList = new ArrayList<>();
    }

    // Hashing method
    private int hash(String cityName) {
        return cityName.length() % cityIndices.size();
    }

    // Method to search for a city in the hash table
    private int searchCity(String cityName) {
        Integer hashValue = cityIndices.get(cityName);
        return hashValue != null ? hashValue : -1;
    }

    // Method to read graph data from the file and construct the adjacency list
    public void readGraphFromFile(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" -> ");

                if (parts.length == 2) {
                    String vertexName = parts[0];
                    String[] edges = parts[1].split(", ");
                    int fromIndex = getOrCreateIndex(vertexName);

                    for (String edge : edges) {
                        String[] edgeParts = edge.split(": ");
                        String toVertex = edgeParts[0];
                        int toIndex = getOrCreateIndex(toVertex);
                        int weight = Integer.parseInt(edgeParts[1]);

                        adjacencyList.get(fromIndex).add(new Edge(fromIndex, toIndex, weight));
                    }
                } else {
                    // Handle invalid line format
                    System.out.println("Invalid line format: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Helper method to get or create index for a city
    private int getOrCreateIndex(String cityName) {
        Integer index = cityIndices.get(cityName);

        if (index == null) {
            index = adjacencyList.size();
            adjacencyList.add(new ArrayList<>());
            cityIndices.put(cityName, index);
        }

        return index;
    }


    // Returns true if there is a path between vertex v1 and vertex v2
    public boolean isThereAPath(String v1, String v2) {
        int start = searchCity(v1);
        int end = searchCity(v2);

        if (start == -1 || end == -1) {
            System.out.println("Invalid city names");
            return false;
        }

        int[] visited = new int[adjacencyList.size()];
        return dfs(start, end, visited);
    }


    // Prints the sequence of vertices and edges in the Breadth-First Search (BFS) from v1 to v2
    public void bfsFromTo(String v1, String v2) {
        int start = searchCity(v1);
        int end = searchCity(v2);

        if (start == -1 || end == -1) {
            System.out.println("Invalid city names");
            return;
        }

        int[] queue = new int[adjacencyList.size()];
        int front = -1, rear = -1;

        int[] visited = new int[adjacencyList.size()];
        int[] parentMap = new int[adjacencyList.size()];

        Arrays.fill(visited, -1); // Mark all vertices as not visited
        Arrays.fill(parentMap, -1); // Initialize parent map

        // Enqueue the start vertex
        queue[++rear] = start;
        visited[start] = start;

        while (front != rear) {
            int current = queue[++front];

            if (current == end) {
                printBFSPathOrdered(start, end, parentMap);
                return;
            }

            for (Edge neighbor : adjacencyList.get(current)) {
                if (visited[neighbor.to] == -1) {
                    // Enqueue the neighbor vertex
                    queue[++rear] = neighbor.to;
                    visited[neighbor.to] = current;
                    parentMap[neighbor.to] = current;
                }
            }
        }

        System.out.println("No path found between " + v1 + " and " + v2);
    }

    // Prints the sequence of vertices and edges in the ordered Breadth-First Search (BFS) path
    private void printBFSPathOrdered(int start, int end, int[] parentMap) {
        LinkedList<String> pathList = new LinkedList<>();
        int current = end;

        // Build the path in reverse order
        while (current != start) {
            pathList.addFirst(" -> " + getCityName(current) + " (" + getEdgeWeight(parentMap[current], current) + ")");
            current = parentMap[current];
        }

        pathList.addFirst(getCityName(start));

        // Print the ordered path
        System.out.print("BFS Path from " + getCityName(start) + " to " + getCityName(end) + ": ");
        for (String vertex : pathList) {
            System.out.print(vertex);
        }
        System.out.println();
    }

    private int getEdgeWeight(int from, int to) {
        for (Edge edge : adjacencyList.get(from)) {
            if (edge.to == to) {
                return edge.weight;
            }
        }
        return -1; // Edge not found
    }


    // Depth-First Search (DFS) algorithm
    private boolean dfs(int current, int end, int[] visited) {
        visited[current] = 1; // Mark current vertex as visited

        if (current == end) {
            return true;
        }

        for (Edge neighbor : adjacencyList.get(current)) {
            if (visited[neighbor.to] == 0 && dfs(neighbor.to, end, visited)) {
                return true;
            }
        }

        return false;
    }

    public void dfsFromTo(String v1, String v2) {
        int start = searchCity(v1);
        int end = searchCity(v2);

        if (start == -1 || end == -1) {
            System.out.println("Invalid city names");
            return;
        }

        int[] visited = new int[adjacencyList.size()];
        ArrayList<Edge> path = new ArrayList<>();

        boolean pathFound = dfsWithPath(start, end, visited, path);

        if (pathFound) {
            printDFSPath(path);
        } else {
            System.out.println("No path found from " + v1 + " to " + v2);
        }
    }

    private boolean dfsWithPath(int current, int end, int[] visited, ArrayList<Edge> path) {
        if (current == end) {
            return true;
        }

        visited[current] = 1;

        for (Edge neighbor : adjacencyList.get(current)) {
            if (visited[neighbor.to] == 0) {
                path.add(neighbor);
                boolean pathExists = dfsWithPath(neighbor.to, end, visited, path);
                if (pathExists) {
                    return true;
                }
                path.remove(path.size() - 1); // Remove the last edge in the path
            }
        }

        return false;
    }

    private void printDFSPath(ArrayList<Edge> path) {
        for (Edge e : path) {
            System.out.println(getCityName(e.from) + " -> " + getCityName(e.to) + " (" + e.weight + ")");
        }
    }


    // Helper method to get city name by index
    private String getCityName(int index) {
        for (Map.Entry<String, Integer> entry : cityIndices.entries) {
            if (entry.getValue().equals(index)) {
                return entry.getKey();
            }
        }
        return null;
    }

    public List<String> highestDegree() {
        int maxDegree = 0;
        List<String> highestDegreeCities = new ArrayList<>();

        for (int i = 0; i < adjacencyList.size(); i++) {
            int degree = adjacencyList.get(i).size();
            if (degree > maxDegree) {
                maxDegree = degree;
                highestDegreeCities.clear();
                highestDegreeCities.add(getCityName(i));
            } else if (degree == maxDegree) {
                highestDegreeCities.add(getCityName(i));
            }
        }

        return highestDegreeCities;
    }


    public boolean isDirected() {
        for (int i = 0; i < adjacencyList.size(); i++) {
            for (Edge edge : adjacencyList.get(i)) {
                // If there is an edge from vertex i, and there is no edge from the corresponding vertex to i, the graph is directed.
                if (!hasEdge(edge.to, i)) {
                    return true;
                }
            }
        }
        return false; // If all edges are bidirectional, the graph is not directed.
    }

    // Helper function to check whether a specific edge exists
    private boolean hasEdge(int from, int to) {
        for (Edge edge : adjacencyList.get(from)) {
            if (edge.to == to) {
                return true;
            }
        }
        return false;
    }

    public int whatIsShortestPathLength(String v1, String v2) {
        int start = searchCity(v1);
        int end = searchCity(v2);

        if (start == -1 || end == -1) {
            System.out.println("Invalid city names");
            return -1;
        }

        Queue<Integer> queue = new Queue<>();
        int[] distances = new int[adjacencyList.size()];
        Arrays.fill(distances, -1); // Mark all vertices as unvisited

        queue.add(start);
        distances[start] = 0;

        while (!queue.isEmpty()) {
            int current = queue.remove();

            for (Edge neighbor : adjacencyList.get(current)) {
                if (distances[neighbor.to] == -1) {
                    distances[neighbor.to] = distances[current] + neighbor.weight;
                    queue.add(neighbor.to);
                }
            }
        }

        if (distances[end] != -1) {
            return distances[end];
        } else {
            System.out.println(v1 + " --x-- " + v2);
            return -1;
        }
    }


    public int numberOfSimplePaths(String v1, String v2) {
        int start = searchCity(v1);
        int end = searchCity(v2);

        if (start == -1 || end == -1) {
            System.out.println("Invalid city names");
            return 0;
        }

        // Use a stack to perform iterative DFS
        Stack<Integer> stack = new Stack<>();
        stack.push(start);

        // Use a map to store the count of simple paths for each node
        Map<Integer, Integer> pathCountMap = new Map<>();
        pathCountMap.put(start, 1);

        int pathCount = 0;

        while (!stack.isEmpty()) {
            int current = stack.pop();

            if (current == end) {
                pathCount += pathCountMap.get(current);
                continue;
            }

            boolean hasUnvisitedNeighbor = false;

            for (Edge neighbor : adjacencyList.get(current)) {
                if (!pathCountMap.containsKey(neighbor.to)) {
                    stack.push(neighbor.to);

                    // Update the path count for the neighbor
                    pathCountMap.put(neighbor.to, pathCountMap.getOrDefault(current, 0) + 1);
                    hasUnvisitedNeighbor = true;
                }
            }

            // If there are no unvisited neighbors, backtrack
            if (!hasUnvisitedNeighbor) {
                pathCountMap.remove(current);
            }
        }

        return pathCount;
    }


    public boolean areTheyAdjacent(String v1, String v2) {
        int index1 = searchCity(v1);
        int index2 = searchCity(v2);

        if (index1 == -1 || index2 == -1) {
            System.out.println("One or both cities not found");
            return false;
        }

        for (Edge edge : adjacencyList.get(index1)) {
            if (edge.to == index2) {
                return true;
            }
        }

        return false;
    }


    public boolean isThereACycle(String v1) {
        int startIndex = searchCity(v1);

        if (startIndex == -1) {
            System.out.println("City not found: " + v1);
            return false;
        }

        boolean[] visited = new boolean[adjacencyList.size()];
        return dfsForCycle(startIndex, startIndex, visited, -1);
    }

    private boolean dfsForCycle(int current, int start, boolean[] visited, int parent) {
        visited[current] = true;

        for (Edge neighbor : adjacencyList.get(current)) {
            if (!visited[neighbor.to]) {
                if (dfsForCycle(neighbor.to, start, visited, current)) {
                    return true;
                }
            } else if (neighbor.to == start && neighbor.to != parent) {
                // If the neighbor is the start vertex, and it is not the parent of the current vertex, there is a cycle
                return true;
            }
        }

        return false;
    }

    public int numberOfVerticesInComponent(String v1) {
        int startIndex = searchCity(v1);

        if (startIndex == -1) {
            System.out.println("City not found: " + v1);
            return 0;
        }

        boolean[] visited = new boolean[adjacencyList.size()];
        return bfsComponentSize(startIndex, visited);
    }

    private int bfsComponentSize(int startIndex, boolean[] visited) {
        Queue<Integer> queue = new Queue<>();
        queue.add(startIndex);
        visited[startIndex] = true;
        int count = 0;

        while (!queue.isEmpty()) {
            int current = queue.pop();
            count++;

            for (Edge neighbor : adjacencyList.get(current)) {
                if (!visited[neighbor.to]) {
                    visited[neighbor.to] = true;
                    queue.add(neighbor.to);
                }
            }
        }
        return count;
    }


    public String[] neighbors(String v1) {
        int vertexIndex = searchCity(v1);

        if (vertexIndex == -1) {
            System.out.println("City not found: " + v1);
            return new String[0];
        }

        String[] neighbors = new String[adjacencyList.get(vertexIndex).size()];
        int i = 0;

        for (Edge edge : adjacencyList.get(vertexIndex)) {
            neighbors[i++] = getCityName(edge.to);
        }

        return neighbors;
    }


    private static class Edge {
        int from;
        int to;
        int weight;

        public Edge(int from, int to, int weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }
    }
}