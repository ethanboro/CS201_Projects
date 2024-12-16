import java.io.*;
import java.util.*;


/**
 * Models a weighted graph of latitude-longitude points
 * and supports various distance and routing operations.
 * To do: Add your name(s) as additional authors
 * @author Brandon Fain
 * @author Owen Astrachan modified in Fall 2023
 *
 */
public class GraphProcessor {

    // include instance variables here
    int myVertexCount;
    int myEdgeCount;
    Map<Point,List<Point>> myGraph;
    
    public GraphProcessor(){
    myGraph = new HashMap<>();
    }
    
    /**
    * Creates and initializes a graph from a source data
    * file in the .graph format. Should be called
    * before any other methods work.
    * @param file a FileInputStream of the .graph file
    * @throws IOException if file not found or error reading
    */
    
    public void initialize(FileInputStream file) throws IOException {
    Scanner s = new Scanner(file);
    if(s == null){
    throw new IOException("Could not read .graph file");
    }
    String line = s.nextLine();
    String[] data = line.split(" ");
    myVertexCount = Integer.parseInt(data[0]);
    myEdgeCount = Integer.parseInt(data[1]);
    
    List<Point> plist = new ArrayList<>();
    
    for(int k=0; k < myVertexCount; k++){
    line = s.nextLine();
    data = line.split(" ");
    
    // TODO: process data, particularly lat and long
    // ignore name, create Point using Double.parseDouble
    // add point to plist for use with indexes for edges
    // add point to myGraph
    Point p = new Point(Double.parseDouble(data[1]), Double.parseDouble(data[2]));
    plist.add(p);
    myGraph.put(p, new ArrayList<Point>());
    
    }
    for(int k=0; k < myEdgeCount; k++){
    line = s.nextLine();
    data = line.split(" ");
    int firstIndex = Integer.parseInt(data[0]);
    int lastIndex = Integer.parseInt(data[1]);
    myGraph.get(plist.get(firstIndex)).add(plist.get(lastIndex));
    myGraph.get(plist.get(lastIndex)).add(plist.get(firstIndex));
    }
    s.close();
    if (myVertexCount != myGraph.size()) {
    System.err.printf("declared %d verts, but only read %d\n",
    myVertexCount,myGraph.size());
    }
    }
    
    /**
    * Searches for the point in the graph that is closest in
    * straight-line distance to the parameter point p
    * @param p is a point, not necessarily in the graph
    * @return The closest point in the graph to p
    */
    public Point nearestPoint(Point p) {
    Point near = null;
    double min = Double.MAX_VALUE;
    // TODO: find point/vertex in graph closest to p
    // use p.distance(..)
    for(Point point : myGraph.keySet()) {
    if(p.distance(point) < min) {
    min = p.distance(point);
    near = point;
    }
    }
    return near;
    }
    
    
    /**
    * Calculates the total distance along the route, summing
    * the distance between the first and the second Points, 
    * the second and the third, ..., the second to last and
    * the last. Distance returned in miles.
    * @param start Beginning point. May or may not be in the graph.
    * @param end Destination point May or may not be in the graph.
    * @return The distance to get from start to end
    */
    public double routeDistance(List<Point> route) {
    double d = 0.0;
    Point last = route.get(route.size()-1);
    
    if(route.size() <= 1 || route.get(0).equals(last)) {
    throw new IllegalArgumentException("fail");
    }
    for(int i=1; i<route.size(); i++) {
    d = d + route.get(i-1).distance(route.get(i));
    }
    return d;
    }
    
    /**
    * Checks if input points are part of a connected component
    * in the graph, that is, can one get from one to the other
    * only traversing edges in the graph
    * @param p1 one point
    * @param p2 another point
    * @return true if and only if p2 is reachable from p1 (and vice versa)
    */
    public boolean connected(Point p1, Point p2) {
    // TODO: use DFS or BFS to see if p1 connected to p2
    // start with p1, follow all edges, see if p2 reached
    
    Set<Point> visited = new HashSet<>();
    Stack<Point> s = new Stack<Point>();
    s.push(p1);
    while(!s.empty()) {
    Point p = s.pop();
    visited.add(p);
    List<Point> arr = myGraph.get(p);
    for(Point point : arr) {
    if(!visited.contains(point)) {
    s.push(point);
    }
    }
    }
    return visited.contains(p2);
    }
    
    /**
    * Return a list of all points that lead to end
    * @param predMap contains predecessor info such that
    * predMap.get(end) is vertex before end in route
    * assumption: if predMap.get(v) == null, then v is start of route
    * @param end last value in route
    * @return list of points that lead to end
    */
    private List<Point> findPath(Map<Point,Point> predMap, Point end){
    LinkedList<Point> list = new LinkedList<>();
    list.add(end);
    // TODO: add all predecessors to list and return
    
    return list;
    }
    private Point minPoint(Map<Point,Double> map, Set<Point> visited){
    Point minP = null;
    double minD = Double.MAX_VALUE;
    for(Point p : map.keySet()){
    if (! visited.contains(p) && map.get(p) < minD){
    minD = map.get(p);
    minP = p;
    }
    }
    return minP;
    }
    
    
    /**
    * Slow implementation of shortest path
    * @param start
    * @param end
    * @return list of points from start to end
    */
    public List<Point> slowRoute(Point start, Point end){
    
    if (!myGraph.keySet().contains(start) || !connected(start, end)){
    throw new IllegalArgumentException("not connected");
    }
    
    HashMap<Point,Point> predMap = new HashMap<>();
    predMap.put(start,null);
    HashSet<Point> visited = new HashSet<>();
    HashMap<Point,Double> distanceMap = new HashMap<>();
    distanceMap.put(start,0.0);
    
    while (distanceMap.size() < myGraph.size()){
    Point current = minPoint(distanceMap,visited);
    visited.add(current);
    if (current.equals(end)){
    break;
    }
    double d = distanceMap.get(current);
    for(Point p : myGraph.get(current)){
    double weight = p.distance(current);
    double newDistance = d + weight;
    
    if (!visited.contains(p) && newDistance < distanceMap.getOrDefault(p,Double.MAX_VALUE)){
    distanceMap.put(p,newDistance);
    predMap.put(p,current);
    }
    }
    }
    List<Point> list = findPath(predMap,end);
    return list;
    }
    
    
    /**
    * Returns the shortest path, traversing the graph, that begins at start
    * and terminates at end, including start and end as the first and last
    * points in the returned list. If there is no such route, either because
    * start is not connected to end or because start equals end, throws an
    * exception.
    * @param start Beginning point.
    * @param end Destination point.
    * @return The shortest path [start, ..., end].
    * @throws IllegalArgumentException if there is no such route, 
    * either because start is not connected to end or because start equals end.
    */
    public List<Point> route(Point start, Point end) throws IllegalArgumentException {
    if(!myGraph.keySet().contains(start) || start.equals(end) || !myGraph.keySet().contains(end) || !connected(start, end)) {
    throw new IllegalArgumentException("not connected");
    }
    HashMap<Point, Point> predMap = new HashMap<>();
    predMap.put(start, null);
    HashMap<Point, Double> distanceMap = new HashMap<>();
    distanceMap.put(start, 0.0);
    for(Point p : myGraph.keySet()) {
    if(!p.equals(start)) {
    distanceMap.put(p, Double.MAX_VALUE);
    }
    }
    
    Point current = start;
    Comparator<Point> comp = (a,b) -> Double.compare(distanceMap.get(a), distanceMap.get(b));
    PriorityQueue<Point> pq = new PriorityQueue<>(comp);
    pq.add(current);
    while(pq.size() > 0) {
    current = pq.remove();
    if(current.equals(end)) {
    break;
    }
    for(Point adj : myGraph.get(current)) {
    Double newDist = distanceMap.get(current) + adj.distance(current);
    if(newDist < distanceMap.get(adj)) {
    distanceMap.put(adj, newDist);
    predMap.put(adj, current);
    pq.add(adj);
    }
    }
    }
    LinkedList<Point> list = new LinkedList<>();
    
    Point cur = end;
    while(cur != null) {
    if(!list.contains(cur)) {
    list.add(cur);
    cur = predMap.get(cur);
    }
    }
    Collections.reverse(list);
    
    return list;
    }
    public static void main(String[] args) throws FileNotFoundException, IOException {
    String name = "data/usa.graph";
    name = "data/simple.graph";
    GraphProcessor gp = new GraphProcessor();
    gp.initialize(new FileInputStream(name));
    System.out.println("running GraphProcessor");
    }
    
    
    }
    