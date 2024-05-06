# City Connections Project

## Project Information
- **Course**: COMP2112 Data Structures and Algorithms
- **Semester**: Fall 2023
- **Project**: Project 3

## Project Objective
The project aims to model connections between fictional cities using a weighted graph representation. The graph's edges will have weights corresponding to fictional distance values between cities. The program should offer various functionalities such as loading graph data from a file, checking for paths between vertices, performing BFS and DFS traversal between vertices, determining the shortest path length, counting the number of paths, identifying neighbors and vertices with the highest degree, detecting cycles, and more.

## Project Requirements
1. **Graph Loading**: Load graph data from a file into an adjacency matrix. Create a hash table to map city names to graph vertices.
2. **Path Queries**:
   - Check for a path between two vertices.
   - Perform BFS and DFS traversal between vertices.
   - Determine the shortest path length between two vertices.
   - Count the number of paths between two vertices.
   - Identify neighbors and vertices with the highest degree.
   - Check if the graph is directed.
   - Determine if two vertices are adjacent.
   - Check for cycles starting and ending at a specific vertex.
   - Print the number of vertices in a component containing a specified vertex.
3. **Menu-driven Interface**: Implement a main method that provides a menu to select different functionalities. Input parameters should be received interactively from the user.

## File Format
- The graph data file (graph.txt) contains information about city connections in a different format than discussed in lectures.
- Each row defines the edges for a single vertex.
- Edges are listed after the "->" sign, separated by commas.
- Each edge definition consists of two elements separated by ":" - the endpoint and the weight of the edge.
