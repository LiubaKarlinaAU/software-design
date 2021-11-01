package ru.itmo.karlina.lab5;

import ru.itmo.karlina.lab5.drawer.AwtDrawerApi;
import ru.itmo.karlina.lab5.drawer.DrawerApi;
import ru.itmo.karlina.lab5.drawer.JavafxDrawerApi;
import ru.itmo.karlina.lab5.model.Graph;
import ru.itmo.karlina.lab5.model.ListGraph;
import ru.itmo.karlina.lab5.model.MatrixGraph;
import ru.itmo.karlina.lab5.model.Pair;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("There should be two arguments: <list or matrix>, <awt or javafx>");
            return;
        }
        DrawerApi drawer;
        Graph graph;
        switch (args[1]) {
            case "awt":
                drawer = new AwtDrawerApi();
                break;
            case "javafx":
                drawer = new JavafxDrawerApi();
                break;
            default:
                System.err.println("Unknown drawer api: " + args[1]);
                return;
        }

        switch (args[0]) {
            case "list":
                List<Pair<Integer>> edges;
                try {
                    edges = readEdgeList();
                } catch (Exception e) {
                    System.err.println("In \"list.txt\" file :" + e.getMessage());
                    return;
                }
                graph = new ListGraph(drawer, edges);
                break;
            case "matrix":
                boolean[][] matrix;
                try {
                    matrix = readMatrix();
                } catch (FileNotFoundException e) {
                    System.err.println("In \"matrix.txt\" file :" + e.getMessage());
                    return;
                }
                graph = new MatrixGraph(drawer, matrix);
                break;
            default:
                System.err.println("Unknown graph implementation: " + args[0]);
                return;
        }
        graph.drawGraph();
    }

    private static List<Pair<Integer>> readEdgeList() throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("list.txt"));
        List<Pair<Integer>> edges = new ArrayList<>();
        while (scanner.hasNext()) {
            int x = scanner.nextInt();
            if (scanner.hasNext()) {
                int y = scanner.nextInt();
                edges.add(new Pair<>(x, y));
            } else {
                throw new IllegalArgumentException("there should be two numbers in each line");
            }
        }

        return edges;
    }

    private static boolean[][] readMatrix() throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("matrix.txt"));
        if (!scanner.hasNextInt()) {
            throw new IllegalArgumentException("there should be numver of vertices in the beginning of the matrix");
        }
        int n = scanner.nextInt();
        boolean[][] matrix = new boolean[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (!scanner.hasNextInt()) {
                    throw new IllegalArgumentException("there should be " + n + " numbers in each line");
                }
                matrix[i][j] = scanner.nextInt() == 1;
            }
        }
        return matrix;
    }
}
