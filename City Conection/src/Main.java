import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        City cityGraph = new City();

        cityGraph.readGraphFromFile("graph.txt");

        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("1. Is there a path between two vertices?");
            System.out.println("2. BFS from one vertex to another");
            System.out.println("3. DFS From To");
            System.out.println("4. What Is Shortest Path Length?");
            System.out.println("5. Number Of Simple Paths");
            System.out.println("6. Neighbors");
            System.out.println("7. Highest Degree");
            System.out.println("8. Is Directed?");
            System.out.println("9. Are They Adjacent?");
            System.out.println("10. Is There a cycle?");
            System.out.println("11. Number Of Vertices In Component");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1 -> {
                    System.out.print("Enter the source city: ");
                    String source = scanner.next().toUpperCase();
                    System.out.print("Enter the destination city: ");
                    String destination = scanner.next().toUpperCase();
                    System.out.println("Is there a path: " + cityGraph.isThereAPath(source, destination));
                }
                case 2 -> {
                    System.out.print("Enter the source city: ");
                    String sourceBFS = scanner.next().toUpperCase();
                    System.out.print("Enter the destination city: ");
                    String destinationBFS = scanner.next().toUpperCase();
                    cityGraph.bfsFromTo(sourceBFS, destinationBFS);
                }
                case 3 -> {
                    System.out.print("Enter the source city: ");
                    String city1 = scanner.next().toUpperCase();
                    System.out.print("Enter the destination city: ");
                    String city2 = scanner.next().toUpperCase();
                    cityGraph.dfsFromTo(city1, city2);
                }
                case 4 -> {
                    System.out.print("Enter the source city: ");
                    String c1 = scanner.next().toUpperCase();
                    System.out.print("Enter the destination city: ");
                    String c2 = scanner.next().toUpperCase();
                    System.out.println(cityGraph.whatIsShortestPathLength(c1, c2));
                }
                case 5 -> {
                    System.out.print("Enter the source city: ");
                    String c3 = scanner.next().toUpperCase();
                    System.out.print("Enter the destination city: ");
                    String c4 = scanner.next().toUpperCase();
                    System.out.println(cityGraph.numberOfSimplePaths(c3, c4));
                }
                case 6 -> {
                    System.out.print("Enter the source city: ");
                    String c5 = scanner.next().toUpperCase();
                    System.out.println(Arrays.toString(cityGraph.neighbors(c5)));
                }
                case 7 -> System.out.println(cityGraph.highestDegree());
                case 8 -> System.out.println(cityGraph.isDirected());
                case 9 -> {
                    System.out.print("Enter the source city: ");
                    String c6 = scanner.next().toUpperCase();
                    System.out.print("Enter the destination city: ");
                    String c7 = scanner.next().toUpperCase();
                    System.out.println(cityGraph.areTheyAdjacent(c6, c7));
                }
                case 10 -> {
                    System.out.println("Enter the source city: ");
                    String v1 = scanner.next().toUpperCase();
                    System.out.println(cityGraph.isThereACycle(v1));
                }
                case 11 -> {
                    System.out.println("Enter the source city: ");
                    String v2 = scanner.next().toUpperCase();
                    System.out.println(cityGraph.numberOfVerticesInComponent(v2));
                }
                case 0 -> System.out.println("Exiting program.");
                default -> System.out.println("Invalid choice. Please enter a valid option.");
            }
        } while (choice != 0);

        scanner.close();
    }
}