package graphs;

import java.util.*;

public class Graph<V extends Comparable<V>>{
    private ArrayList<V> nodeList;  //Nodes associated with this graph
    private HashMap<V, ArrayList<Edge<V>>> adjacencyList;  //List of edges connecting nodes
    private HashMap<Pair<V,V>,Pair<V,Double>> pathDistance; //For a given pair of Nodes record the distance between them and the next Node on the path between them
    private int numEdges=0;
    private boolean changed=true;

    public Graph()
    {
        this.nodeList = new ArrayList<V>();
        this.adjacencyList = new HashMap<V, ArrayList<Edge<V>>>();
        this.pathDistance = new HashMap<Pair<V,V>,Pair<V,Double>>();
    }

    /* Returns the number of nodes in the graph
     */
    public int numNodes( )
    {
        return nodeList.size();
    }

    /* Returns the number of edges in the graph
     */
    public int numEdges( )
    {
        return numEdges;
    }

    /* Returns the number of outgoing edges from node in the graph
     */
    public int numEdgesFromNode( V from )
    {
        ArrayList<Edge<V>> edges = adjacencyList.get(from);
        return edges.size();
    }

    /* 
     * Adds a new node with the specified data to the graph
     */
    public void addNode( V data )
    {
        //Add the node to the hashMap and associate it with new LL of edges
        if( adjacencyList.get( data )==null ){
            nodeList.add( data );
            adjacencyList.put( data, new ArrayList<Edge<V>>() ); //adds a node containing data if it wasn't already in the graph
            changed = true;
        }
    }

    /* 
     * Returns the index-th Node in the graph
     */
    public V getNode( int index )
    {
        return nodeList.get( index ); /* causes an exception if index<0 || index>=nodeList.size()*/
    }

    /* 
     * Adds an new edge(from, to) 
     */
    public void addEdge( V from, V to, double length )
    {
        if( !nodeList.contains( from ) )
            addNode( from ); //adds the fromNode if it wasn't already in the graph
        if( !nodeList.contains( to ) )
            addNode( to ); //adds the toNode if it wasn't already in the graph

        ArrayList<Edge<V>> edges = adjacencyList.get(from);
        edges.add( new Edge<V>( from, to, length ) );
        numEdges++;
        changed = true;
    }  

    /* 
     * Returns the edge (from,to) from the graph or null if it doesn't exist
     */
    public Edge<V> getEdge( V from, V to )
    {
        ArrayList<Edge<V>> edges = adjacencyList.get(from);
        int index = edges.indexOf( new Edge<V>( from, to ) );

        if( index>=0 )
            return edges.get( index );
        else
            return null;
    }  

    /* 
     * Returns the index-th edge of the from node in the graph
     */
    public Edge<V> getEdge( V from, int index )
    {
        ArrayList<Edge<V>> edges = adjacencyList.get(from);
        return edges.get( index ); /* causes an exception if index<0 || index>=edges.size()*/
    } 

    /* 
     * Returns the directed distance to go from 'from' to 'to'
     * If no path exists this will return Double.MAX_VALUE
     * It will automatically call Dijkstra's algorithm if graph has changed since last call
     */   
    public Double getDistance( V from, V to )
    {
        updatePathDistance();
        Pair<V,Double> p = pathDistance.get( new Pair<V,V>(from,to) );
        return p.y;
    } 

    public V getNextOnPath( V from, V to )
    {
        updatePathDistance();
        Pair<V,Double> p = pathDistance.get( new Pair<V,V>(from,to) );
        return p.x;
    } 

    //Update the pathDistance with all shortest paths
    private void updatePathDistance( ){
        if(changed)
            for( int i=0; i<this.numNodes(); i++ )
                this.dijkstras( this.getNode(i) );
        changed = false;
    }

    private void dijkstras( V start ){
        PriorityQueue<PQNode<V>> pq = new PriorityQueue<PQNode<V>>(); //Fibonacci heap would improve runtime
        HashSet<V> visited = new HashSet<V>();

        //Add an arbitrary node with weight 0 to serve as start node
        pq.add( new PQNode<V>(start, 0, start) );
        for( int i=0; i<this.numNodes(); i++){
            V next = this.getNode(i);
            pq.add( new PQNode<V>(next, Double.MAX_VALUE, next) );
        }

        //Loop until all nodes we found have been visited
        while( !pq.isEmpty() ){
            PQNode<V> pqFrom = pq.remove();

            //If this node is unreachable then no path to it exists and we should set it to Double.MAX_VALUE
            if( !visited.contains(pqFrom.getNodeData()) && pqFrom.getPriority()==Double.MAX_VALUE ){
                visited.add(pqFrom.getNodeData()); //mark as visited
                pathDistance.put( new Pair<V,V>(start,pqFrom.getNodeData()), new Pair<V,Double>(null,Double.MAX_VALUE) );
            }

            //If this is the best path found so far to pqFrom from start
            else if( !visited.contains(pqFrom.getNodeData()) ){
                visited.add(pqFrom.getNodeData()); //mark as visited
                pathDistance.put( new Pair<V,V>(start,pqFrom.getNodeData()), new Pair<V,Double>(pqFrom.getPrevious(),pqFrom.getPriority()) );

                for( int j=0; j<this.numEdgesFromNode( pqFrom.getNodeData() ); j++){
                    Edge<V> e = this.getEdge( pqFrom.getNodeData(), j );
                    if( !visited.contains(e.getTo()) ){//if not yet visited (not required for correctness but speeds up execution by skipping unneeded edges)
                        V prev = pqFrom.getPrevious();
                        if( start.equals(pqFrom.getNodeData()) )
                            prev = e.getTo();
                        PQNode<V> next = new PQNode<V>(e.getTo(), e.getLength()+pqFrom.getPriority(), prev );
                        pq.add( next );
                    }
                }
            }
        }
    }

}
