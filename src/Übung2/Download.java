package Übung2;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import javax.swing.JProgressBar;

public class Download extends Thread {
    private JProgressBar progressBar;
    private int schlafZeit;
    private CountDownLatch latch;
    
    public Download(JProgressBar progressBar, CountDownLatch latch) {
        this.progressBar = progressBar;
        this.latch = latch;
        this.schlafZeit = 10 + new Random().nextInt(90);
    }
    
    @Override
    public void run() {
        for (int i = 1; i <= 100; i++) {
            try {
                Thread.sleep(schlafZeit);

                progressBar.setValue(i);

            } catch (InterruptedException e) {
                // handle exception - ignore
            } finally {
                if(i == 100) {
                    latch.countDown();
                }
            }
        }
    }
}
