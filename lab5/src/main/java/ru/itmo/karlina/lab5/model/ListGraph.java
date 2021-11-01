package ru.itmo.karlina.lab5.model;

import ru.itmo.karlina.lab5.drawer.DrawerApi;

import java.util.*;

public class ListGraph extends Graph {
    private final List<Pair<Integer>> edges;
    private final Set<Integer> verticesName = new HashSet<>();

    public ListGraph(DrawerApi drawerApi, List<Pair<Integer>> edges) {
        super(drawerApi);
        this.edges = edges;
        for (Pair<Integer> pair : edges) {
            verticesName.add(pair.getFirst());
            verticesName.add(pair.getSecond());
        }
    }

    @Override
    public void drawGraph() {
        Map<Integer, Pair<Double>> vertices = getVertices(verticesName);

        // draw points
        for (Pair<Double> pair : vertices.values()) {
            drawerApi.drawCircle(pair.getFirst(), pair.getSecond());
        }

        // draw edges
        for (Pair<Integer> pair : edges) {
            drawerApi.drawLine(vertices.get(pair.getFirst()).getFirst(), vertices.get(pair.getFirst()).getSecond(),
                    vertices.get(pair.getSecond()).getFirst(), vertices.get(pair.getSecond()).getSecond());
        }

        drawerApi.draw();
    }
}
