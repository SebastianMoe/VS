package Übung2;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import javax.swing.JProgressBar;
import java.util.concurrent.CyclicBarrier;

public class Download extends Thread {
    private JProgressBar progressBar;
    private int schlafZeit;
    private CountDownLatch latch;
    private CyclicBarrier barrier;

    public Download(JProgressBar progressBar, CyclicBarrier barrier, CountDownLatch latch) {
        this.progressBar = progressBar;
        this.latch = latch;
        this.barrier = barrier;
        this.schlafZeit = 10 + new Random().nextInt(90);
    }
    
    @Override
    public void run() {
        try {
            latch.await();
            for (int i = 1; i <= 100; i++) {
                try {
                    Thread.sleep(schlafZeit);

                    progressBar.setValue(i);

                } catch (InterruptedException e) {
                    // handle exception - ignore
               }
            }
            barrier.await();
        } catch (Exception e) {
            // handle exception - ignore
        }
    }
}
