import java.util.concurrent.Semaphore;

public class Project2 extends Thread {
	
	public static long time = System.currentTimeMillis();

	//Final Vars
	final static int GS=3; 	//Default group size 
	final static int FN=13; //Default fish number 
	final static int MC=7;	//Default Mantis capacity 
	
	//Shared Variables
	static Fish[] fishInGroups = new Fish[FN];	//Fish, put into groups
	static Semaphore mux = new Semaphore(1);
	static Semaphore phase1 = new Semaphore(0);
	static Semaphore phase2 = new Semaphore(0);
	static Semaphore phase3 = new Semaphore(0);
	static Semaphore releaseMantis = new Semaphore(0);
	static Semaphore signalMantis = new Semaphore(0);
	static Semaphore mantisCoolDown = new Semaphore(0);
	static Manaray Mantis = new Manaray();
	static int counter = 0;
	
	
	public void msg(String m) {
		System.out.println("["+(System.currentTimeMillis()-time)+"] "+Thread.currentThread().getName()+": "+ m);
	}
	
	public static void putFishIntoGroups(Fish x){
		
		try {mux.acquire(1);}
		catch (InterruptedException e) {e.printStackTrace();}
		
		fishInGroups[counter] = x;
		x.msg("I am element " + counter + " in the array");
		counter++;
		
		
		//Last in
		if(counter == FN) {
			x.lastToArriveMethod();
		}
		
		mux.release();
		
		
	}
	
	public static void showGroup() {
		int groupCount = 0;
		
		for(int i = 0; i < FN; i++) {
			
			//Group header
			if( i%GS == 0 ) {
				System.out.println();
				System.out.println("--Group " + groupCount++ + "--");
			}
			//Show Fish in Group
			System.out.println(Project2.fishInGroups[i].getName());
		}
		
	}
	
	public void run() {
		
		
	}
	
	public static void main(String[] args) {
		
		Fish[] x = new Fish[FN];
		//Instantiate Fish
		for (int i = 0; i < FN; i++) {
			//Make an array of Fish and pass their number as an ID
			x[i] = new Fish(i);
		}		
		
		//Run Mantis
		Mantis.start();
		
		//Run Fish
		for (int i = 0; i < FN; i++) {
			//Start the fish run() code
			x[i].startThread();
		}
		
		
	}
}



