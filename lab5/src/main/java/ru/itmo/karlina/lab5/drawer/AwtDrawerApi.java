package ru.itmo.karlina.lab5.drawer;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.HashSet;
import java.util.Set;

public class AwtDrawerApi extends Frame implements DrawerApi {
    private final Set<Shape> shapesToDraw = new HashSet<>();
    private int width = DEFAULT_WEIGHT;
    private int height = DEFAULT_HEIGHT;

    @Override
    public void setDrawingAreaWidth(int width) {
        this.width = width;
    }

    @Override
    public void setDrawingAreaHeight(int height) {
        this.height = height;
    }

    @Override
    public int getDrawingAreaWidth() {
        return width;
    }

    @Override
    public int getDrawingAreaHeight() {
        return height;
    }

    @Override
    public void drawCircle(double x, double y) {
        shapesToDraw.add(new Ellipse2D.Double(x - CIRCLE_RADIUS, y - CIRCLE_RADIUS, 2 * CIRCLE_RADIUS, 2 * CIRCLE_RADIUS));
    }

    @Override
    public void drawLine(double x1, double y1, double x2, double y2) {
        shapesToDraw.add(new Line2D.Double(x1, y1, x2, y2));
    }

    @Override
    public void draw() {
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }
        });
        this.setSize(width, height);
        this.setVisible(true);
    }

    @Override
    public void paint(Graphics graphics) {
        Graphics2D graphics2d = (Graphics2D) graphics;
        graphics2d.setStroke(new BasicStroke(LINE_WIDTH));
        for (Shape shape : shapesToDraw) {
            graphics2d.fill(shape);
            graphics2d.draw(shape);
        }
    }
}
