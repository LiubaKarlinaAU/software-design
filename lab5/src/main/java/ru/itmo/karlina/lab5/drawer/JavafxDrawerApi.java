package ru.itmo.karlina.lab5.drawer;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class JavafxDrawerApi extends Application implements DrawerApi {
    private static int width = DEFAULT_WEIGHT;
    private static int height = DEFAULT_HEIGHT;
    private final List<Consumer<GraphicsContext>> figures = new ArrayList<>();
    private static final Canvas canvas = new Canvas(width, height);

    @Override
    public void setDrawingAreaWidth(int width) {
        JavafxDrawerApi.width = width;
    }

    @Override
    public void setDrawingAreaHeight(int height) {
        JavafxDrawerApi.height = height;
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
        figures.add(gc -> gc.fillOval(x - CIRCLE_RADIUS, y - CIRCLE_RADIUS, 2 * CIRCLE_RADIUS, 2 * CIRCLE_RADIUS));
    }

    @Override
    public void drawLine(double x1, double y1, double x2, double y2) {
        figures.add(gc -> gc.strokeLine(x1, y1, x2, y2));
    }

    @Override
    public void draw() {
        GraphicsContext gc = canvas.getGraphicsContext2D();

        gc.setFill(Color.GREEN);
        gc.setLineWidth(LINE_WIDTH);
        gc.setStroke(Color.GREEN);
        for (Consumer<GraphicsContext> cons : figures) {
            cons.accept(gc);
        }
        JavafxDrawerApi.launch(JavafxDrawerApi.class);
    }


    @Override
    public void start(Stage stage) {
        stage.setTitle("Drawing graph");
        Group root = new Group();
        root.getChildren().add(canvas);
        stage.setScene(new Scene(root));
        stage.show();
        stage.setOnCloseRequest(windowEvent -> System.exit(0));
    }
}
