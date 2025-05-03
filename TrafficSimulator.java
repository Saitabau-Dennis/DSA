import java.io.*;
import java.util.*;

import graphs.*;

/* TrafficSimulator
 *
 * Stores the traffic simulation data from a given input file 
 */
public class TrafficSimulator
{
    private Graph<Integer> g;                               /* graph representing the road network */
    private HashMap<Pair<Integer,Integer>, Road> roadmap;   /* map a pair of intersections (from, to) to the associated Road */
    /* TODO: Add the additional variables you need.  E.g.: */
    /* current time step */
    /* number of intersections */
    /* number of roads */
    /* array of the roads of the graph in the sequence they were added to the graph */
    /* priority queue of events where the priority represents the time the event will occur */
    /* track the number of cars still in the simulator */
    /* ... */
    
    /* TO BE COMPLETED BY YOU
     * Fill in your name in the function below
     */  
    public static void printName( )
    {
        /* TODO : Fill in your name */
        System.out.println("This solution was completed by:");
        System.out.println("<student name>");
        System.out.println("<student name #2 (if no partner write \"N/A\")>");
    }

    /* TrafficSimulator constructor
     *
     *  Reads the traffic simulation data in the file fileName
     */
    public TrafficSimulator( String fileName )
    {
        /* TODO: Open the fileName */

        /* TODO: Read in the number of intersections and roads */

        System.out.println("The roads:");
        /* TODO: Read the incoming roads */

        System.out.println("The add car events:");
        /* TODO: Read in the add car events */

        System.out.println("The road accident/resolved events:");
        /* TODO: Read in the accident events */  
    }

    /* hasNextStep
     *
     *  Returns true if the simulation needs to run for at least one more step.  Conditions to check:
     *  - There are more events to process
     *  - The number of active cars is > 0 
     */    
    public boolean hasNextStep(  ){
        /* TODO: replace false with your return value */
        return false;
    }

    /* simulateNextStep
     *
     *  Simulate the next step of the simulation.
     */        
    public void simulateNextStep(  ){

        /* TODO: Print the step number */
        
        /* TODO: Check for events for this time step */
          
        /* TODO: Print the contents of each road (use the provided function ``printRoad") */

        /* TODO: (1) - For every road, move cars onto the end of the road */

        /* TODO: (2) - For every road, move cars on the road forward */

        /* TODO: (3) - For every road, move try to move the front car through the intersection */

        /* TODO: add 1 to the number of time steps so far */

    }    

    /* printSummary
     *
     *  Print the average and maximum number of time steps.
     */
    public void printSummary(  ){
        /* TODO */
    }   
}

/* Road
 *
 * Stores the information associated with a road
 */
class Road
{
    public static final boolean PRINT_ROADS = true;  //JESS: Set this to false to skip printing the roads (makes the output much shorter!)
  
    /* intersections this road starts from and moves to */
    int from;
    int to;

    /* information used to record/update whether the light at the end of this road is green or red */
    int greenOn;
    int greenOff;
    int cycleReset;

    /* array of cars associated with this road */
    Car roadContents[];

    /* cars waiting to enter this road */
    Queue<Car> waitingCars;

    /* number of accidents waiting to be resolved */
    int numAccidents;

    /* provided code
     * constructor for a Road
     * 
     * Call this function to create a new road from the data you read in and to print it out
     */
    public Road( int from, int to, int length, int greenOn, int greenOff, int cycleReset ){
        roadContents = new Car[length];

        for( int i=0; i<roadContents.length; i++ )
            roadContents[i] = null;	//Initially each location on the road is empty

        this.from    = from;
        this.to      = to;

        this.greenOn    = greenOn;
        this.greenOff   = greenOff;
        this.cycleReset = cycleReset;
        waitingCars = new LinkedList<Car>();
        this.numAccidents = 0;

        //Print the created Road:
        System.out.printf(this.toString());
    }

    /* provided code
     * Print the road contents and traffic light based on the given curTimeStep
     */  
    public void printRoad( int curTimeStep ){
        if( PRINT_ROADS ){
            System.out.println( "Cars on the road from "+from+" to "+to+":");
            for( int i=0; i<roadContents.length; i++ ){
                if( roadContents[i]==null )
                    System.out.print( "- " );
                else
                    System.out.print( roadContents[i].destination+" " );
            }

            if( greenLight(curTimeStep) )
                System.out.print( "(GREEN light" );
            else
                System.out.print( "(RED light" );

            if( this.numAccidents==0 )
                System.out.println( ")" );
            else if( this.numAccidents>0 )
                System.out.println( " - The number of unresolved accidents = "+this.numAccidents+")" );
            else
                System.out.println( " - ERROR there are "+this.numAccidents+" unresolved accidents)" );
        }
    }

    /* provided code
     * Determine the light color for the given time step
     */   
    public boolean greenLight( int curTimeStep ){   
        return greenOn <= curTimeStep%cycleReset && curTimeStep%cycleReset < greenOff;
    }
    
    /* provided code
     * Output a String of this roads data
     */   
    public String toString( ){
        char state='R';
        String ret = "";
        ret += String.format("Road from %d to %d with length %d (green=%d; red=%d; reset=%d).\n", from, to, roadContents.length, greenOn, greenOff, cycleReset );
        ret += "Cycle number: ";
        for( int i=0; i<25; i++ ){
            ret += String.format("%2d ", i );
        }
        ret += "...\nLight state : ";   
        for( int i=0; i<25; i++ ){  /* print example light cycle: */
            if( greenLight(i) ){
                state = 'G';
            }
            else {
                state = 'R';
            }
            ret += String.format("%2c ", state );
        }
        ret += String.format("...\n\n");
        return ret;
    } 

    /* TODO: Add any other methods you need */
    /* ... */
}


/* EventType
 *
 * Enumerated type of the kinds of events that are possible
 */
enum EventType { 
    ADD_CAR_EVENT, 
    ROAD_ACCIDENT_EVENT, 
    ROAD_RESOLVED_EVENT 
}

/* Event
 *
 * Stores the information associated with an Event
 * These can be "add car" events and "accident" events
 * They are Comparable so you can add them to a Priority Queue
 */
class Event implements Comparable<Event>
{
    Integer priority;     /* The timestep when this should occur on */
    Queue<Car> carsToAdd; /* Queue of cars to be added if this is an ADD_CAR event */
    Road road;            /* Road where the cars should be added or where accident should added/resolved */
    EventType type;       /* The EventType identifies the type of the event */

    /* Constructor for an event
     * 
     * For an accident event pass null for your carsToAdd queue.
     */
    public Event( int priority, Road road, Queue<Car> carsToAdd, EventType type ){
        this.priority = priority;
        this.road = road;
        this.carsToAdd = carsToAdd; 
        this.type = type;
        
        if( type==EventType.ADD_CAR_EVENT ){
            System.out.println("ADD_CAR_EVENT for time step "+priority+" on road from "+road.from+" to "+road.to+".");
            System.out.println("Destinations of added cars: "+carsToAdd);
        }
        else if( type==EventType.ROAD_ACCIDENT_EVENT )
            System.out.println("ROAD_ACCIDENT_EVENT for time step "+priority+" on road from "+road.from+" to "+road.to+".");  
        else if( type==EventType.ROAD_RESOLVED_EVENT )
            System.out.println("ROAD_RESOLVED_EVENT for time step "+priority+" on road from "+road.from+" to "+road.to+".");  
        else
            System.out.println("ERROR - Unknown event type.");
    }

    /*
     * Compares the data in the given Event to the priority in this Event
     * Returns -1 if this Event comes first
     * Returns 0 if these Events have equal priority
     * Returns 1 if the given Event comes first
     */
    public int compareTo( Event event ){
        if( this.priority.compareTo(event.priority)!=0 )
            return this.priority.compareTo(event.priority);
        else
            return this.type.compareTo(event.type); /* break priority ties with the type of the event */
    }
}


/* Car
 *
 * Stores the information associated with a Car
 */
class Car
{
    int start;              /* The starting point of this car (i.e. the from intersection of the road it was originally added to) */
    int next;               /* The next location the car is traveling toward on its path to destination */
    int destination;        /* The destination of this car is ultimately trying to reach */
    int timeAdded;          /* The timestep where the car entered the simulation */
    int lastMovedTimeStep;  /* The timestep where the car last moved */

    public Car( int start, int next, int destination, int timeAdded ){
        this.start = start;
        this.next = next;
        this.destination = destination;
        this.timeAdded = timeAdded;

        this.lastMovedTimeStep = timeAdded; //Will enter on this step
    }
    
    /*
     * Print the destination of the car
     */    
    public String toString( ){
        return ""+destination;
    } 

    /* TODO: Add any other methods you need */
    /* ... */
}
