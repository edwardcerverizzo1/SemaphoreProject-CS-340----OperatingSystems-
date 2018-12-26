Project empahsied semaphore use.

Biggest challenges: When releasing fish, do not assume that they will release one by one into the next semaphore queue. I had to use another semaphore, mantisCoolDown, to release fish one by one. This was especially critical in keeping fish groups together when loading onto Mantis. Essentally, Mantis would release a fish, and then move into a blocked state during mantisCoolDown.aquire(1) (In Manaray code), and would not be able to move on until the fish released mantisCoolDown (in Fish code).

In Project2.java
Mux demonstrates mutaual exclusion over shared data.
    If you remove this, the varraible counter might malfuction. Go ahead, try it! 

In Fish.java
    Phase 3 semaphore: Fish are released in a "platoon" style. Instead of Mantis releasing all threads at the same time (release(FN);), each fish, once released, will wake up another fish before exiting with mantis waking up only the first fish.
