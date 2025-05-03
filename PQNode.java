package graphs;

public class PQNode<V extends Comparable<V>> implements Comparable<PQNode<V>>
{
    private V node;
    private Double priority;
    private V prev = null;

    /*
     * Constructors to build a PQNode
     */
    public PQNode( V node, double priority ){
        this.node = node;
        this.priority = priority;
    }

    /*
     * Constructors to build a PQNode
     */
    public PQNode( V node, double priority, V prev ){
        this.node = node;
        this.priority = priority;
        this.prev = prev;
    }

    public Double getPriority( )
    {
        return this.priority;
    }

    public V getNodeData( )
    {
        return this.node;
    }

    public void setPrevious( V prev )
    {
        this.prev = prev;
    }

    public V getPrevious( )
    {
        return this.prev;
    }
    /*
     * Compares the data in the given node to the data in this node
     * Returns -1 if this node comes first
     * Returns 0 if these nodes have equal priority
     * Returns 1 if the given node comes first
     */
    public int compareTo( PQNode<V> node ){
        return this.priority.compareTo(node.priority);
    }

    public boolean equals( PQNode<V> node ){
        return this.node.compareTo( node.node ) == 0;
    }

    @Override 
    @SuppressWarnings("unchecked") //Suppresses warning for cast
    public boolean equals(Object aThat) {
        if (this == aThat) //Shortcut the future comparisons if the locations in memory are the same
            return true;
        if (!(aThat instanceof PQNode))
            return false;
        PQNode<V> that = (PQNode<V>)aThat;
        return this.equals( that ); //Use above equals method
    }

    @Override
    public int hashCode() {
        return node.hashCode(); /* use the hashCode for data stored in this node */
    }
}
