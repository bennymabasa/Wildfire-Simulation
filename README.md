# Wildfire Simulation System 🔥

A JavaFX desktop application that simulates wildfire spread across terrain maps using custom data structures, graph algorithms, and pathfinding.

![Java](https://img.shields.io/badge/Java-17+-orange)
![JavaFX](https://img.shields.io/badge/JavaFX-UI-blue)
![License](https://img.shields.io/badge/License-MIT-green)

## Overview

This project models a landscape as a graph where each cell is a terrain block (grass, trees, water, roads, etc.). Users can load an image of terrain, classify it, start fires, watch the fire spread in real time, and compute the safest escape path using Dijkstra’s algorithm.

### Key Features

- **Terrain from Images** – Load a map image and automatically classify terrain types
- **Custom Data Structures** – Implementations of ArrayList, Stack, Queue, and Priority Queue (no reliance on Java Collections for core algorithms)
- **Graph Representation** – Grid-based graph with weighted edges based on terrain
- **Fire Simulation** – Event-driven fire spread using a priority queue (time-based ignition)
- **Safest Path Finding** – Dijkstra’s algorithm to find the lowest-risk route between two points
- **Interactive JavaFX UI** – Visual map, fire overlay, path overlay, and controls

## Project Structure

```
src/
├── WildfireApp.java                 # Application entry point
└── wildfires/
    ├── dataStructures/              # Custom ADTs (MyArrayList, MyStack, MyQueue, MyPriorityQueue)
    ├── graph/                       # GridGraph
    ├── image/                       # Image → Graph builder & terrain classification
    ├── model/                       # BlockNode, Edge, TerrainType, FireState, Rules
    ├── pathFinding/                 # Dijkstra PathFinder
    ├── simulation/                  # FireSimulator (event-driven)
    └── UI/                          # JavaFX panels and controls
```

## Technologies & Concepts

| Area              | Details                                      |
|-------------------|----------------------------------------------|
| Language          | Java                                         |
| UI Framework      | JavaFX                                       |
| Data Structures   | Custom ArrayList, Stack, Queue, PriorityQueue|
| Algorithms        | Dijkstra (shortest/safest path), Event simulation |
| Modeling          | Graph theory, terrain risk weighting         |

## How to Run

### Requirements
- Java 17 or newer
- JavaFX SDK (or use a JDK that bundles JavaFX, e.g. Liberica Full / Azul Zulu FX)

### Steps

1. Clone the repository:
   ```bash
   git clone https://github.com/bennymabasa/Wildfire-Simulation.git
   cd Wildfire-Simulation
   ```

2. Compile (example with JavaFX modules on the module path):
   ```bash
   javac --module-path /path/to/javafx/lib --add-modules javafx.controls,javafx.graphics,javafx.swing -d out $(find src -name "*.java")
   ```

3. Run:
   ```bash
   java --module-path /path/to/javafx/lib --add-modules javafx.controls,javafx.graphics,javafx.swing -cp out WildfireApp
   ```

> **Tip:** If you use an IDE (IntelliJ, Eclipse, VS Code), simply import the `src` folder as a Java project and add the JavaFX library.

## Usage

1. Launch the application.
2. Load a terrain image (or use the built-in map handling).
3. Click to start a fire on burnable terrain.
4. Watch the simulation step through fire spread.
5. Select start and end points to compute and display the safest path.

## Academic Context

This was developed as a Computer Science student project demonstrating:
- Implementation of fundamental data structures from scratch
- Graph modeling of spatial problems
- Priority-queue driven discrete-event simulation
- Classic pathfinding algorithms applied to a real-world inspired scenario

## Author

**Benny Mabasa**  
[GitHub Profile](https://github.com/bennymabasa) · [Portfolio](https://bennymabasa.github.io)

---

Made with ☕ and Java
