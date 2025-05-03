package graphs;

public class Edge<V extends Comparable<V>>{
    private V from;
    private V to;
    private double length;

    /*
     * Constructor to build an Edge
     */
    public Edge( V from, V to ){
        this.from = from;
        this.to = to;
        this.length = 1; //Defaults to length 1
    }

    public Edge( V from, V to, double length ){
        this.from = from;
        this.to = to;
        this.length = length;
    }

    public V getFrom(){
        return this.from;
    }

    public V getTo(){
        return this.to;
    }

    public double getLength(){
        return this.length;
    }

    /*
     * Compares the x in the given Pair to the x in this Pair
     * Returns -1 if this Pair comes first
     * Returns 0 if these pairs have equal x values
     * Returns 1 if the given Pair comes first
     */
    public int compareTo( Edge<V> aThat ){
        if( this.from.compareTo( aThat.from ) == 0 )
            return this.to.compareTo( aThat.to );
        return this.from.compareTo( aThat.from );
    }

    public boolean equals( Edge<V> aThat ){
        return this.compareTo( aThat ) == 0;
    }

    @Override 
    @SuppressWarnings("unchecked") //Suppresses warning for cast
    public boolean equals(Object aThat) {
        if (this == aThat) //Shortcut the future comparisons if the locations in memory are the same
            return true;
        if (!(aThat instanceof Edge))
            return false;
        Edge<V> that = (Edge<V>)aThat;
        return this.equals( that ); //Use above equals method
    }
}
