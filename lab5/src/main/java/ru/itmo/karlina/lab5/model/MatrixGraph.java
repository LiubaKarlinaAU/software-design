package ru.itmo.karlina.lab5.model;

import ru.itmo.karlina.lab5.drawer.DrawerApi;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MatrixGraph extends Graph {
    private final boolean[][] matrix;
    private final Set<Integer> verticesName = new HashSet<>();

    public MatrixGraph(DrawerApi drawerApi, boolean[][] matrix) {
        super(drawerApi);
        assert matrix.length == 0 || matrix.length == matrix[0].length;
        for (int i = 0; i < matrix.length; i++) {
            verticesName.add(i);
        }
        this.matrix = matrix;
    }

    @Override
    public void drawGraph() {
        Map<Integer, Pair<Double>> vertices = getVertices(verticesName);

        // draw points
        for (Pair<Double> pair : vertices.values()) {
            drawerApi.drawCircle(pair.getFirst(), pair.getSecond());
        }

        // draw edges
        for (int i = 0; i < matrix.length; i++) {
            for (int j = i + 1; j < matrix.length; j++) {
                if (matrix[i][j]) {
                    drawerApi.drawLine(vertices.get(i).getFirst(), vertices.get(i).getSecond(),
                            vertices.get(j).getFirst(), vertices.get(j).getSecond());
                }
            }
        }
        drawerApi.draw();
    }
}
