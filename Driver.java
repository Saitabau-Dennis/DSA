public class Driver {
    private static String inputFiles[] = {
            "./InputFiles/data-Trivial1.txt", 
            "./InputFiles/data-Trivial2.txt", 
            "./InputFiles/data-Simple.txt", 
            "./InputFiles/data-Merge1.txt", 
            "./InputFiles/data-Merge2.txt", 
            "./InputFiles/data-Complex.txt"};

    /* set enableTest[i] to false to disable the ith test */
    private static Boolean enableTest[] = {
            true,  //"./InputFiles/data-Trivial1.txt" 
            true,  //"./InputFiles/data-Trivial2.txt"
            true,  //"./InputFiles/data-Simple.txt"
            true,  //"./InputFiles/data-Merge1.txt"
            true,  //"./InputFiles/data-Merge2.txt"
            true}; //"./InputFiles/data-Complex.txt"


    public static void main(String[] args) {
        System.out.println();
        TrafficSimulator.printName( );
        System.out.println();
        //Call student solution for each test file:
        if( args.length==0 ) {
            for( int i=0; i<inputFiles.length; i++){
                testStudentCode( inputFiles[i], enableTest[i] );
            }
        }
        else {
            for( int i=0; i<args.length; i++){
                testStudentCode( args[i], true );
            }            
        }
        System.out.println();
        TrafficSimulator.printName( );
        System.out.println();
    }
    
    public static void testStudentCode( String filename, boolean test ) {
        if( test ){
            System.out.println("\n--------------- START OF OUTPUT FOR "+ filename +" ---------------\n");
            System.out.printf("\n\t\t\tREAD DATA FROM FILE\n\n");
            TrafficSimulator T = new TrafficSimulator( filename );
            System.out.printf("\n\t\t\tSIMULATE THE ROAD NETWORK\n\n");
            while( T.hasNextStep() ){
                T.simulateNextStep();
            }
            System.out.printf("\n\t\t\tSUMMARY OF SIMULATION\n\n");
            T.printSummary( );
            System.out.println("\n--------------- END OF OUTPUT FOR "+ filename +" ---------------\n\n");
        }
        else{
            System.out.println("\n\n------------------ SKIPPED TESTING FOR "+ filename +"--------------------\n\n");
        }
    }
}
