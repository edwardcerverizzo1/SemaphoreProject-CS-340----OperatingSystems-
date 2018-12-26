import java.util.Random;

public class Fish extends Project2 {

	private String fishName;
	private Random r = new Random();
	private int sleepTime = r.nextInt(1000);
	
	public void setFishName(int s) {fishName = "" + s;}
	public String getFishName() {return fishName;}
	
	public Fish(int id) {
		setFishName(id);
	}
	
	public void startThread() {
		start();
	}
	
	/*
	public void callwaitForEveryone() {
		msg("This is my name");
		try {Project2.waitForEveryone.acquire(1);} 
		catch (InterruptedException e) {e.printStackTrace();}
	}
	*/
	
	public void lastToArriveMethod() {
		//Show who is in the groups
		Project2.showGroup();
		//Let Mantis know everyone is ready
		msg("We are all here!");
		Project2.signalMantis.release();
	}
	
	public void getOn() {
		
	}
	
	
	public void run() {
		//Proof of life
		Thread.currentThread().setName("Fish " + getFishName());
		msg("Hello! my name is " + Thread.currentThread().getName() + "!");
		//sleep to simulate random arrival and randomized groups
		try {sleep(sleepTime);} 
		catch (InterruptedException e) {e.printStackTrace();}
		
		//Put Fish (itself) into group--Mantis will also be signaled
		Project2.putFishIntoGroups(this);
		
		//Phase1--acquiring semaphore means they are waitting for mantis
		try {
			Project2.phase1.acquire(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		//Getting onto mantis
		msg("getting on Mantis");
		//read more about mantisCoolDown on the bottom of Manaray.java
		Project2.mantisCoolDown.release(1);
		
		//Phase2--Waitting to get to school
		try {
			Project2.phase2.acquire(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		msg("I am getting off of mantis now");
		Project2.mantisCoolDown.release(1);
		
		//Phase3--waitting to go home
		try {
			Project2.phase3.acquire(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		msg("I am waitting to go home");
		//Tell other fish it is time to go home
		Project2.phase3.release(1);
		

		//Let Mantis know that they are leaving
		msg("Have a good day!");
		Project2.releaseMantis.release(1);
		
		
	}
	
}
