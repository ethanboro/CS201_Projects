# Project 7: Route

This is the README document for Project 7 Route in CompSci 201 at Duke University, Fall 2024

See the [details document](docs/details.md) for information on using Git, starting the project, and more details about the project including information about the classes and concepts that are outlined briefly below. You'll absolutely need to read the information in the details document to understand how the classes in this project work independently and together. The details document also contains project-specific details. This current document provides a high-level overview of the assignment.

You are STRONGLY encouraged to work with a partner on this final project! See the [details document](docs/details.md) for information on using Git with a partner and how the workflow can proceed. 

### Background

This project was initially developed by Brandon Fain and the UTA/TA team in 201, with some ephemeral
connection to the Bear Maps project from UC Berkeley's 61B course. It has been redesigned in recent 
201 semesters to be doable in a week.

## Outline 

- [Project Introduction](#project-introduction)
    - [Summary](#summary)
    - [The `Point` Class](#the-point-class)
    - [The `Visualize` Class](#the-visualize-class)
    - [Graph Data](#graph-data)
- [Implementing `GraphProcessor`](#implementing-graphprocessor)
- [Running `GraphDemo`](#running-graphdemo)
- [Submitting and Grading](#submitting-and-grading)

## Project Introduction

In this project you are asked to implement a routing service that represents the United States highway network as a graph and calculates routes and distances on this network. At a high level, you will implement `GraphProcessor` which stores a graph representation and provides public methods to answer connectivity, distance, and pathfinding queries. This part of the project will be autograded as per usual. 

### Engagement when done

For six engagement points you will run (the `main` method in) `GraphDemo` that produces a visualization of the path finding you implemented, demonstrating the functionality of `GraphProcessor` and visualizing the results. You're asked to choose start/end points you don't think others chose. Fill out [this form for engagement](https://bit.ly/p7-engage-2024): https://bit.ly/p7-engage-2024 

You could, for example, include a video of your demo as part of submitting something for [P8 Create](https://docs.google.com/document/d/1UArW7KY-CWI-PYJFbw0L7_Iv4td_pS5Ah11VZOxRTN8/edit?usp=sharing).

To complete the program, you'll need to understand the classes you're given and the code you're asked to write. The classes you're given include `Point` and `Visualize` (which uses the `StdDraw` class you used in *P1 NBody* and in *P5 Percolation*), and some JUnit testing classes. Details can be found in [details document](docs/details.md) with a high-level overview here.

## Summary

You'll implement four methods in `GraphProcessor.java`. Details below. You *must* implement `initialize` first. Then you should implement methods `nearestPoint`, `routeDistance`, and `connected` in any order, using supplied JUnit tests to verify partial correctness. Then you implement `route` (Dijkstra's shortest path algorithm). After testing, you should run `GraphDemo` for engagement, and modify `PathBenchmark` to compare your implementation with the supplied `slowRoute` method for partial correctness.

## Classes

### The `Point` Class

You are provided in the starter code with `Point.java` that represents an immutable (meaning it cannot be changed after creation) point on the Earth's surface. You call methods in this class, details can be found in the [details document](docs/details.md). You'll use this class extensively to represent vertices in a graph. You do not need to change `Point`.

### The `Visualize` Class

`Visualize.java` (which, in turn, uses `StdDraw.java`, though you won't need to directly call anything from this class). You do not need to edit this class, methods and details can be found in the [details document](docs/details.md).

## Implementing `GraphProcessor`

In this part you will implement `GraphProcessor`, which stores a graph representation and provides public methods to answer connectivity, distance, and pathfinding queries. *This part of the project will be autograded*. To pass autograder compilation, you must write your `GraphProcessor` implementation entirely within the provided `GraphProcessor.java` file. If you use helper classes, they should be included in the file as nested classes.

JUnit tests are also supplied to test your code locally, and we strongly suggest starting by testing with the straightforward `TestSimpleGraphProcessor` for ease of debugging. Once you pass `TestSimpleGraphProcessor`, you can also check compliance with `TestUSGraphProcessor`, which runs on the same data as the autograder. See the [details document](docs/details.md) for help with JUnit.

The starter code for `GraphProcessor.java` includes six public methods you must implement. Each is described below and also in javadocs inside of the starter code. While these are the only methods you must implement, you are very much *encouraged to create additional helper methods* where convenient for keeping your code organized and to avoid repetitive code. As a rough rule of thumb, if you find yourself writing a method that is longer than fits on your text editor at once (maybe 30-40 lines), or if you find yourself copy/pasting many lines of code, you might consider abstracting some of that away into a helper method. 

### Instance variables

The code you fork/clone includes the instance variables you'll likely need in `GraphProcessor`. In particular, `myGraph` represents a graph as an adjacency list. While you don't have to use this, all the code discussed in class uses such a graph representation --  we typically used an adjacency list representation, e.g., `Map<Point, List<Point>>` (or `Map<Point, Set<Point>>`) for the `myGraph` instance variable.

### Implement `initialize`

This method takes as input a `FileInputStream`. This input stream should be for a file in the `.graph` format which is described in detail in the [details document](docs/details.md) as is this method `initialize`. You'll need to read that information to see what `initialize` does, how to test it, and perhaps more about how to create instance variables. The code you're given in `GraphDemo.main` calls `initialize` as an example. There is some code provided in `initialize`, see the [details document](docs/details.md.

### Implement `nearestPoint`

In general you may be interested in routing between points that are not themselves vertices of the graph, in which case you need to be able to find the closest points actually on the graph. This method takes a `Point p` as input and returns the vertex in the graph that is closest to `p`, in terms of the straight-line distance calculated by the `distance` method of [the Point class](#the-point-class). Note that the input `p` may not be in the graph. If there are ties, you can break them arbitrarily. You may test correctness with `testNearestPoint()` in JUnit. *Summary: loop over every `Point` that's a vertex in the graph calling `p.distance(v)` for these vertex points `v` and return the minimal/closest `Point`.*

A simple implementation of the `nearestPoint` method should have $`O(N)`$ runtime complexity where $`N`$ is the number of vertices in the graph. Your implementation should be this efficient. 

### Implement `routeDistance`

This method takes a `List<Point> route` representing a path in the graph as input and should calculate the total distance along that path, starting at the first point and adding the distances from the first to the second point, the second to the third point, and so on. Use the `distance` method of [the `Point` class](#the-point-class). You may test correctness using `testRouteDistance()` in JUnit. *Simply iterate through the points accumulating a sum of distances between points in the path.* Note that this method does *NOT* reference the graph, it simply uses `Point.distance`.

The runtime complexity of the method should be linear in `route.size()`, that is, the number of points on the path. 

### Implement `connected`

This method takes two points `p1` and `p2` and should return `true` if the points are connected, meaning there exists a path in the graph (a sequence of edges) from `p1` to `p2`. Otherwise, the method should return `false`, including if `p1` or `p2` are not themselves points in the graph. You may test correctness using `testConnected()` in JUnit.

You will get full credit for correctness if you implement `connected` by searching in the graph, for example, using a depth-first search (DFS) with linear runtime complexity $`O(V+E)`$ where $`V`$ is the number of vertices in the graph and $`E`$ is the number of edges in the graph. You can use breadth first search too.


### Implement `route`

You'll implement Dijkstra's algorithm here -- this method takes two points, `start` and `end`, as input and should return a `List<Point>` representing the **shortest path** from `start` to `end` as a sequence of points. The total distance along a path is the sum of the edge weights, equal to the sum of the straight-line distance between consecutive points (see [implement `routeDistance`](#implement-routedistance)). Note that you must return the path itself, not just the distance along the path. The first point in your returned list should be `start`, and the last point should be `end`. 

If there is no path between `start` and `end`, either because the two points are not in the graph, or because they are the same point, or because they are not connected in the graph, then you should throw an exception, for example: 
```java
throw new IllegalArgumentException("No path between start and end");
```

See the [details document](docs/details.md) for information on implementing Dijkstra's algorithm.

### Optional: implement `findPath`

To find the path between a `start` and `end` vertex using a predecessor map you can write the code
in a helper method. You can see a similar method in [the WordLadde program](https://coursework.cs.duke.edu/201fall24/graphstuff/-/blob/main/src/WordLaddersFull.java) though you must retun a `List<Point>` in P7: Route.


## Running `GraphDemo`

The starter code for `GraphDemo.java` includes a fully functional, interactive route-finding application. See the  [details document](docs/details.md) for information. You can run your program to find a route you're interested in and upload a screen shot of your route for
four engagement points.


## Submitting and Grading

Commit and push your code often as you develop. To submit:

1. Submit your code on gradescope to the autograder. If you worked with a partner, you and your partner will make a **single submission together on gradescope**. Refer to [this document](https://docs.google.com/document/d/e/2PACX-1vREK5ajnfEAk3FKjkoKR1wFtVAAEN3hGYwNipZbcbBCnWodkY2UI1lp856fz0ZFbxQ3yLPkotZ0U1U1/pub) for submitting to Gradescope with a partner. 

The first part, `GraphProcessor`, will be autograded for the correctness and efficiency of the code. Most of the points are for correctness, so focus on that first rather than on efficiency, which you'll likely achieve simply by following directions. 

