package wildfires.UI;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import wildfires.graph.GridGraph;
import wildfires.image.ImageGraphBuilder;
import wildfires.model.BlockNode;
import wildfires.pathFinding.PathFinder;
import wildfires.simulation.FireSimulator;

public class WildfireUIControl extends BorderPane {

    private Pane gridLayer;
    private Pane fireLayer;
    private Pane pathLayer;
    private StackPane imageStack;
    private Label statusLabel;

    private GridGraph graph;
    private PathFinder pathFinder;
    private FireSimulator fireSimulator;

    private GraphUI graphui;
    private PathUI pathui;
    private FireUI fireui;

    private Timeline simTimeline;
    private boolean fireSelectionMode = false;
    private BlockNode pathStart = null;

    public WildfireUIControl() {
        buildUI();
        loadDemoMap();
    }

    private void buildUI() {
        statusLabel = new Label("Ready – Load a map or use the demo grid");
        statusLabel.setPadding(new Insets(8));

        gridLayer = new Pane();
        fireLayer = new Pane();
        pathLayer = new Pane();

        imageStack = new StackPane(gridLayer, fireLayer, pathLayer);
        imageStack.setPrefSize(900, 650);

        graphui = new GraphUI(gridLayer);

        Button demoBtn = new Button("Demo Map");
        demoBtn.setOnAction(e -> loadDemoMap());

        Button igniteBtn = new Button("Ignite Mode");
        igniteBtn.setOnAction(e -> {
            fireSelectionMode = !fireSelectionMode;
            pathStart = null;
            statusLabel.setText(fireSelectionMode ? "Click a cell to start a fire" : "Ignite mode off");
        });

        Button stepBtn = new Button("Step Simulation");
        stepBtn.setOnAction(e -> {
            if (fireSimulator != null && fireSimulator.step()) {
                fireui.render();
                statusLabel.setText("Time: " + fireSimulator.getCurrentTime());
            } else {
                statusLabel.setText("Simulation finished or nothing to burn");
            }
        });

        Button runBtn = new Button("Run");
        runBtn.setOnAction(e -> startAutoSim());

        Button resetBtn = new Button("Reset");
        resetBtn.setOnAction(e -> {
            if (fireSimulator != null) {
                fireSimulator.reset();
                fireui.render();
                pathui.clear();
                statusLabel.setText("Reset complete");
            }
        });

        Button pathBtn = new Button("Find Safest Path");
        pathBtn.setOnAction(e -> {
            fireSelectionMode = false;
            pathStart = null;
            statusLabel.setText("Click start cell, then end cell for safest path");
            imageStack.setOnMouseClicked(ev -> handlePathClick(ev.getX(), ev.getY()));
        });

        ToolBar toolbar = new ToolBar(demoBtn, igniteBtn, stepBtn, runBtn, resetBtn, pathBtn);
        setTop(toolbar);
        setCenter(imageStack);
        setBottom(statusLabel);
        BorderPane.setAlignment(statusLabel, Pos.CENTER);

        imageStack.setOnMouseClicked(ev -> {
            if (fireSelectionMode) {
                handleIgniteClick(ev.getX(), ev.getY());
            }
        });
    }

    private void loadDemoMap() {
        graph = ImageGraphBuilder.buildDemoGrid(40, 60);
        pathFinder = new PathFinder(graph);
        fireSimulator = new FireSimulator(graph);

        graphui.setGraph(graph, 900, 650);
        graphui.render();

        double cw = graphui.getCellWidth();
        double ch = graphui.getCellHeight();
        fireui = new FireUI(fireLayer, graph, cw, ch);
        pathui = new PathUI(pathLayer, cw, ch);

        fireui.clear();
        pathui.clear();
        statusLabel.setText("Demo map loaded (40x60). Use Ignite Mode or Find Safest Path.");
    }

    private void handleIgniteClick(double x, double y) {
        if (graph == null) return;
        int col = (int) (x / graphui.getCellWidth());
        int row = (int) (y / graphui.getCellHeight());
        if (graph.isValidPosition(row, col)) {
            fireSimulator.ignite(row, col);
            fireui.render();
            statusLabel.setText("Ignited cell (" + row + ", " + col + ")");
        }
    }

    private void handlePathClick(double x, double y) {
        if (graph == null) return;
        int col = (int) (x / graphui.getCellWidth());
        int row = (int) (y / graphui.getCellHeight());
        if (!graph.isValidPosition(row, col)) return;

        BlockNode node = graph.getNode(row, col);
        if (pathStart == null) {
            pathStart = node;
            statusLabel.setText("Start selected. Now click the destination cell.");
        } else {
            var path = pathFinder.findSafestPath(pathStart, node);
            pathui.renderPath(path);
            statusLabel.setText("Path found with " + path.size() + " cells");
            pathStart = null;
        }
    }

    private void startAutoSim() {
        if (simTimeline != null) {
            simTimeline.stop();
        }
        simTimeline = new Timeline(new KeyFrame(Duration.millis(150), e -> {
            if (fireSimulator != null && fireSimulator.step()) {
                fireui.render();
                statusLabel.setText("Time: " + fireSimulator.getCurrentTime());
            } else {
                simTimeline.stop();
                statusLabel.setText("Simulation complete");
            }
        }));
        simTimeline.setCycleCount(Timeline.INDEFINITE);
        simTimeline.play();
    }
}
