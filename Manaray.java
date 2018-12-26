import java.util.concurrent.Semaphore;

public class Manaray extends Project2{

	public static Semaphore mantisHolding = new Semaphore(MC);
	
	
	public Manaray() {
		
	}
	
	public void chooseFish() {
		
	}
	
	public void fishOnBoard() {
		try {mantisHolding.acquire(1);} 
		catch (InterruptedException e) {e.printStackTrace();}
	}
	
	public void run() {
		Thread.currentThread().setName("Mantis");
		
		msg("Hello! I am Mantis!");
		msg("I am now going to wait until all the fish have arrived");
		
		//Wait until the last fish has arrived
		try {Project2.signalMantis.acquire();}
		catch (InterruptedException e) {e.printStackTrace();}
		
		//All fish are gathered, Mantis will allow fish to come aboard
		msg("Climb aboard!");
		//Mantis will continue shuddleing fish to school until all fish are at school
		while(phase1.getQueueLength() != 0) {
			int count = 0;
			//Fish are boarding Mantis (group size fish at a time)
			while(phase2.getQueueLength() + GS <= MC) {
				for(int i = 0; i < GS; i++) {
					//Fish Come aboard
					Project2.phase1.release(1);
					//Mantis only allows fish to come aboard one by one (will be blocked here until fish is onboard)
					try {
						Project2.mantisCoolDown.acquire(1);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
					count++;
					//If there are incompleate groups and Mantis has room for more fish, allow the incompleate groups aboard
					if(phase1.getQueueLength() + phase2.getQueueLength() <= MC && phase1.getQueueLength() < GS) {
						while(phase1.getQueueLength() !=0 ) {
							Project2.phase1.release(1);
							try {
								Project2.mantisCoolDown.acquire(1);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							count++;
						}
					}
					//if all the fish are aboard, go to school
					if(phase1.getQueueLength() == 0)
						break;
				}
				//if all the fish are aboard, go to school
				if(phase1.getQueueLength() == 0)
					break;
			}
			//Release fish at school
			Project2.phase2.release(count);
			
			try {
				Project2.mantisCoolDown.acquire(count);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
		
		//Mantis takes a short rests after transportation
		msg("going to sleep");
		try {sleep(1000);}
		catch (InterruptedException e) {e.printStackTrace();}
		msg("I am awake!");
		
		msg("Time to go home everyone!");
		Project2.phase3.release(1);
		
		Project2.releaseMantis.acquireUninterruptibly(FN);
		
		msg("Everyone has gone, I am leaving now.");
		
	}
}

/* Phase1--	acquire means they are waitting for mantis
 * 			release means they are onboard.
 * 
 * Phase2-- acquire means the fish are waitting to get to school
 * 			release means the fish are at school -- Note: we do not care the order of the fish leaving (we do not need to use mantisCooldown/
 * 
 * Phase3-- Fish are at school and are ready to go home
 * 			Acquire -- wait for Mantis to wake up.
 * 			Release -- platoon policy the fish. And release all to go home.
 * 			
 * 
 * Mantiscooldown--purpose to allow fish to go onto mantis one by one. 
 * 		After a fish is released from phase1, (fish leaves groupup and gets on mantis), mantis must request to release another.
 * 		The purpose of this is to let fish be released one by one, so that they stay all in the same groups.

 * 
 * */



















