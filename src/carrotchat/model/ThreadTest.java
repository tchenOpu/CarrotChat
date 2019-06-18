/*
 * BENSAID Latifa
 * TCHEN Stephen
 * 3e année RT
 * ENSEA 2017
 */

package carrotchat.model;

    /**
     * ThreadTest
     * Collects carrots in a separate thread and displays how many
     * carrots were picked up, test class that is not used in the
     * application.
     */

public class ThreadTest extends Thread {
    int nombreRamasse = 1;
    @Override
    public void run() {
        while (nombreRamasse < 20) {
            System.out.println(++nombreRamasse + " carottes");
            try {
                sleep(50);
            }
            catch(InterruptedException e) {}
        }
    }
}
