package ru.itmo.karlina.lab5.drawer;

public interface DrawerApi {
    double CIRCLE_RADIUS = 10;
    long LINE_WIDTH = 3;
    int DEFAULT_WEIGHT = 800;
    int DEFAULT_HEIGHT = 800;

    void setDrawingAreaWidth(int width);

    void setDrawingAreaHeight(int height);

    int getDrawingAreaWidth();

    int getDrawingAreaHeight();

    void drawCircle(double x, double y);

    void drawLine(double x1, double y1, double x2, double y2);

    void draw();
}
