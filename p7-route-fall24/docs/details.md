## Starter Code and Using Git

**_You should have installed all software (Java, Git, VS Code) before completing this project._** You can find the [directions for installation here](https://coursework.cs.duke.edu/201fall24/resources-201/-/blob/main/installingSoftware.md) (including workarounds for submitting without Git if needed).

We'll be using Git and the installation of GitLab at [coursework.cs.duke.edu](https://coursework.cs.duke.edu). All code for classwork will be kept here. Git is software used for version control, and GitLab is an online repository to store code in the cloud using Git.

**[This document details the workflow](https://coursework.cs.duke.edu/201fall24/resources-201/-/blob/main/projectWorkflow.md) for downloading the starter code for the project, updating your code on coursework using Git, and ultimately submitting to Gradescope for autograding.** We recommend that you read and follow the directions carefully when working on a project! While coding, we recommend that you periodically (perhaps when completing a method or small section) push your changes as explained.

## Details on Git with a Partner

You may find it helpful to begin by reading the Working Together section of the [Git tutorial](https://gitlab.oit.duke.edu/academic-technology/cct/-/tree/master/git) from the Duke Colab. For more, see the [Git tutoraial by Gitlab](https://docs.gitlab.com/ee/tutorials/make_your_first_git_commit.html) including the link to an [extensive video tutorial](https://www.youtube.com/watch?v=4lxvVj7wlZw) if you prefer that.

One person should fork the starter code and then add their partner as a collaborator on the project. Choose Settings>Members>Invite Members. Then use the autocomplete feature to invite your partner to the project as a *maintainer*. Both of you can now clone and push to this project. See the [gitlab documentation here](https://docs.gitlab.com/ee/user/project/members/).

Now you should be ready to clone the code to your local machines.

1. Both students should clone the same repository and import it into VS Code just like previous projects.  
2. After both students have cloned and imported, one person should make a change (you could just write a comment in the code, for example). Commit and push this change. 
3. The other partner will then issue a git pull request. Simply use the command-line (in the same project directory where you cloned the starter code for the project) and type:
```bash
git pull
```
4. If the other partner now opens the project in VS Code again, they should see the modified code with the edit created by the first partner. 
5. You can continue this workflow: Whenever one person finishes work on the project, they commit and push. Whenever anyone starts work on the project, they begin by downloading the current version from the shared online repository using a git pull command.

This process works as long as only one person is editing at a time, and **you always pull before editing** and remember to **commit/push when finished**. If you forget to pull before editing your local code, you might end up working from an old version of the code different than what is in the shared online gitlab repository. If that happens, you may experience an error when you attempt to push your code back to the shared online repository. 

There are many ways to resolve these conflicts, essentially you just need to pick which of the different versions of the code you want to go with. See the [working together Git tutorial](https://gitlab.oit.duke.edu/academic-technology/cct/-/blob/master/git/working_together.md) and the [branching and merging Git tutorial](https://gitlab.oit.duke.edu/academic-technology/cct/-/blob/master/git/branching_merging.md) from the Duke Colab for more information. You can also refer to our [Git troubleshooting document](https://coursework.cs.duke.edu/201-public-documentation/resources-201/-/blob/main/troubleshooting.md#git-faq). 

## Classes Provided to you

You're given several classes, but you'll only need to add code to `GraphProcessor.java` (you may decide to modify lines in `PathBenchmark.java`). However, you should understand the `Point` class and be aware of the other classes below.

### The `Point` Class

You are provided in the starter code with `Point.java` that represents an immutable (meaning it cannot be changed after creation) point on the Earth's surface. Each such point consists of a [latitude](https://en.wikipedia.org/wiki/Latitude), or north-south angle relative to the equator, and a [longitude](https://en.wikipedia.org/wiki/Longitude), or east-west angle relative to the prime meridian. We use the convention whereby **latitudes and longitudes are both measured in degrees between -180.0 and 180.0**, where positive latitudes are for north of the equator and negative latitudes are for south of the equator. Similarly, positive longitudes are for east of the prime meridian, and negative longitudes are for west of the equator. 

Vertices/nodes in the graph we will use to represent the United States highway system will be `Point` objects. You should not need to do edit anything in the `Point` class. However, you will use the various methods that are supplied for you. These methods are described in more detail below.

### Point class Methods

You'll absolutely need to call `distance` and you'll likely call `toString` indirectly to print `Point` objects. Similarly, `hashCode`, `equals`, and perhaps `compare` will be called indirectly when you use `Set<Point>` or `Map<Point, ...>` objects.

- `getLat` and `getLon` are getter methods for returning the values of the private latitude and longitude instance variables. 
- The `distance` method calculates the "straight-line" distance in US miles from one point to another. Note that latitudes and longitudes are *angles* and not x-y coordinates, so this calculation requires trigonometric projection onto a sphere. This can get a little complicated, see [great circle distances](https://en.wikipedia.org/wiki/Great-circle_distance) if you're curious, but you do **not** need to understand or change this math. Please use the `distance` method provided and do not alter or implement a different one, for the sake of consistency with the autograder.
- The `equals` method checks if two points have the same `latitude` and `longitude`.
- The `hashCode` method has been implemented to be consistent with `equals`, and so that you can use `Point` objects in `HashSet`s or as keys in `HashMap`s.
- The `toString` allows you to directly print Point objects.
- The `compareTo` method compares `Point` objects by latitude, then breaks ties by longitude. Note that `Point implements Comparable<Point>`.


### The `Visualize` Class

You do *NOT* need to read/understand the `Visualize` class. It's used in the `GraphDemo` project which is likely the only use in the programs you'll write.

One rewarding part of this project is creating visualizations of the route(s) computed by your algorithms. To do this, you are provided with `Visualize.java` (which, in turn, uses `StdDraw.java`, though you won't need to directly call anything from this). You do not need to edit the `Visualize` class, though you will use it. The Visualize class is described in more detail below.

The constructor to create a `Visualize` object has two parameters. `visFile` should be a file in the `.vis` format:
```
min_longitude max_longitude
min_latitude max latitude
width height
```
where the ranges correspond to the left, right, bottom, and top boundaries respectively of the image onto which the graph will be visualized, and the width and height are the number of pixels in the image to be visualized. You are provided with 3 `.vis` files inside of the `data` folder, corresponding to the three images inside of the `images` folder.

`imageFile` should be a `.png` image with dimensions matching those supplied in the `visFile`. Three such images files are supplied inside of the `images` folder, each of which has a corresponding `.vis` file. These images were taken from [Open Street Map](https://www.openstreetmap.org) for purely educational purposes and are not approved for commercial applications.

The `public` methods of `Visualize` are:
- `drawPoint` draws a single point on the image supplied.
- `drawEdge` draws an edge between two points on the `image` supplied.
- `drawGraph` takes a `List<Point>` and calls `drawPoint` on each, as well as a `List<Point[]>`, and attempts to call `drawEdge` on the index 0 and index 1 elements of each array in the latter list.
- `drawRoute` takes a `List<Point>` and draws each point in the list, connecting each subsequent two points by an edge. **This is the method you are most likely to directly use in visualizing the route(s) you calculate.**

### Visualizing a Route 

As an example, here is screen-capture of `drawRoute` from Miami FL to Seattle WA.
<details>
<summary>Click for Image</summary>
<div align="center">
  <img src="images/miamiseattle.png">
</div>
</details>

## Code in `GraphProcessor.java`

You'll need to implement `initialize` before other code works. As part of understanding `initialize` you'll need to have a high-level understanding of the file format for data representing locations and roads in this project since they'll be the basis of the graph you'll use. But the visualization of routes/cities uses a different image file format described first. You'll choose which of `simple`, `durham` or `usa` you'll use in running `GraphDemo` which calls `GraphProcessor`.

### Graph Data

A graph consists of a number of vertices/nodes and the connections between them (known as edges). Our data represents highway networks, where vertices/nodes are points (see the [`Point` class](#the-point-class)) on the Earth's surface and the edges represent road segments. In this program a graph is **undirected**, meaning we assume every edge can be traversed in either direction. Our graph is also **weighted**, meaning the edges are not all of the same length. **The weight of an edge is the straight-line distance between its endpoints**, see [the `Point` class](#the-point-class) for the `distance` method.

The data we work with was originally pulled from the [METAL project by Dr. James D. Teresco](https://courses.teresco.org/metal/graph-formats.shtml). This data is intended for educational use only and not for any commercial purposes. It has been slightly modified and stored as `.graph` files inside of the `data` folder. Three `.graph` files are supplied, the first two are small and intended for development, testing, and debugging, and the third is much larger and intended for use in the final demo. All three have corresponding `.vis` and `.png` files for use with `Visualize`.

1. `simple.graph` contains a small abstract graph (meaning not a real road network) with ten nodes and ten edges. A visualization is shown below at the left. We recommend using `simple.graph` while developing/debugging your project, as it is much easier to reason about and you don't need to worry much about efficiency.

2. `durham.graph` contains a small but real-world graph, a subset of `usa.graph` that lies within the downtown Durham area. A visualization is shown below at the right. Note that now the graph is imposed on a real image of the road network of Durham instead of an abstract background. We recommend testing on `durham.graph` after you feel comfortable that your code is working on `simple.graph`.

<div align="center">
  <img width="300" src="images/simpleGraph.png">
  <img width="300" src="images/durhamGraph.png">
</div>

3. `usa.graph` contains over 85 thousand vertices and edges representing the (continental) United States Highway Network. This is the network on which you will will likely produce your demo, and for which the efficiency or not of your implementations may become noticeable.

The format of a `.graph` file is described in more detail in the section below.


## Method initialize

The method should read the data from the file and create a representation of the graph, **stored in the instance variables** so that the graph representation is available in all `GraphProcessor` method calls. If the file cannot be opened or does not have the correct format, the method throws an `IOException`, for example:
```java
throw new IOException("Could not read .graph file");
```

### Details on the `.graph` file format

Each `.graph` file represents a graph in the following format:

```
num_vertices num_edges
node0_name node0_latitude node0_longitude
node1_name node1_latitude node1_longitude
...
index_u_edge0 index_v_edge0 optional_edge0_name
index_v_edge1 index_v_edge1 optional_edge1_name
...
```
In other words:
- The first line consists of the number of vertices and edges respectively, space separated.
- The next `num_vertices` lines describe one vertex/node per line, giving its name/label, then its latitude, then its longitude, all space separated. **In your program you'll almost certainly ignore the name.**
- The next `num_edges` lines describe one edge per line, giving the index of its first endpoint and then the index of its second endpoint, space separated. These indices refer to the order in which the vertices/nodes appear in this file (0-indexed). For example, `0 1` would mean there is an edge between the first and second vertices listed above in the file. 
- There may or may not be an edge label/name after the indices for each edge; `simple.graph` and `durham.graph` do not include these labels, but `usa.graph` does, so you will need to be able to handle both cases.

## Finishing `initialize`

You're given a start for this method that reads the file using line-oriented reading. This means each line of the file is read, then split into data that you'll write code to process.  You may find [the `Double.parseDouble()` method](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/Double.html#parseDouble(java.lang.String)) and [the `Integer.parseInt()` method](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/Integer.html#parseInt(java.lang.String)) helpful for converting a String version of a number to the number itself. 

**There are comments in the program you clone indicating where you'll need to add code to process each line.**

Method `initialize` should always be called first before any of the subsequent methods. Make sure to verify that your `initialize` method is working as you expect it to before proceeding, as an incorrect `initialize` method will also cause problems with later methods. You might consider, for example, implementing a `main` method purely for verification purposes, and printing your graph representation of `simple.graph`, comparing to what is visualized in `simpleGraph.png`; see the section on the  `.graph` format for graphs above.

## Using JUnit

You'll need to configure VS Code for using JUnit in this project.

VS Code, by default, runs JUnit tests in a *different* working directory than your project directory. This may cause a problem for JUnit tests on this particular project because the JUnit tests need to call your `initialize` method which opens files using a relative path from the project directory (for example, `data/simple.graph`). The simplest way to fix this is to change the working directory for JUnit with VS Code, which you can do in the following steps.

1. Open your `settings.json` file in VS Code. To do this, open the command palette (`shift` + `command` + `p` on a Mac, or `shift` + `ctrl` + `p` on Windows) and type "settings.json". Select "Preferences: Open Workspace Settings (JSON)."
Edit your `java.test.config` in VS Code, or
2. Edit your `settings.json` to look like the following and then save:

```
{
    "java.test.config": {
        "workingDirectory": "${workspaceFolder}"
        }
}
```

That should be it!

## Dijsktra's algorithm for method `route`

This method will require you to search in the graph itself, and must also take into account the fact that the graph is weighted while searching for shortest paths. You will use Dijkstra's algorithm to accomplish this. You may test correctness using `testRoute()` in JUnit. 

You're given a method `slowRoute` that finds a shortest path very inefficiently. You can use this, for example, in `PathBenchmark` once you've implemented all methods in `GraphProcessor` other than `route` For example, the output of `slowRoute` should be as shown below, though the timings will be different.

```
0.064 secs, 186.979 at 147	 White Plains NY to Boston MA
0.092 secs, 346.013 at 185	 Wilmington NC to Cherokee NC
0.016 secs, 294.669 at 105	 Phoenix AZ to Las Vegas NV
0.011 secs, 455.768 at 177	 Missoula MT to Boise ID
```

The runtime complexity of your implementation should be at most $`O(V+E) \log(V))`$ where $`V`$ is the number of vertices in the graph, $`E`$ is the number of edges in the graph, and we are assuming that each vertex is connected to at most a constant number of other vertices due to the way we use the `PriorityQueue`. Note that the autograder has efficiency tests for full credit. Implementing `route` as described below and in class will achieve this runtime.

### Code for Dijkstra's Algorithm in `route`

The code for implementing Dijkstra's shortest path algorithm was discussed in class, pseudocode can be found in [Wikipedia](https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm) and elsewhere. The code discussed in class is reproduced below in two sections.

You'll need to throw an exception if `start` and `end` are not connected or not in the graph. See the code in `slowRoute` for help with this. The code in that method will also help as you create and initialize two maps: `predMap` and `distanceMap`. You'll have a choice of either adding every vertex (other than start) in the graph as a key to `distanceMap` with a value of `Double.MAX_VALUE` or, alternatively, using
`distanceMap.getOrDefault(v, Double.MAX_VALUE)`. Using the latter approach saves an $O(V)$ operation, but it likely won't make a difference in timing.

<div align="center">
<img width="500" src="images/dijkstra1.png">
</div>

You'll create a `PriorityQueue<Point>` using a `Comparator` based on `distance` values. You are strongly encouraged to use `Double.compare(distanceMap.get(a), distanceMap.get(b))` for the value to return from the `Comparator` you create, or from the lambda expression you use. Since `compare` must return an `int` value, you'll either need a few `if` statements, or you can rely on `Double.compare` returning the appropiate `int` value.

In the code you write, you'll remove a minimal vertex, `current` from the priority queue and check its adjacent
vertices (call each `adj`) to see if the value in the `distanceMap` map for each `adj` vertex is greate than the `distanceMap`  value to `current` + weight of edge `[current,adj]` which is `current.distance(adj)`. If the new distance (from `start` to `adj`) value is less than the existing value, update/replace/store the new distance in the `distanceMap` map and add the adjacent vertex to the priority queue. You'll need to register `predMap.put(adj,current)` as well.

<div align="center">
<img width="500" src="images/dijkstra2.png">
</div>

After the loop (or from the loop body) you'll return the `List<Point>` that represents the shortest path from `start` to `end`. You're encouraged to use a private helper method for this, but you're not required to do so.

## GraphDemo User Input

** You do not need to modify `GraphDemo` ** for this project, but information below describes changes you can make as a challenge so you can interact with the code and data.

An extensive list of latitude-longitude coordinates for US Cities has been included in `data/uscities.csv` (that the file is a `.csv` means each row contains an entry where the values are separated/delimited by commas `,`). This data was obtained from [simplemaps.com](https://simplemaps.com/data/us-cities) for educational use only. 

The code you're given allows the user to input their source and destination cities by selecting them using a standard Java GUI element as illustrated below. You select a city by starting to type and then double-clicking the chosen city.

<div align="center">
  <img width="300" src="images/choosestart.png">
  <img width="300" src="images/chooseend.png">
</div>

1. A user is able to indicate two cities in the United States. Choose two that are reasonably far apart (say, 1,000 miles or more) for the demo. Or choose close cities to help debug.

2. For each of the user indicated points, the demo locates the closest vertex of the road network from `usa.graph`, the large data file containing the highway network of the USA.

3. The demo calculates a route (shortest path) between the two nearest vertices to the cities indicated by the user.

4. The demo indicates the total distance (in miles) of the route calculated.

5. The demo generates a visualization of the route calculated projected onto the map of the USA (see `images/usa.png` and `data/usa.vis`). You can do this using the [`Visualize` class](#the-visualize-class).

As a challenge, write code to find routes between three cities. The image below, for example, routes from Miami FL to San Diego CA and then to Seattle WA. You can write and call code to find such segmented routes as a challenge.

<details>
<summary>Click for Image</summary>
<div align="center">
  <img src="images/routesegments.png">
</div>
</details>

