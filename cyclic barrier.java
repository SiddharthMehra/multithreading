import java.util.concurrent.CyclicBarrier;
import java.util.logging.Level;

class Task implements Runnable {

    private CyclicBarrier barrier;

    public Task(CyclicBarrier barrier) {
        this.barrier = barrier;
    }

    @Override
    public void run() {
        try {
            System.out.println(Thread.currentThread().getName() + "waiting on barrier");
            barrier.await();

            System.out.println(Thread.currentThread().getName() + "has got the barrier");
        } catch (InterrupedException ex) {
            Logger.getLogger(Task.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BrokenBarrierException ex) {
            Logger.getLogger(Task.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
}

public class Main {

    public static void main (String args[]) {

        final CyclicBarrier cb = new CyclicBarrier(3, new Runnable() {
            @Override
            public void run() {
                System.out.println("all parties have arrived, continue execution");
            }
        });

        Thread t1 = new Thread(new Task(cb), "Thread1");
        Thread t2 = new Thread(new Task(cb), "Thread2" );
        Thread t3 = new Thread(new Task(cb), "Thread3");

        t1.start();
        t2.start();
        t3.start();

    }
 }
