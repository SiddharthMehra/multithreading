import java.util.concurrent.CountDownLatch;

// every time a worker completes execution, the counter in the countdown latch is decremented by 1
//counter reaches 0 and informs await. subsequently the master thread is allowed to run


public class Worker extends Thread {

    private CountDownLatch countDownLatch;

    public Worker(CountDownLatch countDownLatch, String name) {
        super(name);
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() 
    {
        System.out.println("Worker" + Thread.currentThread().getName() + "started");
        try {
            Thread.sleep(3000); // simulation of a working thread
        }
        catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        System.out.println("Worker" + Thread.currentThread().getName() + "finished");

        countDownLatch.countDown();
    }
}

public class Master extends Thread {

    public Master(String name) {
        super(name);
    }

    @Override
    public void run() 
    {
        System.out.println("master executed" + Thread.currentThread().getName());

        try {
            Thread.sleep(2000);
        }
        catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}

public class Main 

{
    public static void main (String args[]) throws InterruptedException

    {
        CountDownLatch countdownLatch = new CountDownLatch(2);

        Worker A = new Worker(countdownLatch, "A");
        Worker B = new Worker(countdownLatch, "B");

        A.start();
        B.start();

        countdownLatch.await(); // waits on the counter to become 0

        Master D = new Master("master executed");
        D.start();

    }
}
