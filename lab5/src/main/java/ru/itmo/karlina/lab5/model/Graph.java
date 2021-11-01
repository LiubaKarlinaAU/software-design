package ru.itmo.karlina.lab5.model;

import ru.itmo.karlina.lab5.drawer.DrawerApi;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public abstract class Graph {
    protected DrawerApi drawerApi;

    public Graph(DrawerApi drawerApi) {
        this.drawerApi = drawerApi;
    }

    public abstract void drawGraph();

    protected Map<Integer, Pair<Double>> getVertices(Set<Integer> verticesName) {
        Map<Integer, Pair<Double>> vertices = new HashMap<>();
        int width = drawerApi.getDrawingAreaWidth();
        int height = drawerApi.getDrawingAreaHeight();
        int radius = Math.min(width, height) / 4;
        int numberOfVertices = verticesName.size();
        double angle = Math.PI * 2 / numberOfVertices;
        int i = 0;
        for (int name : verticesName) {
            vertices.put(name, new Pair<>(width * 0.5 + radius * Math.cos(angle * i), height * 0.5 + radius * Math.sin(angle * i)));
            i++;
        }

        return vertices;
    }
}
